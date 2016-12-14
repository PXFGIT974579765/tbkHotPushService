package com.pxf.first.frame.controller.menu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pxf.first.frame.controller.base.controller.BaseController;
import com.pxf.first.frame.enty.menu.vo.EasyUITree;
import com.pxf.first.frame.service.menu.service.IMenuMngService;

@Controller
@RequestMapping(value = "/menu")
public class MenuMngController extends BaseController {
	@Autowired
	private IMenuMngService service;
	
	@RequestMapping(value = "/loadMenu.do")
	public void loadMenu(String userId,String authStr,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		List<EasyUITree> list=this.service.getTreeMenu(userId,authStr);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("menus", list);
		writeObjAsJsonObjToClient(response, map);
		
	}

}
