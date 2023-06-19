package it.cr.collection;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.cr.logging.LoggingInterceptor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Component
@Log4j2
public class CollectionRunner {

    @Getter
    @Setter
    private LinkedHashMap<String,String> context;
    private List<CollectionRequest> requests;
    private ObjectMapper objectMapper;
    private OkHttpClient client;

    @PostConstruct
    public void init(){

        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

        this.client = new OkHttpClient().newBuilder()
                .addInterceptor(new LoggingInterceptor())
                .writeTimeout(10, TimeUnit.SECONDS) // Set write timeout
                .readTimeout(10, TimeUnit.MINUTES) // Set read timeout
                .connectTimeout(10, TimeUnit.MINUTES) // Set connection timeout
                .build();

        this.context = new LinkedHashMap<>();
    }

    public void run(Long iterations) throws Exception {

        long count = 0L;

        while(count < iterations) {
            log.atInfo().log("Iteration {} of {}", count+1, iterations);
            for (CollectionRequest collectionRequest : requests) {

                collectionRequest.preExecutionScript(context);

                Request request = collectionRequest.buildRequest(context);
                Response response;

                do {
                    response = client.newCall(request).execute();
                } while (Boolean.TRUE.equals(collectionRequest.retry(response)));

                collectionRequest.postExecutionScript(context, objectMapper, response);
            }
            count++;
        }

    }

    public void chain(CollectionRequest collectionRequest) {
        if(CollectionUtils.isEmpty(requests)) {
            this.requests = new LinkedList<>();
        }
        this.requests.add(collectionRequest);
    }
}
