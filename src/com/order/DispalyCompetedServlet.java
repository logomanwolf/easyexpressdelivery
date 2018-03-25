package com.order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.dao.OrderDao;
import com.entity.Deliveryorder;
import com.entity.Merchant;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class DispalyCompetedServlet
 */
@WebServlet("/DispalyCompetedServlet")
public class DispalyCompetedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispalyCompetedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * ��ֹ����
		 */
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		/**
		 * �������json���ݶ�ȡ������
		 */
		 InputStream is = request.getInputStream();
		 BufferedReader reader = new BufferedReader(new InputStreamReader(is,
		 "utf-8"));
		 String line = null;
		 StringBuffer sb = new StringBuffer();
		 while ((line = reader.readLine()) != null) {
		 sb.append(line);
		 }
		 String result;
		 System.out.println(sb.toString());
		 String MerchantJson = sb.toString();
		 System.out.println(MerchantJson);//
		// ����sbΪjson���ݰ�����merchantid,address,latitude,longitude,phone,storename,tofgid
		 Merchant merchant = JSON.parseObject(MerchantJson,
		 Merchant.class);
		 System.out.println(merchant.getMerchantid());
		 List<Deliveryorder> deliveryorders = new ArrayList<>();
			OrderDao orderdao = new OrderDao();
			 deliveryorders = orderdao.getAllCompletedOrder(merchant.getMerchantid());
//			deliveryorders = orderdao.getAllInactiveDelOrder(1L);
			//String result;
			if (deliveryorders == null)
				result = "failed";
			else if (deliveryorders.size() == 0)
				result = "failed";
			else
				result = "success";
			JSONArray jsonArray = JSONArray.fromObject(deliveryorders);
			//String delOrdersStr = JSON.toJSONString(deliveryorders);
			System.out.println(jsonArray.toString());
			response.getWriter().append(jsonArray.toString());
	}

}