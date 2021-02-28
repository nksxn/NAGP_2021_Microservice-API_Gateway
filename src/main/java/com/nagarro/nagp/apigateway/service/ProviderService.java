package com.nagarro.nagp.apigateway.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.nagp.apigateway.entity.Provider;
import com.nagarro.nagp.apigateway.wrapper.ProviderList;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Service
public class ProviderService {
	@Autowired
	private EurekaClient eurekaClient;

	@Resource(name = "restTemp")
	private RestTemplate restTemplate;

	public List<Provider> getAllProviders() {
		String url = "/providers";
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("providers", false);
		return restTemplate.getForObject(instance.getHomePageUrl() + url, ProviderList.class).getProviders();

	}

	public Provider addProvider(Provider provider) {
		String url = "/provider";
		HttpEntity<Provider> request = new HttpEntity<>(provider);
		InstanceInfo instance = eurekaClient.getNextServerFromEureka("providers", false);
		Provider response = restTemplate.postForObject(instance.getHomePageUrl() + url, request, Provider.class);
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
