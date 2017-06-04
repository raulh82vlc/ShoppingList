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

package com.raulh82vlc.CheckoutShoppingList.di.components;

import com.raulh82vlc.CheckoutShoppingList.di.modules.ActivityModule;
import com.raulh82vlc.CheckoutShoppingList.di.modules.ProductsListModule;
import com.raulh82vlc.CheckoutShoppingList.di.scopes.ActivityScope;
import com.raulh82vlc.CheckoutShoppingList.ui.activities.ProductsListActivity;
import com.raulh82vlc.CheckoutShoppingList.ui.fragments.ProductsListFragment;

import dagger.Component;

/**
 * ComicsListComponent is the main container of dependencies
 * linked to the activity context. Moreover, this component extends
 * {@link AbstractActivityComponent}, therefore the activity context
 * is provided from the abstract parent component.
 *
 * @author Raul Hernandez Lopez
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class,
                ProductsListModule.class
        })
public interface ProductsListComponent extends AbstractActivityComponent {
    void inject(ProductsListActivity productsListActivity);

    void inject(ProductsListFragment productsListFragment);
}
