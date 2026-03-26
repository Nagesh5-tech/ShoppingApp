package com.example.app.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@ManyToOne
@JoinColumn(name = "user_id", nullable = false)
private User user;

@ManyToOne
@JoinColumn(name = "product_id", nullable = false)
private Product product;

private int quantity;

public CartItem() {
	super();
}

public CartItem(User user, Product product, int quantity) {
	super();
	this.user = user;
	this.product = product;
	this.quantity = quantity;
}

public CartItem(Integer id, User user, Product product, int quantity) {
	super();
	this.id = id;
	this.user = user;
	this.product = product;
	this.quantity = quantity;
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Product getProduct() {
	return product;
}

public void setProduct(Product product) {
	this.product = product;
}

public int getQuantity() {
	return quantity;
}

public void setQuantity(int quantity) {
	this.quantity = quantity;
}

@Override
public int hashCode() {
	return Objects.hash(id, product, quantity, user);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	CartItem other = (CartItem) obj;
	return Objects.equals(id, other.id) && Objects.equals(product, other.product) && quantity == other.quantity
			&& Objects.equals(user, other.user);
}

@Override
public String toString() {
	return "CartItem [id=" + id + ", user=" + user + ", product=" + product + ", quantity=" + quantity + "]";
}


}
