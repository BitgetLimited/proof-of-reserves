package com.upex.model;

import java.util.List;
/**
 * 默克尔树证明实体类
 * @author BitgetLimited
 * @date 2022/11/25 23:40
 */
public class MerkleProof {
    private List<TreeNode> path;
    private TreeNode self;

    public List<TreeNode> getPath() {
        return path;
    }

    public TreeNode getSelf() {
        return self;
    }

    public void setPath(List<TreeNode> path) {
        this.path = path;
    }

    public void setSelf(TreeNode self) {
        this.self = self;
    }

    /**
     * 默克尔树的验证
     * 通过用户提供的path和self正推出一个新的root，与path中的root进行比较
     * @return {@link boolean }
     * @author BitgetLimited
     * @date 2022/11/27 11:54
     */
    public boolean validate() {
        TreeNode newRoot = new MerKelTree().buildMerkelTreeRoot(path, self);
        TreeNode oldRoot = path.get(path.size() - 1);

        System.out.printf("Generator Root BTC balance : %s ,merkel_tree_bg Root BTC balance in file: %s%n", newRoot.getBalances().get("BTC"), oldRoot.getBalances().get("BTC"));
        System.out.printf("Generator Root ETH balance : %s ,merkel_tree_bg Root ETH balance in file: %s%n", newRoot.getBalances().get("ETH"), oldRoot.getBalances().get("ETH"));
        System.out.printf("Generator Root USDT balance : %s ,merkel_tree_bg Root USDT balance in file: %s%n", newRoot.getBalances().get("USDT"), oldRoot.getBalances().get("USDT"));
        System.out.printf("Generator Root MerkelLeaf : %s ,merkel_tree_bg Root MerkelLeaf in file: %s%n", newRoot.getMerkelLeaf(), oldRoot.getMerkelLeaf());

        if (newRoot.getMerkelLeaf().equals(oldRoot.getMerkelLeaf()) && newRoot.validateEqualsBalances(oldRoot) && newRoot.getLevel().equals(oldRoot.getLevel())) {
            return true;
        }
        return false;
    }

}
