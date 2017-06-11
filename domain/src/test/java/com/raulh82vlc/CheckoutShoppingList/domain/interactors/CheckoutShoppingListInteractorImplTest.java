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

package com.raulh82vlc.CheckoutShoppingList.interactors;

import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.InteractorExecutor;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.MainThread;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.CheckoutShoppingListInteractorImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.CheckoutStrategyImpl;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.ProductsRepository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Checks interactor right behaviour
 *
 * @author Raul Hernandez Lopez.
 */
public class CheckoutShoppingListInteractorImplTest {

    private static final double DELTA = 1e-15;
    @Mock
    private ProductsRepository repo;
    @Mock
    InteractorExecutor executor;
    @Mock
    MainThread mainThread;
    CheckoutStrategyImpl checkoutStrategy;
    private CheckoutShoppingListInteractorImpl underTest;
    Map<String, Float> productsReference = new HashMap<>();
    Map<String, Integer> shoppingListDictionary = new HashMap<>();

    @Before
    public void setUp() {
        initMocks(this);
        productsReference.put(ConstantsDomain.TSHIRT_TYPE, 20.0f);
        productsReference.put(ConstantsDomain.VOUCHER_TYPE, 5.0f);
        productsReference.put(ConstantsDomain.MUG_TYPE, 7.5f);
        underTest = spy(new CheckoutShoppingListInteractorImpl(executor, mainThread, repo, checkoutStrategy));
    }

    @After
    public void tearDown() {
        underTest = null;
        productsReference.clear();
    }

    @Test
    public void checkoutCurrentShoppingListMoreComplex() throws Exception {
        shoppingListDictionary.put(ConstantsDomain.TSHIRT_TYPE, 1);
        shoppingListDictionary.put(ConstantsDomain.MUG_TYPE, 1);
        shoppingListDictionary.put(ConstantsDomain.VOUCHER_TYPE, 1);
        // checks expected format for the final check out to show to the user
        assertEquals(32.50, underTest.getResultCheckOutSum(shoppingListDictionary, productsReference), DELTA);
    }

    @Test
    public void checkoutCurrentShoppingEmptyList() throws Exception {
        // checks number of added products
        Assert.assertEquals(0, repo.numberOfProductsOnBasket());

        String expectedResult = "Items: \n"
                + "Total: 0.00€";
        // checks expected format for the final check out to show to the user
        assertEquals(expectedResult, underTest.checkoutCurrentShoppingList());
    }

    @Test
    public void checkoutCurrentShoppingListSimple() throws Exception {
        // Adds products to the shopping list
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 5.0f));
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.TSHIRT_TYPE, "TSHIRT", 20.0f));
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 7.5f));
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
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 5.0f));
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.TSHIRT_TYPE, "TSHIRT", 20.0f));
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 5.0f));
        // checks number of added products
        Assert.assertEquals(3, repo.numberOfProductsOnBasket());
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
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.TSHIRT_TYPE, "TSHIRT", 20.0f));
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.TSHIRT_TYPE, "TSHIRT", 20.0f));
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.TSHIRT_TYPE, "TSHIRT", 20.0f));
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 5.0f));
        repo.addProductToShoppingList(new ProductDomain(ConstantsDomain.TSHIRT_TYPE, "TSHIRT", 20.0f));
        // checks number of added products
        Assert.assertEquals(5, repo.numberOfProductsOnBasket());
        // checks final result
        assertEquals(81.00f, underTest.getResultCheckOutSum(), DELTA);

        String expectedResult = "Items: TSHIRT, TSHIRT, TSHIRT, VOUCHER, TSHIRT\n"
                + "Total: 81.00€";
        // checks expected format for the final check out to show to the user
        assertEquals(expectedResult, underTest.checkoutCurrentShoppingList());
    }
}