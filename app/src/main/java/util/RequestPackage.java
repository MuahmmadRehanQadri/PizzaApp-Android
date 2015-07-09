package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muhammad Rehan Qadri
 */
public class RequestPackage {
    private String uri;
    private String method="GET";
    private Map<String,String> params = new HashMap<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void addParams(String key, String value){
        params.put(key,value);
    }

    public String getEncodedParamsQuestionMarkSeperated(){
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()){
            String value = params.get(key);
            try {
                value = URLEncoder.encode( value, "UTF-8" );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length()>0)
                sb.append("&");

            sb.append(key+"="+value);
        }
        return sb.toString();
    }

    public String getEncodedParamsSlashSeperated(){
        StringBuilder sb = new StringBuilder();
        for (String value : params.values()){
            try {
                value = URLEncoder.encode( value, "UTF-8" );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length()>0)
                sb.append("/");

            sb.append(value);
        }
        return sb.toString();
    }

    public String getUriWithParamsQuestionMarkSeperated() {
        if (method.equals("GET")) {
            uri += "?" + getEncodedParamsQuestionMarkSeperated();
            return uri;
        }
        return null;
    }

    public String getUriWithParamsSlashSeperated() {
        if (method.equals("GET")) {
            uri += "/" + getEncodedParamsSlashSeperated();
            return uri;
        }
        return null;
    }
}
