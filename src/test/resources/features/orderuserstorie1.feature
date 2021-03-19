Feature: Get an order from a restaurant
	As a operator in the restaurant
	I want to get an order information from the restaurant using OrderNumber
	So that I can get info about the order

Scenario Outline: Attempt to Get an Order from the Restaurant
   Given The Order service is running and Order db has <existense> OrderNumber, <order>, in it
   When The Order Microservice receives a request for OrderNumber, <order>
   Then The Order Microservice should return <result> for: <order>

  Examples:
    | order    | existense | result   |
    | ORDER789 | existing  | ORDER789 |
    | ORDER689 | existing  | ORDER689 |
    | ORDER788 | non-exist | null     |
    | ORDER688 | non-exist | null     |
