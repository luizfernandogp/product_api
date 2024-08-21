Feature: OrderController API

  Background:
    * url 'http://localhost:8085/api/orders'

  Scenario: Get order by ID with mock
    Given path '1'
    When method get
    Then status 200
    And match response.id == 1

  Scenario: Get all orders with mock
    When method get
    Then status 200
    And match response[0].id == 1
    And match response[1].id == 2

  Scenario: Create a new order with mock
    Given request { "customerName": "New Order" }
    When method post
    Then status 201
    And match response.customerName == "New Order"

  Scenario: Update an existing order with mock
    Given path '1'
    And request { "customerName": "Updated Order" }
    When method put
    Then status 200
    And match response.customerName == "Updated Order"

  Scenario: Delete an order by ID with mock
    Given path '1'
    When method delete
    Then status 204
