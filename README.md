# microservices

## Controllers:

### Order service:

**/order/placeOrder:** PostMapping method which takes OrderRequest(dto) JSON object as request body and records the order in Order-db.
Product-db and Payment-db are updated accordingly.

**/order/orderDetails/{orderId}:** GetMapping method which takes orderId as PathVariable and returns information about the order from order-db and product-db.
