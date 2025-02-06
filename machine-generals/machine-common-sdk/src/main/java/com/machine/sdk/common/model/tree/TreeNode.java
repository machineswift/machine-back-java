package com.machine.sdk.common.model.tree;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.*;

@Data
@Schema
public class TreeNode<T extends TreeNode<T>> {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "子数据")
    private List<T> children = new ArrayList<>();

    /**
     * 判断当前节点及其子树中是否有节点的ID在给定集合中
     *
     * @param idSet 目标ID集合
     * @return true表示存在，false表示不存在
     */
    public boolean hasChildInList(Set<String> idSet) {
        if (idSet.contains(this.id)) {
            return true;
        }

        for (T child : this.children) {
            if (child.hasChildInList(idSet)) {
                return true;
            }
        }

        return false;
    }
}
