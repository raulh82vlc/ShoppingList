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

package com.raulh82vlc.CheckoutShoppingList.domain.repository;

import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsAndroid;
import com.raulh82vlc.CheckoutShoppingList.domain.datasources.net.NetDataSourceImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.ConnectionException;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.HttpException;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.datasources.NetDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


/**
 * <p>Implements {@link ProductsRepository} and allows to have one or more Data source
 * like {@link NetDataSource} or one another on it</p>
 *
 * @author Raul Hernandez Lopez
 */
public class ProductsRepositoryImpl implements ProductsRepository<ProductResponse, ProductDomain> {

    private NetDataSource<ProductResponse> netDataSource;
    // List of products on the basket of the shopping list (limited size)
    private List<ProductDomain> shoppingList = new ArrayList<>(ConstantsAndroid.LIMIT_OF_MY_SHOPPING);
    // Dictionary of shopping products added by type (type, number on basket)
    private Map<String, Integer> shoppingListDictionary = new HashMap<>();
    // Dictionary reference of products (code, price)
    private Map<String, Float> referenceProductListDictionary = new HashMap<>();

    @Inject
    ProductsRepositoryImpl(NetDataSourceImpl netDataSource) {
        this.netDataSource = netDataSource;
    }

    @Override
    public List<ProductResponse> getProductsList() throws ConnectionException, HttpException {
        return netDataSource.getProductsList();
    }

    @Override
    public boolean addProductToShoppingList(ProductDomain productDomain) {
        boolean isAdded = false;
        if (ConstantsAndroid.LIMIT_OF_MY_SHOPPING > shoppingList.size()) {
            shoppingList.add(productDomain);
            addToArticlesTypeCounter(productDomain);
            isAdded = true;
        }
        return isAdded;
    }

    protected int numberOfProductsOnBasket() {
        return shoppingList.size();
    }

    private void addToArticlesTypeCounter(ProductDomain productDomain) {
        Integer counter = shoppingListDictionary.get(productDomain.getTypeOfProduct());
        if (counter == null) {
            shoppingListDictionary.put(productDomain.getTypeOfProduct(), 1);
            referenceProductListDictionary.put(productDomain.getTypeOfProduct(), productDomain.getPrice());
        } else {
            shoppingListDictionary.put(productDomain.getTypeOfProduct(), ++counter);
        }
    }

    @Override
    public List<ProductDomain> getShoppingList() {
        return shoppingList;
    }

    @Override
    public Map<String, Integer> getShoppingListDictionary() {
        return shoppingListDictionary;
    }

    @Override
    public Map<String, Float> getProductsReferenceDictionary() {
        return referenceProductListDictionary;
    }
}