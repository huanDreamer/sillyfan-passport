package top.sillyfan.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.sillyfan.auxiliaryplatform.domain.model.JwtUser;
import top.sillyfan.auxiliaryplatform.domain.model.User;
import top.sillyfan.auxiliaryplatform.service.AccessTokenService;
import top.sillyfan.auxiliaryplatform.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUser
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .qq(user.getQq())
                .phone(user.getPhone())
                .mark(user.getMark())
                .type(user.getType())
                .maxTokenNum(user.getMaxTokenNum())
                .maxThreadNum(user.getMaxThreadNum())
                .superUser(user.getSuperUser())
                .authorities(user.getAuthorizes())
                .status(user.getStatus())
                .taskStatus(user.getTaskStatus())
                .online(user.getOnline())
                .lastPasswordResetDate(user.getLastPasswordResetDate())
                .expireDate(user.getExpireDate())
                .build();
        }
    }
}
