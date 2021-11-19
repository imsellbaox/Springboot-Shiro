#Shiro

##1.依赖导入   
```
<!--shiro整合spring的包-->
<dependency>
<groupId>org.apache.shiro</groupId>
<artifactId>shiro-spring</artifactId>
<version>1.4.0</version>
</dependency>

<!-- thymeleaf-shiro整合包 -->
<dependency>
<groupId>com.github.theborakompanioni</groupId>
<artifactId>thymeleaf-extras-shiro</artifactId>
<version>2.0.0</version>
</dependency>

```
## 2.Subject Token  令牌
在Controller 中的登录中用subject的Token令牌接收密码和用户名
```
@RequestMapping("/login")
public String login(String password,String username,Model model){
Subject subject = SecurityUtils.getSubject();
UsernamePasswordToken token = new UsernamePasswordToken(username,password);
```
## 3.编写Realm
Shiro需要Realm 来完成认证和授权

###3.1 认证 Authenti ca tionInfo
AuthenticationInfo是认证载体，需要Token获得表单数据。（AuthenticationToken 是所有Token的接口）
Spring会帮你自动注入你Controller中写入的Token，然后你需要把数据库的对象以Principal的形式给info
```
shiroUser user = shirouserService.getOneByName(token.getUsername());

return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt,getName());
```

###3.2 授权 Authori za tionInfo
AuthorizationInfo是授权载体，从subject中获取认证后的Principal进行解析、授权
返回info

###=========Realm就配置好了

## 4.编写ShiroConfig配置类
### 4.1 第一步:注入Realm
把我们自定义写好的Realm注入到Spring容器中
```
@Bean(name = "userRealm")
public UserRealm getUserRealm(@Qualifier("hashedCredentialsMatcher")HashedCredentialsMatcher matcher ) {
UserRealm realm = new UserRealm();
//注入 MD5自动解析类
realm.setCredentialsMatcher(matcher);
return realm;
}
```
此时可以横切注入 hashedCredentialsMatcher加密类，但需要@Qualifier
让spring 精准自动装配（理论上不写@Qua..，也没事，spring会帮我们自动注入）

### 4.2 第二步:注入DefaultSecurityManager
我们是web项目，注入子类DefaultWebSecurityManager到spring容器，需要自动装配上一步注入好的Rleam
，securityManager才是执行者，realm只负责管理

### 4.3 第三步:注入ShiroFilterFactoryBean
ShiroFilterFactoryBean这个类注入后，它会自动生成对应的Filter来做认证、授权、拦截...
我们需要SecurityManager来在这里写入拦截，以及rememberMe、跳转等功能


## 5. 关于加密
shrio 非常垃圾！它可以帮你把数据库的密码解码传给前端，但是不能给前端数据加密
   一个原因是：shiro出现时间很早，没有设计这个功能
另一个原因是 ：也是为了它的自由度考虑，不和SpringSecurity一样局限
这里需要我们在Service层中加入加密功能
```
@Override
public void addOne(shiroUser user){
//给密码进行MD5加密
ByteSource salt = ByteSource.Util.bytes(user.getName());
Object MD5password = new SimpleHash("MD5",user.getPassword(),salt,1024);
user.setPassword(MD5password.toString());

//Mapper 功能
shirouserMapper.addOne(user);
}
```

## 6.整合themeleaf 
###这个自己去看index页面，有注解