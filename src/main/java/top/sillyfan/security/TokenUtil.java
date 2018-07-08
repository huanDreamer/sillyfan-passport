package top.sillyfan.security;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import top.sillyfan.auxiliaryplatform.domain.model.AccessToken;
import top.sillyfan.auxiliaryplatform.service.AccessTokenService;

import java.util.Objects;
import java.util.UUID;

@Component
public class TokenUtil {

    @Autowired
    AccessTokenService accessTokenService;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expiration;

    @Value("${jwt.long-expiration}")
    private Integer longExpiration;

    public AccessToken getUsernameFromToken(String authToken) {
        AccessToken accessToken = accessTokenService.findOne(authToken);

        // 如果token未找到 / token过期
        if (Objects.isNull(accessToken) || accessToken.getExpire().isBefore(DateTime.now())) {
            return null;
        }

        // token 未失效，则延长时效
        accessToken.setExpire(DateTime.now().plusSeconds(getExpirationValue(accessToken.getType())));

        accessTokenService.update(accessToken);

        return accessToken;
    }

    public boolean tokenExpire(AccessToken token) {
        if (Objects.isNull(token)) {
            return true;
        }
        return token.getExpire().isBefore(DateTime.now());
    }

    public void deleteToken(String token) {
        accessTokenService.delete(token);
    }

    public boolean validateToken(AccessToken accessToken, UserDetails userDetails) {

        // 如果token未找到
        if (Objects.isNull(accessToken)) {
            return false;
        }

        return accessToken.getUsername().equals(userDetails.getUsername());
    }

    public String generateToken(String username, Long userId, Integer type) {
        AccessToken accessToken = new AccessToken();
        accessToken.setToken(UUID.randomUUID().toString());
        accessToken.setUserId(userId);
        accessToken.setUsername(username);
        accessToken.setType(type);

        // 脚本过期时间
        accessToken.setExpire(DateTime.now().plusSeconds(getExpirationValue(type)));
        accessTokenService.create(accessToken);

        return accessToken.getToken();
    }

    private Integer getExpirationValue(Integer type) {
        if (type == 1) {
            return longExpiration;
        }
        return expiration;
    }
}
