package com.bakerbeach.market.customer.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.bakerbeach.market.core.api.model.Customer;
import com.bakerbeach.market.core.api.model.TaxCode;
import com.bakerbeach.market.customer.model.CustomerImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CustomerMongoConverter {
	
	public static final String KEY_ATTRIBUTES = "attributes";
	public static final String KEY_ID = "id";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_FIRST_NAME = "first_name";
	public static final String KEY_LAST_NAME = "last_name";
	public static final String KEY_MIDDLE_NAME = "middle_name";
	public static final String KEY_CREATED_AT = "created_at";
	public static final String KEY_UPDATED_AT = "updated_at";
	public static final String KEY_DOB = "dob";
	public static final String KEY_PREFIX = "prefix";
	public static final String KEY_SUFFIX = "suffix";
	public static final String KEY_PRICE_GROUP = "price_group";
	public static final String KEY_TAX_CODE = "tax_code";
	public static final String KEY_SHOPS = "shops";
	
	public static Customer decode(DBObject dbo){
		
		CustomerImpl customer = new CustomerImpl();
		
		customer.setId((String)dbo.get(KEY_ID));
		customer.setEmail((String)dbo.get(KEY_EMAIL));
		customer.setPassword((String)dbo.get(KEY_PASSWORD));
		customer.setFirstName((String)dbo.get(KEY_FIRST_NAME));
		customer.setLastName((String)dbo.get(KEY_LAST_NAME));
		customer.setMiddleName((String)dbo.get(KEY_MIDDLE_NAME));
		customer.setCreatedAt((Date)dbo.get(KEY_CREATED_AT));
		customer.setUpdatedAt((Date)dbo.get(KEY_UPDATED_AT));
		customer.setDateOfBirth((Date)dbo.get(KEY_DOB));
		customer.setPrefix((String)dbo.get(KEY_PREFIX));
		customer.setSuffix((String)dbo.get(KEY_SUFFIX));
		customer.setPriceGroup((String)dbo.get(KEY_PRICE_GROUP));
		customer.setTaxCode(TaxCode.valueOf((String)dbo.get(KEY_TAX_CODE)));
		
		for (Object item : ((BasicDBList) dbo.get(KEY_SHOPS))) {
			customer.getValidShopCodes().add((String)item);
		}
		
		BasicDBObject attributes = (BasicDBObject) dbo.get(KEY_ATTRIBUTES);
		for (String key : attributes.keySet()) {
			customer.setAttribute(key, attributes.get(key));
		}	
		return customer;
	}
	
	public static DBObject encode(Customer customer){
		
		DBObject dbo = new BasicDBObject();
		
		dbo.put(KEY_ID, customer.getId());
		dbo.put(KEY_EMAIL, customer.getEmail());
		dbo.put(KEY_PASSWORD, customer.getPassword());
		dbo.put(KEY_FIRST_NAME, customer.getFirstName());
		dbo.put(KEY_LAST_NAME, customer.getLastName());
		dbo.put(KEY_MIDDLE_NAME, customer.getMiddleName());
		dbo.put(KEY_CREATED_AT, customer.getCreatedAt());
		dbo.put(KEY_UPDATED_AT, customer.getUpdatedAt());
		dbo.put(KEY_DOB, customer.getDateOfBirth());
		dbo.put(KEY_PREFIX, customer.getPrefix());
		dbo.put(KEY_SUFFIX, customer.getSuffix());
		dbo.put(KEY_PRICE_GROUP, customer.getPriceGroup());
		dbo.put(KEY_TAX_CODE, customer.getTaxCode().toString());
		dbo.put(KEY_SHOPS, customer.getValidShopCodes());
		
		DBObject attributes = new BasicDBObject();
		Map<String, Object> attributeMap = customer.getAttributes();
		for (String key : attributeMap.keySet()) {
			Object value = attributeMap.get(key);

			if (value instanceof BigDecimal) {
				attributes.put(key, ((BigDecimal) value).doubleValue());
			} else {
				attributes.put(key, value);
			}

		}
		dbo.put(KEY_ATTRIBUTES, attributes);
		
		
		return dbo;
	}

}
