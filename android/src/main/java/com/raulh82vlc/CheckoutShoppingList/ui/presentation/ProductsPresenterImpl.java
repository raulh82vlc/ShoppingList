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
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.AddProductToShoppingListInteractor;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.CheckoutShoppingListInteractor;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.GetProductsInteractor;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.mappers.ProductsListModelDataMapper;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors_response.AddProductToShoppingListCallbackImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors_response.CheckoutShoppingListCallbackImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors_response.GetProductsListCallbackImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductUI;

import javax.inject.Inject;

/**
 * Implementation of the {@link ProductsPresenter}
 *
 * @author Raul Hernandez Lopez
 */
public class ProductsPresenterImpl implements ProductsPresenter {

    private final GetProductsInteractor interactorGetProductsList;
    private final AddProductToShoppingListInteractor interactorAddProductToShoppingList;
    private final ProductsListModelDataMapper productsListModelDataMapper;
    private final CheckoutShoppingListInteractor interactorCheckOutShoppingList;
    private View view;

    @Inject
    ProductsPresenterImpl(
            GetProductsInteractor interactorGetProductsList,
            AddProductToShoppingListInteractor interactorAddProductToShoppingList,
            ProductsListModelDataMapper productsListModelDataMapper,
            CheckoutShoppingListInteractor interactorCheckOutShoppingList) {
        this.interactorGetProductsList = interactorGetProductsList;
        this.interactorAddProductToShoppingList = interactorAddProductToShoppingList;
        this.interactorCheckOutShoppingList = interactorCheckOutShoppingList;
        this.productsListModelDataMapper = productsListModelDataMapper;
    }

    @Override
    public void setView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("The view should be instantiated");
        }
        this.view = view;
    }

    @Override
    public void resetView() {
        view = null;
    }

    @Override
    public void raiseException(String message) {
        if (view.isReady()) {
            view.showErrorMessage(message);
        }
    }

    @Override
    public void initInitialVisibility() {
        if (view.isReady()) {
            view.showInitialVisibility();
        }
    }

    @Override
    public void addToShoppingList(ProductUI productUI) {
        if (view.isReady()) {
            view.showLoader();
            interactorAddProductToShoppingList.execute(productsListModelDataMapper.transform(productUI),
                    new AddProductToShoppingListCallbackImpl(view));
        }
    }

    @Override
    public void checkOutShoppingList() {
        if (view.isReady()) {
            view.showLoader();
            interactorCheckOutShoppingList.execute(new CheckoutShoppingListCallbackImpl(view));
        }
    }

    @Override
    public void getProducts() throws ConnectionException, HttpException {
        if (view.isReady()) {
            view.showLoader();
            interactorGetProductsList.execute(new GetProductsListCallbackImpl(view, productsListModelDataMapper));
        }
    }
}
