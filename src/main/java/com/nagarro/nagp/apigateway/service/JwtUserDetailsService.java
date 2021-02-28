package com.nagarro.nagp.apigateway.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import com.nagarro.nagp.apigateway.entity.Admin;
import com.nagarro.nagp.apigateway.entity.User;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public class JwtUserDetailsService {

	@Autowired
	private EurekaClient eurekaClient;

	@Resource(name = "restTemp")
	private RestTemplate restTemplate;

	String url;

	@HystrixCommand(fallbackMethod = "handleFallback")
	public User loadUserByUsername(String username) throws UsernameNotFoundException {

		url = "/users/" + username;
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("users", false);
		User user = restTemplate.getForObject(instance.getHomePageUrl() + url, User.class);
		return user;

	}

	@HystrixCommand(fallbackMethod = "handleFallback")
	public com.nagarro.nagp.apigateway.entity.Provider loadProviderByUsername(String username)
			throws UsernameNotFoundException {
		url = "/providers/" + username;
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("providers", false);
		com.nagarro.nagp.apigateway.entity.Provider provider = restTemplate
				.getForObject(instance.getHomePageUrl() + url, com.nagarro.nagp.apigateway.entity.Provider.class);
		return provider;

	}

	@HystrixCommand(fallbackMethod = "handleFallback")
	public Admin loadAdminByUsername(String username) throws UsernameNotFoundException {
		url = "/admins/" + username;
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("admins", false);
		Admin admin = restTemplate.getForObject(instance.getHomePageUrl() + url, Admin.class);
		return admin;
	}

	public EurekaClient getEurekaClient() {
		return eurekaClient;
	}

	public void setEurekaClient(EurekaClient eurekaClient) {
		this.eurekaClient = eurekaClient;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
