package com.bakerbeach.market.customer.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.bakerbeach.market.core.api.model.Customer;

public abstract class AbstractMorphiaCustomerDao<C extends Customer> {
	
	protected Morphia morphia = new Morphia();
	protected Datastore datastore;

	protected String uri;
	protected String dbName;
	protected String customerCollectionName;
	protected String packages;
	
	protected Class<C> customerClass;

	public AbstractMorphiaCustomerDao(Class<C> customerClass, Datastore datastore, String customerCollectionName) {
		this.datastore = datastore;
		this.customerClass = customerClass;
		this.customerCollectionName = customerCollectionName;
	}
	
	public C findById(String id) throws CustomerDaoException{
		Query<C> query = ((AdvancedDatastore) datastore).createQuery(customerCollectionName, customerClass).field("id")
				.equal(id);
		C customer = query.get();
		return customer;
	}

	public Collection<C> findById(Collection<String> customerIdList, Map<String, Object> orderBy, Integer limit,
			Integer offset) throws CustomerDaoException{
		ArrayList<C> customers = new ArrayList<>();
		return customers;
	}

	public Customer findByEmail(String email, String shopCode) throws CustomerDaoException{
		Query<C> query = ((AdvancedDatastore) datastore).createQuery(customerCollectionName, customerClass).field("email")
				.equal(email).field("shopCode").equal(shopCode);;
		C customer = query.get();
		return customer;
	}

	public void saveOrUpdate(Customer customer) throws CustomerDaoException{
		((AdvancedDatastore) datastore).save(customerCollectionName, customer);
	}

}
