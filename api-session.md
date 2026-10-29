# API Session

### 1. Create Product (201 Created)
```bash
curl -i -X POST http://localhost:8080/api/products \
  -H 'Content-Type: application/json' \
  -d '{"name":"USB-C cable","price":7.90,"stock":25}'

curl -i -X POST http://localhost:8080/api/products \
  -H 'Content-Type: application/json' \
  -d '{"name":"","price":0,"stock":-1}'

curl -i http://localhost:8080/api/products

curl -i -X PATCH http://localhost:8080/api/products/6/stock \
  -H 'Content-Type: application/json' \
  -d '{"delta":-30}'

curl -i http://localhost:8080/api/products/999

Run `./mvnw test` in your project terminal to verify that all the MockMvc assertions pass cleanly.