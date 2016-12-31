package com.bakerbeach.market.core.service.customer;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bakerbeach.market.core.api.model.Customer;
import com.bakerbeach.market.customer.api.service.CustomerService;
import com.bakerbeach.market.customer.api.service.CustomerServiceException;
import com.bakerbeach.market.customer.api.service.CustomerServiceException.CustomerNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/customer_service.xml", "classpath:spring/sequence_service.xml",
		"classpath:spring/resources.xml" })
public class CustomerServiceImplTest {

	@Autowired
	private CustomerService customerService;

	@Test
	public void serviceTest() {
		try {
			customerService.register("mark.hoja@web.de", "0123456789", "TestShop");
			customerService.register("mark.hoja@web.de", "0123456789", "TestShop2");
		} catch (CustomerServiceException e) {
			fail("registration error");
		}

		try {
			customerService.register("mark.hoja@web.de", "0123456789", "TestShop");
		} catch (CustomerServiceException e) {
			if (!e.getText().getKey().equals("error.customer.register.exist"))
				fail("duplicate key");
		}
		
		try {
			customerService.findByEmail("mark2.hoja@web.de", "TestShop");
		} catch (CustomerServiceException e) {
			if (!(e instanceof CustomerNotFoundException))
				fail("found non exsiting  customer");
		}
		
		Customer customer = null;
		
		try {
			customer = customerService.findByEmail("mark.hoja@web.de", "TestShop");
		} catch (CustomerServiceException e) {
			fail("loading error");
		}
		
		if(customer != null){
			try {
				Assert.assertTrue(customerService.checkPassword(customer, "0123456789"));
				Assert.assertFalse(customerService.checkPassword(customer, "012345678"));
				customerService.changePassword(customer, "012345678");
				customer = customerService.findByEmail("mark.hoja@web.de", "TestShop");
				Assert.assertTrue(customerService.checkPassword(customer, "012345678"));
				customer.getAttributes().put("test", "1");
				customerService.update(customer);
				customer = customerService.findByEmail("mark.hoja@web.de", "TestShop");
				Assert.assertEquals("1", customer.getAttributes().get("test"));
			} catch (CustomerServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
