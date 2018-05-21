package top.sillyfan.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import top.sillyfan.model.security.AuthorityName;
import top.sillyfan.model.security.User;
import top.sillyfan.security.repository.UserRepository;

import java.util.Collections;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInit {

    @Autowired
    UserRepository userRepository;


    @Test
    public void createUser() {

        User u = new User();

        u.setId(System.currentTimeMillis());
        u.setUsername("admin");
        u.setEmail("huan.dreamer@gmail.com");
        u.setAuthorizes(Collections.singletonList(AuthorityName.ROLE_ADMIN));
        u.setEnabled(true);
        u.setPassword(new BCryptPasswordEncoder().encode("admin"));
        u.setLastPasswordResetDate(new Date());

        userRepository.insert(u);
    }
}
