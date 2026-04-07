package com.example.app.AdminServices;

import com.example.app.entities.User;

import jakarta.transaction.Transactional;

public interface AdminUserServiceContract {
	
	@Transactional
	public User modifyUser(Integer userId, String username, String email, String role);
	public User getUserById(Integer userId);

}
