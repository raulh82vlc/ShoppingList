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

package com.raulh82vlc.CheckoutShoppingList.domain.models;

/**
 * Product used for repository (domain layer) purposes
 * @author Raul Hernandez Lopez.
 */

public class ProductDomain {

    private final String typeOfProduct;
    private final String name;
    private final float price;

    public ProductDomain(String typeOfProduct, String name, float price) {
        this.typeOfProduct = typeOfProduct;
        this.name = name;
        this.price = price;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
