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

import com.raulh82vlc.CheckoutShoppingList.domain.interactors.GetProductsInteractor;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.mappers.ProductsListModelDataMapper;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductUI;
import com.raulh82vlc.CheckoutShoppingList.ui.presentation.ProductsPresenter;

import java.util.List;

/**
 * Get Products list by means of its callback, communicating towards its view, mapping resuts from the API
 * to the view
 * @author Raul Hernandez Lopez.
 */
public class GetProductsListCallbackImpl implements GetProductsInteractor.GetProductsListCallback  {

    private final ProductsPresenter.View view;
    private final ProductsListModelDataMapper productsListModelDataMapper;

    public GetProductsListCallbackImpl(ProductsPresenter.View view,
                                       ProductsListModelDataMapper productsListModelDataMapper) {
        this.view = view;
        this.productsListModelDataMapper = productsListModelDataMapper;
    }


    @Override
    public void onGetProductsListOK(List<ProductResponse> productList) {
        if (view.isReady()) {
            List<ProductUI> productUIList = productsListModelDataMapper.transform(productList);
            view.hideLoader();
            if (productUIList == null || productList.isEmpty()) {
                view.hideListShowText();
                view.showEmptyState();
            } else {
                view.showList();
                view.updateProductsList(productUIList);
                view.showAnimation();
            }
        }
    }

    @Override
    public void onGetProductsListKO(String error) {
        if (view.isReady()) {
            view.hideLoader();
            view.showEmptyState();
            view.errorGettingProducts(error);
        }
    }
}
