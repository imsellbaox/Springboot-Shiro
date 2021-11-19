package studyspringboot.spring_shiro;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringShiroApplicationTests {

	@Test
	public void contextLoads() {
		String hashAlgorithName = "MD5";
		String password = "登录时输入的密码";
		int hashIterations = 1024;//加密次数
		ByteSource credentialsSalt = ByteSource.Util.bytes("登录时输入的用户名");
		Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
		System.out.println(obj.toString());
	}

}
