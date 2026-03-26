package com.example.app.entities;

import java.util.Objects;

import jakarta.persistence .*;

@Entity
@Table(name = "categories")
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer categoryId;

@Column(nullable = false, unique = true)
private String categoryName;

public Category(Integer categoryId, String categoryName) {
	super();
	this.categoryId = categoryId;
	this.categoryName = categoryName;
}

public Category(String categoryName) {
	super();
	this.categoryName = categoryName;
}

public Category() {
	super();
}

public Integer getCategoryId() {
	return categoryId;
}

public void setCategoryld(Integer categoryId) {
	this.categoryId = categoryId;
}

public String getCategoryName() {
	return categoryName;
}

public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}

@Override
public int hashCode() {
	return Objects.hash(categoryName, categoryId);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Category other = (Category) obj;
	return Objects.equals(categoryName, other.categoryName) && Objects.equals(categoryId, other.categoryId);
}

@Override
public String toString() {
	return "Category [categoryld=" + categoryId + ", categoryName=" + categoryName + "]";
}


}