package com.comp301.a08shopping.events;

import com.comp301.a08shopping.Product;
import com.comp301.a08shopping.Store;

public final class BackInStockEvent implements StoreEvent{

    private Product product;
    private Store store;
    public BackInStockEvent(Product product, Store store){
        this.product = product;
        this.store = store;
    }
    @Override
    public Product getProduct() {
        return null;
    }

    @Override
    public Store getStore() {
        return null;
    }
}
