

## 说明
此demo包含登陆、登出、和请求接口（需要登陆和不需要登陆的情况）  
所有涉及到安全性的api接口，都必须要使用https协议。虽然，https为了保证安全性，在效率上是比http协议低。

## 使用
打包：
    在项目根路径下使用maven命令打包：mvn clean package 
部署：
    将target目录下的jar拷贝，并运行项目： java –jar demo-app-api-0.0.1-SNAPSHOT.jar 

## 流程
1. 用户请求登陆接口，返回Token和其他用户基本信息  

2. 用户请求接口需要对一下参数进行签名，MD5Utils.getStringMD5(uri(就是/user/info/苏) + timestamp + token);参考SignUtils.checkSign()方法
   请求例子：http://localhost:8080/user/info/苏?timestamp=1490163174&openid=68d30a9594728bc39aa24be94b319d21&sign=22d30a9594728bc39aa24be94b319d2a  
   
3. 请求不需要签名的接口MD5Utils.getStringMD5(uri(就是/login) + timestamp)/MD5Utils.getStringMD5(uri(就是/login) + timestamp)
   请求例子：http://localhost:8080/login?timestamp=1490163174&sign=123456

## 其他
后期传输body数据可以使用加密算法aes加密数据，而密钥是Token的值，第一次登陆接口返回加密的Token的时候可以约定是密钥用openid来解密。  
api限流？
400/500/404等处理？

参考：  
[15.app后端怎么设计用户登录方案](http://blog.csdn.net/newjueqi/article/details/44062849)  
[16.app后端如何保证通讯安全--url签名](http://blog.csdn.net/newjueqi/article/details/44154791)  
[17.app后端如何保证通讯安全--aes对称加密](http://blog.csdn.net/newjueqi/article/details/44177063)  
 



