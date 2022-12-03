package com.upex.model;

import com.upex.util.MerkelTreeUtils;
import com.upex.util.StringUtils;
import org.apache.commons.collections.MapUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
/**
 * TreeNode
 * @author BitgetLimited
 * @date 2022/11/25 23:39
 */
public class TreeNode {

    private String auditId;

    private Map<String, BigDecimal> balances = new HashMap<>();

    private String nonce;

    private String merkelLeaf;

    private Integer level;

    /**
     * role 0-empty， 1-left， 2-right， 3-root
     */
    private Integer role;

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
     * mergeAsset
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
     * validateEqualsBalances
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
