package top.sillyfan.security;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;
    private Integer type;   // 登录类型 1.网页登录 2.脚本登陆。网页登录只允许登陆3个人，脚本登陆受到最大数量限制

    public AuthenticationRequest() {
        super();
    }

    public AuthenticationRequest(String username, String password, Integer type) {
        this.setUsername(username);
        this.setPassword(password);
        this.setType(type);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
