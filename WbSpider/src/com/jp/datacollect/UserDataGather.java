package com.jp.datacollect;
/* 作者：jiaopan
 * 时间：2016.5
 * */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jp.DbUtil.DbUtil;
import com.jp.model.User;

public class UserDataGather {
	
	public static void main(String[] args) throws Exception{
		// Connection conn=DbUtil.getConn();
		 
			//Spider(conn);
		
		//DbUtil.closeConn(conn);
	}
	
	public static void SpiderUser(Connection conn,int start,int count) throws Exception{
		String sql="SELECT * from t_userlist where visited=0 limit  ";
		List<User> rootusers=selectUserID(conn,sql+start+","+count);
		Insert(conn,rootusers);
	}
	
	//查询用户
	private static List<User> selectUserID(Connection conn,String sql) throws Exception{
		List<User> list=new ArrayList<User>();
		PreparedStatement psmt=conn.prepareStatement(sql);
		ResultSet rs=psmt.executeQuery();
		while(rs.next()){
			User u=new User();
			u.setId(rs.getInt("id"));
			u.setUserid(rs.getString("userid"));
			u.setUsername(rs.getString("username"));
			u.setRootuserid(rs.getString("rootuserid"));
			u.setRootusername(rs.getString("rootusername"));
			
			list.add(u);
		}
		psmt.close();
		DbUtil.closeRs(rs);
		
		return list;
	}
	
	//将新的用户插入到数据库中
	private static void Insert(Connection conn,List<User> rootusers) throws Exception{
		String sql="insert into t_userlist(userid,username,rootuserid,rootusername,visited) values(?,?,?,?,?)";
		String sqlupdate="update t_userlist set visited=1 where userid=?";
		String sqlvisited="SELECT * from t_userlist where visited=1 and userid=";
		PreparedStatement psmt = null;
		PreparedStatement ps;
		List<User> userlist=null;
		User user=null;
		int visitedUser=0;
		for(int i=0;i<rootusers.size();i++){
			 user=rootusers.get(i);//开始遍历的用户
			
			userlist=htmlParser.parseUserlist(user);//被遍历用户关注的列表
			//设置用户已遍历其关注列表
			ps=conn.prepareStatement(sqlupdate);
			ps.setString(1,user.getUserid());
			ps.executeUpdate();
			ps.close();
			if(userlist!=null){
				
				conn.setAutoCommit(false);
				try {
					for(int j=0;j<userlist.size();j++){
						
						if(userlist.get(j).getRootuserid()==user.getUserid()){
							//相同的关注中已被查询 不再遍历其关注用户
							visitedUser=selectUserID(conn,sqlvisited+"'"+userlist.get(j).getUserid()+"'").size();
							
							psmt=conn.prepareStatement(sql);
							psmt.setString(1, userlist.get(j).getUserid());
							psmt.setString(2, userlist.get(j).getUsername());
							psmt.setString(3, user.getUserid());
							psmt.setString(4, user.getUsername());
							if(visitedUser!=0){
								psmt.setInt(5, 1);
							}
							else {
								psmt.setInt(5, 0);
							}
							psmt.executeUpdate();
							psmt.close();
						}else {
							continue;
						}
					}
					
					
					conn.commit();
					
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}catch (Exception e) {
					try {
						if(conn!=null)
						{
						      conn.rollback(); //SQL语句出现异常 事务回滚（取消在当前事务中进行的所有更改）
						     // conn.setAutoCommit( true);
						}
					} catch (Exception e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}

				userlist.clear();
				System.out.println("插入"+user.getUsername()+"关注列表成功！");
			}else {
				continue;
			}
			
		}
		
	}
}
