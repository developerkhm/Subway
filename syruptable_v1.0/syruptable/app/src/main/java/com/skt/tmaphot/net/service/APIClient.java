package com.skt.tmaphot.net.service;

import android.util.Log;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
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
import okio.Okio;
import okio.Sink;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private final String VERSION = "v1/";
    private final String DEFAULT_URL = "http://api.ordertable.co.kr/";
    private String BASE_URL = String.format("%s%s", DEFAULT_URL, VERSION);
    private Retrofit retrofit;

    private static APIClient instance;

    private APIClient() { }

    public static APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();
        }
        return instance;
    }

    public APIService getClient(String base_url) {

        if(base_url != null && base_url.length() > 0){
            BASE_URL = base_url;
        }else{
            BASE_URL = String.format("%s%s", DEFAULT_URL, VERSION);
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(APIService.class);
    }

    public APIService getClient2(String base_url) {

        if(base_url != null && base_url.length() > 0){
            BASE_URL = base_url;
        }else{
            BASE_URL = String.format("%s%s", DEFAULT_URL, VERSION);
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(APIService.class);
    }

//    private Interceptor interceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//            Request request = chain.request();
//            HttpUrl url = request.url();
//            Log.d("okhttp", "[MYLOG] UserId : " + LoginInfo.getInstance().getUserId());
//            AddPostParamRequestBody newBody = new AddPostParamRequestBody(request.body(), "id", LoginInfo.getInstance().getUserId());
//            Request newRequest = request.newBuilder().post(newBody).build();
//
//            return chain.proceed(newRequest);
//        }
//    };

//        private Interceptor interceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//            Request request = chain.request();
//            Log.d("okhttp", "[MYLOG] UserId : " + LoginInfo.getInstance().getUserId());
//            String parameter = "&" + "id" + "=" + LoginInfo.getInstance().getUserId();
//            Request newRequest = interceptRequest(request, parameter);
//            return chain.proceed(newRequest);
//        }
//    };
//
//    public Request interceptRequest(@NotNull Request request, @NotNull String parameter)
//            throws IOException {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        Sink sink = Okio.sink(baos);
//        BufferedSink bufferedSink = Okio.buffer(sink);
//
//        /**
//         * Write old params
//         * */
//        request.body().writeTo(bufferedSink);
//
//        /**
//         * write to buffer additional params
//         * */
//        bufferedSink.writeString(parameter, Charset.defaultCharset());
//
//        RequestBody newRequestBody = RequestBody.create(
//                request.body().contentType(),
//                bufferedSink.buffer().readUtf8()
//        );
//
//        return request.newBuilder().post(newRequestBody).build();
//    }


//    class AddPostParamRequestBody extends RequestBody {
//
//        final RequestBody body;
//        final String parameter;
//
//        AddPostParamRequestBody(RequestBody body, String name, String value) {
//            this.body = body;
//            this.parameter = "&" + name + "=" + value;
//        }
//
//        @Override
//        public long contentLength() throws IOException {
//            return body.contentLength() + parameter.length();
//        }
//
//        @Override
//        public MediaType contentType() {
//            return body.contentType();
//        }
//
//        @Override
//        public void writeTo(BufferedSink bufferedSink) throws IOException {
//            body.writeTo(bufferedSink);
//            bufferedSink.writeString(parameter, Charset.forName("UTF-8"));
//        }
//    }
}
