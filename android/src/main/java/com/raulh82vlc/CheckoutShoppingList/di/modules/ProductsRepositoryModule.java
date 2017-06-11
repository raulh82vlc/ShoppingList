/*
 * Copyright (C) 2017 Raul Hernandez Lopez @raulh82vlc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.raulh82vlc.CheckoutShoppingList.di.modules;

import com.raulh82vlc.CheckoutShoppingList.BuildConfig;
import com.raulh82vlc.CheckoutShoppingList.domain.datasources.net.NetDataSourceImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.datasources.net.NetOperations;
import com.raulh82vlc.CheckoutShoppingList.domain.datasources.net.NetOperationsImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.datasources.net.connection.ConnectionHandler;
import com.raulh82vlc.CheckoutShoppingList.domain.datasources.net.connection.ConnectionHandlerImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.ProductsRepository;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.ProductsRepositoryImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.datasources.NetDataSource;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.datasources.api.ProductsApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>Products repository module which provides the different specific implementations as
 * well as the {@link NetDataSource} which retrieves info from the Network and all sub-components
 * required for starting Network communication</p>
 *
 * @author Raul Hernandez Lopez
 */

@Module
public class ProductsRepositoryModule {
    @Provides
    @Singleton
    ProductsRepository provideComicsRepository(ProductsRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @Singleton
    NetDataSource provideNetDataSource(NetDataSourceImpl dataSource) {
        return dataSource;
    }

    @Provides
    @Singleton
    ConnectionHandler provideConnectionHandler() {
        return new ConnectionHandlerImpl();
    }

    @Provides
    public ProductsApi provideComicsApi(Retrofit retrofit) {
        return retrofit.create(ProductsApi.class);
    }

    @Provides
    @Singleton
    NetOperations provideNetOperations(ProductsApi productsApi, ConnectionHandler connectionHandler) {
        return new NetOperationsImpl(productsApi, connectionHandler);
    }

    @Provides
    @Singleton
    public Converter.Factory providesConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);

        return okHttpClient.build();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(OkHttpClient okHttpClient, Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_API)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build();
    }
}
