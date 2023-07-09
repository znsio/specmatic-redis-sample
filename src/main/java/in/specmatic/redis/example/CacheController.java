package in.specmatic.redis.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CacheController {

    private final StoreService storeService;

    @Autowired
    public CacheController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/stores/{id}/description")
    public String getStoreDescription(@PathVariable String id) {
        return storeService.getStoreDescription(id);
    }

    @PostMapping("/stores/{id}/products")
    public HttpEntity<Long> addProductsToStore(@PathVariable String id, @RequestBody String productName) {
        return new ResponseEntity<>(storeService.addProductsToStore(productName, id), HttpStatus.CREATED);
    }

    @GetMapping("/stores/{id}/products")
    public List<String> getProduct(@PathVariable Long id, @RequestParam String key) {
        return storeService.findMatchingProducts(id, key);
    }

    @GetMapping("/stores/{store_id}/products/{id}/description")
    public String getProductDescription(@PathVariable(name="store_id") String storeId, @PathVariable(name="id") String productId) {
        return storeService.getProductDescription(storeId, productId);
    }
}
