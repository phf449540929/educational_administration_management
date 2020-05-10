package com.cdu.edu.index;

/**
 * description: 登录表单的JavaBean
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:29
 * @since jdk 10.0.1
 */
public class LoginForm {

    private String username;
    private String password;
    private String verification;
    private Identity identity;
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
