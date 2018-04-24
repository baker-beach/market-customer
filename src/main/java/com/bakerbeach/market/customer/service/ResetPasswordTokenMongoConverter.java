package com.bakerbeach.market.customer.service;

import java.util.Date;

import com.bakerbeach.market.customer.model.ResetPasswordTokenImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ResetPasswordTokenMongoConverter {
	public static final String KEY_ID = "_id";
	public static final String KEY_CUSTOMER_ID = "customerId";
	public static final String KEY_EXPIRES_AT = "expiresAt";
	public static final String KEY_SHOP = "shop";

	public static ResetPasswordTokenImpl decode(DBObject dbo) {
		ResetPasswordTokenImpl token = new ResetPasswordTokenImpl();
		token.setId((String) dbo.get(KEY_ID));
		token.setCustomerId((String) dbo.get(KEY_CUSTOMER_ID));
		token.setExpiresAt((Date) dbo.get(KEY_EXPIRES_AT));
		token.setShop((String) dbo.get(KEY_SHOP));

		return token;
	}

	public static DBObject encode(ResetPasswordTokenImpl token) {
		DBObject dbo = new BasicDBObject();

		dbo.put(KEY_ID, token.getId());
		dbo.put(KEY_CUSTOMER_ID, token.getCustomerId());
		dbo.put(KEY_EXPIRES_AT, token.getExpiresAt());
		dbo.put(KEY_SHOP, token.getShop());

		return dbo;
	}

}
