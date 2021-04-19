# product_with_multi_currency_application
CRUD multi currency application using Fixer.io

REST API using Spring boot
A customer should be abl;e to perform CRUD operation for products. Product will be displayed with cost in different currencies like GBP, EUR, USD.
Integrated the application with currency exchange rate API (http://fixer.io). H2 inmemory database and JPA used to perform the CRUD operation.

Add a product using /products/addProduct.
Check all teh products available using /products/allProducts.
get ptoduct by id using /products/productById/{id}
Update a product using /products/update.
Delete a product using /products/delete.
