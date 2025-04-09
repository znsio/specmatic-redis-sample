package io.specmatic.redis.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/stores/{id}/description")
    public String getStoreDescription(@PathVariable(name = "id") String id) {
        return storeService.getStoreDescription(id);
    }

    @PostMapping("/stores/{id}/products")
    public HttpEntity<Long> addProductsToStore(@PathVariable(name = "id") String id, @RequestBody String productName) {
        return new ResponseEntity<>(storeService.addProductsToStore(productName, id), HttpStatus.CREATED);
    }

    @GetMapping("/stores/{id}/products")
    public List<String> getProduct(@PathVariable(name = "id") Long id, @RequestParam String key) {
        return storeService.findMatchingProducts(id, key);
    }

    @GetMapping("/stores/{store_id}/products/{id}/description")
    public String getProductDescription(@PathVariable(name = "store_id") String storeId, @PathVariable(name = "id") String productId) {
        return storeService.getProductDescription(storeId, productId);
    }
}
