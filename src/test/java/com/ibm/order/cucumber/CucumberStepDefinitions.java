package com.ibm.order.cucumber;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.springframework.web.client.RestTemplate;

import com.ibm.order.model.Order;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {

	private Order order;
	private RestTemplate restTemplate;

	public CucumberStepDefinitions() {
		this.restTemplate = new RestTemplate();
	}

	@Given("^The Order service is running and Order db has (.*?) OrderNumber, (.*?), in it$")
	public void orderServiceRunningOrderNumberInOrderDb(String Status, String OrderNumber) throws Exception {

	}

	@When("^The Order Microservice receives a request for OrderNumber, (.*?)$")
	public void orderServiceReceivesGetOrderRequest(String OrderNumber) throws IOException {
		String orderServiceRESTRequest = "http://localhost:9743/order/order/" + OrderNumber;
		this.order = this.restTemplate.getForObject(orderServiceRESTRequest, Order.class);
	}


	@Then("^The Order Microservice should return (.*?) for: (.*?)$")
	public void orderGetOrderReturnsOrder(String expectedResult, String OrderNumber) throws IOException {
	
		if(expectedResult.equals(OrderNumber)) {
			assertNotNull("Order data should not be null", this.order);

		}
		else {
			assertNull("No order data: ", this.order);
		}
	}

}
