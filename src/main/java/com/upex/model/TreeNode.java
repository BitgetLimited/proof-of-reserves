package com.upex.model;

import com.upex.util.MerkelTreeUtils;
import com.upex.util.StringUtils;
import org.apache.commons.collections.MapUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
/**
 * 默克尔树节点
 * @author BitgetLimited
 * @date 2022/11/25 23:39
 */
public class TreeNode {
    /**
     * 审计id
     */
    private String auditId;

    /**
     * 资产集合
     */
    private Map<String, BigDecimal> balances = new HashMap<>();

    /**
     * 随机值
     */
    private String nonce;

    /**
     * 默克尔树节点值
     */
    private String merkelLeaf;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 角色 0-空节点， 1-左节点， 2-右节点， 3-根节点
     */
    private Integer role;

    /**
     * 加密用户id
     */
    private String encryptUid;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public Map<String, BigDecimal> getBalances() {
        return balances;
    }

    public void setBalances(Map<String, BigDecimal> balances) {
        this.balances = balances;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getMerkelLeaf() {
        return merkelLeaf;
    }

    public void setMerkelLeaf(String merkelLeaf) {
        this.merkelLeaf = merkelLeaf;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getEncryptUid() {
        return encryptUid;
    }

    public void setEncryptUid(String encryptUid) {
        this.encryptUid = encryptUid;
    }

    /**
     * 验证self节点
     * @return {@link boolean }
     * @author BitgetLimited
     * @date 2022/11/25 23:44
     */
    public boolean validateSelf() {
        String merkelNodeLeaf = MerkelTreeUtils.createMerkelNodeLeaf(this);
        return merkelLeaf.equals(merkelNodeLeaf);
    }

    /**
     * 验证Path节点数据
     * @return {@link boolean }
     * @author BitgetLimited
     * @date 2022/11/25 23:45
     */
    public boolean validatePath() {
        if (!validateBalances() || StringUtils.isBlank(merkelLeaf) || role < 0 || role > 3 || level < 1) {
            return false;
        }
        return true;
    }

    /**
     * 验证资产集合
     * @return {@link boolean }
     * @author BitgetLimited
     * @date 2022/11/25 22:30        
     */
    public boolean validateBalances() {
        if(MapUtils.isEmpty(balances)){
            return false;
        }

        for (BigDecimal decimal : balances.values()) {
            if(decimal == null){
                return false;
            }
        }
        return true;
    }

    /**
     * 合并资产
     * @param childNode
     */
    public void mergeAsset(TreeNode childNode){
        if (childNode == null) {
            return;
        }
        childNode.balances.forEach((coinName,amount)->{
            BigDecimal oldValue = balances.get(coinName);
            if (oldValue != null) {
                BigDecimal newValue = oldValue.add(amount);
                balances.put(coinName,newValue);
            }else{
                balances.put(coinName, amount);
            }
        });
    }

    /**
     * 验证资产集合
     * @return {@link boolean }
     * @author BitgetLimited
     * @date 2022/11/25 22:30
     */
    public boolean validateEqualsBalances(TreeNode oldRoot) {
        Map<String, BigDecimal> oldBalances = oldRoot.getBalances();
        for (Map.Entry<String, BigDecimal> entry : balances.entrySet()) {
            if(oldBalances.get(entry.getKey()).compareTo(entry.getValue()) != 0){
                return false;
            }
        }
        return true;
    }
}
