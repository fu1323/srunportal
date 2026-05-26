package xin.chunming;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xin.chunming.bean.LoginBean;

import xin.chunming.utils.tools;

import java.io.*;
import java.net.SocketTimeoutException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fusesource.jansi.Ansi.ansi;

@Slf4j
public class Main {
    private static String userName;
    private static String password;
    private static String rootURL;
    private static String testip;
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
            .build();
    static LoginBean lb = new LoginBean();

    public static void main(String[] args) throws Exception {
        Login.setClient(okHttpClient);

        System.out.println(ansi().fgBrightCyan().a("++++++++++猫小咪Srun自动登录(考研贺岁版)V2.0++++++++++").reset());
        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

        // 2. 处理路径（如果是 JAR 运行，获取其父目录）
        File jarFile = new File(path);
        String jarDir = jarFile.getParentFile().getAbsolutePath();

        // 3. 拼接配置文件的完整路径
        File configFile = new File(jarDir, "srun_config.json");
        if (configFile.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
            StringBuilder configJson = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                configJson.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            bufferedReader.close();
            JsonNode jsonNode = objectMapper.readTree(configJson.toString());
            try {


                userName = jsonNode.get("username").asText().strip();
                password = jsonNode.get("password").asText().strip();
                rootURL = jsonNode.get("root_url").asText().strip();
                testip = jsonNode.get("test_ip").asText().strip();
            } catch (NullPointerException e) {
                //   System.out.println("配置文件有问题,请删除后重新生成");
                log.info("配置文件有问题,请删除后重新生成");
            }
//            System.out.println(password);
//            macAddr = jsonNode.get("mac_addr").asText().strip();
            check(configFile, okHttpClient);

        } else {
            //System.out.println(ansi().fgRed().a("配置文件不存在,已生成,请填写srun_config.json! ").reset());
            log.info("配置文件不存在,已生成,请填写srun_config.json! ");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile));
            bufferedWriter.write("{ \"username\":\"此处请填写用户名\",\"password\":\"此处请填写密码(后面root_url为认证服务器地址,按实际修改 默认192.168.88.7)\",\"root_url\":\"http://192.168.88.7\",\"test_ip\":\"223.5.5.5\"}");
            bufferedWriter.flush();
            bufferedWriter.close();
        }
//        String ip = "10.1.6.130";
//    get token
        /*
         * */


    }

    private static void dologin(File configFile, OkHttpClient okhttp) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        if (!userName.equals("填写用户名") && !password.equals("填写密码") && !userName.isEmpty() && !password.isEmpty() && userName != null && password != null) {
            Date date = new Date();
            lb.setCallback("jQuery" + tools.jqueryBuilder() + "_" + date.getTime());
            lb.setAction("login");
            lb.setUsername(userName);
            lb.setPassword(password);
            lb.setOs("Mac os");
            lb.setName("Machintosh");
            lb.setDoube_stack("0");
            lb.setSt_(String.valueOf(date.getTime()));
            lb.setN("200");
            lb.setType("1");
            lb.setRootURL(rootURL);
            lb.setTestip(testip == null ? "1.2.3.4" : testip);

            if (Login.wanipst(lb) ? (Login.challengeGet(lb) ? Login.login(lb) : false) : false) {
                // System.out.println(ansi().fgBrightCyan().a("成功!").reset());
                log.info("成功!");
            } else {
                // System.out.println(ansi().fgRed().a("失败!").reset());
                log.info("失败!");
            }

        } else {
            // System.out.println(ansi().fgRed().a("配置文件有问题!").reset());
            log.info("配置文件有问题!");
            boolean delete = configFile.delete();
            if (delete) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile));
                bufferedWriter.write("{ \"username\":\"此处请填写用户名\",\"password\":\"此处请填写密码(后面root_url为认证服务器地址,按实际修改 默认192.168.88.7)\",\"root_url\":\"192.168.88.7\"}");
                bufferedWriter.flush();
                bufferedWriter.close();
            }


        }
    }

    private static void check(File configFile, OkHttpClient okhttp) throws Exception {

        Request request = new Request.Builder()
                .url("http://" + testip)  //
                .build();
        Response execute = null;
        try {
            execute = okhttp.newCall(request).execute();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
        // System.out.println(execute.request().isHttps());
        // System.out.println(execute.request().url().host());
        String s = execute.body().string();
        execute.close();

        Pattern compile = Pattern.compile("location\\.href=\"http.*\"");
        Matcher matcher = compile.matcher(s);

        Pattern compile2 = Pattern.compile("url=/.*\">");
        Matcher matcher2 = compile2.matcher(s);
        if (matcher.find()) {
            String group = matcher.group();
            //System.out.println(group);
            Login.setTmpurl(group.replace("location.href=", "").replace("\"", ""));

            if (group.contains(rootURL)) {
                log.info("检测到Portal认证要求 准备认证");
                String url = (execute.request().isHttps() ? "https://" : "http://") + execute.request().url().host() + "/";
                rootURL = url;


                //  System.out.println("检测到Portal认证要求 准备认证");
                dologin(configFile, okhttp);


            }

        }
        //System.out.println(s);
        else {
            if (matcher2.find()) {
                String group1 = matcher2.group();
                String path = group1.replace("url=/", "").replace("&amp;", "&").replace("\">", "");
                String url = (execute.request().isHttps() ? "https://" : "http://") + execute.request().url().host() + "/";
                rootURL = url;
                log.info("检测到https:将自动使用此地址认证" + rootURL);
                dologin(configFile, okhttp);

            }
            //  System.out.println(s);
            // System.out.println("正常");


            else {
                log.info("正常");
            }

        }
    }


}



