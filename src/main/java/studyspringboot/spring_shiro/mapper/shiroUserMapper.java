package studyspringboot.spring_shiro.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studyspringboot.spring_shiro.pojo.shiroUser;


/**
 * @author: V
 * @param:
 * @description:
 */
@Repository
@Mapper
public interface shiroUserMapper {

    @Select("SELECT * FROM shirouser WHERE name=#{name}")
    shiroUser getOneByName(String name);
    @Insert("INSERT INTO shirouser(name, password, prems) VALUES (#{name},#{password},#{prems})")
    void addOne(shiroUser user);
}
