package com.upex;

import com.alibaba.fastjson.JSONObject;
import com.upex.model.MerkleProof;
import com.upex.util.CollectionUtils;
import com.upex.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Verify whether the account assets are included in the Merkle tree published by Bitget
 *
 * @author BitgetLimited
 */
public class BgMerkleValidator {
    /**
     * merkel_tree_bg.json
     **/
    private static final String MERKLE_TREE_BG_FILE_PATH = "merkel_tree_bg.json";

    public static void main(String[] args) {
        System.out.println("Merkel tree path validation start");
        // 获取"merkel_tree_bg.json"文件内容
        String merkleJsonFile = getMerkleJsonFile();
        if (StringUtils.isBlank(merkleJsonFile)) {
            System.out.println("Merkel tree path validation failed, invalid merkle proof file");
            return;
        }

        // 获得默克尔树证明对象
        MerkleProof merkleProof = JSONObject.parseObject(merkleJsonFile, MerkleProof.class);

        // 默克尔树参数验证
        if(validate(merkleProof)){
            System.out.println("Consistent with the Merkle tree root hash. The verification succeeds");
        }else {
            System.out.println("Inconsistent with the Merkle tree root hash. The verification fails");
        }
    }

    /**
     * validate
     * @param merkleProof
     * @return
     * @author BitgetLimited
     * @date 2022/11/25 20:29
     */
    private static boolean validate(MerkleProof merkleProof){

        // self节点不能为空 并且 path节点也不能为空
        if(merkleProof.getSelf() == null || CollectionUtils.isEmpty(merkleProof.getPath())){
            return false;
        }

        // 验证self数据一致性
        if(!merkleProof.getSelf().validateSelf()){
            return false;
        }

        // 验证path参数验证
        if(!merkleProof.getPath().get(0).validatePath()){
            return false;
        }

        if(merkleProof.getPath().get(0).getRole().intValue() == merkleProof.getSelf().getRole().intValue()){
            return false;
        }

        return merkleProof.validate();
    }


    /**
     * get merkel_tree_bg.json content
     *
     * @author BitgetLimited
     * @date 2022/11/25 16:53
     */
    private static String getMerkleJsonFile() {
        StringBuilder builder = new StringBuilder();
        try {
            Files.readAllLines(Path.of(MERKLE_TREE_BG_FILE_PATH), StandardCharsets.UTF_8).forEach(builder::append);
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(MERKLE_TREE_BG_FILE_PATH + " file does not exist");
        }
    }
}


