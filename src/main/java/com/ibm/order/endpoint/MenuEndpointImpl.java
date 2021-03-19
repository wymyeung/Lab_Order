package com.ibm.order.endpoint;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ibm.order.model.MenuItem;
//import com.ibm.order.rest.OrderController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@Component
public class MenuEndpointImpl implements MenuEndpoint {

	// private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	RestTemplate restTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(MenuEndpointImpl.class);

	@Value("${menuServiceEndpoint.endpoint}")
	private String menuServiceEndpoint; //menuServiceEndpoint.endpoint=localhost:9742
	
	@Override
	@HystrixCommand(fallbackMethod = "getMenu_fallBack", commandKey = "endpointGetMenu", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75") })
	public MenuItem getMenuItem(String menuItemNumber) {
		//trace 1
		MenuItem menuItem = null;

		String menuServiceEndpointURL = "http://" + menuServiceEndpoint + "/menu/menu/" + menuItemNumber;
		
		menuItem = this.restTemplate.getForObject(menuServiceEndpointURL, MenuItem.class);

		return menuItem;
	}
	
	public MenuItem getMenu_fallBack(String menuItemNumber) {
		logger.warn("!!!!!!!!!! IN FALLBACK.  getMenu_fallBack Menu Item Number = " + menuItemNumber);
		MenuItem cachedMenu = new MenuItem("Canned Dinner (fallback)", "Canned Dinner (fallback)", "Canned Dinner (fallback)", "Cached Menu", 19, 89.64);
		return cachedMenu;
	}

	@Override
	public Collection<MenuItem> getMenuItems() {
		// TODO Auto-generated method stub
		return null;
	}
}
