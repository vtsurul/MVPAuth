package com.softdesign.mvpauth.di.modules;

import com.softdesign.mvpauth.utils.AppConfig;
import com.softdesign.mvpauth.data.network.RestService;
import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return createClient();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttp) {
        return createRetrofit(okHttp);
    }

    @Provides
    @Singleton
    RestService provideRestService(Retrofit retrofit) {
        return retrofit.create(RestService.class);
    }

    private Retrofit createRetrofit(OkHttpClient okHttp) {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(createConverterFactory())
                .client(okHttp)
                .build();
    }

    private Converter.Factory createConverterFactory() {
        return MoshiConverterFactory.create(new Moshi.Builder()
                    .build());
    }

    private OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(AppConfig.MAX_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(AppConfig.MAX_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(AppConfig.MAX_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

}
