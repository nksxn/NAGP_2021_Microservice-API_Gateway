package com.nagarro.nagp.apigateway.wrapper;

import java.util.List;

import com.nagarro.nagp.apigateway.entity.Provider;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderList {
	private List<Provider> providers;
}
