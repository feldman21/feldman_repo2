package com.comp301.a08shopping;

import com.comp301.a08shopping.events.BackInStockEvent;
import com.comp301.a08shopping.events.PurchaseEvent;
import com.comp301.a08shopping.events.SaleEndEvent;
import com.comp301.a08shopping.events.SaleStartEvent;
import com.comp301.a08shopping.exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreImpl implements Store {

  private final String name;
  private final List<StoreObserver> observers;
  private final List<Product> products;

  private final HashMap<Product, Integer> inventory;
  private final HashMap<Product, Double> discounts;

  public StoreImpl(String name) {
    this.name = name;
    observers = new ArrayList<StoreObserver>();
    products = new ArrayList<Product>();
    inventory = new HashMap<>();
    discounts = new HashMap<>();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void addObserver(StoreObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(StoreObserver observer) {
    observers.remove(observer);

  }

  @Override
  public List<Product> getProducts() {
    List<Product> products_copy = new ArrayList<Product>();
    products_copy.addAll(products);
    return products_copy;
  }

  @Override
  public Product createProduct(String name, double basePrice, int inventory) {
    if (name == null || basePrice <= 0.0 || inventory == 0) {
      throw new IllegalArgumentException("invalid");
    }
    return new ProductImpl(name, basePrice);
  }

  @Override
  public ReceiptItem purchaseProduct(Product product) {
    if (!products.contains(product)) {
      throw new ProductNotFoundException("not found");
    }
    if (product == null) {
      throw new IllegalArgumentException("null");
    }
    int curr = inventory.get(product);
    inventory.put(product, curr - 1);
    PurchaseEvent purchase = new PurchaseEvent(product, new StoreImpl(name));
    return new ReceiptItemImpl(product.getName(), product.getBasePrice(), name);
  }

  @Override
  public void restockProduct(Product product, int numItems) {
    if (!products.contains(product)) {
      throw new ProductNotFoundException("not found");
    }
    if (product == null || numItems < 0) {
      throw new IllegalArgumentException("null");
    }
    int curr = inventory.get(product);
    inventory.put(product, curr + numItems);
    if (curr == 0) {
      BackInStockEvent back = new BackInStockEvent(product, new StoreImpl(name));
    }
  }

  @Override
  public void startSale(Product product, double percentOff) {
    if (!products.contains(product)) {
      throw new ProductNotFoundException("not found");
    }
    if (product == null || percentOff <= 0.0 || percentOff >= 1.0) {
      throw new IllegalArgumentException("null");
    }
    double curr = discounts.get(product);
    discounts.put(product, curr + percentOff);
    SaleStartEvent saleEvent = new SaleStartEvent(product, new StoreImpl(name));
  }

  @Override
  public void endSale(Product product) {
    if (!products.contains(product)) {
      throw new ProductNotFoundException("not found");
    }
    if (product == null) {
      throw new IllegalArgumentException("null");
    }
    double curr = discounts.get(product);
    discounts.put(product, 0.0);
    SaleEndEvent saleEvent = new SaleEndEvent(product, new StoreImpl(name));
  }

  @Override
  public int getProductInventory(Product product) {
    if (!products.contains(product)) {
      throw new ProductNotFoundException("not found");
    }
    if (product == null) {
      throw new IllegalArgumentException("null");
    }
    return inventory.get(product);
  }

  @Override
  public boolean getIsInStock(Product product) {
      return getProductInventory(product) > 0;
  }

  @Override
  public double getSalePrice(Product product) {
    if (!products.contains(product)) {
      throw new ProductNotFoundException("not found");
    }
    if (product == null) {
      throw new IllegalArgumentException("null");
    }
    double sale = discounts.get(product);
    return Math.round(product.getBasePrice() * (1.0 - sale));
  }

  @Override
  public boolean getIsOnSale(Product product) {
    if (product == null || !products.contains(product)) {
      throw new ProductNotFoundException("invalid");
    }
      return getSalePrice(product) < product.getBasePrice();
  }
}
