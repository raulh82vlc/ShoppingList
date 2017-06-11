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

import com.raulh82vlc.CheckoutShoppingList.di.scopes.ActivityScope;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.AddProductToShoppingListInteractor;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.AddProductToShoppingListInteractorImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.CheckoutShoppingListInteractor;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.CheckoutShoppingListInteractorImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.GetProductsInteractor;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.GetProductsInteractorImpl;
import com.raulh82vlc.CheckoutShoppingList.ui.presentation.ProductsPresenter;
import com.raulh82vlc.CheckoutShoppingList.ui.presentation.ProductsPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Module which provides all user required artifacts
 * (presenter, interactors)
 * in order to use them in a decoupled way
 *
 * @author Raul Hernandez Lopez
 */
@Module
public class ProductsListModule {

    @Provides
    @ActivityScope
    CheckoutShoppingListInteractor provideCheckoutInteractor(CheckoutShoppingListInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    AddProductToShoppingListInteractor provideAddProductInteractor(AddProductToShoppingListInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    GetProductsInteractor provideGetProductsInteractor(GetProductsInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    ProductsPresenter provideProductsPresenter(ProductsPresenterImpl presenter) {
        return presenter;
    }
}
