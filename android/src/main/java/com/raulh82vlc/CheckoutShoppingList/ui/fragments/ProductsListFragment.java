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

package com.raulh82vlc.CheckoutShoppingList.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.raulh82vlc.CheckoutShoppingList.R;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.ConnectionException;
import com.raulh82vlc.CheckoutShoppingList.domain.exceptions.HttpException;
import com.raulh82vlc.CheckoutShoppingList.domain.models.ProductUI;
import com.raulh82vlc.CheckoutShoppingList.ui.activities.ProductsListActivity;
import com.raulh82vlc.CheckoutShoppingList.ui.adapters.ProductsListAdapter;
import com.raulh82vlc.CheckoutShoppingList.ui.presentation.ProductsPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * <p>Comics List Fragment which uses all injected views or components</p>
 * <p>first of all when the activity is created the component,
 * as well as presenter and view injections for each UI element</p>
 *
 * @author Raul Hernandez Lopez
 */
public class ProductsListFragment extends BaseFragment implements
        ProductsPresenter.View,
        ProductsListAdapter.OnItemClickListener {

    /**
     * UI injections
     */
    @InjectView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @InjectView(R.id.no_results_view)
    public TextView noResultsTextView;

    @OnClick(R.id.check_out)
    public void onCheckOut() {
        productsPresenter.checkOutShoppingList();
    }

    /**
     * DI
     */
    @Inject
    ProductsPresenter productsPresenter;

    // UI Widgets
    private ProductsListAdapter adapter;
    private ProductsListActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ProductsListActivity) context;
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity.component().inject(this);
        productsPresenter.setView(this);
        productsPresenter.initInitialVisibility();
        try {
            productsPresenter.getProducts();
        } catch (ConnectionException | HttpException e) {
            productsPresenter.raiseException(e.getMessage());
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
    }

    @Override
    public void onDestroyView() {
        productsPresenter.resetView();
        adapter = null;
        super.onDestroyView();
    }

    /**
     * <p>Sets the adapter and recyclerview</p>
     **/
    private void setRecyclerView() {
        adapter = new ProductsListAdapter();
        adapter.setOnItemClickFromList(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setUIWidgetsVisibility(int visible, int gone) {
        noResultsTextView.setVisibility(visible);
        recyclerView.setVisibility(gone);
    }

    @Override
    public void showInitialVisibility() {
        setUIWidgetsVisibility(View.VISIBLE, View.GONE);
    }

    @Override
    public void errorGettingProducts(String error) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemFromListClick(ProductUI productUI, View view) {
        productsPresenter.addToShoppingList(productUI);
    }

    @Override
    public void updateProductsList(List<ProductUI> comicsList) {
        adapter.updateProducts(comicsList);
    }

    @Override
    public void showList() {
        setUIWidgetsVisibility(View.GONE, View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        activity.hideLoader();
    }

    @Override
    public void showAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            recyclerView.scheduleLayoutAnimation();
        }
    }

    @Override
    public void showLoader() {
        activity.showLoaderWithTitleAndDescription("", getString(R.string.loading));
    }

    @Override
    public void showEmptyState() {
        noResultsTextView.setText(getString(R.string.no_results));
    }

    @Override
    public void showErrorMessage(String error) {
        Timber.e(error);
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isReady() {
        return isAdded();
    }

    @Override
    public void hideListShowText() {
        setUIWidgetsVisibility(View.VISIBLE, View.GONE);
    }

    @Override
    public void errorAddingProduct(String error) {
        Toast.makeText(activity, getString(R.string.no_added_product, error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProductAddedCorrectly(String name) {
        Toast.makeText(activity, getString(R.string.added_product, name), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCheckoutResult(String shoppingListCalculated) {
        createDialogWithCheckout(shoppingListCalculated);
    }

    private void createDialogWithCheckout(String shoppingListCalculated) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(getString(R.string.checkout_result));
        alertDialog.setMessage(shoppingListCalculated);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_btn),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
