package edu.itc.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.itc.shop.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void restock(long fromId, long toId, int qty) {
        if (repo.updateStock(fromId, -qty) == 0) {
            throw new IllegalStateException("Cannot take " + qty + " from product " + fromId);
        }
        if (repo.updateStock(toId, qty) == 0) {
            throw new IllegalStateException("Target product " + toId + " not found");
        }
    }
}