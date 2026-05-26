一个深澜校园网一键认证的工具   java实现
api分析懒得讲了，贴个别人的链接  
https://blog.csdn.net/qq_41797946/article/details/89417722
请求的核心是携带的info字段，是对认证字段进行的加密，密钥是请求getChallenge接口返回的token，加密算法
srun接口返回的是jsonp，程序自己会处理
是srun自己实现的，应该是对XXTEA的魔改，在结果最前面加{SRBX1}
加密部份在认证系统Protal.js里面能找到srun官方的js实现  特别鸣谢Grok 帮忙根据js实现改成了java版本
chksum字段是一个Hmac-md5。
工具下载即用（可自行编译后执行）将账号密码填入json配置文件，可实现功能
感谢grok帮助根据前端js逆向了最核心的加密逻辑

V2.1版本新增：（2026.5）
针对某些http https认证跳来跳去的学校做了优化，配置文件root_url填写http地址就可以
当跳到https的时候 程序会自动用他https的地址  如果只有http/https，不会跳来跳去，则直接写
地址就可以，新版要求配置文件root_url必须带协议（http://xxx或https://  不可以裸写ip域名）
新版支持网络检测并指定联网检测地址（默认用了阿里云dns的223.5.5.5，http请求的时候会返回空白，如果没有认证会被篡改响应带到校园网认证页面）
用了https://www.baidu.com检测网络联通性（增加检测可靠性）只有检测网络不在线才会执行登陆
对深澜系大学通用，可能不同学校逻辑需要微调  灵活使用