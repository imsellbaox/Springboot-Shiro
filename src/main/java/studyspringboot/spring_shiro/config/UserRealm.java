package studyspringboot.spring_shiro.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import studyspringboot.spring_shiro.pojo.shiroUser;
import studyspringboot.spring_shiro.service.shiroUserService;

/**
 * @author: V
 * @param:   整个登录过程是  前端数据登录--->令牌--->认证---->授权----->调转首页
 *           整个拦截过程是  spring初始化时）页面拦截器启动---->前端访问--->拦截
 * @description:
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    shiroUserService shirouserService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了>>>>>>授权");
//        封装授权info
        SimpleAuthorizationInfo zinfo = new SimpleAuthorizationInfo();
//        从subject中获取Principal，就是认证获得的权限信息
        Subject subject = SecurityUtils.getSubject();
//        取得权限信息
        shiroUser user = (shiroUser) subject.getPrincipal();
        String[] prems = user.getPrems().split(",");
        for (String prem : prems) {
//            为该用户授权
            zinfo.addStringPermission(prem);
        }
//        执行
        return zinfo;
    }

//    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了>>>>>认证");
//        获得令牌,里面有前端登陆数据
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        根据name获取对象，判断是否存在
        shiroUser user = shirouserService.getOneByName(token.getUsername());
        if (user==null){
            return null;
        }
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("", user.getPassword(), "");

//        给前端传一个属性，验证是否已经登陆
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("userExist",user);
//        盐
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getName());
//        realm

//        自动密码验证！ Principal就是user用于授权
        return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt,getName());
    }
}
