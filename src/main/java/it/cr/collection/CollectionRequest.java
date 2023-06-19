package it.cr.collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CollectionRequest  {

    protected abstract void preExecutionScript(Map<String,String> context);

    protected abstract String getMethod(Map<String,String> context);

    protected abstract String getUrl(Map<String,String> context);

    protected abstract LinkedHashMap<String,String> getHeaders(Map<String,String> context);

    protected abstract MediaType getMediaType(Map<String,String> context);

    protected abstract RequestBody getBody(Map<String,String> context);

    protected Request buildRequest(Map<String, String> context) {
        Request.Builder builder = new Request.Builder();
        builder.url(getUrl(context)).method(getMethod(context), getBody(context));

        LinkedHashMap<String, String> headers = getHeaders(context);
        headers.keySet().forEach(key -> builder.addHeader(key, headers.get(key)));

        return builder.build();
    }

    protected abstract Boolean retry(Response r) throws Exception;

    protected abstract void postExecutionScript(Map<String,String> context, ObjectMapper objectMapper, Response response) throws Exception;
}
