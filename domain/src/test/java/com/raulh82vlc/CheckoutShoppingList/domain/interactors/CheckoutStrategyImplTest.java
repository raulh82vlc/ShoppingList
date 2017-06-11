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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Raul Hernandez Lopez.
 */
public class CheckoutStrategyImplTest {
    private static final double DELTA = 1e-15;
    private CheckoutStrategyImpl checkoutStrategy;

    @Before
    public void setUp() {
        checkoutStrategy = new CheckoutStrategyImpl();
    }

    @Test
    public void applyDiscountsToTypeXperYMinimum2x1() throws Exception {
        assertEquals(5f, checkoutStrategy.applyDiscountsToTypeXperY(2, 5.0f, 2, 1), DELTA);
    }

    @Test
    public void applyDiscountsToTypeXperY() throws Exception {
        assertEquals(10f, checkoutStrategy.applyDiscountsToTypeXperY(3, 5.0f, 2, 1), DELTA);
    }

    @Test
    public void applyDiscountsToTypeXOrMore() throws Exception {
        assertEquals(57.0f, checkoutStrategy.applyDiscountsToTypeXOrMore(3, 20.0f, 3,
                ConstantsDomain.DISCOUNT_PRICE_PER_UNIT), DELTA);
    }

    @Test
    public void applyDiscountsToTypeXOrMoreNoDiscount() throws Exception {
        assertEquals(40.0f, checkoutStrategy.applyDiscountsToTypeXOrMore(2, 20.0f, 3,
                ConstantsDomain.DISCOUNT_PRICE_PER_UNIT), DELTA);
    }

    @Test
    public void applyNoDiscounts() throws Exception {
        assertEquals(37.5f, checkoutStrategy.applyNoDiscounts(5, 7.5f), DELTA);
    }
}