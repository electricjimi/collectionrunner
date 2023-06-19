package it.cr.example.dto;

import it.cr.example.dto.BaseRequest;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Map;

@Log4j2
public class SecondRequest extends BaseRequest {

    String ids;

    @Override
    public void preExecutionScript(Map<String, String> context) {
        this.ids = context.get("ids");
    }

    @Override
    public String getMethod(Map<String,String> context) {
        return "POST";
    }

    @Override
    public String getUrl(Map<String,String> context) {
        return "https://endpoint_2";
    }

    @Override
    public MediaType getMediaType(Map<String,String> context) {
        return MediaType.parse("application/json");
    }

    @Override
    public RequestBody getBody(Map<String,String> context) {
        return RequestBody.create(getMediaType(context), ids);
    }

    @Override
    public Boolean retry(Response r) throws Exception {
        if(r.code() != 200) {
            log.atInfo().log("Received response code: " + r.code() + ". Sleeping..");
            Thread.sleep(1000L * 90);
            return true;
        }
        return false;
    }
}
