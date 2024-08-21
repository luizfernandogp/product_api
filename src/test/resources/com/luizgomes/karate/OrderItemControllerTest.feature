Feature: OrderItemController API

  Background:
    * url 'http://localhost:8085/api/order-items'

  Scenario: Get all order items
    When method get
    Then status 200
    And match response == '#[]'

  Scenario: Get order item by ID with mock
    Given path '1'
    When method get
    Then status 200
    And match response.id == 1

  Scenario: Create a new order item with mock
    Given request { "orderId": 1, "productId": 1, "quantity": 2 }
    When method post
    Then status 201
    And match response.order.id == 1
    And match response.product.id == 1
    And match response.quantity == 2

  Scenario: Create a new order item with mock2
    Given request { "orderId": 10, "productId": 5, "quantity": 2 }
    When method post
    Then status 201
    And match response.order.id == 10
    And match response.product.id == 5
    And match response.quantity == 2

  Scenario: Update an existing order item with mock
    Given path '1'
    And request { "orderId": 1, "productId": 1, "quantity": 3 }
    When method put
    Then status 200
    And match response.quantity == 3

  Scenario: Delete an order item with mock
    Given path '1'
    When method delete
    Then status 204
