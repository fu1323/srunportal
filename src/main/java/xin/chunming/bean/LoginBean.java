package xin.chunming.bean;

public class LoginBean {
    private String callback;
    private String action;
    private String username;
    private String password;
    private String md5Password;
    private String os;
    private String name;

    public String getMd5Password() {
        return md5Password;
    }

    public void setMd5Password(String md5Password) {
        this.md5Password = md5Password;
    }

    public LoginBean(String callback, String action, String username, String password, String md5Password, String os, String name, String doube_stack, String chksum, String info, String mac, String ac_id, String ip, String n, String type, String st_, String challengeToken) {
        this.callback = callback;
        this.action = action;
        this.username = username;
        this.password = password;
        this.md5Password = md5Password;
        this.os = os;
        this.name = name;
        this.doube_stack = doube_stack;
        this.chksum = chksum;
        this.info = info;
        this.mac = mac;
        this.ac_id = ac_id;
        this.ip = ip;
        this.n = n;
        this.type = type;
        this.st_ = st_;
        this.challengeToken = challengeToken;
    }

    private String doube_stack;
    private String chksum;
    private String info;
    private String mac;


    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }



    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoube_stack() {
        return doube_stack;
    }

    public void setDoube_stack(String doube_stack) {
        this.doube_stack = doube_stack;
    }

    public String getChksum() {
        return chksum;
    }

    public void setChksum(String chksum) {
        this.chksum = chksum;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAc_id() {
        return ac_id;
    }

    public void setAc_id(String ac_id) {
        this.ac_id = ac_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSt_() {
        return st_;
    }

    public void setSt_(String st_) {
        this.st_ = st_;
    }

    public String getChallengeToken() {
        return challengeToken;
    }

    public void setChallengeToken(String challengeToken) {
        this.challengeToken = challengeToken;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "callback='" + callback + '\'' +
                ", action='" + action + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", md5Password='" + md5Password + '\'' +
                ", os='" + os + '\'' +
                ", name='" + name + '\'' +
                ", doube_stack='" + doube_stack + '\'' +
                ", chksum='" + chksum + '\'' +
                ", info='" + info + '\'' +
                ", mac='" + mac + '\'' +
                ", ac_id='" + ac_id + '\'' +
                ", ip='" + ip + '\'' +
                ", n='" + n + '\'' +
                ", type='" + type + '\'' +
                ", st_='" + st_ + '\'' +
                ", challengeToken='" + challengeToken + '\'' +
                '}';
    }

    private String ac_id;
    private String ip;
    private String n;
    private String type;
    private String st_;
    private String challengeToken;

}
