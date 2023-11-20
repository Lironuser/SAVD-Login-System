package com.example.loginserver.Logic;
import com.example.loginserver.Errors.CompanyError;
import com.example.loginserver.dto.EmailVo;
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
    public  static CompanyError connectToServer(String path, String status){
        try {
            url=new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(status.toUpperCase());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            return CompanyError.GOOD;
        } catch (MalformedURLException e) {
            System.out.println(e);
            return CompanyError.URL_ERROR;
        } catch (IOException e) {
            System.out.println(e);
            return CompanyError.OPEN_CONNECTION_ERROR;
        }catch (Exception e){
            System.out.println(e);
            return CompanyError.ELSE_ERROR;
        }
    }

    public static CompanyError sendToServer(String gmail, String message){
        EmailVo emailVo=new EmailVo();
        emailVo.setMassage(message);
        emailVo.setSendTo(gmail);
        Gson json=new Gson();
        String jsonString=json.toJson(emailVo);
        try {
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(jsonString);
            outputStream.flush();
            return CompanyError.GOOD;
        } catch (IOException e) {
            System.out.println(e);
            return CompanyError.OPEN_CONNECTION_ERROR;
        }
    }
    public static CompanyError getFromServer(){
        BufferedReader in;
        String inputLine;
        StringBuilder response = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            System.out.println(e);
            return CompanyError.OPEN_CONNECTION_ERROR;
        }
        try {

            while ((inputLine=in.readLine())!=null){
                response.append(inputLine);
            }
        } catch (IOException e) {
            System.out.println(e);
            return CompanyError.READ_ERROR;
        }
        System.out.println("answer from service:  " + response);
        return CompanyError.GOOD;
    }
}
