package com.skt.tmaphot.net.service.test;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TESTAPIClient {

//    private final String VERSION = "v1/";
//    private final String DEFAULT_URL = "http://api.ordertable.co.kr/";
    private String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private Retrofit retrofit;

    private static TESTAPIClient instance;

    private TESTAPIClient() { }

    public static TESTAPIClient getInstance() {
        if (instance == null) {
            instance = new TESTAPIClient();
        }
        return instance;
    }

    public TESTAPIService getClient(String base_url) {

        if(base_url != null && base_url.length() > 0){
            BASE_URL = base_url;
        }else{
            BASE_URL = BASE_URL;
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
                .client(client)
                .build();

        return retrofit.create(TESTAPIService.class);
    }

//    private Interceptor interceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//            Request request = chain.request();
//            HttpUrl url = request.url();
//            AddPostParamRequestBody newBody = new AddPostParamRequestBody(request.body(), "id", LoginInfo.getInstance().getUserId());
//            Request newRequest = request.newBuilder().post(newBody).url(url).build();
//
//            return chain.proceed(newRequest);
//        }
//    };
//
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
