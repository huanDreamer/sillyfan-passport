package top.sillyfan.security.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    // 剩余可用脚本数量
    private final Integer leftTokenNum;

    // 过期时间，单位秒
    private final Integer expire;

    public JwtAuthenticationResponse(String token, Integer leftTokenNum, Integer expire) {
        this.token = token;
        this.leftTokenNum = leftTokenNum;
        this.expire = expire;
    }

    public String getToken() {
        return this.token;
    }

    public Integer getLeftTokenNum() {
        return leftTokenNum;
    }

    public Integer getExpire() {
        return expire;
    }
}
