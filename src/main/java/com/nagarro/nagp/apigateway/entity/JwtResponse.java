package com.nagarro.nagp.apigateway.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String username;

	public JwtResponse(String jwttoken, String username) {
		this.jwttoken = jwttoken;
		this.username = username;
	}

}