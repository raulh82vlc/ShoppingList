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

import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Repository adding to the Shopping list basket
 * @author Raul Hernandez Lopez.
 */
public class ProductsRepositoryImplTest {

    private ProductsRepositoryImpl underTest;

    @Before
    public void setUp() {
        underTest = new ProductsRepositoryImpl(null);
    }

    @After
    public void tearDown() {
        underTest = null;
    }

    @Test
    public void addProductsToShoppingList() throws Exception {
        underTest.addProductToShoppingList(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 5f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 5f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 5f));
        assertEquals(3, underTest.getShoppingList().size());
        assertEquals(1, underTest.getProductsReferenceDictionary().size());
        assertEquals(1, underTest.getShoppingListDictionary().size());
    }

    @Test
    public void addProductsDifferentToShoppingList() throws Exception {
        underTest.addProductToShoppingList(new ProductDomain(ConstantsDomain.TSHIRT_TYPE, "TSHIRT", 5f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 10f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsDomain.TSHIRT_TYPE, "TSHIRT", 15f));
        assertEquals(3, underTest.getShoppingList().size());
        assertEquals(2, underTest.getProductsReferenceDictionary().size());
        assertEquals(2, underTest.getShoppingListDictionary().size());
    }

    @Test
    public void addProductsDifferentToShoppingListAllTypes() throws Exception {
        underTest.addProductToShoppingList(new ProductDomain(ConstantsDomain.TSHIRT_TYPE, "TSHIRT", 15f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 5f));
        underTest.addProductToShoppingList(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 10f));
        assertEquals(3, underTest.getShoppingList().size());
        assertEquals(3, underTest.getProductsReferenceDictionary().size());
        assertEquals(3, underTest.getShoppingListDictionary().size());
    }
}