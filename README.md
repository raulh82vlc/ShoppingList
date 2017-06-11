# Checkout Shopping list [![Build Status](https://travis-ci.org/raulh82vlc/ShoppingList.svg?branch=master)](https://travis-ci.org/raulh82vlc/ShoppingList)
Checkout shopping list shows a list of product which can be added to the basket and making checkout with their discounts

This project uses Material design with *Clean architecture* by means of *Model-View-Presenter (MVP)* with *Repository pattern*
as well as `Dagger 2` for *Dependency Injection*, trying to respect *SOLID principles* as much as possible.

### Logo
![Logo](./art/shopping_list.png)

## Overview
At this open-source project, it is decoupled between `android` and `domain` modules or high level layers.

This means, `android` is strongly coupled with the Android framework and `domain` is decoupled from it, and can be re-used for other purposes when
required as is purely *Java* based, but not framework coupled.

Inside the those modules, there are some good practices being employed, for instance:
- There is an implementation of the *repository pattern* with a Network datasource (it could be extended to have others if required such as InternetDataSource and so on).

*Retrofit 2* is the Network library which uses *OkHttp* as its client, this makes calls to the API, transforming the responses to models, for this purpose and properly handling
the different requests on background threads with a pool of threads which passes their use cases result and avoids to lock the
UI thread.

## Architecture design overview
The exchange between the different *layers* is as follows:
- **Repository layer**:
  - from the models coming from a concrete *data source* to the *Repository* (repository is the responsible of managing from 1 to n datasources, in this case only network datasource)
  - from the *Repository* to their associated *Interactor* (or use case)
  - this *Repository* contains cached information for the shopping list content in RAM memory, being part of its implementation
- **Interactor layer**: from the *Interactor*, which is responsible of the *business logic* and communicating results to the *Presenter*
- **Presenter layer**: from the *Presenter*, which provides the final formatted info to a passive `View` from a UI element (fragments / activities).
Finally, this information would be passed through the UI thread.


### Material design
- This code test uses a wide range of Material design widgets from the Design support library such as:
- `AppBarLayout`, `CoordinatorLayout`, `Toolbar`, `RecyclerView` as well as Material theme styles.

### Features
- At the main screen a list of products which can be bought with their code, name and price
- Once clicked an item, the product is added to the list, and a toast is shown if correctly added
- For *checking out* and seeing the total basket price, click on the FAB button, this provides a final amount with discounts on it
- The *repository* contains cached information (RAM) for making an easy check out of the shopping list at any time
- For *removing* a previous shopping list (cached one), you need to entirely close the app, then the cache is clean
- Error handling integrated for `Http` or `Connection` or `IO` issues, also empty state indicating no results when required.
- A Loader is placed each time a new request is started, to indicate the user the fact that there is an action going on
- There is a small amount of Unit test cases for the most critical parts: mainly the strategy for discounts as well as repository applying of its strategy (unit tests).
- Discount 1: Applied in **2-for-1** promotions (buy two of the same product, get one free), and would like for there to be a 2-for-1 special on VOUCHER items.
- Discount 2: For bulk purchases (buying x or more of a product, the price of that product is reduced), and demands that if you buy **3 or more** TSHIRT items, the price per unit should be 19.00€.

### SDK support
Support SDKs from **16** to **25**

# Disclosure - Libraries used
- [Dagger 2](http://google.github.io/dagger) for Dependency Injection
- [ButterKnife](http://jakewharton.github.io/butterknife) v6.1.0 for Views Injection
- [Retrofit 2](https://github.com/square/retrofit) v2.1.0 for Network requests
- [OkHttp 3](https://github.com/square/okhttp) v3.6.0 as client for Retrofit
- [Timber](https://github.com/timber/timber) v4.1.2 for logs in debug mode only
- [Mockito](http://site.mockito.org/) for Mocking artifacts
- [JUnit](http://junit.org/) for Unit tests

# References (special thanks) - those are the same I indicated at my personal blog ([Insights and projects](https://raulh82vlc.github.io/Movies-Finder)): 
- [Uncle Bob: The Clean Architecture](https://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html) by Uncle Bob
- [The Repository pattern](https://msdn.microsoft.com/en-us/library/ff649690.aspx) by Microsoft
- [Effective Android UI](https://github.com/pedrovgs/EffectiveAndroidUI) by Pedro Gomez
- [Android Clean Architecture](https://github.com/android10/Android-CleanArchitecture) by Fernando Cejas

### Contributions
Please read first [CONTRIBUTING](./CONTRIBUTING.md)

## About the author
**Raul Hernandez Lopez**,
- [Insights and projects (Personal projects blog)](https://raulh82vlc.github.io)
- [@RaulHernandezL (Twitter)](https://twitter.com/RaulHernandezL)
- [raul.h82@gmail.com](mailto:raul.h82@gmail.com)

# License
```
Copyright (C) 2017 Raul Hernandez Lopez

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
