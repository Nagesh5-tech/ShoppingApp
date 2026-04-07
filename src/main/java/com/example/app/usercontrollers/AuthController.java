package com.example.app.usercontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.DTOs.LoginRequest;
import com.example.app.entities.User;
import com.example.app.userimplementations.AuthService;
import com.example.app.userservices.AuthServiceContract;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthServiceContract authService;

    
    public AuthController(AuthServiceContract authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            String token = authService.generateToken(user);

            Cookie cookie = new Cookie("authToken", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // OK for localhost
            cookie.setPath("/");
            cookie.setMaxAge(3600);

            response.addCookie(cookie);

         

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("role", user.getRole().name());
            responseBody.put("username", user.getUsername());

            return ResponseEntity.ok(responseBody);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request,
                                                      HttpServletResponse response) {
        try {
            // Retrieve authenticated user
            User user = (User) request.getAttribute("authenticatedUser");

            // Handle unauthenticated case
            if (user == null) {
                return ResponseEntity
                        .status(401)
                        .body(Map.of("message", "User not authenticated"));
            }

            // Perform logout
            authService.logout(user);

            // Clear auth cookie
            Cookie cookie = new Cookie("authToken", null);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // ⚠ set true in production (HTTPS)
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            // Success response
            return ResponseEntity.ok(Map.of("message", "Logout successful"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body(Map.of("message", "Logout failed"));
        }
    }
}