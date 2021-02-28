package com.nagarro.nagp.apigateway.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private String firstName;

	private String lastName;

	private String phone;

	private String email;

	private String creationTime;

	private String modifiedTime;

	private String username;

	private String password;
}
