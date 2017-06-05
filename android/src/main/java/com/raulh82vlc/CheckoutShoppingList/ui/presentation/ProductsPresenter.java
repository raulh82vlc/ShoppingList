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

package com.raulh82vlc.CheckoutShoppingList.ui.presentation;

import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.ConnectionException;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.HttpException;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductUI;

import java.util.List;


/**
 * Responsible of passing Products List result to the View
 *
 * @author Raul Hernandez Lopez
 */
public interface ProductsPresenter {

    void getProducts() throws ConnectionException, HttpException;

    void setView(View view);

    void resetView();

    void raiseException(String message);

    void initInitialVisibility();

    void addToShoppingList(ProductUI productUI);

    void checkOutShoppingList();

    interface View {
        void showInitialVisibility();

        void errorGettingProducts(String error);

        void updateProductsList(List<ProductUI> listOfComics);

        void showList();

        void showAnimation();

        void showLoader();

        void hideLoader();

        void showEmptyState();

        void showErrorMessage(String error);

        boolean isReady();

        void hideListShowText();

        void errorAddingProduct(String error);

        void showProductAddedCorrectly(String name);

        void showCheckoutResult(String shoppingListCalculated);
    }
}
