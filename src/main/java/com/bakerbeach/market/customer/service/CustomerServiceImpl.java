package com.bakerbeach.market.customer.service;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.DigestUtils;

import com.bakerbeach.market.core.api.model.Customer;
import com.bakerbeach.market.core.api.model.PriceGroup;
import com.bakerbeach.market.core.api.model.TaxCode;
import com.bakerbeach.market.core.api.model.Text;
import com.bakerbeach.market.customer.api.service.CustomerService;
import com.bakerbeach.market.customer.api.service.CustomerServiceException;
import com.bakerbeach.market.customer.api.service.CustomerServiceException.CustomerNotFoundException;
import com.bakerbeach.market.customer.model.AnonymousCustomer;
import com.bakerbeach.market.customer.model.CustomerImpl;
import com.bakerbeach.market.customer.service.CustomerDaoException.DuplicateEntry;
import com.bakerbeach.market.sequence.service.SequenceService;
import com.bakerbeach.market.sequence.service.SequenceServiceException;

public class CustomerServiceImpl implements CustomerService {
	private static final Logger log = LoggerFactory.getLogger(CustomerService.class.getName());

	public static final String CUSTOMER_SEQUENCE_KEY = "customer";

	private static final Integer SALT_LENGTH = 12;

	private CustomerDao customerDao;

	private SequenceService sequenceService;

	private String defaultPriceGroup = PriceGroup.DEFAULT;

	private TaxCode defaultTaxCode = TaxCode.NORMAL;
	
	@Autowired
	@Qualifier("producerTemplate")
	private ProducerTemplate producerTemplate;

	@Override
	public void update(Customer customer) throws CustomerServiceException {
		try {
			customer.setUpdatedAt(new Date());
			customerDao.update(customer);
		} catch (Exception e) {
			throw new CustomerNotFoundException(new Text("error.customer.update"));
		}
	}

	@Override
	public Customer findById(String customerId) throws CustomerServiceException {
		try {
			return customerDao.findById(customerId);
		} catch (Exception e) {
			throw new CustomerNotFoundException(new Text("error.customer.notfound"));
		}
	}

	@Override
	public Map<String, Customer> findById(Collection<String> customerIdList) throws CustomerServiceException {
		try {
			Collection<Customer> collection = customerDao.findById(customerIdList);
			LinkedHashMap<String, Customer> map = new LinkedHashMap<String, Customer>(collection.size());
			for (Customer customer : collection) {
				map.put(customer.getId(), customer);
			}

			return map;
		} catch (Exception e) {
			throw new CustomerNotFoundException(new Text("error.customer.notfound"));
		}
	}

	@Override
	public Customer findByEmail(String email, String shopCode) throws CustomerServiceException {
		try {
			return customerDao.findByEmail(email.toLowerCase(), shopCode);
		} catch (Exception e) {
			throw new CustomerNotFoundException(new Text("error.customer.notfound"));
		}
	}

	@Deprecated
	@Override
	public Customer register(String email, String password, String shopCode) throws CustomerServiceException {
		CustomerImpl customer = new CustomerImpl();
		customer.setEmail(email.toLowerCase());
		String salt = RandomStringUtils.random(SALT_LENGTH, "abcdefghijklmnopqrstuvwxyz0123456789");
		customer.setPassword(generateHash(password, salt) + ":" + salt);
		customer.getValidShopCodes().add(shopCode);
		customer.setTaxCode(getDefaultTaxCode());
		customer.setPriceGroup(getDefaultPriceGroup());
		customer.setCreatedAt(new Date());
		customer.setUpdatedAt(new Date());
		try {
			customer.setId(sequenceService.generateId(CUSTOMER_SEQUENCE_KEY).toString());
		} catch (SequenceServiceException e1) {
			throw new CustomerServiceException(new Text("error.customer.register"));
		}
		
		try {
			customerDao.save(customer);
			
			Map<String, String> payload = new HashMap<String, String>();
			payload.put("customer_id", customer.getId());
			payload.put("shop_code", shopCode);
			
			producerTemplate.sendBody("direct:registration", payload);

			return customer;
		} catch (DuplicateEntry e) {
			throw new CustomerServiceException(new Text("error.customer.register.exist"));
		} catch (CustomerDaoException e) {
			throw new CustomerServiceException(new Text("error.customer.register"));
		}
	}

	@Override
	public Customer register(String email, String password, List<String> shopCode) throws CustomerServiceException {
		CustomerImpl customer = new CustomerImpl();
		customer.setEmail(email.toLowerCase());
		String salt = RandomStringUtils.random(SALT_LENGTH, "abcdefghijklmnopqrstuvwxyz0123456789");
		customer.setPassword(generateHash(password, salt) + ":" + salt);
		customer.getValidShopCodes().addAll(shopCode);
		customer.setTaxCode(getDefaultTaxCode());
		customer.setPriceGroup(getDefaultPriceGroup());
		customer.setCreatedAt(new Date());
		customer.setUpdatedAt(new Date());
		try {
			customer.setId(sequenceService.generateId(CUSTOMER_SEQUENCE_KEY).toString());
		} catch (SequenceServiceException e1) {
			throw new CustomerServiceException(new Text("error.customer.register"));
		}
		
		try {
			customerDao.save(customer);
			
			Map<String, String> payload = new HashMap<String, String>();
			payload.put("customer_id", customer.getId());
			payload.put("shop_code", shopCode.get(0));
			
			producerTemplate.sendBody("direct:registration", payload);
			
			return customer;
		} catch (DuplicateEntry e) {
			throw new CustomerServiceException(new Text("error.customer.register.exist"));
		} catch (CustomerDaoException e) {
			throw new CustomerServiceException(new Text("error.customer.register"));
		}
	}
	
	@Override
	public Boolean checkPassword(Customer customer, String password) {
		try {
			return checkPassword(password, customer.getPassword());
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	private Boolean checkPassword(String password, String passwordHash) {
		try {
			String[] parts = passwordHash.split(":");
			passwordHash = parts[0];
			String salt = parts[1];
			return generateHash(password, salt).equals(passwordHash);
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			return Boolean.FALSE;
		}
	}

	private String generateHash(String password, String salt) {
		String passwordComposed = salt + password;
		try {
			String hash = DigestUtils.md5DigestAsHex(passwordComposed.getBytes("UTF-8"));
			return hash;
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}

	@Override
	public void changePassword(Customer customer, String newPassword) throws CustomerServiceException {
		try {
			String salt = RandomStringUtils.random(SALT_LENGTH, "abcdefghijklmnopqrstuvwxyz0123456789");
			String newHash = generateHash(newPassword, salt);
			customer.setPassword(newHash + ":" + salt);
			this.update(customer);
		} catch (Exception e) {
			throw new CustomerServiceException(new Text("error.customer.new_password"));
		}

	}

	@Override
	public void renewPassword(String email, String shopCode) throws CustomerServiceException {
		try {
			Customer customer = findByEmail(email.toLowerCase(), shopCode);

			String newPassword = RandomStringUtils.random(6, "abcdefghijklmnopqrstuvwxyz0123456789");
			changePassword(customer, newPassword);

			Map<String, String> payload = new HashMap<String, String>();
			payload.put("password", newPassword);
			payload.put("customer_id", customer.getId());
			payload.put("shop_code", shopCode);

			producerTemplate.sendBody("direct:password", payload);
		} catch (CustomerServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new CustomerServiceException(e);
		}		
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDoa) {
		this.customerDao = customerDoa;
	}

	/**
	 * @return the sequenceService
	 */
	public SequenceService getSequenceService() {
		return sequenceService;
	}

	/**
	 * @param sequenceService
	 *            the sequenceService to set
	 */
	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	/**
	 * @return the defaultTaxCode
	 */
	public TaxCode getDefaultTaxCode() {
		return defaultTaxCode;
	}

	/**
	 * @param defaultTaxCode
	 *            the defaultTaxCode to set
	 */
	public void setDefaultTaxCode(TaxCode defaultTaxCode) {
		this.defaultTaxCode = defaultTaxCode;
	}

	/**
	 * @return the defaultPriceGroup
	 */
	public String getDefaultPriceGroup() {
		return defaultPriceGroup;
	}

	/**
	 * @param defaultPriceGroup
	 *            the defaultPriceGroup to set
	 */
	public void setDefaultPriceGroup(String defaultPriceGroup) {
		this.defaultPriceGroup = defaultPriceGroup;
	}

	@Override
	public Customer createAnonymousCustomer() {
		return new AnonymousCustomer();
	}
	
}
