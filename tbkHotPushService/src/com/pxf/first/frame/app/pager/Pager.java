package com.pxf.first.frame.app.pager;

import java.util.List;
import java.util.Map;

import com.pxf.first.frame.enty.user.bo.UserInfo;

public class Pager<T> {
	//��ǰҳ
	private int currentPage;
	//��ҳ��
	private int pageTotal;
	//������
	private int rowsTotal;
	//ÿҳ����
	private int pageRows;
	//hql���
	private String hql;
	//��ѯ���
	private List<T> list;
	//����
	private Map<String , Object> params;
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Pager() {
	}
	public Pager(int currentPage,int pageTotal,int rowsTotal,int pageRows,String hql,Map<String , Object> pramas,List<T> list) {
		this.currentPage=currentPage;
		this.pageTotal=pageTotal;
		this.rowsTotal=rowsTotal;
		this.pageRows=pageRows;
		this.hql=hql;
		this.list=list;
		this.params=pramas;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	public int getRowsTotal() {
		return rowsTotal;
	}
	public void setRowsTotal(int rowsTotal) {
		this.rowsTotal = rowsTotal;
	}
	public int getPageRows() {
		return pageRows;
	}
	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}
	public String getHql() {
		return hql;
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

}
