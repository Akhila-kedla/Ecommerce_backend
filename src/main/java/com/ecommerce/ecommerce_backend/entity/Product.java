package com.ecommerce.ecommerce_backend.entity;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Product {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name ;
	
	@Column(length=2000)
	private String description;
	private double price;
	private double stock;
	private String category;
	private String imageurl;
	private double rating;
	
	 @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<CartItem> cartitems;
	 @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<OrderItem> orderitems;
	 public Long getId() {
		 return id;
	 }
	 public void setId(Long id) {
		 this.id = id;
	 }
	 public String getName() {
		 return name;
	 }
	 public void setName(String name) {
		 this.name = name;
	 }
	 public String getDescription() {
		 return description;
	 }
	 public void setDescription(String description) {
		 this.description = description;
	 }
	 public double getPrice() {
		 return price;
	 }
	 public void setPrice(double price) {
		 this.price = price;
	 }
	 public double getStock() {
		 return stock;
	 }
	 public void setStock(double d) {
		 this.stock = d;
	 }
	 public String getCategory() {
		 return category;
	 }
	 public void setCategory(String category) {
		 this.category = category;
	 }
	 public String getImageurl() {
		 return imageurl;
	 }
	 public void setImageurl(String imageurl) {
		 this.imageurl = imageurl;
	 }
	 public double getRating() {
		 return rating;
	 }
	 public void setRating(double rating) {
		 this.rating = rating;
	 }
	 public List<CartItem> getCartitems() {
		 return cartitems;
	 }
	 public void setCartitems(List<CartItem> cartitems) {
		 this.cartitems = cartitems;
	 }
	 public List<OrderItem> getOrderitems() {
		 return orderitems;
	 }
	 public void setOrderitems(List<OrderItem> orderitems) {
		 this.orderitems = orderitems;
	 }
	 
	 
	

}
