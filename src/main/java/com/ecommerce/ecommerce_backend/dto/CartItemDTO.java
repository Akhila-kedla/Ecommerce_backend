package com.ecommerce.ecommerce_backend.dto;

public class CartItemDTO {

    private Long productId;
    private String productName;
    private double productPrice;
    private double quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getQuantity() {
        return quantity;
    }

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

    
}
