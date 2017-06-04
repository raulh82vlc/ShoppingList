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

package com.raulh82vlc.CheckoutShoppingList.domain.interactors.mappers;

import com.raulh82vlc.CheckoutShoppingList.di.scopes.ActivityScope;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.AddProductToShoppingListInteractor;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductUI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * As a Mapper, means it is a converter between different domains
 *
 * @author Raul Hernandez Lopez
 */
@ActivityScope
public class ProductsListModelDataMapper {

    @Inject
    ProductsListModelDataMapper() {

    }

    /**
     * Transforms a {@link ProductResponse} into a List of {@link ProductUI}
     *
     * @param productsList to be transformed.
     * @return List of {@link ProductUI}
     */
    public List<ProductUI> transform(List<ProductResponse> productsList) {
        if (productsList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        int size = productsList.size();
        List<ProductUI> productListUIs = null;
        if (size > 0) {
            productListUIs = new ArrayList<>(productsList.size());
            for (ProductResponse product : productsList) {
                productListUIs.add(
                        new ProductUI(
                                product.getProductCode(),
                                product.getProductName(),
                                product.getProductPrice()));
            }
        }
        return productListUIs;
    }

    public ProductDomain transform(ProductUI productUI) {
        return new ProductDomain(productUI.getTypeOfProduct(), productUI.getName(), productUI.getPrice());
    }
}
