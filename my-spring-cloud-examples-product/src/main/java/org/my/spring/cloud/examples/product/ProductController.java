package org.my.spring.cloud.examples.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fescar.core.context.RootContext;

@RestController
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	private static final String SUCCESS = "SUCCESS";
	private static final String FAIL = "FAIL";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@RequestMapping(value = "/storage/{commodityCode}/{count}", method = RequestMethod.GET, produces = "application/json")
	public String echo(@PathVariable String commodityCode, @PathVariable int count) {
		LOGGER.info("Storage Service Begin ... xid: " + RootContext.getXID());
		int result = jdbcTemplate.update(
				"update product set count = count - ? where commodity_code = ?",
				new Object[] { count, commodityCode });
		LOGGER.info("Storage Service End ... ");
		if (result == 1) {
			return SUCCESS;
		}
		return FAIL;
	}
}
