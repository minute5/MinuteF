package per.zongwlee.oauth.api.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author zongw.lee@gmail.com
 * @since 2019/03/25
 */
@Component
public class UserValidator {

    private static final String EMAIL_REGULAR_EXPRESSION = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

    private static final String PHONE_REGULAR_EXPRESSION = "^((13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8})?$";

    public boolean emailValidator(String email) {
        return Pattern.compile(EMAIL_REGULAR_EXPRESSION).matcher(email).matches();
    }

    public boolean phoneValidator(String phone) {
        return Pattern.compile(PHONE_REGULAR_EXPRESSION).matcher(phone).matches();
    }
}
