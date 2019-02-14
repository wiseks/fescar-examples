package org.my.spring.cloud.examples.account;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	
	private static final String SUCCESS = "SUCCESS";
	private static final String FAIL = "FAIL";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value = "/account", method = RequestMethod.POST, produces = "application/json")
	public String account(String userId, int money){
		Random random = new Random();
		if (random.nextInt(2)==0) {
			throw new RuntimeException("this is a mock Exception");
		}
		int result = jdbcTemplate.update(
				"update account set money = money - ? where user_id = ?",
				new Object[] { money, userId });
		LOGGER.info("Account Service End ... ");
		if (result == 1) {
			return SUCCESS;
		}
		return FAIL;
	}

}
