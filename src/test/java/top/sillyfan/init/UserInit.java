package top.sillyfan.init;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import top.sillyfan.auxiliaryplatform.constants.TaskDef;
import top.sillyfan.auxiliaryplatform.constants.UserDef;
import top.sillyfan.auxiliaryplatform.domain.model.User;
import top.sillyfan.auxiliaryplatform.domain.model.json.StringList;
import top.sillyfan.auxiliaryplatform.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInit {

    @Autowired
    UserService userService;

    @Test
    public void createUser() {

        User u = new User();

//        u.setId(UUID.randomUUID().toString());
        u.setUsername("huan");
        u.setEmail("huan.dreamer@gmail.com");
        u.setType(UserDef.UserTypeEnum.Admin.getCode());
        u.setAuthorizes(StringList.of(UserDef.UserTypeEnum.Admin.getAuths()));
        u.setStatus(UserDef.UserStatusEnum.Enabled.getCode());
        u.setPassword(new BCryptPasswordEncoder().encode("123"));
        u.setTaskstatus(UserDef.UserStatusEnum.Enabled.getCode());
        u.setLastpasswordresetdate(DateTime.now());

        userService.create(u);
    }
}
