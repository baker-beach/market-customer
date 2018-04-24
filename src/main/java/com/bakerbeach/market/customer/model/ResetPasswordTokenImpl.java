package com.bakerbeach.market.customer.model;

import java.util.Date;

import com.bakerbeach.market.customer.api.model.ResetPasswordToken;

public class ResetPasswordTokenImpl implements ResetPasswordToken {

	private String id;
	private String customerId;
	private Date expiresAt;
	private String shop;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getCustomerId() {
		return customerId;
	}

	@Override
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public Date getExpiresAt() {
		return expiresAt;
	}

	@Override
	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	@Override
	public String getShop() {
		return shop;
	}

	@Override
	public void setShop(String shop) {
		this.shop = shop;
	}

}
