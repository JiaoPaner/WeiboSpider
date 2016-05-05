package com.jp.main;
/* 作者：jiaopan
 * 时间：2016.5
 * */
import java.sql.Connection;
import java.util.Scanner;

import com.jp.DbUtil.DbUtil;
import com.jp.datacollect.UserDataGather;


public class ExecuteUserSpider {

	public static void main(String[] args) throws Exception {
		 Connection conn=DbUtil.getConn();
		 int start=0,count=9;
		 for(int i=0;i<7;i++){
			 UserDataGather.SpiderUser(conn,start,count);
		 }
		 DbUtil.closeConn(conn);
	     System.out.println("已抓取"+(count+1)*7+"个用户的关注数据");
		//Scanner in=new Scanner(System.in);
		// String op=in.next();
	}

}
