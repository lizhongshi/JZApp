package com.dly.app.commons.sms;

public class SmsBean {

	private  String product ;
	private  String domain;
	private String accessKeyId ;
	private String accessKeySecret;
	private String templateCode;
	private String signName;
	
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	@Override
	public String toString() {
		return "SmsBean [product=" + product + ", domain=" + domain + ", accessKeyId=" + accessKeyId
				+ ", accessKeySecret=" + accessKeySecret + ", templateCode=" + templateCode + ", signName=" + signName
				+ "]";
	}
	
}
