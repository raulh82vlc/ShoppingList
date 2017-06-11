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
import com.raulh82vlc.CheckoutShoppingList.domain.executors.InteractorExecutor;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.MainThread;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.ProductsRepository;

import org.junit.After;
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
    private Map<String, Float> productsReference;
    private Map<String, Integer> shoppingListDictionary;

    @Before
    public void setUp() {
        initMocks(this);
        checkoutStrategy = new CheckoutStrategyImpl();
        productsReference = new HashMap<>();
        shoppingListDictionary = new HashMap<>();
        productsReference.put(ConstantsDomain.TSHIRT_TYPE, 20.0f);
        productsReference.put(ConstantsDomain.VOUCHER_TYPE, 5.0f);
        productsReference.put(ConstantsDomain.MUG_TYPE, 7.5f);
        underTest = spy(new CheckoutShoppingListInteractorImpl(executor, mainThread, repo, checkoutStrategy));
    }

    @After
    public void tearDown() {
        underTest = null;
        checkoutStrategy = null;
        productsReference.clear();
    }

    @Test
    public void checkoutCurrentShoppingEmptyList() throws Exception {
        // checks expected format for the final check out to show to the user
        assertEquals(0.0, underTest.getResultCheckOutSum(shoppingListDictionary, productsReference), DELTA);
    }

    @Test
    public void checkoutCurrentShoppingListSimple() throws Exception {

        shoppingListDictionary.put(ConstantsDomain.TSHIRT_TYPE, 1);
        shoppingListDictionary.put(ConstantsDomain.MUG_TYPE, 1);
        shoppingListDictionary.put(ConstantsDomain.VOUCHER_TYPE, 1);
        // checks expected format for the final check out to show to the user
        assertEquals(32.50, underTest.getResultCheckOutSum(shoppingListDictionary, productsReference), DELTA);
    }

    @Test
    public void checkoutCurrentShoppingListSimple2() throws Exception {
        shoppingListDictionary.put(ConstantsDomain.TSHIRT_TYPE, 1);
        shoppingListDictionary.put(ConstantsDomain.VOUCHER_TYPE, 2);
        // checks expected format for the final check out to show to the user
        assertEquals(25.00, underTest.getResultCheckOutSum(shoppingListDictionary, productsReference), DELTA);
    }

    @Test
    public void checkoutCurrentShoppingListSomeDiscount() throws Exception {
        shoppingListDictionary.put(ConstantsDomain.TSHIRT_TYPE, 4);
        shoppingListDictionary.put(ConstantsDomain.VOUCHER_TYPE, 1);
        // checks expected format for the final check out to show to the user
        assertEquals(81.00f, underTest.getResultCheckOutSum(shoppingListDictionary, productsReference), DELTA);
    }
}