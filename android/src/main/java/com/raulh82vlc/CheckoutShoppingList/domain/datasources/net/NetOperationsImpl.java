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

package com.raulh82vlc.CheckoutShoppingList.domain.datasources.net;

import android.content.Context;

import com.raulh82vlc.CheckoutShoppingList.BuildConfig;
import com.raulh82vlc.CheckoutShoppingList.R;
import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsForProducts;
import com.raulh82vlc.CheckoutShoppingList.domain.datasources.net.connection.ConnectionHandler;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.ConnectionException;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.HttpException;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.IOCustomException;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductsResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.datasources.api.ProductsApi;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;


/**
 * Declared read operations from the Network API interface
 *
 * @author Raul Hernandez Lopez
 */
public class NetOperationsImpl implements NetOperations<ProductResponse> {

    private final ProductsApi productsApi;
    private final ConnectionHandler connectionHandler;

    @Inject
    public NetOperationsImpl(ProductsApi productsApi, ConnectionHandler connectionHandler) {
        this.productsApi = productsApi;
        this.connectionHandler = connectionHandler;
    }

    @Override
    public List<ProductResponse> getComicsList(Context context)
            throws ConnectionException, HttpException {
        if (connectionHandler.isThereConnection(context)) {
            Call<ProductsResponse> comicsResponseCall = productsApi.getProductsList();
            try {
                Response<ProductsResponse> response = comicsResponseCall.execute();
                if (BuildConfig.DEBUG) {
                    Timber.d(MessageFormat.format("Http header: {0}", response.headers().toString()));
                    Timber.d(MessageFormat.format("Http response: {0}", response.message()));
                }
                if (isSuccess(response.code())) {
                    List<ProductResponse> productResponses = null;
                    ProductsResponse productsResponse = response.body();
                    if (productsResponse != null) {
                        productResponses = productsResponse.getProducts();
                        if (BuildConfig.DEBUG) {
                            if (!productResponses.isEmpty()) {
                                for (ProductResponse productResponseItem : productResponses) {
                                    Timber.d(MessageFormat.format("Http response body: {0}", productResponseItem.toString()));
                                }
                            }
                        }
                    }
                    return productResponses;
                } else {
                    Timber.e(MessageFormat.format("Error: Http response code: {0}", response.code()));
                    throw new HttpException(context.getResources().getString(R.string.error_http, response.code()));
                }
            } catch (IOException e) {
                Timber.e("Error: " + e.getMessage());
                e.printStackTrace();
                throw new IOCustomException(context.getResources().getString(R.string.error_io, e.getMessage()));
            }
        } else {
            throw new ConnectionException(context.getResources().getString(R.string.no_connection));
        }
    }

    @Override
    public boolean isSuccess(int code) {
        return code == ConstantsForProducts.HTTP_OK_CODE;
    }
}
