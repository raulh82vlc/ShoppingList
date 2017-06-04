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

package com.raulh82vlc.CheckoutShoppingList.domain.interactors_response;

import com.raulh82vlc.CheckoutShoppingList.domain.interactors.CheckoutShoppingListInteractor;
import com.raulh82vlc.CheckoutShoppingList.ui.presentation.ProductsPresenter;

/**
 * Get Products list by means of its callback, communicating towards its view, mapping resuts from the API
 * to the view
 * @author Raul Hernandez Lopez.
 */
public class CheckoutShoppingListCallbackImpl implements CheckoutShoppingListInteractor.CheckoutShoppingListCallback {

    private final ProductsPresenter.View view;

    public CheckoutShoppingListCallbackImpl(ProductsPresenter.View view) {
        this.view = view;
    }

    @Override
    public void onCheckoutOK(String shoppingListCalculated) {
        if (view.isReady()) {
            view.hideLoader();
            view.showCheckoutResult(shoppingListCalculated);
        }
    }

    @Override
    public void onCheckoutKO(String error) {
        if (view.isReady()) {
            view.hideLoader();
            view.errorWhenCheckout(error);
        }
    }
}
