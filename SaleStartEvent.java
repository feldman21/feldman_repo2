package com.comp301.a08shopping.events;

import com.comp301.a08shopping.Product;
import com.comp301.a08shopping.Store;

public final class SaleStartEvent implements StoreEvent{
    private Product product;
    private Store store;

    public SaleStartEvent(Product product, Store store){
        this.product = product;
        this.store = store;
    }
    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public Store getStore() {
        return store;
    }
}
