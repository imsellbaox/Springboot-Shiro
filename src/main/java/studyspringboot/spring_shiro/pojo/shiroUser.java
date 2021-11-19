package studyspringboot.spring_shiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: V
 * @param:
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class shiroUser {
    int id;
    String name;
    String password;
    String prems;

}
