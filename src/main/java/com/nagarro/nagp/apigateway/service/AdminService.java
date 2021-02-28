package com.nagarro.nagp.apigateway.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.nagp.apigateway.entity.Admin;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Service
public class AdminService {

	@Autowired
	private EurekaClient eurekaClient;

	@Resource(name = "restTemp")
	private RestTemplate restTemplate;

	public Admin addAdmin(Admin admin) {
		String url = "/admin";
		HttpEntity<Admin> request = new HttpEntity<>(admin);
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("admin", false);
		Admin response = restTemplate.postForObject(instance.getHomePageUrl() + url, request, Admin.class);
		return response;
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
