package top.sillyfan.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import top.sillyfan.auxiliaryplatform.constants.UserDef;
import top.sillyfan.auxiliaryplatform.domain.model.User;
import top.sillyfan.auxiliaryplatform.domain.repository.UserRepository;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInit {

    @Autowired
    UserRepository userRepository;

    @Test
    public void createUser() {

        User u = new User();

        u.setId(UUID.randomUUID().toString());
        u.setUsername("admin");
        u.setEmail("huan.dreamer@gmail.com");
        u.setType(UserDef.UserTypeEnum.Admin.getCode());
        u.setAuthorizes(UserDef.UserTypeEnum.Admin.getAuths());
        u.setEnabled(true);
        u.setPassword(new BCryptPasswordEncoder().encode("admin"));
        u.setLastPasswordResetDate(new Date());

        userRepository.insert(u);
    }
}
