package com.nagarro.nagp.apigateway.wrapper;

import java.util.List;

import com.nagarro.nagp.apigateway.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserList {
	private List<User> users;
}
