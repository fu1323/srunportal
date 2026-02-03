一个深澜校园网一键认证的工具   java实现
api分析懒得写了，贴个别人的链接  
https://blog.csdn.net/qq_41797946/article/details/89417722
请求的核心是携带的info字段，是对认证字段进行的加密，密钥是请求getChallenge接口返回的token，加密算法
是srun自己实现的，应该是对XXTEA的魔改，在结果最前面加{SRBX1}
加密部份在认证系统Protal.js里面能找到srun官方的js实现  特别鸣谢Grok 帮忙根据js实现改成了java版本
chksum字段是一个Hmac-md5。
工具下载即用（可自行编译后执行）将账号密码填入json配置文件，可实现功能