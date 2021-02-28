package com.nagarro.nagp.apigateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.apigateway.entity.Admin;
import com.nagarro.nagp.apigateway.entity.Order;
import com.nagarro.nagp.apigateway.entity.Provider;
import com.nagarro.nagp.apigateway.entity.User;
import com.nagarro.nagp.apigateway.service.AdminService;
import com.nagarro.nagp.apigateway.service.OrderService;
import com.nagarro.nagp.apigateway.service.ProviderService;
import com.nagarro.nagp.apigateway.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/urbanClap")
public class HomeController {

	@Autowired
	UserService userService;

	@Autowired
	ProviderService providerService;

	@Autowired
	AdminService adminService;

	@Autowired
	OrderService orderService;

	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<List<User>>(userService.getAllUsers(), new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "/admins")
	public ResponseEntity<List<Admin>> getAllAdmins() {
		return new ResponseEntity<List<Admin>>(adminService.getAllAdmins(), new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "/providers")
	public ResponseEntity<List<Provider>> getAllProviders() {
		return new ResponseEntity<List<Provider>>(providerService.getAllProviders(), new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping(value = "/placeOrder")
	public ResponseEntity<Void> placeOrder(@RequestBody Order order) {
		orderService.placeOrder(order);
		return null;
	}

}
