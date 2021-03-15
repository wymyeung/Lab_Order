package com.ibm.order.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.order.model.OrderInput;
import com.ibm.order.model.Order;
import com.ibm.order.model.OrderInputMenuItem;
import com.ibm.order.model.MenuItem;
import com.ibm.order.model.OrderMenuItem;

import com.ibm.order.repo.OrderRepo;
import com.ibm.order.endpoint.MenuEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements OrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private MenuEndpoint menuEndpoint;

	@Override
	public Order getOrder(String orderNumber) {

		Order order = null;

		logger.info("Entered OrderServiceImpl.getOrder().  orderNumber = {}", orderNumber);

		order = this.orderRepo.findByOrderNumber(orderNumber);
		logger.info("Leaving OrderServiceImpl.getOrder().  order = {}", order);

		return order;
	}

	@Override
	public List<Order> getOrders() {

		List<Order> orders = null;

		logger.info("Entered OrderServiceImpl.getOrders(). ");

		orders = this.orderRepo.findAll();
		logger.info("Leaving OrderServiceImpl.getOrders().  orders = {}", orders);

		return orders;
	}

	@Override
	public Order addOrder(OrderInput orderInput) {

		Order order = new Order();
		List<OrderMenuItem> orderMenuItems = new ArrayList<OrderMenuItem>();

		logger.info("Entered OrderServiceImpl.addOrder(). To add Order = {}", orderInput);

		order.setOrderMenuItems(orderMenuItems);
		order.setCustomerNumber(orderInput.getCustomerNumber()); // Input Customer Number

		OrderInputMenuItem orderInputMenuItem = null;
		OrderMenuItem orderMenuItem = null;
		MenuItem menuItem = null;
		double orderPrice = 0.0;

		Iterator<OrderInputMenuItem> iter = orderInput.getOrderInputMenuItems().iterator();

		while (iter.hasNext()) {

			orderInputMenuItem = iter.next();

			menuItem = this.menuEndpoint.getMenuItem(orderInputMenuItem.getMenuItemNumber()); // Retrieve menu item

			orderMenuItem = new OrderMenuItem();

			orderMenuItem.setMenuItemNumber(orderInputMenuItem.getMenuItemNumber()); // Input - menu item number
			orderMenuItem.setMenuName(menuItem.getName()); // get from menu endpoint
			orderMenuItem.setQuantityOfMenuItem(orderInputMenuItem.getQuantityOfMenuItem()); // Input - order quantity
			orderMenuItem.setPrice(menuItem.getPrice()); // get from menu endpoint

			orderMenuItems.add(orderMenuItem);

			orderPrice = orderPrice + menuItem.getPrice() * (orderInputMenuItem.getQuantityOfMenuItem());

			logger.debug("OrderServiceImpl.addOrder().MenuItem() MenuItem added to Order = {}", orderMenuItem);
		}

		order.setOrderPrice(orderPrice);

		order.setOrderNumber(orderInput.getOrderNumber());

		order = this.orderRepo.insert(order);
		logger.info("Leaving OrderServiceImpl.addOrder(). Added Order = {}", order);

		return order;
	}
}
