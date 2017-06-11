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

import android.support.annotation.NonNull;

import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsAndroid;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.CheckoutShoppingListInteractor;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;
import com.raulh82vlc.CheckoutShoppingList.ui.presentation.ProductsPresenter;

import java.util.List;
import java.util.Locale;

/**
 * Checkout of shopping list by means of its callback, communicating towards its view
 *
 * @author Raul Hernandez Lopez.
 */
public class CheckoutShoppingListCallbackImpl implements CheckoutShoppingListInteractor.CheckoutShoppingListCallback {

    private final ProductsPresenter.View view;

    public CheckoutShoppingListCallbackImpl(ProductsPresenter.View view) {
        this.view = view;
    }

    @Override
    public void onCheckoutOK(float shoppingListCalculated, List<ProductDomain> shoppingList) {
        if (view.isReady()) {
            view.hideLoader();
            view.showCheckoutResult(getCheckoutFormatted(shoppingListCalculated, shoppingList));
        }
    }

    @NonNull
    protected String getCheckoutFormatted(float resultCheckOutSum, List<ProductDomain> shoppingList) {
        StringBuilder builder = new StringBuilder();
        builder.append("Items: ");
        int position = 0, sizeOfShoppingList = shoppingList.size();
        for (ProductDomain productDomain : shoppingList) {
            builder.append(productDomain.getTypeOfProduct());
            position++;
            if (position != sizeOfShoppingList) {
                builder.append(", ");
            }
        }
        builder.append("\nTotal: ");
        builder.append(String.format(Locale.UK, ConstantsAndroid.FORMAT_FOR_DECIMALS, resultCheckOutSum));
        builder.append("€");
        return builder.toString();
    }

}
