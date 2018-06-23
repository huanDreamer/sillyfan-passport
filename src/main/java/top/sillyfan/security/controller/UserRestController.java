package top.sillyfan.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.sillyfan.auxiliaryplatform.domain.model.AccessToken;
import top.sillyfan.security.TokenUtil;
import top.sillyfan.auxiliaryplatform.domain.model.JwtUser;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        AccessToken accessToken = tokenUtil.getUsernameFromToken(token, 2);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(accessToken.getUsername());
        return user;
    }
}
