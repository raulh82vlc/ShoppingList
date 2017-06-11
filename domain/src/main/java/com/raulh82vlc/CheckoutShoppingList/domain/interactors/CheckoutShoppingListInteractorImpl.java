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

import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.Interactor;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.InteractorExecutor;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.MainThread;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductResponse;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.ProductsRepository;

import java.util.List;
import java.util.Map;

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
    // Strategy to checkout with discounts or without any
    private CheckoutStrategy checkoutStrategy;

    @Inject
    public CheckoutShoppingListInteractorImpl(InteractorExecutor executor,
                                       MainThread mainThread,
                                       ProductsRepository repository,
                                       CheckoutStrategy checkoutStrategy) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
        this.checkoutStrategy = checkoutStrategy;
    }

    @Override
    public void execute(CheckoutShoppingListCallback callback) {
        this.callback = callback;
        executor.run(this);
    }

    @Override
    public void run() {
        //there is no error case, because despite this is with an empty basket, says 0.00€
        notifySuccessfullyCheckedOut(
                getResultCheckOutSum(
                        repository.getShoppingListDictionary(),
                        repository.getProductsReferenceDictionary()),
                repository.getShoppingList());

    }

    /**
     * Checks proper business logic calculations with and without discounts
     * @param shoppingListDictionary product amounts per product type
     * @param referenceProductListDictionary product price per product type
     * @return total amount calculated
     */
    protected float getResultCheckOutSum(Map<String, Integer> shoppingListDictionary,
                                         Map<String, Float> referenceProductListDictionary) {
        float resultCheckOutSum = 0f;
        for (Map.Entry<String, Integer> setOfValues : shoppingListDictionary.entrySet()) {
            switch (setOfValues.getKey()) {
                case ConstantsDomain.VOUCHER_TYPE:
                    resultCheckOutSum += checkoutStrategy.applyDiscountsToTypeXperY(setOfValues.getValue(),
                            referenceProductListDictionary.get(ConstantsDomain.VOUCHER_TYPE),
                            ConstantsDomain.BUY,
                            ConstantsDomain.FREE);
                    break;
                case ConstantsDomain.TSHIRT_TYPE:
                    resultCheckOutSum += checkoutStrategy.applyDiscountsToTypeXOrMore(setOfValues.getValue(),
                            referenceProductListDictionary.get(ConstantsDomain.TSHIRT_TYPE),
                            ConstantsDomain.LIMIT_FOR_APPLYING_DISCOUNT,
                            ConstantsDomain.DISCOUNT_PRICE_PER_UNIT);
                    break;
                case ConstantsDomain.MUG_TYPE:
                default:
                    resultCheckOutSum += checkoutStrategy.applyNoDiscounts(setOfValues.getValue(),
                            referenceProductListDictionary.get(ConstantsDomain.MUG_TYPE));
                    break;
            }
        }
        return resultCheckOutSum;
    }

    /**
     * <p>Notifies to the UI (main) thread the result of checkout,
     * and sends a callback the string</p>
     */
    private void notifySuccessfullyCheckedOut(final float shoppingListCalculated,
                                              final List<ProductDomain> shoppingList) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCheckoutOK(shoppingListCalculated, shoppingList);
            }
        });
    }
}
