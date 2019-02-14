package org.my.spring.cloud.examples.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableFeignClients
public class BusinessApp {
	public static void main(String[] args) {
		SpringApplication.run(BusinessApp.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@FeignClient(value = "account", url = "http://127.0.0.1:8001")
	public interface AccountService {

		@RequestMapping(path = "/account", method = RequestMethod.POST)
		String account(@RequestParam("userId") String userId,
				@RequestParam("money") int money);
	}
	
	@FeignClient(value = "order", url = "http://127.0.0.1:8002")
	public interface OrderService {

		@RequestMapping(path = "/order", method = RequestMethod.POST)
		String order(@RequestParam("userId") String userId,
				@RequestParam("commodityCode") String commodityCode,
				@RequestParam("orderCount") int orderCount);
	}
	
	@FeignClient(value = "product", url = "http://127.0.0.1:8003")
	public interface ProductService {

		@RequestMapping(path = "/storage/{commodityCode}/{count}")
		String product(@RequestParam("commodityCode") String commodityCode,
				@RequestParam("count") int count);
	}
}
