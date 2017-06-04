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

import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.ConnectionException;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.HttpException;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.datasources.NetDataSource;

import java.util.List;

import javax.inject.Inject;

/**
 * {@link NetDataSource} Implementation
 *
 * @author Raul Hernandez Lopez
 */
public class NetDataSourceImpl implements NetDataSource<ProductResponse> {

    private Context context;
    private NetOperations<ProductResponse> netOperations;

    @Inject
    NetDataSourceImpl(Context context,
                      NetOperationsImpl netOperations) {
        this.context = context;
        this.netOperations = netOperations;
    }

    @Override
    public List<ProductResponse> getProductsList() throws ConnectionException, HttpException {
        return netOperations.getComicsList(context);
    }
}
