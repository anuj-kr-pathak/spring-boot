package com.anuj.springboot.productrestapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;

@Configuration
public class ProductCacheConfig {

	@Bean("productCache")
	public Config cacheConfig() {
		/* hazel-instance is name of cache and product-cache is the name of cache */
		//setTimeToLiveSeconds --> how much time data will be in cache
		return new Config().setInstanceName("hazel-instance")
				.addMapConfig(new MapConfig().setName("product-cache")
						.setTimeToLiveSeconds(3000));
	}
}
