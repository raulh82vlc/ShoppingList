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

import android.app.Application;
import android.content.Context;

import com.raulh82vlc.CheckoutShoppingList.CheckoutShoppingListApp;
import com.raulh82vlc.CheckoutShoppingList.di.modules.ApplicationModule;
import com.raulh82vlc.CheckoutShoppingList.di.modules.ProductsRepositoryModule;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.InteractorExecutor;
import com.raulh82vlc.CheckoutShoppingList.domain.executors.MainThread;
import com.raulh82vlc.CheckoutShoppingList.domain.interactors.CheckoutStrategy;
import com.raulh82vlc.CheckoutShoppingList.domain.repository.ProductsRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ApplicationComponent is the top level component for this architecture.
 * It provides generic dependencies such as
 * {@link MainThread} or {@link InteractorExecutor}
 * and makes them available to sub-components and other external dependant classes.
 * <p/>
 * Scope {@link Singleton} is used to limit dependency instances across whole execution.
 *
 * @author Raul Hernandez Lopez
 */
@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                ProductsRepositoryModule.class
        })
public interface ApplicationComponent {

    /**
     * Injections for the dependencies
     */
    void inject(CheckoutShoppingListApp app);

    void inject(Context context);

    /**
     * Used in child components
     */
    Application application();

    /**
     * Background processes executor (interactors use this)
     */
    InteractorExecutor threadExecutor();

    /**
     * Direct contact to UI thread
     */
    MainThread mainThread();

    /**
     * Direct contact to repo
     */
    ProductsRepository dataRepo();

    /**
     * Direct contact to checkoutStrategy
     */
    CheckoutStrategy checkoutStrategy();
}
