package xin.chunming.bean;
/*  username: username,
                            password: password,
                            ip: ip,
                            acid: ac_id,
                            enc_ver: enc*/
public class InfoBean {
    private String username;
    private String password;
    private String ip;
    private String acid;
    private String enc_ver;

    public String getEnc_ver() {
        return enc_ver;
    }

    public void setEnc_ver(String enc_ver) {
        this.enc_ver = enc_ver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public InfoBean(String username, String password, String ip, String acid, String enc_ver) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.acid = acid;
        this.enc_ver = enc_ver;
    }
}
