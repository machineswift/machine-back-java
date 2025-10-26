package com.machine.sdk.common.tool;

import com.machine.sdk.common.model.tree.TreeNode;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.util.*;

import static com.machine.sdk.common.constant.CommonHrmConstant.Department.DEPARTMENT_ROOT_PARENT_ID;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.DATA_ORGANIZATION_ROOT_PARENT_ID;


public class TreeUtil {

    private static final int DEFAULT_ARRAY_SIZE = 2048;
    private static final int SMALL_LIST_THRESHOLD = 16;
    private static final int MIN_CHILD_CAPACITY = 8;

    /**
     * 构建树结构，并按 sort 属性排序
     */
    public static <T extends TreeNode<T>> List<T> buildTree(Collection<? extends T> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return new ArrayList<>();
        }

        int size = nodes.size();
        @SuppressWarnings("unchecked")
        T[] nodeArray = (T[]) new TreeNode[size];
        String[] idArray = new String[size];
        List<T> roots = new ArrayList<>(size / 8 + 1);

        // 单次遍历完成初始化
        int idx = 0;
        for (T node : nodes) {
            nodeArray[idx] = node;
            idArray[idx] = node.getId();
            node.setChildren(new ArrayList<>(Math.min(MIN_CHILD_CAPACITY, size / 4)));
            idx++;
        }

        // 构建树结构
        for (int i = 0; i < size; i++) {
            T node = nodeArray[i];
            String parentId = node.getParentId();

            if (isRoot(parentId)) {
                roots.add(node);
                continue;
            }

            // 快速查找父节点
            for (int j = 0; j < size; j++) {
                if (parentId.equals(idArray[j])) {
                    nodeArray[j].getChildren().add(node);
                    break;
                }
            }
        }

        // 优化的排序处理
        if (!roots.isEmpty()) {
            sortTreeNodes(roots, false);
        }

        return roots;
    }

    /**
     * 递归遍历树结构，收集所有节点
     */
    public static <T extends TreeNode<T>> List<T> collectAllNodes(T root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<T> nodes = new ArrayList<>(DEFAULT_ARRAY_SIZE);
        nodes.add(root);

        Deque<T> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            T node = stack.pop();
            List<T> children = node.getChildren();
            if (children != null && !children.isEmpty()) {
                for (int i = children.size() - 1; i >= 0; i--) {
                    T child = children.get(i);
                    if (child != null) {
                        nodes.add(child);
                        stack.push(child);
                    }
                }
            }
        }
        return nodes;
    }

    /**
     * 查找指定ID的节点
     */
    public static <T extends TreeNode<T>> T findNode(T root, String id) {
        if (root == null || id == null) {
            return null;
        }

        if (id.equals(root.getId())) {
            return root;
        }

        // 使用数组模拟栈，避免LinkedList开销
        @SuppressWarnings("unchecked")
        T[] stack = (T[]) new TreeNode[DEFAULT_ARRAY_SIZE];
        int top = 0;
        stack[top++] = root;

        while (top > 0) {
            T node = stack[--top];
            List<T> children = node.getChildren();
            if (children != null) {
                for (int i = children.size() - 1; i >= 0; i--) {
                    T child = children.get(i);
                    if (child != null) {
                        if (id.equals(child.getId())) {
                            return child;
                        }
                        if (top < stack.length) {
                            stack[top++] = child;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 过滤树形数据
     */
    public static <T extends TreeNode<T>> T filterTree(T root,
                                                       Set<String> idSet) {
        if (root == null || idSet == null || idSet.isEmpty()) {
            return null;
        }

        if (!idSet.contains(root.getId()) &&
                (root.getChildren() == null || root.getChildren().isEmpty())) {
            return null;
        }

        T newRoot = createNewRoot(root);
        List<T> children = root.getChildren();
        if (children == null || children.isEmpty()) {
            return idSet.contains(newRoot.getId()) ? newRoot : null;
        }

        List<T> filteredChildren = new ArrayList<>(Math.min(children.size(), idSet.size()));
        boolean hasValidChild = false;

        for (T child : children) {
            T filteredChild = filterTree(child, idSet);
            if (filteredChild != null) {
                hasValidChild = true;
                filteredChildren.add(filteredChild);
            }
        }

        if (!hasValidChild && !idSet.contains(newRoot.getId())) {
            return null;
        }

        newRoot.setChildren(filteredChildren);
        return newRoot;
    }

    /**
     * 排序
     */
    public static <T extends TreeNode<T>> void sortTreeNodes(List<T> nodes, boolean asc) {
        // 递归排序子节点
        for (T node : nodes) {
            List<T> children = node.getChildren();
            if (children != null) {
                sortTreeNodes(children, asc);
            }
        }

        if (nodes.size() <= 1) return;

        // 小数组使用插入排序
        if (nodes.size() <= SMALL_LIST_THRESHOLD) {
            insertionSort(nodes, asc);
            return;
        }

        // 大数组使用快速排序
        quickSort(nodes, 0, nodes.size() - 1, asc);
    }

    /**
     * 获取指定节点集合的所有父级节点（包括根节点）
     */
    public static <T extends TreeNode<T>> Set<T> getAllParentNodes(T root,
                                                                   Set<String> targetIds) {
        if (root == null || targetIds == null || targetIds.isEmpty()) {
            return new HashSet<>();
        }

        // 构建ID到节点的映射和父子关系映射
        Map<String, T> idToNode = new HashMap<>();
        Map<String, String> childToParent = new HashMap<>();

        Deque<T> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            T node = stack.pop();
            idToNode.put(node.getId(), node);
            List<T> children = node.getChildren();

            if (children != null) {
                for (int i = children.size() - 1; i >= 0; i--) {
                    T child = children.get(i);
                    if (child != null) {
                        childToParent.put(child.getId(), node.getId());
                        stack.push(child);
                    }
                }
            }
        }

        // 收集所有父级节点
        Set<T> result = new HashSet<>();
        for (String targetId : targetIds) {
            T node = idToNode.get(targetId);
            if (node != null) {
                result.add(node);
                String parentId = childToParent.get(targetId);
                while (parentId != null) {
                    T parentNode = idToNode.get(parentId);
                    if (parentNode != null) {
                        result.add(parentNode);
                        parentId = childToParent.get(parentId);
                    } else {
                        break;
                    }
                }
            }
        }

        return result;
    }

    private static <T extends TreeNode<T>> void insertionSort(List<T> nodes, boolean asc) {
        for (int i = 1; i < nodes.size(); i++) {
            T key = nodes.get(i);
            int j = i - 1;
            while (j >= 0 && compareNodes(nodes.get(j), key, asc) > 0) {
                nodes.set(j + 1, nodes.get(j));
                j--;
            }
            nodes.set(j + 1, key);
        }
    }

    private static <T extends TreeNode<T>> void quickSort(List<T> nodes,
                                                          int left,
                                                          int right,
                                                          boolean asc) {
        while (left < right) {
            if (right - left <= SMALL_LIST_THRESHOLD) {
                insertionSort(nodes.subList(left, right + 1), asc);
                break;
            }

            int pivot = partition(nodes, left, right, asc);

            if (pivot - left < right - pivot) {
                quickSort(nodes, left, pivot - 1, asc);
                left = pivot + 1;
            } else {
                quickSort(nodes, pivot + 1, right, asc);
                right = pivot - 1;
            }
        }
    }

    private static <T extends TreeNode<T>> int partition(List<T> nodes,
                                                         int left,
                                                         int right,
                                                         boolean asc) {
        // 三数取中值作为基准
        int mid = (left + right) >>> 1;
        T pivot = nodes.get(mid);
        swap(nodes, mid, right);

        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (compareNodes(nodes.get(j), pivot, asc) <= 0) {
                swap(nodes, ++i, j);
            }
        }
        swap(nodes, i + 1, right);
        return i + 1;
    }

    private static <T extends TreeNode<T>> void swap(List<T> nodes, int i, int j) {
        T temp = nodes.get(i);
        nodes.set(i, nodes.get(j));
        nodes.set(j, temp);
    }

    private static <T extends TreeNode<T>> int compareNodes(T a, T b, boolean asc) {
        Long sortA = a.getSort();
        Long sortB = b.getSort();
        if (asc) {
            return sortA.compareTo(sortB);
        } else {
            return sortB.compareTo(sortA);
        }
    }

    private static boolean isRoot(String parentId) {
        return parentId == null
                || DATA_ORGANIZATION_ROOT_PARENT_ID.equals(parentId)
                || DEPARTMENT_ROOT_PARENT_ID.equals(parentId);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private static <T extends TreeNode<T>> T createNewRoot(T root) {
        Class<? extends TreeNode<?>> clazz = (Class<? extends TreeNode<?>>) root.getClass();
        TreeNode<?> newTreeNode = clazz.getDeclaredConstructor().newInstance();
        T newRoot = (T) newTreeNode;
        BeanUtils.copyProperties(root, newRoot, "children");
        return newRoot;
    }
}

