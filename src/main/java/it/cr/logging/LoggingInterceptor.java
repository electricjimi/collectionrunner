package it.cr.logging;

import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;

@Log4j2
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request request = chain.request();

        Request copy = request.newBuilder().build();
        Buffer buffer = new Buffer();
        copy.body().writeTo(buffer);

        long t1 = System.nanoTime();
        log.atInfo().log(String.format("Sending request %s on %s%n%s%s",
                request.url(), chain.connection(), request.headers(), buffer.readUtf8()));

        Response response = chain.proceed(request);

        MediaType contentType = response.body().contentType();
        String content = response.body().string();

        long t2 = System.nanoTime();
        log.atInfo().log(String.format("Received response for %s in %.1fms%n%s%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers(),content));

        return response.newBuilder().body(ResponseBody.create(contentType, content)).build();
    }
}
