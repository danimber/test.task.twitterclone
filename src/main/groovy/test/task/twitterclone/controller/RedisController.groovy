package test.task.twitterclone.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import test.task.twitterclone.service.RedisService

@RestController
class RedisController {

    @Autowired
    RedisService redisService

    @PostMapping("/save")
    String saveData(@RequestParam("key") String key, @RequestParam("value") String value) {
        redisService.saveDataToRedis(key, value)
        return "Data saved"
    }

    @GetMapping("/get")
    String getData(@RequestParam("key") String key) {
        return redisService.getDataFromRedis(key) ?: "No data found"
    }
}
