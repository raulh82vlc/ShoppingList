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

import com.raulh82vlc.CheckoutShoppingList.domain.executors.Interactor;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.InteractorExecutor;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.MainThread;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.ProductsRepository;

import javax.inject.Inject;

/**
 * Implementation of the Add Product to Shopping List Interactor
 *
 * @author Raul Hernandez Lopez
 */
public class CheckoutShoppingListInteractorImpl implements CheckoutShoppingListInteractor, Interactor {

    final private InteractorExecutor executor;
    final private MainThread mainThread;
    final private ProductsRepository<ProductResponse, ProductDomain> repository;
    private CheckoutShoppingListCallback callback;
    private ProductDomain productDomain;

    @Inject
    CheckoutShoppingListInteractorImpl(InteractorExecutor executor,
                                       MainThread mainThread,
                                       ProductsRepository repository) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
    }

    @Override
    public void execute(CheckoutShoppingListCallback callback) {
        this.callback = callback;
        executor.run(this);
    }

    @Override
    public void run() {
        String errorMessage = "Issue when adding product to the shopping list: ";
        String formattedCheckout = repository.checkoutCurrentShoppingList();
        if (!formattedCheckout.isEmpty()) {
            notifySuccessfullyLoaded(formattedCheckout);
        } else {
            notifyError(errorMessage + productDomain.getName());
        }
    }

    /**
     * <p>Notifies to the UI (main) thread the result of the request,
     * and sends a callback with a list</p>
     * @param name
     */
    private void notifySuccessfullyLoaded(final String name) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCheckoutOK(name);
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
                callback.onCheckoutKO(error);
            }
        });
    }
}
