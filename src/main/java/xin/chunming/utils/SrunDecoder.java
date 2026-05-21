package xin.chunming.utils;

public class SrunDecoder {
    private static final String PADCHAR = "=";
    private static final String ALPHA = "LVoJPiCN2R8G90yg+hmFHuacZ1OWMnrsSTXkYpUq/3dlbfKwv6xztjI7DeBE45QA";

    public static String decodeUserInfo(String info, String token) {
        // 1. 去掉前缀 {SRBX1}
        if (info.startsWith("{SRBX1}")) {
            info = info.substring(7);
        }

        // 2. 自定义 Base64 解码
        byte[] decryptedBytes = customBase64Decode(info);

        // 3. 将 byte 还原为 String (中间加密形态)
        char[] chars = new char[decryptedBytes.length];
        for (int i = 0; i < decryptedBytes.length; i++) {
            chars[i] = (char) (decryptedBytes[i] & 0xff);
        }
        String encryptedStr = new String(chars);

        // 4. XXTEA 解密
        return decode(encryptedStr, token);
    }

    private static String decode(String str, String key) {
        if (str.isEmpty()) return "";

        int[] v = s(str, false);
        int[] k = s(key, false);
        if (k.length < 4) {
            int[] newK = new int[4];
            System.arraycopy(k, 0, newK, 0, k.length);
            k = newK;
        }

        int n = v.length - 1;
        int z = v[n], y = v[0];
        long c = 0x86014019L | 0x183639A0L;
        int q = (int) Math.floor(6 + 52.0 / (n + 1));
        long d = (long) q * c & 0xFFFFFFFFL; // 初始 delta 为 q * c

        while (d != 0) {
            int e = (int) ((d >>> 2) & 3);

            // 从后往前解密
            for (int p = n; p > 0; p--) {
                z = v[p - 1];
                int m = (z >>> 5) ^ (y << 2);
                m += ((y >>> 3) ^ (z << 4)) ^ ((int) d ^ y);
                m += k[(p & 3) ^ e] ^ z;
                y = v[p] = (v[p] - m) & 0xFFFFFFFF;
            }

            z = v[n];
            int m = (z >>> 5) ^ (y << 2);
            m += ((y >>> 3) ^ (z << 4)) ^ ((int) d ^ y);
            m += k[(0 & 3) ^ e] ^ z;
            y = v[0] = (v[0] - m) & 0xFFFFFFFF;

            d = (d - c) & 0xFFFFFFFFL;
        }

        return l(v, true); // true 表示根据最后一个元素还原长度
    }

    // 辅助方法 s 和 l 与原代码逻辑对应
    private static int[] s(String a, boolean b) {
        int len = a.length();
        int size = (len + 3) / 4 + (b ? 1 : 0);
        int[] v = new int[size];
        for (int i = 0; i < len; i += 4) {
            int val = a.charAt(i) & 0xff;
            if (i + 1 < len) val |= (a.charAt(i + 1) & 0xff) << 8;
            if (i + 2 < len) val |= (a.charAt(i + 2) & 0xff) << 16;
            if (i + 3 < len) val |= (a.charAt(i + 3) & 0xff) << 24;
            v[i >> 2] = val;
        }
        if (b) v[v.length - 1] = len;
        return v;
    }

    private static String l(int[] a, boolean b) {
        int len = a.length;
        int charCount = (len - 1) << 2;
        if (b) {
            int m = a[len - 1]; // XXTEA 存储的原始长度
            if (m < 0 || m > charCount) return "";
            charCount = m;
        }
        char[] chars = new char[charCount];
        for (int i = 0; i < charCount; i++) {
            chars[i] = (char) ((a[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
        }
        return new String(chars);
    }

    private static byte[] customBase64Decode(String str) {
        // 建立反向索引表
        int[] index = new int[256];
        for (int i = 0; i < ALPHA.length(); i++) {
            index[ALPHA.charAt(i)] = i;
        }

        // 移除末尾填充
        int pad = 0;
        if (str.endsWith("==")) pad = 2;
        else if (str.endsWith("=")) pad = 1;

        int len = str.length();
        byte[] result = new byte[(len * 3 / 4) - pad];
        int i = 0, j = 0;

        while (i < len - pad) {
            int c1 = index[str.charAt(i++)];
            int c2 = index[str.charAt(i++)];
            result[j++] = (byte) ((c1 << 2) | (c2 >> 4));
            if (j >= result.length) break;

            int c3 = index[str.charAt(i++)];
            result[j++] = (byte) ((c2 << 4) | (c3 >> 2));
            if (j >= result.length) break;

            int c4 = index[str.charAt(i++)];
            result[j++] = (byte) ((c3 << 6) | c4);
        }
        return result;
    }
}
