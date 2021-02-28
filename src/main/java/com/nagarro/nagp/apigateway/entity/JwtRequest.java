package com.nagarro.nagp.apigateway.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String role;

}
