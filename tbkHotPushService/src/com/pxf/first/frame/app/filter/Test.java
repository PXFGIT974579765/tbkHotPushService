package com.pxf.first.frame.app.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		List<Book> list= new ArrayList<Book>();
		Test test=new Test();
		Map<String,String> map=new HashMap<String, String>();
		map.put("", "");
		Book book=test.new Book("1", "��");
		Book book2=test.new Book("2", "��");
		Book book3=test.new Book("3", "��");
		Book book4=test.new Book("4", "��");
		list.add(book);
		list.add(book2);
		list.add(book3);
		list.add(book4);
		for(Book b:list){
			System.out.println(b.getName()+":"+b.getAge());
		}
		System.out.println("==================");
		list.get(2).setAge("�������˵�");
		for(Book b:list){
			System.out.println(b.getName()+":"+b.getAge());
		}

	}
	
	class Book{
		String name;
		String age;
		public Book(String name,String age) {
			this.name=name;
			this.age=age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
	}
	

}
