package com.pxf.first.frame.enty.product.vo;

public class ProductVo {
	private String productId;
	private String productName;
	private String productImgUrl;
	private String productCategory;
	private String tbkUrl;
	private String productPrice;
	private String productSaleNum;
	private String productIncomePercent;
	private String productIncome;
	private String sellersId;
	private String shopName;
	private String platCategory;
	private String couponId;
	private String couponUrl;
	private String couponCounts;
	private String couponDetails;
	private String couponStart;
	private String couponFinish;
	private String couponTgUrl;
	private String productDetailUrl;
	private String flag;
	private String serviceCategoryId;
	private String priceF;
	private String priceT;
	public String getPriceF() {
		return priceF;
	}
	public void setPriceF(String priceF) {
		this.priceF = priceF;
	}
	public String getPriceT() {
		return priceT;
	}
	public void setPriceT(String priceT) {
		this.priceT = priceT;
	}
	//分页用
	//当前页
	private int currentPage;
	//每页条数
	private int pageRows;
	
	
	public ProductVo() {
		// TODO Auto-generated constructor stub
	}
	public ProductVo(String productImgUrl,String tbkUrl,String platCategory,String productName,
			String couponTgUrl,String productPrice,String couponDetails) {
		this.productImgUrl=productImgUrl;
		this.tbkUrl=tbkUrl;
		this.platCategory=platCategory;
		this.productName=productName;
		this.couponTgUrl=couponTgUrl;
		this.productPrice=productPrice;
		this.couponDetails=couponDetails;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageRows() {
		return pageRows;
	}
	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImgUrl() {
		return productImgUrl;
	}
	public void setProductImgUrl(String productImgUrl) {
		this.productImgUrl = productImgUrl;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getTbkUrl() {
		return tbkUrl;
	}
	public void setTbkUrl(String tbkUrl) {
		this.tbkUrl = tbkUrl;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductSaleNum() {
		return productSaleNum;
	}
	public void setProductSaleNum(String productSaleNum) {
		this.productSaleNum = productSaleNum;
	}
	public String getProductIncomePercent() {
		return productIncomePercent;
	}
	public void setProductIncomePercent(String productIncomePercent) {
		this.productIncomePercent = productIncomePercent;
	}
	public String getProductIncome() {
		return productIncome;
	}
	public void setProductIncome(String productIncome) {
		this.productIncome = productIncome;
	}
	public String getSellersId() {
		return sellersId;
	}
	public void setSellersId(String sellersId) {
		this.sellersId = sellersId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPlatCategory() {
		return platCategory;
	}
	public void setPlatCategory(String platCategory) {
		this.platCategory = platCategory;
	}
	public String getCouponUrl() {
		return couponUrl;
	}
	public void setCouponUrl(String couponUrl) {
		this.couponUrl = couponUrl;
	}
	public String getCouponCounts() {
		return couponCounts;
	}
	public void setCouponCounts(String couponCounts) {
		this.couponCounts = couponCounts;
	}
	public String getCouponDetails() {
		return couponDetails;
	}
	public void setCouponDetails(String couponDetails) {
		this.couponDetails = couponDetails;
	}
	public String getCouponStart() {
		return couponStart;
	}
	public void setCouponStart(String couponStart) {
		this.couponStart = couponStart;
	}
	public String getCouponFinish() {
		return couponFinish;
	}
	public void setCouponFinish(String couponFinish) {
		this.couponFinish = couponFinish;
	}
	public String getCouponTgUrl() {
		return couponTgUrl;
	}
	public void setCouponTgUrl(String couponTgUrl) {
		this.couponTgUrl = couponTgUrl;
	}
	public String getProductDetailUrl() {
		return productDetailUrl;
	}
	public void setProductDetailUrl(String productDetailUrl) {
		this.productDetailUrl = productDetailUrl;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getServiceCategoryId() {
		return serviceCategoryId;
	}
	public void setServiceCategoryId(String serviceCategoryId) {
		this.serviceCategoryId = serviceCategoryId;
	}

}
