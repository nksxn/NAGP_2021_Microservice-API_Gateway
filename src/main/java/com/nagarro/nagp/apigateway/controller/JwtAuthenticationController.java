package com.nagarro.nagp.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.apigateway.config.JwtTokenUtil;
import com.nagarro.nagp.apigateway.entity.Admin;
import com.nagarro.nagp.apigateway.entity.JwtRequest;
import com.nagarro.nagp.apigateway.entity.JwtResponse;
import com.nagarro.nagp.apigateway.entity.Provider;
import com.nagarro.nagp.apigateway.entity.User;
import com.nagarro.nagp.apigateway.service.AdminService;
import com.nagarro.nagp.apigateway.service.JwtUserDetailsService;
import com.nagarro.nagp.apigateway.service.ProviderService;
import com.nagarro.nagp.apigateway.service.UserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	UserService userService;

	@Autowired
	ProviderService providerService;

	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		UserDetails userDetails = null;

		try {
			if (authenticationRequest.getRole().equals("User")) {
				userDetails = (UserDetails) jwtUserDetailsService
						.loadUserByUsername(authenticationRequest.getUsername());
			} else if (authenticationRequest.getRole().equals("Provider")) {
				userDetails = (UserDetails) jwtUserDetailsService
						.loadProviderByUsername(authenticationRequest.getUsername());
			} else if (authenticationRequest.getRole().equals("Admin")) {
				userDetails = (UserDetails) jwtUserDetailsService
						.loadAdminByUsername(authenticationRequest.getUsername());
			} else {
				System.out.println("Handle Exception if role is other than ");
			}
			final String token = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<Void> addUser(@RequestBody User user) {
		userService.addUser(user);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/provider/register", method = RequestMethod.POST)
	public ResponseEntity<Void> addProvider(@RequestBody Provider provider) {
		providerService.addProvider(provider);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/register", method = RequestMethod.POST)
	public ResponseEntity<Void> addAdmin(@RequestBody Admin admin) {
		adminService.addAdmin(admin);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
