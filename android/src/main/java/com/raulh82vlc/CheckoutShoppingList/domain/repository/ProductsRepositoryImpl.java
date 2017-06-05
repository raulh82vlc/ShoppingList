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

import android.support.annotation.NonNull;

import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsForProducts;
import com.raulh82vlc.CheckoutShoppingList.domain.datasources.net.NetDataSourceImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.ConnectionException;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.HttpException;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.datasources.NetDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    private CheckoutStrategy checkoutStrategy;
    private List<ProductDomain> shoppingList = new ArrayList<>(ConstantsForProducts.LIMIT_OF_MY_SHOPPING);
    private Map<String, Integer> shoppingListDictionary = new HashMap<>();
    private Map<String, Float> referenceProductListDictionary = new HashMap<>();

    @Inject
    ProductsRepositoryImpl(NetDataSourceImpl netDataSource,
                           CheckoutStrategyImpl checkOutStrategy) {
        this.netDataSource = netDataSource;
        this.checkoutStrategy = checkOutStrategy;
    }

    @Override
    public List<ProductResponse> getProductsList() throws ConnectionException, HttpException {
        return netDataSource.getProductsList();
    }

    @Override
    public boolean addProductToShoppingList(ProductDomain productDomain) {
        boolean isAdded = false;
        if (ConstantsForProducts.LIMIT_OF_MY_SHOPPING > shoppingList.size()) {
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
    public String checkoutCurrentShoppingList() {
        float resultCheckOutSum = getResultCheckOutSum();
        StringBuilder builder = getCheckoutFormatted(resultCheckOutSum);
        return builder.toString();
    }

    @NonNull
    private StringBuilder getCheckoutFormatted(float resultCheckOutSum) {
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
        builder.append(String.format(Locale.UK, ConstantsForProducts.FORMAT_FOR_DECIMALS, resultCheckOutSum));
        builder.append("€");
        return builder;
    }

    protected float getResultCheckOutSum() {
        float resultCheckOutSum = 0f;
        for (Map.Entry<String, Integer> setOfValues : shoppingListDictionary.entrySet()) {
            switch (setOfValues.getKey()) {
                case ConstantsForProducts.VOUCHER_TYPE:
                    resultCheckOutSum += checkoutStrategy.applyDiscountsToTypeXperY(setOfValues.getValue(),
                            referenceProductListDictionary.get(ConstantsForProducts.VOUCHER_TYPE),
                            ConstantsForProducts.BUY,
                            ConstantsForProducts.FREE);
                    break;
                case ConstantsForProducts.TSHIRT_TYPE:
                    resultCheckOutSum += checkoutStrategy.applyDiscountsToTypeXOrMore(setOfValues.getValue(),
                            referenceProductListDictionary.get(ConstantsForProducts.TSHIRT_TYPE),
                            ConstantsForProducts.LIMIT_FOR_APPLYING_DISCOUNT,
                            ConstantsForProducts.DISCOUNT_PRICE_PER_UNIT);
                    break;
                case ConstantsForProducts.MUG_TYPE:
                default:
                    resultCheckOutSum += checkoutStrategy.applyNoDiscounts(setOfValues.getValue(),
                            referenceProductListDictionary.get(ConstantsForProducts.MUG_TYPE));
                    break;
            }
        }
        return resultCheckOutSum;
    }
}
