package studyspringboot.spring_shiro.service;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studyspringboot.spring_shiro.mapper.shiroUserMapper;
import studyspringboot.spring_shiro.pojo.shiroUser;

/**
 * @author: V
 * @param:
 * @description:
 */
@Service
public class shiroUserServiceImpl implements shiroUserService {

    @Autowired
    shiroUserMapper shirouserMapper;

    @Override
    public shiroUser getOneByName(String name) {
        shiroUser user = shirouserMapper.getOneByName(name);
        return user;
    }
    @Override
    public void addOne(shiroUser user){
        //给密码进行MD5加密
        ByteSource salt = ByteSource.Util.bytes(user.getName());
        Object MD5password = new SimpleHash("MD5",user.getPassword(),salt,1024);
        user.setPassword(MD5password.toString());

        shirouserMapper.addOne(user);
    }
}
