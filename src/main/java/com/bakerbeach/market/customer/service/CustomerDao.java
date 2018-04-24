package com.bakerbeach.market.customer.service;

import java.util.Collection;
import java.util.Map;

import com.bakerbeach.market.core.api.model.Customer;
import com.bakerbeach.market.customer.api.model.ResetPasswordToken;
import com.bakerbeach.market.customer.model.ResetPasswordTokenImpl;

public interface CustomerDao {

	Customer findById(String id) throws CustomerDaoException;

	Collection<Customer> findById(Collection<String> customerIdList, Map<String, Object> orderBy, Integer limit,
			Integer offset) throws CustomerDaoException;

	Collection<Customer> findById(Collection<String> customerIdList) throws CustomerDaoException;

	Customer findByEmail(String email, String shopCode) throws CustomerDaoException;

	void save(Customer customer) throws CustomerDaoException;

	void update(Customer customer) throws CustomerDaoException;

	void saveResetPasswordToken(ResetPasswordTokenImpl token) throws CustomerDaoException;

	ResetPasswordToken getResetPasswordToken(String tokenId) throws CustomerDaoException;

	void deleteResetPasswordToken(String tokenId) throws CustomerDaoException;

}
