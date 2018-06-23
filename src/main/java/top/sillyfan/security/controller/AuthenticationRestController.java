package top.sillyfan.security.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import top.sillyfan.auxiliaryplatform.domain.model.AccessToken;
import top.sillyfan.auxiliaryplatform.domain.model.JwtUser;
import top.sillyfan.auxiliaryplatform.service.AccessTokenService;
import top.sillyfan.security.AuthenticationRequest;
import top.sillyfan.security.TokenUtil;
import top.sillyfan.security.service.JwtAuthenticationResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class AuthenticationRestController {

    @Autowired
    AccessTokenService accessTokenService;
    @Value("${jwt.header}")
    private String tokenHeader;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtil tokenUtil;

    //    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        if (Objects.isNull(authenticationRequest.getType())) {
            authenticationRequest.setType(2);
        }

        // Reload password post-security so we can generate the token
        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // 如果是需求方，并且限制了最大token数量。并且是脚本登陆
        if (jwtUser.isDemander() && jwtUser.getMaxtokennum() != 0 && authenticationRequest.getType() == 2) {

            int count = accessTokenService.countByUserIdAndType(jwtUser.getId(), authenticationRequest.getType());

            if (count >= jwtUser.getMaxtokennum()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("脚本数量已达到最大限制");
            }
        }

        final String token = tokenUtil.generateToken(jwtUser.getUsername(), jwtUser.getId(), authenticationRequest.getType());

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        if (StringUtils.isBlank(authToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("请求参数缺少token");
        }
        final String token = authToken.substring(7);
        AccessToken accessToken = accessTokenService.findOne(token);

        // token找到，则删除原来的token，并且生成新的token
        if (Objects.nonNull(accessToken)) {

            tokenUtil.deleteToken(accessToken.getToken());

            String newToken = tokenUtil.generateToken(accessToken.getUsername(), accessToken.getUserid(), accessToken.getType());
            return ResponseEntity.ok(new JwtAuthenticationResponse(newToken));

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token未找到");
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
}
