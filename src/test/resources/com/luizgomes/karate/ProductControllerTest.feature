Feature: ProductController API

Background:
* url 'http://localhost:8085/api/products'

Scenario: Get all products
When method get
Then status 200
And match response == '#[]'

Scenario: Get product by ID with mock
Given path '1'
When method get
Then status 200
And match response.id == 1

Scenario: Create a new product with mock
Given request { "name": "Product A", "price": 100 }
When method post
Then status 201
And match response.name == 'Product A'

Scenario: Update an existing product with mock
Given path '1'
And request { "name": "Updated Product A", "price": 120 }
When method put
Then status 200
And match response.name == 'Updated Product A'

Scenario: Delete a product with mock
Given path '1'
When method delete
Then status 204
