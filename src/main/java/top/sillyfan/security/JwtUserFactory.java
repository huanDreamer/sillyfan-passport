package top.sillyfan.security;

import top.sillyfan.domain.model.User;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.getEmail(),
            user.getAuthorizes(),
            user.getEnabled(),
            user.getLastPasswordResetDate()
        );
    }
}
