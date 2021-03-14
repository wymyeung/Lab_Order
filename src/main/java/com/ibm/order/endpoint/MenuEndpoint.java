package com.ibm.order.endpoint;

import java.util.Collection;

import com.ibm.order.model.MenuItem;

public interface MenuEndpoint {
	
	public Collection<MenuItem> getMenuItems();

	public MenuItem getMenuItem(String menuItemNumber);
}
