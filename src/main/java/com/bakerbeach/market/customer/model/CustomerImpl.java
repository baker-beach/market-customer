package com.bakerbeach.market.customer.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.bakerbeach.market.core.api.model.Customer;
import com.bakerbeach.market.core.api.model.TaxCode;

@SuppressWarnings("serial")
public class CustomerImpl implements Customer {
	
	private String id;
	private String password;
	private String email;
	
	private String prefix;
	private String firstName;
	private String middleName;
	private String lastName;


	private String suffix;
	private Date dateOfBirth;
	private Date createdAt;
	private Date updatedAt;
	private String priceGroup;
	private TaxCode taxCode; 
	
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	private Set<String> validShopCodes = new HashSet<String>();
	

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setPassword(String password) {
		this.password = password;

	}

	@Override
	public String getPassword() {
		return password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	public String getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}

	public TaxCode getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(TaxCode taxCode) {
		this.taxCode = taxCode;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Set<String> getValidShopCodes() {
		return validShopCodes;
	}

	public void setValidShopCodes(Set<String> validShopCodes) {
		this.validShopCodes = validShopCodes;
	}

	@Override
	public Boolean isAnonymousCustomer() {
		return false;
	}

}
