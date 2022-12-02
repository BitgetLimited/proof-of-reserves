package com.upex.model;

import com.upex.constants.TreeNodeRoleConstants;
import com.upex.util.MerkelTreeUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默克尔树
 * @author BitgetLimited
 * @date 2022/11/26 00:04
 */
public class MerKelTree {

    /**
     * 构建merkelTree树根节点
     * @param path 待验证路径
     * @param self 自己节点
     * @return {@link TreeNode }
     * @author BitgetLimited
     * @date 2022/11/27 11:56
     */
    public TreeNode buildMerkelTreeRoot(List<TreeNode> path, TreeNode self) {
        if (path.size() <= 1) {
            throw new IllegalArgumentException("Must be at least two leafs to construct a Merkle tree");
        }

        TreeNode parent = createParentTreeNode(path.get(0), self);

        for (int i = 1; i < path.size() - 1; i++) {
            self = parent;
            parent = createParentTreeNode(path.get(i), self);
        }

        return parent;
    }

    /**
     * 创建父节点
     * @param friend 朋友节点
     * @param self 自己节点
     * @return {@link TreeNode }
     * @author BitgetLimited
     * @date 2022/11/27 11:57
     */
    TreeNode createParentTreeNode(TreeNode friend, TreeNode self) {
        TreeNode parent;
        if (TreeNodeRoleConstants.LEFT_NODE.equals(friend.getRole())) {
            // friend 是左节点
            parent = constructInternalNode(friend, self);
        }else {
            // friend 是右节点或者空节点
            parent = constructInternalNode(self, friend);
        }

        return parent;
    }

    /**
     * 构建内部节点
     * @param left 左节点
     * @param right 右节点
     * @return
     */
    private TreeNode constructInternalNode(TreeNode left, TreeNode right) {
        TreeNode parent = new TreeNode();

        if (right == null) {
            right = createEmptyTreeNode(left);
        }
        parent.mergeAsset(left);
        parent.mergeAsset(right);

        // 左节点hash+右节点hash+parent资产+左节点层级
        parent.setLevel(left.getLevel() - 1);
        parent.setMerkelLeaf(MerkelTreeUtils.createMerkelParentLeaf(left, right, parent));
        return parent;
    }

    /**
     * 清除节点资产
     * @param right 右节点
     * @return {@link java.util.Map<java.lang.String,java.math.BigDecimal> }
     * @author BitgetLimited
     * @date 2022/11/27 11:59
     */
    private static Map<String, BigDecimal> clearAssetsMap(TreeNode right) {
        Map<String, BigDecimal> assetsMap = right.getBalances();
        Map<String, BigDecimal> result = new HashMap<>();
        assetsMap.keySet().forEach(coinName -> result.put(coinName, BigDecimal.ZERO));
        return result;
    }

    /**
     * 创建一个空节点
     * 将左节点复制，资产清空
     * @param source 源节点
     * @return {@link TreeNode }
     * @author BitgetLimited
     * @date 2022/11/25 23:40
     */
    private TreeNode createEmptyTreeNode(TreeNode source){
        TreeNode target = new TreeNode();

        target.setAuditId(source.getAuditId());
        target.setNonce(source.getNonce());
        target.setMerkelLeaf(source.getMerkelLeaf());
        target.setLevel(source.getLevel());
        target.setRole(source.getRole());
        target.setEncryptUid(source.getEncryptUid());
        target.setRole(TreeNodeRoleConstants.EMPTY_NODE);
        target.setBalances(clearAssetsMap(target));

        return target;
    }

}
