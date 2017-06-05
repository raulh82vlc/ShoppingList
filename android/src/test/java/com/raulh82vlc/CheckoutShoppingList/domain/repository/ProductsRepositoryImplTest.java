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

import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsForProducts;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

/**
 * Checks repository right behaviour
 *
 * @author Raul Hernandez Lopez.
 */
public class ProductsRepositoryImplTest {

    private static final double DELTA = 1e-15;

    private ProductsRepositoryImpl underTest;

    @Before
    public void setUp() {
        underTest = spy(new ProductsRepositoryImpl(null, new CheckoutStrategyImpl()));
    }

    @After
    public void tearDown() {
        underTest = null;
    }

    @Test
    public void checkoutCurrentShoppingListMoreComplex() throws Exception {
        // Adds products to the shopping list
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.VOUCHER_TYPE, "VOUCHER", 5.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.TSHIRT_TYPE, "TSHIRT", 20.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.VOUCHER_TYPE, "VOUCHER", 5.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.VOUCHER_TYPE, "VOUCHER", 5.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.MUG_TYPE, "MUG", 7.5f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.TSHIRT_TYPE, "TSHIRT", 20.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.TSHIRT_TYPE, "TSHIRT", 20.0f));
        // checks number of added products
        assertEquals(7, underTest.numberOfProductsOnBasket());
        // checks final result
        assertEquals(74.50f, underTest.getResultCheckOutSum(), DELTA);

        String expectedResult = "Items: VOUCHER, TSHIRT, VOUCHER, VOUCHER, MUG, TSHIRT, TSHIRT\n"
                + "Total: 74.50€";
        // checks expected format for the final check out to show to the user
        assertEquals(expectedResult, underTest.checkoutCurrentShoppingList());
    }

    @Test
    public void checkoutCurrentShoppingEmptyList() throws Exception {
        // checks number of added products
        assertEquals(0, underTest.numberOfProductsOnBasket());
        // checks final result
        assertEquals(0f, underTest.getResultCheckOutSum(), DELTA);

        String expectedResult = "Items: \n"
                + "Total: 0.00€";
        // checks expected format for the final check out to show to the user
        assertEquals(expectedResult, underTest.checkoutCurrentShoppingList());
    }

    @Test
    public void checkoutCurrentShoppingListSimple() throws Exception {
        // Adds products to the shopping list
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.VOUCHER_TYPE, "VOUCHER", 5.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.TSHIRT_TYPE, "TSHIRT", 20.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.MUG_TYPE, "MUG", 7.5f));
        // checks number of added products
        assertEquals(3, underTest.numberOfProductsOnBasket());
        // checks final result
        assertEquals(32.50f, underTest.getResultCheckOutSum(), DELTA);

        String expectedResult = "Items: VOUCHER, TSHIRT, MUG\n"
                + "Total: 32.50€";
        // checks expected format for the final check out to show to the user
        assertEquals(expectedResult, underTest.checkoutCurrentShoppingList());
    }

    @Test
    public void checkoutCurrentShoppingListSimple2() throws Exception {
        // Adds products to the shopping list
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.VOUCHER_TYPE, "VOUCHER", 5.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.TSHIRT_TYPE, "TSHIRT", 20.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.VOUCHER_TYPE, "VOUCHER", 5.0f));
        // checks number of added products
        assertEquals(3, underTest.numberOfProductsOnBasket());
        // checks final result
        assertEquals(25.00f, underTest.getResultCheckOutSum(), DELTA);

        String expectedResult = "Items: VOUCHER, TSHIRT, VOUCHER\n"
                + "Total: 25.00€";
        // checks expected format for the final check out to show to the user
        assertEquals(expectedResult, underTest.checkoutCurrentShoppingList());
    }

    @Test
    public void checkoutCurrentShoppingListSomeDiscount() throws Exception {
        // Adds products to the shopping list
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.TSHIRT_TYPE, "TSHIRT", 20.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.TSHIRT_TYPE, "TSHIRT", 20.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.TSHIRT_TYPE, "TSHIRT", 20.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.VOUCHER_TYPE, "VOUCHER", 5.0f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsForProducts.TSHIRT_TYPE, "TSHIRT", 20.0f));
        // checks number of added products
        assertEquals(5, underTest.numberOfProductsOnBasket());
        // checks final result
        assertEquals(81.00f, underTest.getResultCheckOutSum(), DELTA);

        String expectedResult = "Items: TSHIRT, TSHIRT, TSHIRT, VOUCHER, TSHIRT\n"
                + "Total: 81.00€";
        // checks expected format for the final check out to show to the user
        assertEquals(expectedResult, underTest.checkoutCurrentShoppingList());
    }
}