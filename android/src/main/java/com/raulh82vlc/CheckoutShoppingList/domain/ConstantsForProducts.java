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

package com.raulh82vlc.CheckoutShoppingList.domain;

/**
 * Products constants
 * @author Raul Hernandez Lopez.
 */

public final class ConstantsForProducts {

    public final static int HTTP_OK_CODE = 200;
    // Maximum number of products to add to the basket
    public final static int LIMIT_OF_MY_SHOPPING = 100;
    // types of products
    public static final String VOUCHER_TYPE = "VOUCHER";
    public static final String TSHIRT_TYPE = "TSHIRT";
    public static final String MUG_TYPE = "MUG";
    // Promotion offer like: BUY 2
    public static final int BUY = 2;
    // GET FREE 1
    public static final int FREE = 1;
    // DISCOUNT FOR AN ITEM
    public static final int DISCOUNT_PRICE_PER_UNIT = 1;
    // WHEN BOUGHT 3 OR MORE
    public static final int LIMIT_FOR_APPLYING_DISCOUNT = 3;
    // NUMBER OF DECIMALS TO SHOW FORMAT
    public static final String FORMAT_FOR_DECIMALS = "%.2f";

    private ConstantsForProducts() {

    }
}
