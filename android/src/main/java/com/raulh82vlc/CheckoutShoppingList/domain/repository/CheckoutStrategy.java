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

/**
 *
 * Check out strategy to get different discounts applied to different list of shopping lists
 *
 * @author Raul Hernandez Lopez.
 */

public interface CheckoutStrategy {
    /**
     * <p>Depending the number of buy items are got certain others free</p>
     * <p>Formulae says to the total is substracted the positive integer given when dividing:
     * buyItemsStartsPromo by buyItemsStartsPromo, then multiplies to this integer value: freeItemsGot
     * and finally multiplying by price</p>
     * @param amountOfItems amount of items bought
     * @param price price of the item
     * @param buyItemsStartsPromo buy this amount of items to get a promo
     * @param freeItemsGot amount of free items got when buyItemsStartsPromo
     * @return amount calculated after discounts applied
     */
    float applyDiscountsToTypeXperY(int amountOfItems, float price, int buyItemsStartsPromo, int freeItemsGot);

    /**
     * <p>Depending if there are X amount of more bought, applied a discount to the price of each item</p>
     * @param amountOfItems amount of items bought
     * @param price price of the item
     * @param buyItemsStartsPromo buy this amount of items to get a promo
     * @param discount discount applied to the price
     * @return amount calculated after discounts applied
     */
    float applyDiscountsToTypeXOrMore(int amountOfItems, float price, int buyItemsStartsPromo, int discount);

    /**
     * Applies no discounts, simply calculates the quan
     * @param amountOfItems amount of items bought
     * @param price price of the item
     * @return amount calculated after discounts applied
     */
    float applyNoDiscounts(int amountOfItems, float price);
}
