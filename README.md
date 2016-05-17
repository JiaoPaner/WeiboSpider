# WeiboSpider

作者：JiaoPan  
Email:jiaopaner@163.com

本项目是一个抓取微博数据的爬虫程序,抓取用户的个人信息以及用户的关注列表

程序会对用户的关注列表的关注列表进行再次抓取,数据库中已抓取的不再抓取



用到的第三方库：Okhttp,Jsoup   [运行项目前请下载并添加这些库]

首先在数据库t_userlist 表中添加一条你自己的微博账号信息或者其他作为初始数据 程序会从该账号开始抓取数据

如http://weibo.com/u/3190836xxx/home  登录微博时 网址中u后面的数字就是该微博账号的ID 数据库中添加它作为初始的数据

注意：spider包下的spider类中的cookies的值需要修改为你自己的微博测试账号模拟登陆时返回的cookie值

最好注册微博小号进行模拟登陆抓数据  

否则在你真正登陆微博时你的账号会提示异常 而且查看自己粉丝一类信息时会无限的跳到自己主页

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


