package com.comp301.a08shopping;

public class ProductImpl implements Product {

  private final String name;
  private final double basePrice;

  private final int inventory;
  private final double saleInfo;

  public ProductImpl(String name, double basePrice) {
    if (basePrice <= 0.0) {
      throw new IllegalArgumentException("invalid");
    }
    this.name = name;
    this.basePrice = basePrice;
    inventory = 0;
    saleInfo = 0.0;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public double getBasePrice() {
    return 0;
  }
}
