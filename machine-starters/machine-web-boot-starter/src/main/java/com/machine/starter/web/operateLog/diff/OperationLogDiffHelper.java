package com.machine.starter.web.operateLog.diff;

import cn.hutool.core.util.StrUtil;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.InitialValueChange;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.core.diff.changetype.ReferenceChange;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.custom.BigDecimalComparatorWithFixedEquals;
import org.javers.core.diff.changetype.container.ContainerChange;
import org.javers.core.diff.changetype.container.ContainerElementChange;
import org.javers.core.diff.changetype.container.ElementValueChange;
import org.javers.core.diff.changetype.container.ValueAdded;
import org.javers.core.diff.changetype.container.ValueRemoved;
import org.javers.core.diff.changetype.map.EntryAdded;
import org.javers.core.diff.changetype.map.EntryChange;
import org.javers.core.diff.changetype.map.EntryRemoved;
import org.javers.core.diff.changetype.map.EntryValueChange;
import org.javers.core.diff.changetype.map.KeyValueChange;
import org.javers.core.metamodel.object.GlobalId;

import java.math.BigDecimal;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * 基于 JaVers 的字段级变更 diff 工具，输出可直接序列化为 JSON 的统一结构：
 * <pre>
 * 值变更     : "user.name": {"old": "...", "new": "..."}
 * 新增字段   : "user.createdAt": {"old": null, "new": "..."}     // InitialValueChange 归一化
 * 集合变更   : "user.roles": {"added": [...], "removed": [...],
 *                             "changed": {"0": {"old": ..., "new": ...}}, ["truncated": true]}
 * Map 变更   : "user.props": {"added": [{"k": v}], "removed": [...],
 *                             "changed": {"k": {"old": ..., "new": ...}}, ["truncated": true]}
 * 引用变更   : "user.leader": {"old": "Type/id", "new": "Type/id"}  // 用 GlobalId，不落整对象
 * 新增对象   : "Type/id": {"old": null, "new": <快照>}             // key 取被影响对象 GlobalId
 * 删除对象   : "Type/id": {"old": <快照>, "new": null}
 * </pre>
 */
public final class OperationLogDiffHelper {

    /**
     * 默认忽略的审计/系统字段（支持全路径/叶子字段名/前缀路径匹配）
     */
    private static final Set<String> DEFAULT_IGNORE_FIELDS = Set.of(
            "id", "createBy", "createTime", "updateBy", "updateTime", "deleted");

    /**
     * 单个 diff 的顶层变更数上限，防止一次变更产生海量日志
     */
    private static final int MAX_DIFF_CHANGES = 8192;

    /**
     * 单个集合/Map 的 added/removed/changed 元素数上限
     */
    private static final int MAX_COLLECTION_ELEMENTS = 8192;

    private static final String TRUNCATED = "truncated";

    private static final Javers JAVERS = buildJavers();

    private OperationLogDiffHelper() {
    }

    private static Javers buildJavers() {
        return JaversBuilder.javers()
                .withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
                .registerValue(BigDecimal.class, new BigDecimalComparatorWithFixedEquals())
                .build();
    }

    public static Map<String, Object> diff(Object before,
                                           Object after,
                                           String[] ignoreFields) {
        if (before == null && after == null) {
            return null;
        }
        Set<String> ignore = resolveIgnore(ignoreFields);
        return diffByJavers(before, after, ignore);
    }

    private static Set<String> resolveIgnore(String[] ignoreFields) {
        Set<String> ignore = new HashSet<>(DEFAULT_IGNORE_FIELDS);
        if (ignoreFields != null) {
            for (String field : ignoreFields) {
                if (StrUtil.isNotBlank(field)) {
                    ignore.add(field.trim());
                }
            }
        }
        return ignore;
    }

    private static Map<String, Object> diffByJavers(Object before,
                                                    Object after,
                                                    Set<String> ignore) {
        Diff javersDiff = compare(before, after);
        Map<String, Object> result = new LinkedHashMap<>();
        for (Change change : javersDiff.getChanges()) {
            String path = resolvePropertyPath(change);
            if (isIgnored(path, ignore)) {
                continue;
            }
            Object converted = convertChange(change);
            if (converted == null) {
                continue;
            }
            if (result.size() >= MAX_DIFF_CHANGES) {
                result.put(TRUNCATED, true);
                break;
            }
            result.put(path, converted);
        }
        return result.isEmpty() ? null : result;
    }

    private static Diff compare(Object before, Object after) {
        if (before instanceof Collection<?> left && after instanceof Collection<?> right) {
            Class<?> itemClass = inferCollectionItemClass(left, right);
            if (itemClass != null && !isSimpleValue(itemClass)) {
                return compareCollections(left, right, itemClass);
            }
        }
        return JAVERS.compare(before, after);
    }

    private static Class<?> inferCollectionItemClass(Collection<?> left, Collection<?> right) {
        for (Collection<?> c : List.of(left, right)) {
            for (Object item : c) {
                if (item != null) {
                    return item.getClass();
                }
            }
        }
        return null;
    }

    /**
     * 简单值类型（Primitive/String/Number/Boolean/日期/枚举等）不需要深度比较。
     */
    private static boolean isSimpleValue(Class<?> type) {
        return type.isPrimitive() || type.isEnum()
                || CharSequence.class.isAssignableFrom(type)
                || Number.class.isAssignableFrom(type)
                || Boolean.class == type
                || Character.class == type
                || Date.class.isAssignableFrom(type)
                || Temporal.class.isAssignableFrom(type)
                || UUID.class == type;
    }

    @SuppressWarnings("unchecked")
    private static <T> Diff compareCollections(Collection<?> left, Collection<?> right, Class<?> itemClass) {
        return JAVERS.compareCollections((Collection<T>) left, (Collection<T>) right, (Class<T>) itemClass);
    }

    private static String resolvePropertyPath(Change change) {
        if (change instanceof PropertyChange propertyChange) {
            return propertyChange.getPropertyNameWithPath();
        }
        if (change instanceof NewObject || change instanceof ObjectRemoved) {
            GlobalId globalId = change.getAffectedGlobalId();
            return globalId == null ? "object" : globalId.value();
        }
        return "object";
    }

    /**
     * 忽略匹配优先级：完整路径（user.createTime）＞ 叶子字段名（createTime）＞ 前缀路径（user.roles）。
     */
    private static boolean isIgnored(String path, Set<String> ignore) {
        if (path == null || ignore.isEmpty()) {
            return false;
        }
        if (ignore.contains(path)) {
            return true;
        }
        int dot = path.lastIndexOf('.');
        if (dot >= 0 && ignore.contains(path.substring(dot + 1))) {
            return true;
        }
        for (String prefix : ignore) {
            if (path.startsWith(prefix + ".")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 把 Javers Change 转换为统一的 {old,new}/{added,removed} 结构。
     */
    private static Object convertChange(Change change) {
        if (change instanceof InitialValueChange initialValueChange) {
            // 新增对象的属性：left 恒为 ""，归一化为 null，语义更准确
            return valuePair(null, initialValueChange.getRight());
        }
        if (change instanceof ValueChange valueChange) {
            return valuePair(valueChange.getLeft(), valueChange.getRight());
        }
        if (change instanceof ReferenceChange referenceChange) {
            return valuePair(idOf(referenceChange.getLeft()), idOf(referenceChange.getRight()));
        }
        if (change instanceof ContainerChange<?> containerChange) {
            return convertContainerChange(containerChange);
        }
        if (change instanceof KeyValueChange<?> keyValueChange) {
            return convertMapChange(keyValueChange);
        }
        if (change instanceof NewObject newObject) {
            return newObject.getAffectedObject().map(obj -> valuePair(null, obj)).orElse(null);
        }
        if (change instanceof ObjectRemoved objectRemoved) {
            return objectRemoved.getAffectedObject().map(obj -> valuePair(obj, null)).orElse(null);
        }
        return null;
    }

    /**
     * 集合/数组变更
     */
    private static Object convertContainerChange(ContainerChange<?> containerChange) {
        List<Object> added = new ArrayList<>();
        List<Object> removed = new ArrayList<>();
        Map<String, Object> changed = new LinkedHashMap<>();
        int total = containerChange.getChanges().size();
        for (ContainerElementChange elementChange : containerChange.getChanges()) {
            if (elementChange instanceof ValueAdded valueAdded) {
                addWithCap(added, valueAdded.getAddedValue());
            } else if (elementChange instanceof ValueRemoved valueRemoved) {
                addWithCap(removed, valueRemoved.getRemovedValue());
            } else if (elementChange instanceof ElementValueChange elementValueChange) {
                putChangedWithCap(changed, String.valueOf(elementValueChange.getIndex()),
                        valuePair(elementValueChange.getLeftValue(), elementValueChange.getRightValue()));
            }
        }
        return assembleContainerResult(added, removed, changed, total > MAX_COLLECTION_ELEMENTS);
    }

    /**
     * Map 变更
     */
    private static Object convertMapChange(KeyValueChange<?> keyValueChange) {
        List<Object> added = new ArrayList<>();
        List<Object> removed = new ArrayList<>();
        Map<String, Object> changed = new LinkedHashMap<>();
        int total = keyValueChange.getEntryChanges().size();
        for (EntryChange entryChange : keyValueChange.getEntryChanges()) {
            if (entryChange instanceof EntryAdded entryAdded) {
                addWithCap(added, entryMap(entryAdded.getKey(), entryAdded.getValue()));
            } else if (entryChange instanceof EntryRemoved entryRemoved) {
                addWithCap(removed, entryMap(entryRemoved.getKey(), entryRemoved.getValue()));
            } else if (entryChange instanceof EntryValueChange entryValueChange) {
                putChangedWithCap(changed, String.valueOf(entryValueChange.getKey()),
                        valuePair(entryValueChange.getLeftValue(), entryValueChange.getRightValue()));
            }
        }
        return assembleContainerResult(added, removed, changed, total > MAX_COLLECTION_ELEMENTS);
    }

    private static Object assembleContainerResult(List<Object> added, List<Object> removed,
                                                  Map<String, Object> changed, boolean truncated) {
        if (added.isEmpty() && removed.isEmpty() && changed.isEmpty()) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<>();
        if (!added.isEmpty()) {
            map.put("added", added);
        }
        if (!removed.isEmpty()) {
            map.put("removed", removed);
        }
        if (!changed.isEmpty()) {
            map.put("changed", changed);
        }
        if (truncated) {
            map.put(TRUNCATED, true);
        }
        return map;
    }

    /**
     * 引用变更时取 GlobalId 的字符串表示（如 "Employee/Frodo"），
     * 不落整个对象，避免循环引用、大字段与懒加载问题；null 表示引用被置空。
     */
    private static String idOf(Object globalId) {
        return globalId == null ? null : String.valueOf(globalId);
    }

    /**
     * 构造单个键值对；兼容 null key/value（{@code Map.of} 遇 null 会抛 NPE）。
     */
    private static Map<String, Object> entryMap(Object key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(String.valueOf(key), value);
        return map;
    }

    private static Object valuePair(Object oldValue, Object newValue) {
        if (Objects.equals(oldValue, newValue)) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("old", oldValue);
        map.put("new", newValue);
        return map;
    }


    private static <T> void addWithCap(List<T> target, T value) {
        if (target.size() < MAX_COLLECTION_ELEMENTS) {
            target.add(value);
        }
    }

    private static void putChangedWithCap(Map<String, Object> target, String key, Object value) {
        if (value != null && target.size() < MAX_COLLECTION_ELEMENTS) {
            target.put(key, value);
        }
    }

}
