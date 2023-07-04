package Logic;

import Errors.UserInfoError;
import org.apache.commons.codec.binary.Base32;

import java.security.SecureRandom;
import java.util.Random;

public class UserInfoCheck {
    private static int sizeOfCode=6;
    private static  int ageConnectUser=7;
    private static String pathToGmailServer="http://localhost:8080/sendGmail/send";
    private static int minLengthName=1;


    public static   UserInfoError checkUserName(String userName){
        if(!userName.matches(".*[A-Z].*")){
            return UserInfoError.NoBigChar;
        }
        if(!userName.matches(".*[a-z].*")){
            return UserInfoError.NoSmallChar;
        }
        if(userName.length()<8||userName.length()>16){
            return UserInfoError.LengthError;
        }
        if (userName.matches(".*[0-9].*")){
            return UserInfoError.NumberInTheName;
        }
        return UserInfoError.GOOD;
    }
    public  static String getCodeToGmail(){
        Random random=new Random();
        String code="";
        String codeNow="";
        for (int i = 0; i <sizeOfCode ; i++) {
            codeNow=String.valueOf(random.nextInt(10));
            code+=codeNow;
        }
        return code;
    }

    public static UserInfoError checkEmail(String gmail){
        UserInfoError e;
        e=ConnectToEmail.connectToServer(pathToGmailServer,"post");
        if(e!=UserInfoError.GOOD){
            return e;
        }
        String code=getCodeToGmail();
        e=ConnectToEmail.sendToServer(gmail,code);
        if(e!=UserInfoError.GOOD){
            return e;
        }
        e=ConnectToEmail.getFromServer();
        return e;
    }
    public static UserInfoError checkNickname(String nickname){
        if(nickname.contains("[0-9]")){
            return UserInfoError.ThereIsANumber;
        }
        if(nickname.length()<=minLengthName){
            return UserInfoError.LengthError;
        }
        return UserInfoError.GOOD;
    }
    public static String getSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);

    }
}
