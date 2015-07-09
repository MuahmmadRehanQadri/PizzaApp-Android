package util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Muhammad Rehan Qadri.
 */
public class HttpManager {

    public static String getData(RequestPackage requestPackage) {
        BufferedReader bufferedReader = null;
        String uri = requestPackage.getUriWithParamsSlashSeperated();

        try{
            URL url = new URL(uri);
            Log.d("imaq","URI: "+uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(requestPackage.getMethod());

            StringBuilder sb = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            Log.d("imaq","Going fine");
            String line=null;
            while ((line=bufferedReader.readLine())!=null){
                sb.append(line+"\n");
                Log.d("imaq", "Appending: "+line);
            }
            return sb.toString();

        } catch (MalformedURLException e) {
            Log.d("imaq", "Exception: "+e.toString());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.d("imaq", "Exception: "+e.toString());
            e.printStackTrace();
            return null;
        }
    }
}
