package studyspringboot.spring_shiro.service;

import studyspringboot.spring_shiro.pojo.shiroUser;

/**
 * @author: V
 * @param:
 * @description:
 */
public interface shiroUserService {
    shiroUser getOneByName(String name);
    void addOne(shiroUser user);
}
