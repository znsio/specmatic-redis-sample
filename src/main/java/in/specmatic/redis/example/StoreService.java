package in.specmatic.redis.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.util.Objects.requireNonNull;

@Service
public class StoreService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public StoreService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getStoreDescription(String id) {
        return redisTemplate.opsForValue().get("Description-" + id);
    }

    public Long addProductsToStore(String productName, String storeId) {
        return redisTemplate.opsForList().rightPush("Products-" + storeId, productName);
    }

    public List<String> findMatchingProducts(Long storeId, String key) {
        return new ArrayList<>(requireNonNull(redisTemplate.opsForZSet().reverseRange("Stores-" + storeId + "-Products-" + key, 0, currentTimeMillis())));
    }

    public String getProductDescription(String storeId, String productId) {
        return redisTemplate.opsForValue().get("Stores-" + storeId + "-Description-" + productId);
    }
}
