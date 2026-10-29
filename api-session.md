# API session: Challenge (order management)

Recorded against the app running locally (`mvnw spring-boot:run`, H2 in-memory, default profile).
Seed data: products 1 (Keyboard, 49.99, stock 10), 2 (Mouse, 25.00, stock 5), 3 (Cable, 10.00, stock 20); customers 1 (Alice), 2 (Bob). Tax rate `shop.tax-rate=0.10`.

## 1. Place an order: 201 Created

```
curl.exe -i -X POST localhost:8080/api/orders -H "Content-Type: application/json" -d "{\"customerId\":1,\"lines\":[{\"productId\":1,\"qty\":2},{\"productId\":3,\"qty\":1}]}"
```
```
HTTP/1.1 201
X-Request-Id: 2c5b85d5
Location: /api/orders/1
Content-Type: application/json

{"id":1,"status":"NEW","total":120.98,"lines":[{"productId":1,"qty":2,"unitPrice":49.99},{"productId":3,"qty":1,"unitPrice":10.00}]}
```
Total check: (2 x 49.99 + 10.00) x 1.10 = 120.978, rounded HALF_UP to 120.98.

## 2. Insufficient stock: 409 Conflict

```
curl.exe -i -X POST localhost:8080/api/orders -H "Content-Type: application/json" -d "{\"customerId\":1,\"lines\":[{\"productId\":1,\"qty\":2},{\"productId\":2,\"qty\":999}]}"
```
```
HTTP/1.1 409
X-Request-Id: 9434c260
Content-Type: application/problem+json

{"detail":"Insufficient stock for product 2","instance":"/api/orders","status":409,"title":"Conflict"}
```

## 3. Get one order: 200 OK

```
curl.exe -i localhost:8080/api/orders/1
```
```
HTTP/1.1 200
X-Request-Id: 72eee41e
Content-Type: application/json

{"id":1,"status":"NEW","total":120.98,"lines":[{"productId":1,"qty":2,"unitPrice":49.99},{"productId":3,"qty":1,"unitPrice":10.00}]}
```

## 4. List with filter and paging: 200 OK

```
curl.exe -i "localhost:8080/api/orders?customerId=1&status=NEW&page=0&size=10"
```
```
HTTP/1.1 200
X-Request-Id: d2ac3ad9
Content-Type: application/json

{"content":[{"id":1,"status":"NEW","total":120.98,"lines":[{"productId":1,"qty":2,"unitPrice":49.99},{"productId":3,"qty":1,"unitPrice":10.00}]}],"page":0,"size":10,"totalElements":1}
```

## 5. Cancel an order: 200 OK

```
curl.exe -i -X POST localhost:8080/api/orders/1/cancel
```
```
HTTP/1.1 200
X-Request-Id: 73c1abf8
Content-Type: application/json

{"id":1,"status":"CANCELLED","total":120.98,"lines":[{"productId":1,"qty":2,"unitPrice":49.99},{"productId":3,"qty":1,"unitPrice":10.00}]}
```

## 6. Cancel again: 409 Conflict

```
curl.exe -i -X POST localhost:8080/api/orders/1/cancel
```
```
HTTP/1.1 409
X-Request-Id: b03fa26e
Content-Type: application/problem+json

{"detail":"Order 1 cannot be cancelled (status CANCELLED)","instance":"/api/orders/1/cancel","status":409,"title":"Conflict"}
```

## 7. Unknown order: 404 Not Found

```
curl.exe -i localhost:8080/api/orders/999
```
```
HTTP/1.1 404
X-Request-Id: 2619a6b8
Content-Type: application/problem+json

{"detail":"Order 999 not found","instance":"/api/orders/999","status":404,"title":"Not Found"}
```

## 8. Validation error (empty lines): 400 Bad Request

```
curl.exe -i -X POST localhost:8080/api/orders -H "Content-Type: application/json" -d "{\"customerId\":1,\"lines\":[]}"
```
```
HTTP/1.1 400
X-Request-Id: 33f8174a
Content-Type: application/problem+json

{"detail":"Invalid request content.","instance":"/api/orders","status":400,"title":"Bad Request"}
```

## Audit log excerpt (same request ids as the X-Request-Id headers above)

```
12:38:24.313 INFO  [2c5b85d5] edu.itc.shop.audit.AuditAspect : AUDIT OrderService.place requestId=2c5b85d5 args=[PlaceOrder[customerId=1, lines=[Line[productId=1, qty=2], Line[productId=3, qty=1]]]] result=OrderDto[id=1, status=NEW, total=120.98, ...] (3 ms)
12:38:43.582 WARN  [9434c260] edu.itc.shop.audit.AuditAspect : AUDIT OrderService.place requestId=9434c260 args=[PlaceOrder[customerId=1, lines=[Line[productId=1, qty=2], Line[productId=2, qty=999]]]] failed=edu.itc.shop.order.InsufficientStockException: Insufficient stock for product 2 (0 ms)
12:39:12.420 INFO  [73c1abf8] edu.itc.shop.notify.LogNotifier : NOTIFY: Order 1 cancelled
12:39:12.421 INFO  [73c1abf8] edu.itc.shop.audit.AuditAspect : AUDIT OrderService.cancel requestId=73c1abf8 args=[1] result=OrderDto[id=1, status=CANCELLED, ...] (3 ms)
12:39:20.904 WARN  [b03fa26e] edu.itc.shop.audit.AuditAspect : AUDIT OrderService.cancel requestId=b03fa26e args=[1] failed=edu.itc.shop.order.OrderNotCancellableException: Order 1 cannot be cancelled (status CANCELLED) (1 ms)
```

## TODO (not recorded yet)

- Duplicate product in one order -> expect 400 "Duplicate product 1".
- Rollback proof: after the 409 in step 2, product 1 stock must still be 8, so ordering product 1 with qty 8 returns 201 and then qty 1 returns 409.
