package com.pxf.first.frame.service.menu.service;

import java.util.List;

import com.pxf.first.frame.enty.menu.vo.EasyUITree;

public interface IMenuMngService {
	List<EasyUITree> getTreeMenu(String id,String authStr);

}
