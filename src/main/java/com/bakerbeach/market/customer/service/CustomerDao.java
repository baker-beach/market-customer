package com.bakerbeach.market.customer.service;

import java.util.Collection;
import java.util.Map;

import com.bakerbeach.market.core.api.model.Customer;

public interface CustomerDao {

	Customer findById(String id) throws CustomerDaoException;

	Collection<Customer> findById(Collection<String> customerIdList, Map<String, Object> orderBy, Integer limit,
			Integer offset) throws CustomerDaoException;

	Collection<Customer> findById(Collection<String> customerIdList) throws CustomerDaoException;

	Customer findByEmail(String email, String shopCode) throws CustomerDaoException;

	void save(Customer customer) throws CustomerDaoException;

	void update(Customer customer) throws CustomerDaoException;

}
