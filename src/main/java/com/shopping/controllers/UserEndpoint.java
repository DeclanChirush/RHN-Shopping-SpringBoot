//Name : Malwatta H.G.
//ID : IT19240848

package com.shopping.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.api.UserApi;
import com.shopping.dal.model.User;
import com.shopping.dto.JwtResponseDto;
import com.shopping.dto.MessageResponseDto;
import com.shopping.dto.UserLoginDto;
import com.shopping.dto.UserRegisterDto;


@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*",exposedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class UserEndpoint {
	
	@Autowired
	UserApi userApi;
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> userRegister(@Valid @RequestBody UserRegisterDto userRegister) throws UnsupportedEncodingException, MessagingException {
		return userApi.registerUser(userRegister);	
	}
	
	
	@PostMapping("/sign-in")
	public ResponseEntity<?> userLogin(@Valid @RequestBody UserLoginDto userLogin){
		return userApi.authUserLogin(userLogin);
		
	}
	
	@GetMapping("/get-all-users")
	public List<User> getAllUser(){
		return userApi.getAllUserDetails();
	}
}
