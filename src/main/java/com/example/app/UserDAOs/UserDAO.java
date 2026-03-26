package com.example.app.UserDAOs;

import java.util.Objects;

public class UserDAO {
	int userid;
	String username;
	String email;
	String role;
	public UserDAO(int userid, String username, String email, String role) {
		super();
		this.userid = userid;
		this.username = username;
		this.email = email;
		this.role = role;
	}
	public UserDAO(String username, String email, String role) {
		super();
		this.username = username;
		this.email = email;
		this.role = role;
	}
	public UserDAO() {
		super();
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, role, userid, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDAO other = (UserDAO) obj;
		return Objects.equals(email, other.email) && Objects.equals(role, other.role) && userid == other.userid
				&& Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "UserDAO [userid=" + userid + ", username=" + username + ", email=" + email + ", role=" + role + "]";
	}
	
	

}
