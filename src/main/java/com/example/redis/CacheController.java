package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CacheController {

    private final CacheService cacheService;

    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping("/store/description/{id}")
    public String getStoreDescription(@PathVariable String id) {
        return cacheService.getStoreDescription(id);
    }

    @GetMapping("/store/addProducts/{id}")
    public Long addProductsToStore(@PathVariable String id) {
        return cacheService.addProductsToStore(id);
    }

    @GetMapping("/store/getProducts/{id}")
    public List<String> getProducts(@PathVariable String id) {
        return cacheService.getStoreProducts(id);
    }
}
