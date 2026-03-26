package com.example.app.userimplementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.entities.Category;
import com.example.app.entities.Product;
import com.example.app.entities.ProductImage;
import com.example.app.userrepositories.CategoryRepository;
import com.example.app.userrepositories.ProductImageRepository;
import com.example.app.userrepositories.ProductRepository;

@Service
public class ProductService {

@Autowired
private ProductRepository productRepository;

@Autowired
private ProductImageRepository productlmageRepository;

@Autowired
private CategoryRepository categoryRepository;



public List<Product> getProductsByCategory(String categoryName) {
if (categoryName != null && !categoryName.isEmpty()) {
Optional<Category> categoryOpt = categoryRepository.findByCategoryName(categoryName);
if (categoryOpt.isPresent()) {
Category category = categoryOpt.get();
return productRepository.findByCategory_CategoryId(category.getCategoryId());
} else {
throw new RuntimeException("Category not found");
} 
}
else {
return productRepository.findAll();
}
}



public List<String> getProductlmages(Integer productId) {
List<ProductImage> productImages = productlmageRepository.findByProduct_ProductId(productId);
List<String> imageUrls = new ArrayList<>();
for (ProductImage image : productImages) {
imageUrls.add(image.getImageUrl());
}

return imageUrls;
}
}
