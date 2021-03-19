package com.ibm.order.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ibm.order.endpoint.MenuEndpoint;
import com.ibm.order.model.MenuItem;
import com.ibm.order.model.Order;
import com.ibm.order.model.OrderMenuItem;
import com.ibm.order.repo.OrderRepo;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

class OrderServiceImplTest {

	@Mock
    private OrderRepo orderRepo;
	
	@Mock
	private MenuEndpoint menuEndpoint;

    @InjectMocks  
    private OrderServiceImpl orderService;
    
    @BeforeEach
    public void init() {
       MockitoAnnotations.initMocks(this);
    }
 
    @DisplayName("Test OrderService getOrder with valid Order Number")
    @Test
    void testGetOrder() {
        // Given - set the parameter values and mock the methods for this test case
        String orderID = "1988";
        List<OrderMenuItem> emptyMenu = new ArrayList<OrderMenuItem>();
        Order testOrder = new Order(orderID, "testCustomer", emptyMenu, 19.88);
        
        when(orderService.getOrder(orderID)).thenReturn(testOrder);
     
        // When - call the method being tested and save the response
        Order order = orderService.getOrder(orderID);
        
        // Then - check that the results are valid (and that the expected mocked methods were called)
        assertNotNull(order, "Order should not be null");
        assertEquals(order, testOrder, "Order should be the same as: " + testOrder);
     
        verify(orderRepo).findByOrderNumber(orderID);
   }
    
    @DisplayName("Test MenuEndpoint getMenuItem with valid Menu Number")
    @Test
    void testGetMenuItem() {
        // Given - set the parameter values and mock the methods for this test case
        String menuID = "MENU301";     
        MenuItem testMenu = new MenuItem(menuID, "testMenu","testMenuDesc","testMenuCategory",999, 19.88);
        
        when(menuEndpoint.getMenuItem(menuID)).thenReturn(testMenu);
     
        // When - call the method being tested and save the response
        MenuItem menu = menuEndpoint.getMenuItem(menuID);
        
        // Then - check that the results are valid (and that the expected mocked methods were called)
        assertNotNull(menu, "Menu should not be null");
        assertEquals(menu, testMenu, "Order should be the same as: " + testMenu);
     
        verify(menuEndpoint).getMenuItem(menuID);
   }
}
