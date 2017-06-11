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

import javax.inject.Inject;

/**
 * Check out strategy to calculate all kind of products prices when check out happens
 * @author Raul Hernandez Lopez.
 */

public class CheckoutStrategyImpl implements CheckoutStrategy {

    @Inject
    public CheckoutStrategyImpl() {

    }

    @Override
    public float applyDiscountsToTypeXperY(int amountOfItems, float price, int buyItemsStartsPromo, int freeItemsGot) {
        return (calculateBase(amountOfItems, price)) - ((amountOfItems / buyItemsStartsPromo) * freeItemsGot * price);
    }

    @Override
    public float applyDiscountsToTypeXOrMore(int amountOfItems, float price, int buyItemsStartsPromo, int discount) {
        float finalPrice = price;
        if (amountOfItems >= buyItemsStartsPromo) {
            finalPrice = price - discount;
        }
        return calculateBase(amountOfItems, finalPrice);
    }

    @Override
    public float applyNoDiscounts(int amountOfItems, float price) {
        return calculateBase(amountOfItems, price);
    }

    private float calculateBase(int amountOfItems, float price) {
        return amountOfItems * price;
    }
}
