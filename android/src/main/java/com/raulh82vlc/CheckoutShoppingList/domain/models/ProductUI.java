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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Product adapted to UI needs
 *
 * @author Raul Hernandez Lopez.
 */

public class ProductUI implements Parcelable {

    private final String typeOfProduct;
    private final String name;
    private final float price;

    public ProductUI(String typeOfProduct, String name, float price) {
        this.typeOfProduct = typeOfProduct;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    public float getPrice() {
        return price;
    }

    private ProductUI(Parcel in) {
        typeOfProduct = in.readString();
        name = in.readString();
        price = in.readFloat();
    }

    public static final Creator<ProductUI> CREATOR = new Creator<ProductUI>() {
        @Override
        public ProductUI createFromParcel(Parcel in) {
            return new ProductUI(in);
        }

        @Override
        public ProductUI[] newArray(int size) {
            return new ProductUI[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(typeOfProduct);
        dest.writeString(name);
        dest.writeFloat(price);
    }
}
