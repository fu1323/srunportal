//
//
//import okhttp3.*;
//import org.test.utils.tools;
//
//import java.io.IOException;
/*
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {
    public static void main(String[] args) {
        try {
            // 1. 获取当前类所在的 JAR 包或 Class 文件的绝对路径
            String path = ConfigLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

            // 2. 处理路径（如果是 JAR 运行，获取其父目录）
            File jarFile = new File(path);
            String jarDir = jarFile.getParentFile().getAbsolutePath();

            // 3. 拼接配置文件的完整路径
            File configFile = new File(jarDir, "config.properties");

            // 4. 读取配置
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(configFile)) {
                props.load(fis);
                System.out.println("配置加载成功: " + props.getProperty("some.key"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}









*/
//public void login() {
//    OkHttpClient client = new OkHttpClient.Builder()
//            .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
//            .readTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
//            .build();
//
//    // 1. 使用 HttpUrl.Builder 自动处理参数编码
//    HttpUrl url = HttpUrl.parse("http://192.168.88.7/cgi-bin/srun_portal").newBuilder()
//            .addQueryParameter("callback", loginBean.getCallback())
//            .addQueryParameter("action", loginBean.getAction())
//            .addQueryParameter("username", loginBean.getUsername())
//            // 这里 OkHttp 会自动把 {MD5} 编码为 %7BMD5%7D
//            .addQueryParameter("password", "{MD5}" + loginBean.getMd5Password())
//            .addQueryParameter("os", loginBean.getOs())
//            .addQueryParameter("name", loginBean.getName())
//            .addQueryParameter("double_stack", loginBean.getDoube_stack())
//            .addQueryParameter("chksum", tools.chkSumCaculator(loginBean))
//            .addQueryParameter("info", build) // build 里的特殊字符也会被自动处理
//            .addQueryParameter("ac_id", loginBean.getAc_id())
//            .addQueryParameter("ip", loginBean.getIp())
//            .addQueryParameter("n", loginBean.getN())
//            .addQueryParameter("type", loginBean.getType())
//            .addQueryParameter("_", String.valueOf(System.currentTimeMillis()))
//            .build();
//
//    // 2. 构建 Request
//    Request request = new Request.Builder()
//            .url(url)
//            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
//            .header("Accept", "text/javascript, application/javascript, */*; q=0.01")
//            .header("Referer", "http://192.168.88.7/srun_portal_pc?ac_id=" + loginBean.getAc_id())
//            .header("Cookie", "lang=zh-CN")
//            .build();
//
//    // 3. 执行请求
//    try (Response response = client.newCall(request).execute()) {
//        // 无论成功还是 400/500，都可以通过 response.body() 获取内容
//        String responseData = response.body() != null ? response.body().string() : "";
//
//        if (response.isSuccessful()) {
//            System.out.println("登录成功: " + responseData);
//        } else {
//            // 这里会捕获 400, 404, 500 等所有错误情况的响应体
//            System.err.println("服务器返回错误状态码: " + response.code());
//            System.err.println("错误响应体: " + responseData);
//        }
//    } catch (IOException e) {
//        System.err.println("网络连接失败: " + e.getMessage());
//    }
//}
//
//
//
//
//
//
//
///*
//package org.test.utils;
//
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.util.Arrays;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.util.Arrays;
//
//
//public class InfoXXteaDecoder {
//
//
//    private static final String PREFIX = "{SRBX1}";
//    private static final String ALPHABET =
//            "LVoJPiCN2R8G90yg+hmFHuacZ1OWMnrsSTXkYpUq/3dlbfKwv6xztjI7DeBE45QA";
//
//    public static String decodeInfo(String info, String token) {
//        if (!info.startsWith(PREFIX)) {
//            throw new IllegalArgumentException("Invalid prefix");
//        }
//
//        // 1. 去前缀
//        String base64Part = info.substring(PREFIX.length());
//
//        // 2. Base64（自定义字母表）解码
//        byte[] encryptedBytes = decode(base64Part, ALPHABET);
//
//        // 3. XXTEA 解密
//        byte[] decrypted = decrypt(encryptedBytes, token);
//
//        // 4. 转字符串（先按 raw byte）
//        return new String(decrypted, java.nio.charset.StandardCharsets.UTF_8);
//    }
//
//*/
///*
//* class CustomBase64 {
//
//    public static byte[] decode(String input, String alphabet) {
//        int[] reverse = new int[256];
//        java.util.Arrays.fill(reverse, -1);
//
//        for (int i = 0; i < alphabet.length(); i++) {
//            reverse[alphabet.charAt(i)] = i;
//        }
//        reverse['='] = 0;
//
//        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
//        int[] buf = new int[4];
//        int bufLen = 0;
//
//        for (char c : input.toCharArray()) {
//            if (c == '=') buf[bufLen++] = 0;
//            else if (reverse[c] >= 0) buf[bufLen++] = reverse[c];
//            else continue;
//
//            if (bufLen == 4) {
//                out.write((buf[0] << 2) | (buf[1] >> 4));
//                if (input.charAt(out.size() * 4 / 3 + 2) != '=')
//                    out.write((buf[1] << 4) | (buf[2] >> 2));
//                if (input.charAt(out.size() * 4 / 3 + 3) != '=')
//                    out.write((buf[2] << 6) | buf[3]);
//                bufLen = 0;
//            }
//        }
//        return out.toByteArray();
//    }
//}*//*
//
//public static byte[] decode(String input, String alphabet) {
//    int[] reverse = new int[256];
//    java.util.Arrays.fill(reverse, -1);
//
//    for (int i = 0; i < alphabet.length(); i++) {
//        reverse[alphabet.charAt(i)] = i;
//    }
//    reverse['='] = 0;
//
//    java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
//    int[] buf = new int[4];
//    int bufLen = 0;
//
//    for (char c : input.toCharArray()) {
//        if (c == '=') buf[bufLen++] = 0;
//        else if (reverse[c] >= 0) buf[bufLen++] = reverse[c];
//        else continue;
//
//        if (bufLen == 4) {
//            out.write((buf[0] << 2) | (buf[1] >> 4));
//            if (input.charAt(out.size() * 4 / 3 + 2) != '=')
//                out.write((buf[1] << 4) | (buf[2] >> 2));
//            if (input.charAt(out.size() * 4 / 3 + 3) != '=')
//                out.write((buf[2] << 6) | buf[3]);
//            bufLen = 0;
//        }
//    }
//    return out.toByteArray();
//}
//
//
//    private static final int DELTA = 0x9E3779B9;
//
//    public static byte[] decrypt(byte[] data, String key) {
//        if (data.length == 0) return data;
//
//        int[] v = toIntArray(data, true);   // ★ true
//        int[] k = toIntArray(
//                key.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1),
//                false
//        );
//
//        if (k.length < 4) k = java.util.Arrays.copyOf(k, 4);
//
//        int n = v.length - 1;
//        int rounds = 6 + 52 / (n + 1);
//        int sum = rounds * 0x9E3779B9;
//
//        while (sum != 0) {
//            int e = (sum >>> 2) & 3;
//            for (int p = n; p > 0; p--) {
//                int z = v[p - 1];
//                int y = v[p];
//                v[p] -= mx(sum, y, z, p, e, k);
//            }
//            int z = v[n];
//            int y = v[0];
//            v[0] -= mx(sum, y, z, 0, e, k);
//            sum -= 0x9E3779B9;
//        }
//
//        return toByteArray(v, true);  // ★ true
//    }
//
//    private static int mx(int sum, int y, int z, int p, int e, int[] k) {
//        return ((z >>> 5 ^ y << 2)
//                + (y >>> 3 ^ z << 4))
//                ^ ((sum ^ y)
//                + (k[(p & 3) ^ e] ^ z));
//    }
//
//    private static int[] toIntArray(byte[] data, boolean includeLength) {
//        int length = data.length;
//        int n = ((length & 3) == 0) ? (length >>> 2) : ((length >>> 2) + 1);
//        int[] result;
//
//        if (includeLength) {
//            result = new int[n + 1];
//            result[n] = length;
//        } else {
//            result = new int[n];
//        }
//
//        ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
//        for (int i = 0; i < length; i += 4) {
//            result[i >>> 2] = buffer.getInt(i);
//        }
//        return result;
//    }
//
//    private static byte[] toByteArray(int[] data, boolean includeLength) {
//        int length = data.length << 2;
//        if (includeLength) {
//            int m = data[data.length - 1];
//            length = m;
//        }
//
//        ByteBuffer buffer = ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN);
//        for (int i = 0; i < data.length && buffer.remaining() >= 4; i++) {
//            buffer.putInt(data[i]);
//        }
//        return buffer.array();
//    }
//}*/
