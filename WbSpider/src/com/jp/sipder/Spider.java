package com.jp.sipder;

/* 作者：jiaopan
 * 时间：2016.5
 * */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Spider {
	//http://weibo.com/p/1005053472041784  访问个人主页
	//http://weibo.com/p/1005053472041784/info?mod=pedit_more //个人信息页面
	//http://weibo.com/p/1005053472041784/follow?&page=3  翻页
	
	//抓取对关注的关注
	//http://weibo.com/p/100505"+用户ID+"/follow?&page=3  //不用更改cookie
	
	//you must modify the the value of cookies base on your own Weibo account
	//你必须根据你自己的微博账号更改这个cookies的值 【利用浏览器的抓包工具】
	private static String cookies="cookies";
	private static String userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36";
	//获取关注列表的方法
	public static String getHtml(String url) throws Exception{
		
		String html=null;
		OkHttpClient client=new OkHttpClient();
		
		Request request=new Request.Builder()
		                .url(url)
		                .addHeader("Cookie",cookies)
		                .addHeader("Host", "weibo.com")
		                .addHeader("User-Agent",userAgent)
		                .build();
		Response response=client.newCall(request).execute();
		
		if(response.isSuccessful()){
			html=response.body().string().replace("\\", "");
			Document doc=Jsoup.parse(html);
			Elements scripts = doc.getElementsByTag("script"); //获取script标签
			//Element script=scripts.get(scripts.size()-1);  // 获取包含了网页内容的script标签
			
			//System.out.println(script.html());
			Pattern p=Pattern.compile("\"html\":\"");    //从该json数据格式中抽取出html内容
			String htmlstr="";
			for(Element script:scripts){
				Matcher m=p.matcher(html=script.html());
				if(m.find()){
					String str=html.substring(m.end(),html.length()-3);
					htmlstr+=str;
				}
			}
			
			html=Jsoup.parse(htmlstr).html();
			//System.out.println(html);
			
			
		}
		else{
			System.out.println("Network is error");
		}
		
		return html;
	}
	
	
	

	
	//测试
	public static void main(String[] args) throws Exception{
		//getHtml("http://weibo.com/p/1005053472041784/info?mod=pedit_more");
	}
	
}
