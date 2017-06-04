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

package com.raulh82vlc.CheckoutShoppingList.ui.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raulh82vlc.CheckoutShoppingList.R;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductUI;
import com.raulh82vlc.CheckoutShoppingList.ui.viewholders.ProductViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Comics listing
 */
public class ProductsListAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<ProductUI> productsUIs = new ArrayList<>();

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            if (onItemClickListener != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onItemClickListener.onItemFromListClick((ProductUI) view.getTag(), view);
                    }
                }, 200);
            }
        }
    };

    public ProductsListAdapter() {
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemListView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(itemListView);
        productViewHolder.onSetClickListener(onClickListener);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder comicProductViewHolder, int position) {
        comicProductViewHolder.onBindView(productsUIs.get(position), comicProductViewHolder.itemView.getContext());
    }

    @Override
    public int getItemCount() {
        return productsUIs.size();
    }

    public void setOnItemClickFromList(OnItemClickListener onItemClickFromList) {
        onItemClickListener = onItemClickFromList;
    }

    public interface OnItemClickListener {
        void onItemFromListClick(ProductUI productUI, View view);
    }

    public void updateProducts(List<ProductUI> comicsUIList) {
        productsUIs.clear();
        productsUIs.addAll(comicsUIList);
        notifyDataSetChanged();
    }
}
