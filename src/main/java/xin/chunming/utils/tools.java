package xin.chunming.utils;

import org.apache.commons.codec.digest.DigestUtils;
import xin.chunming.bean.LoginBean;
import xin.chunming.bean.InfoBean;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Random;

public class tools {
    /*var str = token + username;
                      str += token + hmd5;
                      str += token + ac_id;
                      str += token + ip;
                      str += token + n;
                      str += token + type;
                      str += token + i; */
    public static String chkSumCaculator(LoginBean lb) {
//        System.out.println(lb);
        /*
        *
        *
        *
        *
        *
        *   b2917719ef582e4ee071662e02c6f4eb5c3ec08bf89459360e8092be789258ac
        * 24198001
        * b2917719ef582e4ee071662e02c6f4eb5c3ec08bf89459360e8092be789258ac
        * a9b098794d85f8b0916498da727abd1fb2917719ef582e4ee071662e02c6f4eb5c3ec08bf89459360e8092be789258ac
        * 14
        * b2917719ef582e4ee071662e02c6f4eb5c3ec08bf89459360e8092be789258ac
        * 10.1.41.154
        * b2917719ef582e4ee071662e02c6f4eb5c3ec08bf89459360e8092be789258ac
        * 200
        * b2917719ef582e4ee071662e02c6f4eb5c3ec08bf89459360e8092be789258ac
        * 1
        * b2917719ef582e4ee071662e02c6f4eb5c3ec08bf89459360e8092be789258ac
        * {SRBX1}OhPyxuFP/CPHyCBkPPVmeeYk1tOibV2mihhYqGfYGqoUKzg+vZ8o8PRm+zS7pK627a3BRM0T/4aNatiN08X1lObWNee2UGJRXHc0HWHtXx6MYZoaN+Q4yFNo9myOlsRpNyJ0f+==
        * */
        String pre = lb.getChallengeToken() + lb.getUsername() +
                lb.getChallengeToken() + lb.getMd5Password() +
                lb.getChallengeToken() + lb.getAc_id() +
                lb.getChallengeToken() + lb.getIp() +
                lb.getChallengeToken() + lb.getN() +
                lb.getChallengeToken() + lb.getType() +
                lb.getChallengeToken() + lb.getInfo();
//        System.out.println(pre);
        return DigestUtils.sha1Hex(pre);
    }

    public static String jqueryBuilder() {

        Random random = new Random();
        // 第一位建议不为 0，以保持严格的 21 位
        long part1 = random.nextLong(100_000_000_000L, 1_000_000_000_000L); // 12位
        long part2 = random.nextLong(100_000_000L, 1_000_000_000L); // 9位

        return String.valueOf(part1) + part2;


    }

    public static String passwordCaculator(String token, String pwd) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacMD5");
        mac.init(new SecretKeySpec(token.getBytes(StandardCharsets.UTF_8), "HmacMD5"));
        byte[] digest = mac.doFinal(pwd.getBytes(StandardCharsets.UTF_8));
        String hex = HexFormat.of().formatHex(digest);
        return hex;
        /*{"username":"24198001","password":"ABC123","ip":"10.1.122.27","acid":"14","enc_ver":"srun_bx1"}*/

    }

    /*{"username":"24198001","password":"ABC123","ip":"10.1.122.27","acid":"14","enc_ver":"srun_bx1"}*/
    /*84edd967a077c5742f0a9c3c0517f0b9b15d754433a52f70b814a948bd2ec430*/
    public static InfoBean copyAngGet(LoginBean lb) {
        System.out.println(lb);
        return new InfoBean(lb.getUsername(), lb.getPassword(), lb.getIp(), lb.getAc_id(), "srun_bx1");
    }
    /*{"username":"24198001","password":"ABC123","ip":"10.1.122.27","acid":"14","enc_ver":"srun_bx1"}*/
}