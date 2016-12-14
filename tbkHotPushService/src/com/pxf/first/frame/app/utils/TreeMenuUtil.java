package com.pxf.first.frame.app.utils;

import java.util.ArrayList;
import java.util.List;

import com.pxf.first.frame.enty.menu.bo.TreeNode;
import com.pxf.first.frame.enty.menu.vo.EasyUITree;

public class TreeMenuUtil {
	
	public static List<EasyUITree> convertEasyUITree(List<TreeNode> list){
		List<EasyUITree> easyUITreeList=new ArrayList<EasyUITree>();
		for(TreeNode node:list){
			EasyUITree easyUITreeNode=new EasyUITree();
			easyUITreeNode.setMenuid(node.getId());
			easyUITreeNode.setPid(node.getPid());
			easyUITreeNode.setMenuname(node.getText());
			easyUITreeNode.setIcon(node.getImgUrl());
			//如果不是父节点
			if(!node.getPid().equals("0")){
				easyUITreeNode.setUrl(node.getUrl());
			}
			easyUITreeList.add(easyUITreeNode);
		}
		List<EasyUITree> newEasyUITreeList=getFatherNode(easyUITreeList);
		return newEasyUITreeList;
	}
	public static List<EasyUITree> getFatherNode(List<EasyUITree> list){
		List<EasyUITree> newEasyUITreeList=new ArrayList<EasyUITree>();
		for(EasyUITree treeNode:list){
			if(treeNode.getPid().equals("0")){
				treeNode.setChild(getChildrenNode(treeNode.getMenuid(), list));
				newEasyUITreeList.add(treeNode);
			}
		}
		return newEasyUITreeList;
	}
	public static List<EasyUITree> getChildrenNode(String pid,List<EasyUITree> list){
		List<EasyUITree> newEasyUITree=new ArrayList<EasyUITree>();
		for(EasyUITree node:list){
			if(node.getPid().equals("0")){
				continue;
			}else if(node.getPid().equals(pid)){
				node.setChild(getChildrenNode(node.getMenuid(), list));
				newEasyUITree.add(node);
			}
		}
		return newEasyUITree;
	}
	

}
