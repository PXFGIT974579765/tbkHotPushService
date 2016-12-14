package com.pxf.first.frame.dao.order.dao;

import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.dao.base.dao.IBaseDao;
import com.pxf.first.frame.enty.product.vo.OrderVo;
import com.pxf.first.frame.enty.product.vo.ProductVo;

public interface IOrderMgrDao extends IBaseDao {
	void test();
	Pager queryUsersByConditionWithPage(ProductVo vo);
}
