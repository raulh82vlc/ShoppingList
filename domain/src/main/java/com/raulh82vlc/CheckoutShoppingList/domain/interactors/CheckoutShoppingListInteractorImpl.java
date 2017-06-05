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
 * Implementation of Checkout of the Shopping List Interactor
 *
 * @author Raul Hernandez Lopez
 */
public class CheckoutShoppingListInteractorImpl implements CheckoutShoppingListInteractor, Interactor {

    final private InteractorExecutor executor;
    final private MainThread mainThread;
    final private ProductsRepository<ProductResponse, ProductDomain> repository;
    private CheckoutShoppingListCallback callback;

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
        //there is no error case, because despite this is with an empty basket, says 0.00€
        notifySuccessfullyCheckedOut(repository.checkoutCurrentShoppingList());
    }

    /**
     * <p>Notifies to the UI (main) thread the result of checkout,
     * and sends a callback the string</p>
     * @param checkOutFormattedList
     */
    private void notifySuccessfullyCheckedOut(final String checkOutFormattedList) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCheckoutOK(checkOutFormattedList);
            }
        });
    }
}
