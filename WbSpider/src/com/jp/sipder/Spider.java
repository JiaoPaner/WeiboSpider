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
	private static String cookies="SINAGLOBAL=275609153322.8755.1462188716594; un=15388478188; wvr=6; wb_publish_vip_3190836912=5; YF-Ugrow-G0=8751d9166f7676afdce9885c6d31cd61; SUS=SID-3190836912-1462422379-GZ-85nku-962359cb7381b52e60cd8e91ffd9330f; SUE=es%3D93c51aca6a3332f8d1f2986a2f6e8eae%26ev%3Dv1%26es2%3D8b0ec568c24aca12cd563fab8c40f364%26rs0%3DnZj3bgnzoA5R3IkyYAfwrTLpD2kQ9kFAbuy%252FEoUt9XGDbl9Q%252BfunPUycvCo47WMskZVCQd4uLD2Dv%252FRUf9uMnhIzDfVcQhaVPCVBX7ww%252BGWuuPlAhDQeYpWcRtd8OoBp0tTlPbc3z2OOOmVgcxBhZmPEszZFLAWXpQHUyLhGsS0%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1462422379%26et%3D1462508779%26d%3Dc909%26i%3D330f%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D3190836912%26name%3D15388478188%2540sina.cn%26nick%3Dkyleson%26fmp%3D%26lcp%3D2013-08-25%252001%253A46%253A20; SUB=_2A256Lrs7DeRxGeVP4lIZ8yjFyj6IHXVZXavzrDV8PUNbvtBeLW77kW9LHesGzqG6ltOPUJH1hwrvZ1wscqjxsw..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WW28zmDrWGMsns2-MrL8N5i5JpX5KMhUgL.Foep1K5Re0q4eKzt; SUHB=0vJIYgRpPBOErM; ALF=1493958378; SSOLoginState=1462422379; YF-V5-G0=b1e3c8e8ad37eca95b65a6759b3fc219; _s_tentry=login.sina.com.cn; Apache=367797287181.0198.1462422355521; ULV=1462422355548:5:5:5:367797287181.0198.1462422355521:1462370504763; UOR=,,spr_web_sq_kings_weibo_t001; YF-Page-G0=074bd03ae4e08433ef66c71c2777fd84";
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
