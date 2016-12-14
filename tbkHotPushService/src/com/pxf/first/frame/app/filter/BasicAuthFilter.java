package com.pxf.first.frame.app.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Decoder.BASE64Decoder;

public class BasicAuthFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		HttpSession session=request.getSession();

        String user=(String)session.getAttribute("user");

        String pass;

        if(user==null){

            try{

               response.setCharacterEncoding("UTF-8");
               response.setContentType("application/json; charset=utf-8"); 

               PrintWriter out=response.getWriter();

               String authorization=request.getHeader("authorization");

               if(authorization==null||authorization.equals("")){

                   response.setStatus(401);

                   response.setHeader("WWW-authenticate","Basic realm=\"请输入管理员密码\"");

                   out.print("对不起你没有权限！！");

                   return;

               }

               String userAndPass=new String(new BASE64Decoder().decodeBuffer(authorization.split(" ")[1]));

               if(userAndPass.split(":").length<2){

                   response.setStatus(401);

                   response.setHeader("WWW-authenticate","Basic realm=\"请输入管理员密码\"");

                   out.print("对不起你没有权限！！");

                   return;

               }

               user=userAndPass.split(":")[0];

               pass=userAndPass.split(":")[1];

               if(user.equals("111")&&pass.equals("111")){

                   session.setAttribute("user",user);

                   RequestDispatcher dispatcher=request.getRequestDispatcher("index.jsp");

                   dispatcher.forward(request,response);

               }else{

                   response.setStatus(401);

                   response.setHeader("WWW-authenticate","Basic realm=\"请输入管理员密码\"");

                   out.print("对不起你没有权限！！");

                   return;

               }

            }catch(Exception ex){

               ex.printStackTrace();

            }

        }else{

            RequestDispatcher dispatcher=request.getRequestDispatcher("index.jsp");

            dispatcher.forward(request,response);

      }

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
