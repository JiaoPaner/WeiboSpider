# WeiboSpider
//test
作者：JiaoPan  
Email:jiaopaner@163.com

<br>
2017/6/28 更新
这个小项目是一年多前写的  很烂 不够通用 遇到微博便签变化后需要重改 效率不高 
近期公司项目完成后 将会把所有代码重构 变成抓取微博数据的通用框架 
<br>
++++++++++++++++++++
本项目是一个抓取微博数据的爬虫程序,抓取用户的个人信息以及用户的关注列表

程序会对用户的关注列表的关注列表进行再次抓取,数据库中已抓取的不再抓取

程序效果图

![image](http://a3.qpic.cn/psb?/9f72dd68-4a03-4651-9f2d-60315a7e474a/k7JI94LbutdjTLmD6h2Qiqc.8H.cpvupIC6n53CDi6s!/b/dA0BAAAAAAAA&bo=xAMvAgAAAAAFB84!&rf=viewer_4)

![image](http://a1.qpic.cn/psb?/9f72dd68-4a03-4651-9f2d-60315a7e474a/5tcygnDCgMfv*lxgsB1oRLrb8.81I.SL4XnjgAhZMog!/b/dHEBAAAAAAAA&bo=PQTrAQAAAAAFAPA!&rf=viewer_4)

![image](http://a1.qpic.cn/psb?/9f72dd68-4a03-4651-9f2d-60315a7e474a/xzdfZd8r6I5dzMCSrKLUQaVSMsaqE38ziw1jvu4rPxE!/b/dHEBAAAAAAAA&bo=vgKpAQAAAAAFADc!&rf=viewer_4)

用到的第三方库：Okhttp,okio,Jsoup   [运行项目前请下载并添加构建这些库]

main包中就是两个程序的执行类 一个执行时抓用户的关注列表 另一个用来抓用户个人数据  

当然你可以写在一个类中 利用线程并发执行 我只是为了测试方便  

同时你也可以利用IDE 看程序的执行和方法的调用过程 方便理解 这里我就不再一一解释说明了 程序员都会

Spider包中是抓取信息并解析的类 

datacollect包的类是将Spider下的类返回的相关list数据插入到数据库

[我用的是mysql, DBUtil中改为你自己的数据库名] 

首先在数据库 t_userlist 表中添加一条你自己的微博账号信息或者其他作为初始数据 程序会从该账号开始抓取数据

如http://weibo.com/u/3190836xxx/home  登录微博时 网址中u后面的数字就是该微博账号的ID 数据库中添加它作为初始的数据

注意：spider包下的spider类中的cookies的值需要修改为你自己的微博测试账号模拟登陆时返回的cookie值

若不清楚如何抓包获取cookies 请自行Google

最好注册微博小号进行模拟登陆抓数据  

否则在你真正登陆微博时你的账号会提示异常 而且查看自己粉丝一类信息时会无限的跳到自己主页

在执行程序时 如果返回不到数据 有可能是微博网页的标签值有改变[出现机率不大] 

你需要打开微博网页查看其源代码 修改源码中利用jsoup解析html的标签名 

PS：由于微博反爬虫做的比较好  登陆时进行了三重加密. 
我还不太清楚其加密机制 同时为了省去麻烦 本项目没有加密操作 下次打开电脑运行项目前先抓包再重新修改下cookie值就可以了


=============================
数据库表：用户表和用户信息表

用户表：
-- ----------------------------
-- Table structure for t_userlist
-- ----------------------------
DROP TABLE IF EXISTS `t_userlist`;

CREATE TABLE `t_userlist` (

  `id` int(11) NOT NULL auto_increment,
  
  `userid` varchar(255) default NULL,
  
  `username` varchar(255) default NULL,
  
  `rootuserid` varchar(255) default NULL,
  
  `rootusername` varchar(255) default NULL,
  
  `mutualfollow` varchar(255) default '已关注',
  
  `visited` tinyint(4) default '0',
  
  `infostored` tinyint(4) default '0',
  
  PRIMARY KEY  (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=52476 DEFAULT CHARSET=utf8;



=========================================

用户信息表：

-- ----------------------------
-- Table structure for t_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;

CREATE TABLE `t_userinfo` (

  `id` int(11) NOT NULL auto_increment,
  
  `userid` varchar(255) default NULL,
  
  `username` varchar(255) default NULL,
  
  `address` varchar(255) default '无' COMMENT '所在地',
  
  `gender` varchar(255) default '保密' COMMENT '性别',
  
  `sexual` varchar(255) default '保密' COMMENT '性取向',
  
  `Relationship` varchar(255) default '保密' COMMENT '感情状况',
  
  `birthday` varchar(255) default '保密',
  
  `college` varchar(255) default '保密',
  
  `middlesch` varchar(255) default '保密',
  
  `blog` varchar(255) default '保密',
  
  `blood` varchar(255) default '保密',
  
  `profile` varchar(255) default '人懒什么都没写',
  
  `personaldomain` varchar(255) default '无' COMMENT '个性域名',
  
  `registertime` varchar(255) default '保密',
  
  `email` varchar(255) default '保密',
  
  `qq` varchar(255) default '保密',

  `tag` varchar(255) default '闲人一个' COMMENT '标签',
  
  PRIMARY KEY  (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

 ========================================================================================


