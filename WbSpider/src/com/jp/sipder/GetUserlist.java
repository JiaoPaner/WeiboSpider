package com.jp.sipder;
/* 作者：jiaopan
 * 时间：2016.5
 * */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetUserlist {
	static String lists;
	private static String url;
	private static Document doc;
	private static Elements W_pages;
	private static Elements pages=null;
	private static Document docnext;
	private static Elements followUsersnext;
	private static int pageCount=0;
	public static String getUserList(String userid) throws Exception{
		lists = null;
	    url="http://weibo.com/p/100505"+userid+"/follow?page=";
		doc=Jsoup.parse(Spider.getHtml(url+"1"));
		W_pages=doc.getElementsByAttributeValue("class", "W_pages");
		        
		if(W_pages.first()!=null){
			pages = W_pages.first().getElementsByAttributeValue("class", "page S_txt1");  
			//System.out.println(pages.size());
			pageCount=Integer.parseInt(pages.get(pages.size()-1).text());	//关注列表总页数
			System.out.println("已估计下一个用户关注列表总页数："+pageCount);
			for(int i=1;i<=pageCount+1;i++){
				 docnext=Jsoup.parse(Spider.getHtml(url+i));
				 followUsersnext=docnext.getElementsByAttributeValue("class", "S_txt1");//关注列表
				lists+=followUsersnext.toString();
				//lists+=followUsersnext.toString().replace("id=", "").replace("&amp;refer_flag=1005050006_", "");
			}
		}else {
			System.out.println("======查询下一个用户======");
		}
		
		return lists;
	}
	
	public static void main(String[] args) throws Exception{
		//String userid="2645870462";
		//getUserList(userid);
	}
}
