package it.cr.example.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import it.cr.example.dto.BaseRequest;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONArray;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Log4j2
public class FirstRequest extends BaseRequest {

    @Override
    public String getMethod(Map<String,String> context) {
        return "POST";
    }

    @Override
    public String getUrl(Map<String,String> context) {
        return "https://endpoint_1";
    }

    @Override
    public MediaType getMediaType(Map<String,String> context) {
        return MediaType.parse("application/json");
    }

    @Override
    public RequestBody getBody(Map<String,String> context) {
        return RequestBody.create(getMediaType(context), "{\"filters\":{\"code\":null,\"groupId\":\"1234\",\"status\":\"POSITIVE\",\"hasErrors\":null,\"category\":null,\"type\":null},\"search\":\"\",\"order\":[{\"property\":\"lastModifiedDate\",\"direction\":\"DESC\"}],\"start\":0,\"length\":30}");
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

    @Override
    public void postExecutionScript(Map<String,String> context, ObjectMapper objectMapper, Response response) throws Exception {
        DocumentContext doc = JsonPath.parse(new String(response.body().bytes()));
        JSONArray items = doc.read("$.data.items");
        List<Integer> ids = new LinkedList<>();

        for (Object o : items) {
            LinkedHashMap item = (LinkedHashMap) o;
            ids.add((Integer) item.get("id"));
        }

        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        ids.forEach(arrayNode::add);
        rootNode.put("ids", arrayNode);
        context.put("ids", objectMapper.writeValueAsString(rootNode));
    }
}
