package com.ibm.order.endpoint;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ibm.order.model.MenuItem;

@Component
public class MenuEndpointImpl implements MenuEndpoint {
	
	//private RestTemplate restTemplate;
	
	private RestTemplate restTemplate = new RestTemplate();
	/*public MenuEndpointImpl() {
		this.restTemplate = new RestTemplate();
	}*/
	
	@Value("${menuServiceEndpoint.endpoint}")
	private String menuServiceEndpoint; //menuServiceEndpoint.endpoint=localhost:9742
	

	
	@Override
	public MenuItem getMenuItem(String menuItemNumber) {
		
		MenuItem menuItem = null;

		String menuServiceEndpointURL = "http://" + menuServiceEndpoint + "/menu/menu/" + menuItemNumber;
		
		//menuItem = new MenuItem("M123", "Entree", "Salmon Dinner", "Salmon with vegetables and rice", 8, 12.95);
		menuItem = this.restTemplate.getForObject(menuServiceEndpointURL, MenuItem.class);

		return menuItem;
	}

	@Override
	public Collection<MenuItem> getMenuItems() {
		// TODO Auto-generated method stub
		return null;
	}
}
