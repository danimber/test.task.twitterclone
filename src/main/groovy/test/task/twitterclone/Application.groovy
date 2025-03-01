package test.task.twitterclone

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@SpringBootApplication
class Application {

	static void main(String[] args) {
		SpringApplication.run(Application, args)
	}

	@Bean
	RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>()
		template.setConnectionFactory(redisConnectionFactory)
		template.setKeySerializer(new StringRedisSerializer())
		template.setValueSerializer(new StringRedisSerializer())
		return template
	}
}
