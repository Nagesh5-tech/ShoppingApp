package com.example.app.AdminServiceImplemetations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.app.AdminServices.AdminProductServiceContract;
import com.example.app.entities.Category;
import com.example.app.entities.Product;
import com.example.app.entities.ProductImage;
import com.example.app.userrepositories.CategoryRepository;
import com.example.app.userrepositories.ProductImageRepository;
import com.example.app.userrepositories.ProductRepository;
@Service
public class AdminProductService implements AdminProductServiceContract {
	
	
	private final ProductRepository productRepository;
	private final ProductImageRepository productImageRepository;
	private final CategoryRepository categoryRepository;
	
	
	

	public AdminProductService(ProductRepository productRepository, ProductImageRepository productImageRepository,
			CategoryRepository categoryRepository) {
		super();
		this.productRepository = productRepository;
		this.productImageRepository = productImageRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Product addProductWithImage(String name, String description, Double price, Integer stock, Integer categoryId,String imageUrl) {
		// Validate the category
		Category category = categoryRepository.findById(categoryId)
		        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

		// Create and save the product
		/*Product product = new Product();
		product.setName(name);
		product.setDescription(description);
		product.setPrice(BigDecimal.valueOf(price));
		product.setStock(stock);
		product.setCategory(category);
		product.setCreatedAt(LocalDateTime.now());
		product.setUpdatedAt(LocalDateTime.now());*/
		
		// Create product using constructor (CORRECT WAY)
	    Product product = new Product(name,description,BigDecimal.valueOf(price),stock,category,LocalDateTime.now(),LocalDateTime.now());
		Product savedProduct = productRepository.save(product);

		// Validate image (mandatory)
		if (imageUrl == null || imageUrl.isEmpty()) {
		    throw new IllegalArgumentException("Product image URL cannot be empty");
		}

		// Create and save the product image
		ProductImage productImage = new ProductImage();
		productImage.setProduct(savedProduct);
		productImage.setImageUrl(imageUrl);

		productImageRepository.save(productImage);

		return savedProduct;
	}

	@Override
	public void deleteProduct(Integer productId) {

	    // Check if product exists
	    if (!productRepository.existsById(productId)) {
	        throw new IllegalArgumentException("Product not found");
	    }

	    // Delete associated product images
	    productImageRepository.deleteByProductId(productId);

	    // Delete the product
	    productRepository.deleteById(productId);
	}

}
