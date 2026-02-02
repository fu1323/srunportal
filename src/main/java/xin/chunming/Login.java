package xin.chunming;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import xin.chunming.bean.LoginBean;
import xin.chunming.utils.SrunEncoder;
import xin.chunming.utils.tools;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Login {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
            .build();

    private static Date date = new Date();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean wanipst(LoginBean loginBean) throws IOException {
// 1. 使用 HttpUrl.Builder 自动处理参数编码
        HttpUrl url = HttpUrl.parse("http://192.168.88.7//cgi-bin/rad_user_info").newBuilder()
                .addQueryParameter("callback", loginBean.getCallback())
                .addQueryParameter("ip", loginBean.getIp())
                .addQueryParameter("_", String.valueOf(date.getTime()))
                .build();

        // 2. 构建 Request
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Referer", "http://192.168.88.7/srun_portal_pc?ac_id=" + loginBean.getAc_id() + "&nas_ip=10.0.50.2&theme=pro&wlanuserip=" + loginBean.getIp() + "&wlanusermac=9E-B0-81-11-24-42")
                .header("Cookie", "lang=zh-CN")
                .header("Connection", "keep-alive")
                .header("Priority", "u=3, i")
                .build();

        try (Response response = client.newCall(request).execute()) {
            // 无论成功还是 400/500，都可以通过 response.body() 获取内容
            String s = response.body() != null ? response.body().string() : "";
            if (response.isSuccessful()) {
                s = s.replace(loginBean.getCallback() + "(", "").replace(")", "");
                JsonNode jsonNode = objectMapper.readTree(s);
                String wanip = jsonNode.get("client_ip").asText();
                System.out.println("服务器返回当前ip: "+wanip);
                String st = jsonNode.get("st").asText();
                loginBean.setIp(wanip);
                loginBean.setSt_(st);
                return true;
            } else {
                System.out.println("获取ip 发生问题: \n" + s);
                return false;
            }
        }

    }


    /*http://192.168.88.7/cgi-bin/get_challenge?callback=jQuery1124042957073484962227_1766906811756&username=23198050&ip=10.1.6.130&_=1766906811758*/
    public static boolean challengeGet(LoginBean loginBean) throws IOException {


        //User-Agent	Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/26.1 Safari/605.1.15//
        //Accept	text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01
        // X-Requested-With	XMLHttpRequest
        // Referer	http://192.168.88.7/srun_portal_pc?ac_id=14&nas_ip=10.0.50.2&theme=pro&wlanuserip=10.1.6.130&wlanusermac=9E-B0-81-B0-44-42
        // Accept-Language	zh-HK,zh-Hant;q=0.9
        // Priority	u=3, i
        // Accept-Encoding	gzip, deflate
        // Cookie lang=zh-CN
        // Connection	keep-alive

//            ObjectMapper objectMapper = new ObjectMapper();

////            Date date = new Date();
//            String s1 = "http://192.168.88.7/cgi-bin/get_challenge?callback=" + loginBean.getCallback() +
//                    "&username=" + loginBean.getUsername() + "&ip=" + loginBean.getIp() + "&_=" + date.getTime();
        HttpUrl url = HttpUrl.parse("http://192.168.88.7//cgi-bin/get_challenge").newBuilder()
                .addQueryParameter("callback", loginBean.getCallback())
                .addQueryParameter("username", loginBean.getUsername())
                .addQueryParameter("ip", loginBean.getIp())
                .addQueryParameter("_", String.valueOf(date.getTime()))
                .build();

        // 2. 构建 Request
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Referer", "http://192.168.88.7/srun_portal_pc?ac_id=" + loginBean.getAc_id() + "&nas_ip=10.0.50.2&theme=pro&wlanuserip=" + loginBean.getIp() + "&wlanusermac=9E-B0-81-11-24-42")
                .header("Cookie", "lang=zh-CN")
                .header("Connection", "keep-alive")
                .header("Priority", "u=3, i")
                .build();

        try (Response response = client.newCall(request).execute()) {
            // 无论成功还是 400/500，都可以通过 response.body() 获取内容
            String s = response.body() != null ? response.body().string() : "";
            if (response.isSuccessful()) {
                s = s.replace(loginBean.getCallback() + "(", "").replace(")", "");
                JsonNode jsonNode = objectMapper.readTree(s);
                String token = jsonNode.get("challenge").asText();
                System.out.println("服务器返回token: " + token);
                loginBean.setChallengeToken(token);
                return true;
            } else {
                System.out.println("获取token 发生问题: \n" + s);
                return false;
            }

        }


    }


    public static boolean login(LoginBean loginBean) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        loginBean.setMd5Password(tools.passwordCaculator(loginBean.getChallengeToken(), loginBean.getPassword()));
        System.out.println("计算info加密值....");
        String build = SrunEncoder.build(tools.copyAngGet(loginBean), loginBean.getChallengeToken());
        loginBean.setInfo(build);
/* String s1 = "http://192.168.88.7/cgi-bin/srun_portal?callback=" + loginBean.getCallback() +
                    "&action=" + loginBean.getAction() + "&username=" + loginBean.getUsername() + "&password=" + "{MD5}" + loginBean.getMd5Password() + "&os=" + loginBean.getOs() + "&name="
                    + loginBean.getName() + "&double_stack=" + loginBean.getDoube_stack() + "&chksum=" +
                    tools.chkSumCaculator(loginBean) +
                    "&info=" + build +
                    "&ac_id=" + loginBean.getAc_id() + "&ip="
                    + loginBean.getIp() + "&n=" + loginBean.getN() + "&type=" + loginBean.getType() + "&_=" + date.getTime();*/
        HttpUrl url = HttpUrl.parse("http://192.168.88.7//cgi-bin/srun_portal").newBuilder()
                .addQueryParameter("callback", loginBean.getCallback())
                .addQueryParameter("action", loginBean.getAction())
                .addQueryParameter("username", loginBean.getUsername())
                .addQueryParameter("password", "{MD5}" + loginBean.getMd5Password())
                .addQueryParameter("os", loginBean.getOs())
                .addQueryParameter("name", loginBean.getName())
                .addQueryParameter("double_stack", loginBean.getDoube_stack())
                .addQueryParameter("chksum", tools.chkSumCaculator(loginBean))
                .addQueryParameter("info", build)
                .addQueryParameter("ac_id", loginBean.getAc_id())
                .addQueryParameter("ip", loginBean.getIp())
                .addQueryParameter("n", loginBean.getN())
                .addQueryParameter("type", loginBean.getType())
                .addQueryParameter("_", String.valueOf(date.getTime()))
                .build();

        // 2. 构建 Request
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Referer", "http://192.168.88.7/srun_portal_pc?ac_id=" + loginBean.getAc_id() + "&nas_ip=10.0.50.2&theme=pro&wlanuserip=" + loginBean.getIp() + "&wlanusermac=9E-B0-81-11-24-42")
                .header("Cookie", "lang=zh-CN")
                .header("Connection", "keep-alive")
                .header("Priority", "u=3, i")
                .build();

        try (Response response = client.newCall(request).execute()) {
            // 无论成功还是 400/500，都可以通过 response.body() 获取内容
            String s = response.body() != null ? response.body().string() : "";
            if (response.isSuccessful()) {
                System.out.println(s);
                if (s.contains("Login is successful")) {
                    System.out.println("认证成功!");
                }else if (s.contains("Password is error")) {
                    System.out.println("认证失败 用户名密码错误!");

                }
                return true;
            } else {
                System.out.println("登陆 发生问题: \n" + s);
                return false;
            }

        }


    }
}
/*jQuery304048378468105094047_1767178414753({"client_ip":"10.1.41.154","ecode":"E2553","error":"login_error","error_msg":"E2553: Password is error."
,"online_ip":"10.1.41.154","res":"login_error","srun_ver":"SRunCGIAuthIntfSvr V1.18 B20221130","st":1767178414})*/


