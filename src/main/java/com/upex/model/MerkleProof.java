package com.upex.model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * MerkleProof
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
     * validate
     * A new root is being introduced through the path and self provided by the user. Compare with the root in the path
     * @return {@link boolean }
     * @author BitgetLimited
     * @date 2022/11/27 11:54
     */
    public boolean validate() {
        TreeNode newRoot = new MerKelTree().buildMerkelTreeRoot(path, self);
        TreeNode oldRoot = path.get(path.size() - 1);


        // 动态遍历所有币种
        Set<String> allCoins = new LinkedHashSet<>();
        allCoins.addAll(newRoot.getBalances().keySet());
        allCoins.addAll(oldRoot.getBalances().keySet());
        for (String coin : allCoins) {
            System.out.printf("Generator Root %s balance : %s ,merkel_tree_bg Root %s balance in file: %s%n",coin, newRoot.getBalances().get(coin),coin, oldRoot.getBalances().get(coin));
        }
        if (newRoot.getMerkelLeaf().equals(oldRoot.getMerkelLeaf()) && newRoot.validateEqualsBalances(oldRoot) && newRoot.getLevel().equals(oldRoot.getLevel())) {
            return true;
        }
        return false;
    }

}
