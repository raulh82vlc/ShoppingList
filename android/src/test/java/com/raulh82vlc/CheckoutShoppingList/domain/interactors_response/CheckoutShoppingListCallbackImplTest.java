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

package com.raulh82vlc.CheckoutShoppingList.domain.interactors_response;

import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsDomain;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductDomain;
import com.raulh82vlc.CheckoutShoppingList.ui.presentation.ProductsPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Checkout shopping list output
 * @author Raul Hernandez Lopez.
 */
public class CheckoutShoppingListCallbackImplTest {

    @Mock
    ProductsPresenter.View view;

    private CheckoutShoppingListCallbackImpl underTest;
    private List<ProductDomain> shoppingList;

    @Before
    public void setUp() {
        initMocks(this);
        shoppingList = new ArrayList<>();
        underTest = new CheckoutShoppingListCallbackImpl(view);
    }

    @After
    public void tearDown() {
        shoppingList.clear();
    }

    @Test
    public void getCheckoutFormatted() throws Exception {
        shoppingList.add(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 5.0f));
        shoppingList.add(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 15.0f));
        shoppingList.add(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 5.0f));
        String output = "Items: MUG, VOUCHER, MUG"
         + "\nTotal: 20.00€";
        assertEquals(output, underTest.getCheckoutFormatted(20.f, shoppingList));
    }

    @Test
    public void getCheckoutFormattedComplex() throws Exception {
        shoppingList.add(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 2.0f));
        shoppingList.add(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 5.0f));
        shoppingList.add(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 5.0f));
        shoppingList.add(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 5.0f));
        shoppingList.add(new ProductDomain(ConstantsDomain.VOUCHER_TYPE, "VOUCHER", 5.0f));
        shoppingList.add(new ProductDomain(ConstantsDomain.MUG_TYPE, "MUG", 2.0f));
        String output = "Items: MUG, VOUCHER, VOUCHER, VOUCHER, VOUCHER, MUG"
                + "\nTotal: 19.00€";
        assertEquals(output, underTest.getCheckoutFormatted(19.f, shoppingList));
    }
}