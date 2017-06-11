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

package com.raulh82vlc.CheckoutShoppingList.ui.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.raulh82vlc.CheckoutShoppingList.R;
import com.raulh82vlc.CheckoutShoppingList.domain.ConstantsAndroid;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductUI;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Product View Holder with onBindView & onSetClickListener functionalities
 *
 * @author Raul Hernandez Lopez
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.txt_code)
    public TextView typeOfProduct;

    @InjectView(R.id.txt_name)
    public TextView nameOfProduct;

    @InjectView(R.id.txt_price)
    public TextView priceOfProduct;

    public ProductViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    public void onBindView(ProductUI productUI, Context context) {
        typeOfProduct.setText(productUI.getTypeOfProduct());
        nameOfProduct.setText(productUI.getName());
        priceOfProduct.setText(context.getString(R.string.euro,
                String.format(Locale.UK, ConstantsAndroid.FORMAT_FOR_DECIMALS,
                productUI.getPrice())));
        itemView.setTag(productUI);
    }

    /**
     * Sets the corresponding listener
     * for a click {@link View.OnClickListener} to our {@link ProductViewHolder} item
     *
     * @param onClickListener {@link View.OnClickListener }
     */
    public void onSetClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}