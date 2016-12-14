package com.pxf.first.frame.enty.product.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_tbk_production")
public class ProductBo {
	@Id
	@Column(name = "product_id", nullable = false, insertable = true, updatable = true, length = 32)
	private String productId;
	@Column(name = "product_name", length = 255, nullable = true)
	private String productName;
	@Column(name = "product_img_url", length = 255, nullable = true)
	private String productImgUrl;
	@Column(name = "product_category", length = 50, nullable = true)
	private String productCategory;
	@Column(name = "tbk_url", length = 50, nullable = true)
	private String tbkUrl;
	@Column(name = "product_price", length =10, nullable = true)
	private String productPrice;
	@Column(name = "product_sale_num", length = 10, nullable = true)
	private String productSaleNum;
	@Column(name = "product_income_percent", length = 10, nullable = true)
	private String productIncomePercent;
	@Column(name = "product_income", length = 10, nullable = true)
	private String productIncome;
	@Column(name = "sellers_id", length = 32, nullable = true)
	private String sellersId;
	@Column(name = "shop_name", length = 50, nullable = true)
	private String shopName;
	@Column(name = "plat_category", length = 2, nullable = true)
	private String platCategory;
	@Column(name = "coupon_id", length = 32, nullable = true)
	private String couponId;

	@Column(name = "coupon_url", length = 200, nullable = true)
	private String couponUrl;
	@Column(name = "coupon_counts", length = 10, nullable = true)
	private String couponCounts;
	@Column(name = "coupon_detail", length = 30, nullable = true)
	private String couponDetails;
	@Column(name = "coupon_start", length = 20, nullable = true)
	private String couponStart;
	@Column(name = "coupon_finish", length = 20, nullable = true)
	private String couponFinish;
	@Column(name = "coupon_tg_url", length = 200, nullable = true)
	private String couponTgUrl;
	@Column(name = "product_detail_url", length = 200, nullable = true)
	private String productDetailUrl;
	@Column(name = "flag", length = 4, nullable = true)
	private String flag;
	@Column(name = "service_category_id", length = 10, nullable = true)
	private String serviceCategoryId;
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
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
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
