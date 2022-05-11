//Name : Malwatta H.G.
//ID : IT19240848


package com.shopping.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.shopping.dal.adapter.UserDetailsImpl;
import com.shopping.dal.adapter.UserDetailsServiceImpl;
import com.shopping.dal.model.ERole;
import com.shopping.dal.model.EmailSender;
import com.shopping.dal.model.Role;
import com.shopping.dal.model.User;
import com.shopping.dal.repository.RoleMongoRepository;
import com.shopping.dal.repository.UserMongoRepository;
import com.shopping.dto.JwtResponseDto;
import com.shopping.dto.MessageResponseDto;
import com.shopping.dto.UserLoginDto;
import com.shopping.dto.UserRegisterDto;
import com.shopping.security.jwt.JwtUtils;

@Service
public class UserApi {

	@Autowired
	UserMongoRepository userRepository;

	@Autowired
	RoleMongoRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	EmailSender emailSender;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	// User registration method
	public MessageResponseDto registerUser(@Valid @RequestBody UserRegisterDto userRegister)
			throws UnsupportedEncodingException, MessagingException {

		if (userRepository.existsByUsername(userRegister.getUsername())) {
			return new MessageResponseDto("Username is already taken!");
		}

		if (userRepository.existsByEmail(userRegister.getEmail())) {
			return new MessageResponseDto("Email is already taken!");
		}

		// This is for check the program display correct values or not
		System.out.println(userRegister.getUsername());
		System.out.println(userRegister.getContactNo());
		System.out.println(userRegister.getEmail());
		System.out.println(userRegister.getPassword());
		System.out.println(userRegister.getUserType());

		// Create new user's account
		User user = new User(userRegister.getUsername(), userRegister.getContactNo(),
				passwordEncoder.encode(userRegister.getPassword()), userRegister.getEmail(),
				userRegister.getUserType());

		// Create new HashSet to store user Roles
		Set<Role> roles = new HashSet<>();

		// Check user role and assigned
		if (userRegister.getUserType().equals("buyer")) {

			// If it is true, Add ROLE_USER to that user
			Role userRole = roleRepository.findByName(ERole.ROLE_BUYER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);

		} else if (userRegister.getUserType().equals("seller")) {

			Role researcherRole = roleRepository.findByName(ERole.ROLE_SELLER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(researcherRole);

		} else {

			return new MessageResponseDto("Please select valid role!");

		}

		// send email to user
		emailSender.setEmail(userRegister.getEmail());
		emailSender.setUsername(userRegister.getUsername());
		emailSender.sendEmail();

		// set all roles to user object
		user.setRoles(roles);

		// Save all user details into the database
		userRepository.save(user);

		// return success MSG to frontEnd user is registered successfully
		return new MessageResponseDto("User registered successfully!");
	}

	// User authenticate and Login method
	public JwtResponseDto authUserLogin(@Valid @RequestBody UserLoginDto userLoginDto) {

		// Get user name and password and create new AuthenticationToken
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));

		// Set above assigned user credentials using Authentication object
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// After that create new JWT Token for that person
		String jwt = jwtUtils.generateJwtToken(authentication);

		// Then get authentication principles and set that UserDetailimpl object
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		// Get getAuthorities and set to List object
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		// This is for check the program display correct values or not
		System.out.println(userDetails.getUsername());
		System.out.println(userDetails.getPassword());
		System.out.println(jwt);
		System.out.println(roles.toString());

		// Return JWT response to FrontEnd
		return new JwtResponseDto(  jwt,
									userDetails.getId(), 
									userDetails.getUsername(), 
									userDetails.getEmail(), 
									roles);
	}

	public List<User> getAllUserDetails() {
		return new ArrayList<>(userDetailsServiceImpl.getAllUserDetails());
	}

}
