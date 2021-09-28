package com.tong.common.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @author oran
 * @description redis配置类   extends CachingConfigurerSupport
 * @date created  2021/2/2 10:08
 */

@Slf4j
@Configuration
public class RedisConfig {


    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

//        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        log.info("redis缓存初始化成功->{}:{}", lettuceConnectionFactory.getHostName(), lettuceConnectionFactory.getPort());
        return redisTemplate;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return stringRedisTemplate;
    }

//    @Bean
//    public LettuceConnectionFactory lettuceConnectionFactory() {
//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//        genericObjectPoolConfig.setMaxIdle(maxIdle);
//        genericObjectPoolConfig.setMinIdle(minIdle);
//        genericObjectPoolConfig.setMaxTotal(maxActive);
//        genericObjectPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(100);
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setDatabase(database);
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
//        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//                .commandTimeout(Duration.ofMillis(timeout))
////                .shutdownTimeout(Duration.ofMillis(shutdowntimeout))
//                .poolConfig(genericObjectPoolConfig)
//                .build();
//
//        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
////        factory.setShareNativeConnection(true);
////        factory.setValidateConnection(false);
//        return factory;
//    }

//    @Bean
//    public CacheManager cacheManager(LettuceConnectionFactory factory) {
//        // 以锁写入的方式创建RedisCacheWriter对象
//        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
//        // 创建默认缓存配置对象
//        /* 默认配置，设置缓存有效期 1小时*/
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1));
//        /* 配置test的超时时间为120s*/
//        return RedisCacheManager
//                .builder(RedisCacheWriter.lockingRedisCacheWriter(factory))
//                .cacheDefaults(defaultCacheConfig)
//                .withInitialCacheConfigurations(singletonMap("test", RedisCacheConfiguration.defaultCacheConfig()
//                        .entryTtl(Duration.ofMinutes(120))
//                        .disableCachingNullValues()))
//                .transactionAware()
//                .build();
//    }

}
