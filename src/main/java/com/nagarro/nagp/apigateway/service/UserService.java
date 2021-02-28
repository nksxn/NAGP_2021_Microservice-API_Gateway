package com.nagarro.nagp.apigateway.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.nagp.apigateway.entity.User;
import com.nagarro.nagp.apigateway.wrapper.UserList;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Service
public class UserService {

	@Autowired
	private EurekaClient eurekaClient;

	@Resource(name = "restTemp")
	private RestTemplate restTemplate;

	public User addUser(User user) {
		String url = "/users";
		HttpEntity<User> request = new HttpEntity<>(user);
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("users", false);
		User response = restTemplate.postForObject(instance.getHomePageUrl() + url, request, User.class);
		return response;
	}

	public List<User> getAllUsers() {
		String url = "/users";
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("users", false);
		return restTemplate.getForObject(instance.getHomePageUrl() + url, UserList.class).getUsers();

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
