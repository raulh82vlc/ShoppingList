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


import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.ConnectionException;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.HttpException;

import java.util.List;

/**
 * Products repository contract
 *
 * @author Raul Hernandez Lopez
 */
public interface ProductsRepository<C, P> {

    /**
     * to get Products List
     *
     * @throws ConnectionException
     * @throws HttpException
     **/
    List<C> getProductsList() throws ConnectionException, HttpException;

    boolean addProductToShoppingList(P productDomain);

    String checkoutCurrentShoppingList();
}
