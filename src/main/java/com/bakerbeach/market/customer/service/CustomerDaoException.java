package com.bakerbeach.market.customer.service;

@SuppressWarnings("serial")
public class CustomerDaoException extends Exception {
	
	public CustomerDaoException(Exception e){
		super(e);
	}
	
	public CustomerDaoException(){
		super();
	}
	
	public static class DuplicateEntry extends CustomerDaoException{
		public DuplicateEntry(Exception e){
			super(e);
		}
	}
	
	public static class EntryNotFound extends CustomerDaoException{
		public EntryNotFound(){
			super();
		}
	}

}
