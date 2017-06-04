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

package com.raulh82vlc.CheckoutShoppingList.domain.interactors;

import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.ConnectionException;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.HttpException;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.Interactor;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.InteractorExecutor;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.MainThread;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.ProductsRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of the Get Products List Interactor
 *
 * @author Raul Hernandez Lopez
 */
public class GetProductsInteractorImpl implements GetProductsInteractor, Interactor {

    final private InteractorExecutor executor;
    final private MainThread mainThread;
    final private ProductsRepository<ProductResponse, ProductDomain> repository;
    private GetProductsListCallback callback;

    @Inject
    GetProductsInteractorImpl(InteractorExecutor executor,
                              MainThread mainThread,
                              ProductsRepository repository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
    }

    @Override
    public void execute(GetProductsListCallback callback) throws ConnectionException, HttpException {
        this.callback = callback;
        executor.run(this);
    }

    @Override
    public void run() throws ConnectionException, HttpException {
        String errorMessage = "Issue when reading comic from hero: ";
        try {
            List<ProductResponse> productResponseList = repository.getProductsList();
            if (productResponseList != null) {
                notifySuccessfullyLoaded(productResponseList);
            } else {
                notifyError(errorMessage);
            }
        } catch (ConnectionException | HttpException e) {
            notifyError(e.getMessage());
        }
    }

    /**
     * <p>Notifies to the UI (main) thread the result of the request,
     * and sends a callback with a list</p>
     */
    private void notifySuccessfullyLoaded(final List<ProductResponse> comicList) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetProductsListOK(comicList);
            }
        });
    }

    /**
     * <p>Notifies to the UI (main) thread that an error happened</p>
     */
    private void notifyError(final String error) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onGetProductsListKO(error);
            }
        });
    }
}
