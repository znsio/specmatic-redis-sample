package in.specmatic.redis.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.util.Objects.requireNonNull;

@Service
public class CacheService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public CacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Cacheable("myCache")
    public String getStoreDescription(String id) {
        return redisTemplate.opsForValue().get("Description : " + id);
    }

    @Cacheable("myCache")
    public Long addProductsToStore(String id) {
        String[] productsToAdd = {"912340", "956780"};
        return redisTemplate.opsForSet().add("Products : " + id, productsToAdd[0], productsToAdd[1]);
    }

    @Cacheable("myCache")
    public List<String> getStoreProducts(String id) {
        return new ArrayList<>(requireNonNull(redisTemplate.opsForZSet().reverseRange("Products : " + id, 0, currentTimeMillis())));
    }
}
