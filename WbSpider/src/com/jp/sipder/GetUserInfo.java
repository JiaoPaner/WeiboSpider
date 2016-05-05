package com.jp.sipder;
/* 作者：jiaopan
 * 时间：2016.5
 * */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jp.model.UserInfo;

public class GetUserInfo {
	
	private static UserInfo userInfo;
	private static String url;
	private static Document doc;
	private static Elements infos;
	public static UserInfo getUserInfo(String userid) throws Exception{
		url="http://weibo.com/p/100505"+userid+"/info?mod=pedit_more";
		doc=Jsoup.parse(Spider.getHtml(url));
		infos=doc.getElementsByAttributeValue("class", "li_1 clearfix");
		//System.out.println(infos.toString());
		userInfo = null; 
		
		if(infos!=null){
			userInfo= new UserInfo();
			Element info;
			String profile;
			for(int i=0;i<infos.size();i++){
				 info=infos.get(i);
				if(info.getElementsByAttributeValueContaining("href", "loc=infblog").size()==0){
					
					profile=info.getElementsByAttributeValue("class","pt_detail").first().text().trim();
					//System.out.println(profile);
					switch(info.getElementsByAttributeValue("class", "pt_title S_txt2").first().text()){
					case "昵称：":
						userInfo.setUsername(profile);
						break;
					case "所在地：":
						userInfo.setAddress(profile);
						break;
					case "性别：":
						userInfo.setGender(profile);
						break;
					case "性取向：":
						userInfo.setSexual(profile.replace("t", "").replace("rn", ""));
						break;
					case "感情状况：":
						userInfo.setRelationship(profile.replace("t", "").replace("rn", ""));
						break;
					case "生日：":
						userInfo.setBirthday(profile);
						break;
					case "血型：":
						userInfo.setBlood(profile);
						break;
					//case "博客：":
					//	userInfo.setBlog(profile.replace("t", "").replace("rn", ""));
					//	break;
					case "个性域名：":
						if(info.getElementsByAttributeValueContaining("href", "loc=infdomain").size()!=0)
						profile=info.select("a").text();
						userInfo.setPersonaldomain(profile);
						break;
					case "简介：":
						userInfo.setProfile(profile);
						break;
					case "注册时间：":
						userInfo.setRegistertime(profile.replace("t", "").replace("rn", ""));
						break;
					case "邮箱：":
						userInfo.setEmail(profile);
						break;
					case "QQ：":
						userInfo.setQq(profile);
						break;
					case "大学：":
						userInfo.setCollege(profile.replace("t", "").replace("rn", ""));
						break;
					case "高中：":
						userInfo.setMiddlesch(profile.replace("t", "").replace("rn", ""));
						break;
					case "标签：":
						userInfo.setTag(profile.replace("t", "").replace("rn", ""));
						break;
					}
				
					
					
				}else {
					String blogurl=info.select("a").text();
					//System.out.println(blogurl);
					userInfo.setBlog(blogurl);
				}
					
				
			}
		}
		
		//System.out.println(userInfo.getSexual());
		return userInfo;
	}
	
	public static void main(String[] args) throws Exception{
		String userid="2366566374";
		getUserInfo(userid);
	}
}
