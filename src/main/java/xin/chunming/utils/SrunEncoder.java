package xin.chunming.utils;

import xin.chunming.bean.InfoBean;
//Thanks to Grok AI 他写的
public class SrunEncoder {
    private static final String PADCHAR = "=";
    private static final String ALPHA = "LVoJPiCN2R8G90yg+hmFHuacZ1OWMnrsSTXkYpUq/3dlbfKwv6xztjI7DeBE45QA";


    public static String build(InfoBean ib, String token) {

        // 手动构造 JSON
        String infoJson = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"ip\":\"%s\",\"acid\":\"%s\",\"enc_ver\":\"%s\"}",
                ib.getUsername(), ib.getPassword(), ib.getIp(), ib.getAcid(), ib.getEnc_ver()
        );
//        System.out.println(ib.getIp());
        System.out.println(infoJson);
        return encodeUserInfo(infoJson, token);

    }

    public static String encodeUserInfo(String info, String token) {
        String encrypted = encode(info, token);

        // 关键修复：将 encrypted 的每个 char 转为 byte (0-255)
        byte[] bytes = new byte[encrypted.length()];
        for (int i = 0; i < encrypted.length(); i++) {
            bytes[i] = (byte) (encrypted.charAt(i) & 0xff);
        }

        String base64Encoded = customBase64Encode(bytes);
        return "{SRBX1}" + base64Encoded;
    }

    private static String encode(String str, String key) {
        if (str.isEmpty()) return "";

        int[] v = s(str, true);
        int[] k = s(key, false);
        if (k.length < 4) {
            int[] newK = new int[4];
            System.arraycopy(k, 0, newK, 0, k.length);
            k = newK;
        }

        int n = v.length - 1;
        int z = v[n];
        int y = v[0];
        long c = 0x86014019L | 0x183639A0L;
        int q = (int) Math.floor(6 + 52.0 / (n + 1));
        long d = 0;

        while (q-- > 0) {
            d = (d + c) & 0xFFFFFFFFL;  // 确保无符号 32 位
            int e = (int) ((d >>> 2) & 3);

            for (int p = 0; p < n; p++) {
                y = v[p + 1];
                int m = (z >>> 5) ^ (y << 2);
                m += ((y >>> 3) ^ (z << 4)) ^ ((int) d ^ y);
                m += k[(p & 3) ^ e] ^ z;
                z = v[p] = v[p] + m & 0xFFFFFFFF;
            }

            y = v[0];
            int m = (z >>> 5) ^ (y << 2);
            m += ((y >>> 3) ^ (z << 4)) ^ ((int) d ^ y);
            m += k[(n & 3) ^ e] ^ z;
            z = v[n] = v[n] + m & 0xFFFFFFFF;
        }

        return l(v, false);
    }

    private static int[] s(String a, boolean b) {
        int len = a.length();
        int size = (len + 3) / 4 + (b ? 1 : 0);
        int[] v = new int[size];

        for (int i = 0; i < len; i += 4) {
            int val = a.charAt(i) & 0xffff;
            if (i + 1 < len) val |= (a.charAt(i + 1) & 0xffff) << 8;
            if (i + 2 < len) val |= (a.charAt(i + 2) & 0xffff) << 16;
            if (i + 3 < len) val |= (a.charAt(i + 3) & 0xffff) << 24;
            v[i >> 2] = val;
        }
        if (b) v[v.length - 1] = len;
        return v;
    }

    private static String l(int[] a, boolean b) {
        int len = a.length;
        int charCount = (len - 1) << 2;
        if (b) {
            int m = a[len - 1];
            if (m < charCount - 3 || m > charCount) return null;
            charCount = m;
        }

        char[] chars = new char[len * 4];
        int pos = 0;
        for (int val : a) {
            chars[pos++] = (char) (val & 0xff);
            chars[pos++] = (char) ((val >>> 8) & 0xff);
            chars[pos++] = (char) ((val >>> 16) & 0xff);
            chars[pos++] = (char) ((val >>> 24) & 0xff);
        }
        return b ? new String(chars, 0, charCount) : new String(chars);
    }

    private static String customBase64Encode(byte[] bytes) {
        if (bytes.length == 0) return "";

        StringBuilder sb = new StringBuilder();
        int i = 0;

        while (i < bytes.length) {
            int b1 = bytes[i++] & 0xff;
            if (i == bytes.length) {
                sb.append(ALPHA.charAt(b1 >>> 2));
                sb.append(ALPHA.charAt((b1 & 0x3) << 4));
                sb.append(PADCHAR).append(PADCHAR);
                break;
            }
            int b2 = bytes[i++] & 0xff;
            if (i == bytes.length) {
                sb.append(ALPHA.charAt(b1 >>> 2));
                sb.append(ALPHA.charAt(((b1 & 0x3) << 4) | ((b2 & 0xf0) >>> 4)));
                sb.append(ALPHA.charAt((b2 & 0xf) << 2));
                sb.append(PADCHAR);
                break;
            }
            int b3 = bytes[i++] & 0xff;
            sb.append(ALPHA.charAt(b1 >>> 2));
            sb.append(ALPHA.charAt(((b1 & 0x3) << 4) | ((b2 & 0xf0) >>> 4)));
            sb.append(ALPHA.charAt(((b2 & 0xf) << 2) | ((b3 & 0xc0) >>> 6)));
            sb.append(ALPHA.charAt(b3 & 0x3f));
        }
        return sb.toString();
    }
}