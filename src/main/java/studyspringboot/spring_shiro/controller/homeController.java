package studyspringboot.spring_shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studyspringboot.spring_shiro.pojo.shiroUser;
import studyspringboot.spring_shiro.service.shiroUserService;

/**
 * @author: V
 * @param:
 * @description:
 */
@Controller
public class homeController {
    @Autowired
    shiroUserService shirouserService;

    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello Shiro");
    return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }


    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    /**
     * @return
     */
    @RequestMapping("/login")
    public String login(String password,String username,Model model){
        Subject subject = SecurityUtils.getSubject();
//        设置令牌，放入前端数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try {
//            注入给subject
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","用户名错误！");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误！");
            return "login";
        }catch (LockedAccountException e){
            model.addAttribute("msg","账号冻结！");
            return "login";
        }

    }

    //没授权
    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "没经授权无法进入";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject current = SecurityUtils.getSubject();
        current.logout();
        return "index";
    }

    /**
     0* @param user
     * @return
     */
    @PostMapping("/addOne")
    public String add(shiroUser user){
        shirouserService.addOne(user);
    return "index";
    }
}
