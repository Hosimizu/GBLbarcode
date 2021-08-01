package com;

public class PrintBean {

	private String vincode;
	private String binCode;
	private String receiveDate;
	private String productName;
	private String destName;
	
	public String getVincode() {
		return vincode;
	}
	public void setVincode(String vincode) {
		this.vincode = vincode;
	}
	public String getBinCode() {
		if(this.binCode == null){
			binCode = "";
		}
		return binCode;
	}
	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}
	public String getReceiveDate() {
		if(this.receiveDate == null){
			receiveDate = "";
		}
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getProductName() {
		if(this.productName == null){
			productName = "";
		}else{
			productName = productName.trim().replaceAll(" ", "+");
			productName = productName.trim().replaceAll("（", "(");
			productName = productName.trim().replaceAll("）", ")");
		}
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDestName() {
		if(this.destName == null){
			destName = "";
		}
		return destName.trim();
	}
	public void setDestName(String destName) {
		this.destName = destName;
	}
	
	
}
