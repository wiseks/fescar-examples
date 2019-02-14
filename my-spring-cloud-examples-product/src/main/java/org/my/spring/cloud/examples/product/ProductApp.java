package org.my.spring.cloud.examples.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ProductApp {
	public static void main(String[] args) {
		SpringApplication.run(ProductApp.class, args);
	}
}
