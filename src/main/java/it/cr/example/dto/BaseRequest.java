package it.cr.example.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.cr.collection.CollectionRequest;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
public class BaseRequest extends CollectionRequest {

    @Override
    public void preExecutionScript(Map<String, String> context) {
        // Implemented in subclasses
    }

    @Override
    public String getMethod(Map<String,String> context) {
        return "";
    }

    @Override
    public String getUrl(Map<String,String> context) {
        return "";
    }

    @Override
    protected LinkedHashMap<String, String> getHeaders(Map<String,String> context) {
        LinkedHashMap<String,String> headers = new LinkedHashMap<>();
        headers.put("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"");
        headers.put("X-CSRF-TOKEN", context.get("X-CSRF-TOKEN"));
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("Cookie", context.get("Cookie"));
        return headers;
    }

    @Override
    public MediaType getMediaType(Map<String,String> context) {
        return null;
    }

    @Override
    public RequestBody getBody(Map<String,String> context) {
        return null;
    }

    @Override
    public Boolean retry(Response r) throws Exception {
        return Boolean.FALSE;
    }

    @Override
    public void postExecutionScript(Map<String, String> context, ObjectMapper objectMapper, Response response) throws Exception {
        // Implemented in subclasses
    }
}
