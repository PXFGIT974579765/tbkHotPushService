package com.pxf.first.frame.dao.order.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.StringUtils;
import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.dao.base.dao.impl.BaseDaoImpl;
import com.pxf.first.frame.dao.order.dao.IOrderMgrDao;
import com.pxf.first.frame.enty.product.bo.OrderBasic;
import com.pxf.first.frame.enty.product.vo.OrderVo;
import com.pxf.first.frame.enty.product.vo.OwnerVo;
import com.pxf.first.frame.enty.product.vo.ProductVo;


@Repository
public class OrderMgrDaoImpl extends BaseDaoImpl implements IOrderMgrDao {

	private final static int PAGE_SIZE_DEFAULT=12;
	private final static int CURRENT_PAGE_DEFAULT=1;
	@Override
	public void test() {
		
	}
	@Override
	public Pager queryUsersByConditionWithPage(ProductVo vo) {
		Pager<ProductVo> page=new Pager<ProductVo>();
		StringBuffer sb=new StringBuffer();
		Map<String , Object> params = new HashMap<String , Object>();
		sb.append("select new com.pxf.first.frame.enty.product.vo.ProductVo( o.productImgUrl,o.tbkUrl, o.platCategory,");
		sb.append("o.productName,o.couponTgUrl,o.productPrice,o.couponDetails,o.productIncome");
		sb.append(") From ProductBo o where 1=1");
		
		if(!StringUtils.isNullOrEmpty(vo.getProductName())){
			sb.append(" and o.productName like:productName");
			params.put("productName", anisyChineseForLike(vo.getProductName().trim()));
		}
		if(!StringUtils.isNullOrEmpty(vo.getPriceF())){
			sb.append(" and cast(o.productPrice as integer) >:priceF ");
			params.put("priceF", Integer.parseInt(vo.getPriceF().trim()));
		}
		if(!StringUtils.isNullOrEmpty(vo.getPriceT())){
			sb.append(" and cast(o.productPrice as integer) <:priceT ");
			params.put("priceT", Integer.parseInt(vo.getPriceT().trim()));
		}
		if(StringUtils.isNullOrEmpty(vo.getPageRows())||"0".equals(vo.getPageRows())){
			vo.setPageRows(PAGE_SIZE_DEFAULT+"");
		}
		if(StringUtils.isNullOrEmpty(vo.getCurrentPage())||"0".equals(vo.getCurrentPage())){
			vo.setCurrentPage(CURRENT_PAGE_DEFAULT+"");
		}
		page.setHql(sb.toString());
		page.setParams(params);
		page.setPageRows(Integer.parseInt(vo.getPageRows()+""));
		page.setCurrentPage(Integer.parseInt(vo.getCurrentPage()+""));
		page=queryWithPage(page);
		return page;
	}
	public String anisyChineseForLike(String s){
		InputStream is=null;
		InputStreamReader bis=null; 
		StringBuffer sb=new StringBuffer();
		sb.append("%");
		int i;     
        try {
        	is=new ByteArrayInputStream(s.getBytes("utf-8"));
        	bis=new InputStreamReader(is,"utf-8");
			while((i=bis.read()) != -1){     
			    sb.append((char)i).append("%");   
			}
			System.out.println(sb.toString());
			 return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally{
			try {
				bis.close();
				is.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
        }
	}

}
