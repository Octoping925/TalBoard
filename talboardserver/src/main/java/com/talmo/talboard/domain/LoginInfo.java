package com.talmo.talboard.domain;

import com.talmo.talboard.exception.ExceptionConstants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class LoginInfo {
    private String id;
    private String password;
    private String emailAddress;

    protected LoginInfo() {}

    public LoginInfo(String id, String password, String emailAddress) {
        if(!isValidId(id)) throw new IllegalArgumentException(ExceptionConstants.INVALID_ID_MESSAGE);
        if(!isValidPassword(password)) throw new IllegalArgumentException(ExceptionConstants.INVALID_PW_MESSAGE);
        if(!isValidEmailAddress(emailAddress)) throw new IllegalArgumentException(ExceptionConstants.INVALID_EMAIL_MESSAGE);

        this.id = id;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    private boolean isValidId(String id) {
        if(id.contains(" ")) return false;
        if(id.length() < 6) return false;
        return true;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && !password.contains(" ");
    }

    private boolean isValidEmailAddress(String emailAddress) {
        String regx = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

}
