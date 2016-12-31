package com.bakerbeach.market.customer.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.bakerbeach.market.core.api.model.Customer;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.QueryBuilder;

public class CustomerMongoDao implements CustomerDao {

	private static final Logger log = LoggerFactory.getLogger(CustomerDao.class.getName());

	private MongoTemplate mongoTemplate;

	private String collectionName;

	@Override
	public Customer findById(String id) throws CustomerDaoException {
		try {
			QueryBuilder qb = QueryBuilder.start();
			qb.and(CustomerMongoConverter.KEY_ID).is(id);
			DBObject dbo = getDBCollection().findOne(qb.get());
			if (dbo != null) {
				return CustomerMongoConverter.decode(dbo);
			} else {
				throw new CustomerDaoException.EntryNotFound();
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getMessage(e));
			throw new CustomerDaoException(e);
		}
	}
	
	@Override
	public Collection<Customer> findById(Collection<String> customerIdList, Map<String, Object> orderBy, Integer limit, Integer offset) throws CustomerDaoException {
		try {
			QueryBuilder qb = QueryBuilder.start();
			qb.and(CustomerMongoConverter.KEY_ID).in(customerIdList);
			
			return find(qb, null, (orderBy != null)? new BasicDBObject(orderBy) : null, limit, offset);
		} catch (Exception e) {
			log.error(ExceptionUtils.getMessage(e));
			throw new CustomerDaoException(e);
		}		
	}
	
	@Override
	public Collection<Customer> findById(Collection<String> customerIdList) throws CustomerDaoException {
		return findById(customerIdList, null, null, null);
	}
	
	@Override
	public Customer findByEmail(String email, String shopCode) throws CustomerDaoException {
		try {
			QueryBuilder qb = QueryBuilder.start();
			qb.and(CustomerMongoConverter.KEY_EMAIL).is(email);
			qb.and(CustomerMongoConverter.KEY_SHOPS).is(shopCode);
			DBObject dbo = getDBCollection().findOne(qb.get());
			if (dbo != null) {
				return CustomerMongoConverter.decode(dbo);
			} else {
				throw new CustomerDaoException.EntryNotFound();
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getMessage(e));
			throw new CustomerDaoException(e);
		}
	}

	@Override
	public void save(Customer customer) throws CustomerDaoException {
		try {
			getDBCollection().save(CustomerMongoConverter.encode(customer));
		} catch (DuplicateKeyException e) {
			log.warn(ExceptionUtils.getMessage(e));
			throw new CustomerDaoException.DuplicateEntry(e);
		} catch (Exception e) {
			log.error(ExceptionUtils.getMessage(e));
			throw new CustomerDaoException(e);
		}
	}

	@Override
	public void update(Customer customer) throws CustomerDaoException {
		try {
			QueryBuilder qb = QueryBuilder.start();
			qb.and(CustomerMongoConverter.KEY_ID).is(customer.getId());
			getDBCollection().update(qb.get(), CustomerMongoConverter.encode(customer));
		} catch (Exception e) {
			log.error(ExceptionUtils.getMessage(e));
			throw new CustomerDaoException(e);
		}
	}
	
	private List<Customer> find(QueryBuilder qb, DBObject keys, DBObject orderBy, Integer limit, Integer offset) {
		DBCursor cur = null;
		if (orderBy != null)
			cur = getDBCollection().find(qb.get(), keys).sort(orderBy);
		else
			cur = getDBCollection().find(qb.get(), keys);
		
		List<Customer> customerList = new ArrayList<Customer>();
		for (Iterator<DBObject> it = cur.iterator(); it.hasNext();) {
			DBObject source = it.next();
			Customer customer = CustomerMongoConverter.decode(source);
			customerList.add(customer);
		}
		
		return customerList;
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoShopTemplate) {
		this.mongoTemplate = mongoShopTemplate;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	private DBCollection getDBCollection() {
		return mongoTemplate.getCollection(collectionName);
	}

}
