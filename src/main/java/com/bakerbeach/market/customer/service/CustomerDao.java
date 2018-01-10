package com.bakerbeach.market.customer.service;

import java.util.Collection;
import java.util.Map;

import com.bakerbeach.market.core.api.model.Customer;

public interface CustomerDao<C extends Customer> {

	C findById(String id) throws CustomerDaoException;

	Collection<C> findById(Collection<String> customerIdList, Map<String, Object> orderBy, Integer limit,
			Integer offset) throws CustomerDaoException;

	C findByEmail(String email, String shopCode) throws CustomerDaoException;

	void saveOrUpdate(Customer customer) throws CustomerDaoException;

}
