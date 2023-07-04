package Logic;
import Errors.UserInfoError;
import com.example.loginserver.vo.EmailVo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class ConnectToEmail {
    private static URL url;
    private static HttpURLConnection connection;
    public  static UserInfoError connectToServer(String path, String status){
        try {
            url=new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(status.toUpperCase());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            return UserInfoError.GOOD;
        } catch (MalformedURLException e) {
            System.out.println(e);
            return UserInfoError.URLError;
        } catch (IOException e) {
            System.out.println(e);
            return UserInfoError.OpenConnectionError;
        }catch (Exception e){
            System.out.println(e);
            return UserInfoError.ElseError;
        }
    }

    public static UserInfoError sendToServer(String gmail,String message){
        EmailVo emailVo=new EmailVo();
        emailVo.setMassage(message);
        emailVo.setSendTo(gmail);
        Gson json=new Gson();
        String jsonString=json.toJson(emailVo);
        try {
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(jsonString);
            outputStream.flush();
            return UserInfoError.GOOD;
        } catch (IOException e) {
            System.out.println(e);
            return UserInfoError.OpenConnectionError;
        }
    }
    public static UserInfoError getFromServer(){
        BufferedReader in;
        String inputLine;
        StringBuilder response = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            System.out.println(e);
            return UserInfoError.OpenConnectionError;
        }
        try {

            while ((inputLine=in.readLine())!=null){
                response.append(inputLine);
            }
        } catch (IOException e) {
            System.out.println(e);
            return UserInfoError.ReadError;
        }
        System.out.println("answer from service:  " + response);
        return UserInfoError.GOOD;
    }
}
