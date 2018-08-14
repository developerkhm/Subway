package com.skt.tmaphot.net.service;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static ServiceGenerator instance;

    private final String VERSION = "v1";
    private final String DEFAULT_URL = "http://api.ordertable.co.kr/";

//    private static final String BASE_URL = String.format("%s%s", DEFAULT_URL, VERSION);
    private String BASE_URL = "http://api.ordertable.co.kr/v1/";
    private Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    private Retrofit retrofit = builder.build();
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


    private ServiceGenerator() { }

    public static ServiceGenerator getInstance() {
        if (instance == null) {
            instance = new ServiceGenerator();
        }
        return instance;
    }


    public APIService createService(){

        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.addInterceptor(interceptor);
        builder = builder.client(httpClientBuilder.build());
        retrofit = builder.build();

        return retrofit.create(APIService.class);
    }

    public Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            HttpUrl url = request.url();
            AddPostParamRequestBody newBody = new AddPostParamRequestBody(request.body(), "id",LoginInfo.getInstance().getUserId());
            Request newRequest = request.newBuilder().post(newBody).url(url).build();

            return chain.proceed(newRequest);
        }
};

    class AddPostParamRequestBody extends RequestBody {

        final RequestBody body;
        final String parameter;

        AddPostParamRequestBody(RequestBody body, String name, String value) {
            this.body = body;
            this.parameter = "&" + name + "=" + value;
        }

        @Override
        public long contentLength() throws IOException {
            return body.contentLength() + parameter.length();
        }

        @Override
        public MediaType contentType() {
            return body.contentType();
        }

        @Override
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            body.writeTo(bufferedSink);
            bufferedSink.writeString(parameter, Charset.forName("UTF-8"));
        }
    }
}
