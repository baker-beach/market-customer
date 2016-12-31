package com.bakerbeach.market.customer.model;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.bakerbeach.market.core.api.model.Customer;
import com.bakerbeach.market.core.api.model.PriceGroup;
import com.bakerbeach.market.core.api.model.TaxCode;

public class AnonymousCustomer implements Customer {
	private static final long serialVersionUID = 1L;

	private String id;
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDateOfBirth(Date dateOfBirth) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getDateOfBirth() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEmail(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSuffix(String suffix) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSuffix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastName(String lastName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMiddleName(String middleName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMiddleName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFirstName(String firstName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrefix(String prefix) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getCreatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCreatedAt(Date createdAt) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getUpdatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUpdatedAt(Date updatedAt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPriceGroup(String priceGroup) {
	}

	@Override
	public String getPriceGroup() {
		return PriceGroup.DEFAULT;
	}

	@Override
	public void setTaxCode(TaxCode taxCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public TaxCode getTaxCode() {
		// TODO Auto-generated method stub
		return TaxCode.NORMAL;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getValidShopCodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isAnonymousCustomer() {
		// TODO Auto-generated method stub
		return true;
	}



}
