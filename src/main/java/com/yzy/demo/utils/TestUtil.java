package com.yzy.demo.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;

@Slf4j
public class TestUtil {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String signature = "07F280A335EE8B3663B19062BFC2B85ABAC51CF615C5AD744B7240FCBCC262A5";
        String timestamp = "1619593503";
        String nonce = "eyJVc2VySWQiOiJrdTVsNjZvMmE2eHMzaGg3djJnZGphIiwiRGV2aWNlSWQiOiIwNzhhYzgzOGU1MTE5N2UyIiwiZXJyY29kZSI6MCwiZXJybXNnIjoib2siLCJ1c2VydHlwZSI6NX0=";
        String uid = "ku5l66o2a6xs3hh7v2gdja";
        String uinfo = "";
        String ext = "";

        // 验签
        String sign = EncodeUtil.encodeBySHA256(timestamp + "PemjfsiXQuaIjL5mtk2Thdoh8ZOLmeZi" + nonce + "," + uid + ","
                + uinfo + "," + ext + timestamp).toUpperCase();
        if (signature.equals(sign)) {
            log.info("签名通过");
        }
    }
}
