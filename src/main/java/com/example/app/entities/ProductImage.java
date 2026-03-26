package com.example.app.entities;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "productimages")
public class ProductImage {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer imageId;

@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
@JoinColumn(name = "product_id", nullable = false)
private Product product;

@Column(nullable = false, columnDefinition = "TEXT")
private String imageUrl;

public ProductImage() {
	super();
}

public ProductImage(Product product, String imageUrl) {
	super();
	this.product = product;
	this.imageUrl = imageUrl;
}

public ProductImage(Integer imageId, Product product, String imageUrl) {
	super();
	this.imageId = imageId;
	this.product = product;
	this.imageUrl = imageUrl;
}

public Integer getImageId() {
	return imageId;
}

public void setImageId(Integer imageId) {
	this.imageId = imageId;
}

public Product getProduct() {
	return product;
}

public void setProduct(Product product) {
	this.product = product;
}

public String getImageUrl() {
	return imageUrl;
}

public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}

@Override
public int hashCode() {
	return Objects.hash(imageId, imageUrl, product);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	ProductImage other = (ProductImage) obj;
	return Objects.equals(imageId, other.imageId) && Objects.equals(imageUrl, other.imageUrl)
			&& Objects.equals(product, other.product);
}

@Override
public String toString() {
	return "ProductImage [imageId=" + imageId + ", product=" + product + ", imageUrl=" + imageUrl + "]";
}



}
