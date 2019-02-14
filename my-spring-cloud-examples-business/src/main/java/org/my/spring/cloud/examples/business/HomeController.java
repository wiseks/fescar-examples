package org.my.spring.cloud.examples.business;

import org.my.spring.cloud.examples.business.BusinessApp.AccountService;
import org.my.spring.cloud.examples.business.BusinessApp.OrderService;
import org.my.spring.cloud.examples.business.BusinessApp.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fescar.spring.annotation.GlobalTransactional;

@RestController
public class HomeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	private static final String SUCCESS = "SUCCESS";
	private static final String FAIL = "FAIL";
	private static final String USER_ID = "U100001";
	private static final String COMMODITY_CODE = "C00321";
	private static final int ORDER_COUNT = 2;

	private RestTemplate restTemplate;
	
	private OrderService orderService;
	
	private AccountService accountService;
	
	private ProductService productService;
	
	public HomeController(RestTemplate restTemplate,AccountService accountService, OrderService orderService,
			ProductService productService) {
		this.restTemplate = restTemplate;
		this.accountService = accountService;
		this.orderService = orderService;
		this.productService = productService;
	}


	@GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
	@RequestMapping(value = "/fescar/rest", method = RequestMethod.GET, produces = "application/json")
	public String rest() {

		String result = restTemplate.getForObject(
				"http://127.0.0.1:18082/storage/" + COMMODITY_CODE + "/" + ORDER_COUNT,
				String.class);

		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		String url = "http://127.0.0.1:18083/order";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("userId", USER_ID);
		map.add("commodityCode", COMMODITY_CODE);
		map.add("orderCount", ORDER_COUNT + "");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, request,
				String.class);

		result = response.getBody();

		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		return SUCCESS;
	}

	@GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
	@RequestMapping(value = "/fescar/feign", method = RequestMethod.GET, produces = "application/json")
	public String feign() {
		String oresult = orderService.order(USER_ID, COMMODITY_CODE, ORDER_COUNT);
		String presult = productService.product(COMMODITY_CODE, ORDER_COUNT);
		System.out.println(presult);
		String result = accountService.account(USER_ID, ORDER_COUNT);
		System.out.println(oresult);
		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		//result = orderService.order(USER_ID, COMMODITY_CODE, ORDER_COUNT);

		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		return SUCCESS;

	}

}
