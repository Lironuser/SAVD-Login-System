package Logic;

import Errors.PasswordError;
import com.example.loginserver.vo.PasswordVo;

public class PasswordCheck {
    private static PasswordError checkLength(String password){
        if(password.length()<8 && password.length()>16){
            return PasswordError.LengthErrorPassword;
        }
        return PasswordError.GOOD;
    }
    private static PasswordError checkChars(String password){
        if(!password.matches(".*[A-Z]*.")){
            return PasswordError.NotBigChar;
        }
        if(!password.matches(".*[a-z]*.")){
            return PasswordError.NotSmallChar;
        }
        if(!password.matches(".*[0-9]*.")){
            return PasswordError.NotNumber;
        }
        return PasswordError.GOOD;
    }
    public static PasswordError checkPassObject(PasswordVo passwordVo){
        PasswordError e;
        e=checkLength(passwordVo.getPass());
        if(e!= PasswordError.GOOD){
            return e;
        }
        e=checkChars(passwordVo.getPass());
        return e;
    }
}
