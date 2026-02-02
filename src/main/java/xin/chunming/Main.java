package xin.chunming;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import xin.chunming.bean.LoginBean;

import xin.chunming.utils.tools;

import java.io.*;
import java.util.Date;

public class Main {
    private static String userName;
    private static String password;

    public static void main(String[] args) throws Exception {
        System.out.println("++++++++++猫小咪Srun自动登录++++++++++");
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
            userName = jsonNode.get("username").asText().strip();
            password = jsonNode.get("password").asText().strip();
            if (!userName.equals("填写用户名") && !password.equals("填写密码") && !userName.isEmpty() && !password.isEmpty() && userName != null && password != null) {
                Date date = new Date();
                LoginBean lb = new LoginBean("jQuery" + tools.jqueryBuilder() + "_" + date.getTime(), "login", userName, password,
                        null,
                        "Mac os", "Machintosh", "0", null, null, null, "14",
                        null, "200", "1", String.valueOf(date.getTime()), null);

                if (Login.wanipst(lb) ? (Login.challengeGet(lb) ? Login.login(lb) : false) : false) {
                    System.out.println("成功!");
                } else System.out.println("失败!");


            } else {
                System.out.println("配置文件有问题!");
                boolean delete = configFile.delete();
                if (delete) {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile));
                    bufferedWriter.write("{ \"username\":\"填写用户名\",\"password\":\"填写密码\" }");
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }


            }

        } else {
            System.out.println("配置文件不存在,已生成,请填写srun_config.json! ");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile));
            bufferedWriter.write("{ \"username\":\"填写用户名\",\"password\":\"填写密码\" }");
            bufferedWriter.flush();
            bufferedWriter.close();
        }
//        String ip = "10.1.6.130";
//    get token
        /*
         * */


    }
}
