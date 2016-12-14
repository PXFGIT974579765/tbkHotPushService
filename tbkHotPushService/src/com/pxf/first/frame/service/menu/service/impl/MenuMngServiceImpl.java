package com.pxf.first.frame.service.menu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pxf.first.frame.app.utils.TreeMenuUtil;
import com.pxf.first.frame.dao.base.dao.IBaseDao;
import com.pxf.first.frame.enty.menu.bo.TreeNode;
import com.pxf.first.frame.enty.menu.vo.EasyUITree;
import com.pxf.first.frame.service.menu.service.IMenuMngService;


@Service
@Transactional
public class MenuMngServiceImpl implements IMenuMngService {

	@Autowired
	private IBaseDao baseDao;
	@Override
	public List<EasyUITree> getTreeMenu(String id,String authStr) {
		List<TreeNode> list=baseDao.getMenu(authStr);
		List<EasyUITree> easyUITreeList=TreeMenuUtil.convertEasyUITree(list);
		return easyUITreeList;
	}

}
