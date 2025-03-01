package test.task.twitterclone.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate

    void saveDataToRedis(String key, String value) {
        redisTemplate.opsForValue().set(key, value)
    }

    String getDataFromRedis(String key) {
        return redisTemplate.opsForValue().get(key) as String
    }
}