package com.tong.entity.sms;

import java.util.ArrayList;
import java.util.List;

public class SMSParameter {
	private static final String CUSTOMER_NAME = "customer";
	private static final String CUSTOMER_TEL = "customerTel";
	private static final String MERCHANT_NAME = "merchantName";
	private static final String BUILDING_PROJECT_NAME = "buildingProjectName";
	private static final String SRC_CHANNEL_NAME = "srcChannelName";
	private static final String VALID_CODE = "code";
	private static final String SELLER_NAME = "seller";
	private static final String DATE_TIME = "dateTime";
	private static final String CUSTOMER_TYPE = "customerType";
	private static final String COMPANY_NAME = "companyName";
	private static final String ADDRESS = "address";
	private static final String EXTEND = "extend";

	private List<P> pl = new ArrayList<P>();
	
	public List<P> getParamsList() {
		return pl;
	}

	public SMSParameter addCustomerName(String customerName) {
		P p = new P(CUSTOMER_NAME, customerName);
		pl.add(p);

		return this;
	}

	public SMSParameter addCustomerTel(String customerTel) {
		P p = new P(CUSTOMER_TEL, customerTel);
		pl.add(p);

		return this;
	}
	
	public SMSParameter addCustomerType(String customerType) {
		P p = new P(CUSTOMER_TYPE, customerType);
		pl.add(p);

		return this;
	}

	public SMSParameter addMerchantName(String merchantName) {
		P p = new P(MERCHANT_NAME, merchantName);
		pl.add(p);

		return this;
	}

	public SMSParameter addBuildingProjectName(String buildingProjectName) {
		P p = new P(BUILDING_PROJECT_NAME, buildingProjectName);
		pl.add(p);

		return this;
	}

	public SMSParameter addExtend(String extend) {
		P p = new P(EXTEND, extend);
		pl.add(p);

		return this;
	}

	public SMSParameter addSrcChannelName(String channelName) {
		P p = new P(SRC_CHANNEL_NAME, channelName);
		pl.add(p);

		return this;
	}

	public SMSParameter addValidCode(String validCode) {
		P p = new P(VALID_CODE, validCode);
		pl.add(p);

		return this;
	}
	
	public SMSParameter addDateTime(String dateTime) {
		P p = new P(DATE_TIME, dateTime);
		pl.add(p);

		return this;
	}
	
	public SMSParameter addCompanyName(String companyName) {
		P p = new P(COMPANY_NAME, companyName);
		pl.add(p);

		return this;
	}
	
	public SMSParameter addAddress(String address) {
		P p = new P(ADDRESS, address);
		pl.add(p);

		return this;
	}
	
	public SMSParameter addSellerName(String sellerName) {
		P p = new P(SELLER_NAME, sellerName);
		pl.add(p);

		return this;
	}

	public P get(int index) {
		return pl.get(index);
	}

	public int size() {
		return pl.size();
	}

	public class P {
		private String key;
		private String value;

		public P(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
