package in.specmatic.redis.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CacheController {

    private final StoreService storeService;

    @Autowired
    public CacheController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/store/description/{id}")
    public String getStoreDescription(@PathVariable String id) {
        return storeService.getStoreDescription(id);
    }

    @GetMapping("/store/addProducts/{id}")
    public Long addProductsToStore(@PathVariable String id) {
        return storeService.addProductsToStore(id);
    }

    @GetMapping("/store/getProducts/{id}")
    public List<String> getProducts(@PathVariable String id) {
        return storeService.getStoreProducts(id);
    }
}
