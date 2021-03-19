Feature: Get an existing order from a restaurant
	As a operator in the restaurant
	I want to get an existing from the restaurant using its OrderNumber
	So that I can get info about the order

Scenario Outline: Get an existing Order from the Restaurant
   Given The Order service is running and Order db has OrderNumber, ORDER789, in it
   When The Order Microservice receives a request for OrderNumber, ORDER789
   Then The Order microservice should return order information for OrderNumber, ORDER789

   Given The Order service is running and Order db has OrderNumber, ORDER689, in it
   When The Order Microservice receives a request for OrderNumber, ORDER689
   Then The Order microservice should return order information for OrderNumber, ORDER689

Scenario: Try to get an Order that does not exist in the Restaurant
   Given Order service is running and Order db does not have OrderNumber, ORDER1874, in it
   When The Order Microservice receives a request for OrderNumber, ORDER1874
   Then The Order microservice should return no data for OrderNumber, ORDER1874
