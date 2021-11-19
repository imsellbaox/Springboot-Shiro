package studyspringboot.spring_shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: V
 * @param:
 * @description:
 */
@Configuration
public class ShrioConfig {




    //ShiroFilterFactoryBean   第三步
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
//        设置登陆界面
        bean.setLoginUrl("/tologin");
//        设置无权限界面
        bean.setUnauthorizedUrl("/noauth");

        Map<String, String> map = new HashMap<>();
        /*
            添加shiro的内置过滤器
            anon：无须认证就可以访问
            authc：必须认证了才可以访问
            perms：拥有对某个资源的权限才能访问
            role:拥有某个角色才可以访问
         */
//        设置页面拦截
//            map.put("/user/add","perms[add]");
//            map.put("/user/update","perms[update]");
        bean.setFilterChainDefinitionMap(map);
//        生成拦截器
        return bean;
    }

    //DefaultWebSecurityManager    第二步
    @Bean(name = "securityManager")                                  //  由Spring 自动注入 realm
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//     SecurityManager要验证用户身份，需要从Realm得到用户相应的角色、权限
        securityManager.setRealm(realm);

        return securityManager;
    }

    //创建realm ，Realm里包含安全数据，Manager需要这些数据做对比   第一步
    @Bean(name = "userRealm")
    public UserRealm getUserRealm(@Qualifier("hashedCredentialsMatcher")HashedCredentialsMatcher matcher ) {
        UserRealm realm = new UserRealm();
//        注入 MD5自动解析类
        realm.setCredentialsMatcher(matcher);
        return realm;
    }


//     启动shiro与thymleaf同步
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

//    该类 为数据库里的MD5 密码进行解析， 需要注入到realm里面
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

}
