<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.opensymphony.xwork2.inject.Context"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page import="com.chat.application.util.ConnectionUtil"%>
<%
	response.setHeader("Cache-Control", "no-cache, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Content-Type", "text/xml; charset=utf-8");
%>
<%!Connection connection = null;
%>
<%
Statement statement = null;
PreparedStatement stmt=null;
	try {
		connection = ConnectionUtil.getConnection();
		statement = connection.createStatement();
		//Check to see if a message was sent.
		String message = request.getParameter("message");
		String chat = request.getParameter("chat");		
		String name = (String)session.getAttribute("userName");
		String action = request.getParameter("action");
		String last = request.getParameter("last");
		String messageId = request.getParameter("message_id");
		String toUser = request.getParameter("chatUser");
		System.out.println(toUser);
		StringBuffer xml = new StringBuffer();
		String sourceNode ="";
		String destinationNode ="";
		boolean isFriend = false; 
		//application.getAttribute("")
		String friendsQuery = "select idfriend, friendUserName, status, userName from friend  where userName='"+name+"' and status='accepted'";
		
		/*	System.out.println("\nchat- " + chat + "\nname- " + name
					+ "\nAction- " + action + "\nlast- " + last
					+ "messageId=" + messageId + "Message- " + message);*/
		//statement.executeUpdate(friendsQuery);
		ResultSet resultSet = statement.executeQuery(friendsQuery);
		if(resultSet.next()) isFriend = true;
		if(isFriend){
			String sql = "SELECT nodeName FROM node where userName = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				sourceNode = rs.getString(1);
			}
			if(rs!=null)
				rs.close();	
			
			sql = "SELECT nodeName FROM node where userName = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, toUser);
			rs = stmt.executeQuery();
			if(rs.next()){
				destinationNode = rs.getString(1);
			}
			if(rs!=null)
				rs.close();	
		}
		
		if (message != null && isFriend) {
			String sql = "INSERT INTO message(chat_id, user_id, user_name, message, post_time, toUser) VALUES ('"
					+ chat
					+ "', 1, '"
					+ name
					+ "', '"
					+ message
					+ "', NOW(),'"+toUser+"')";
			statement.executeUpdate(sql);
		} else if(!isFriend){
			int count = 0;
			if (session.getAttribute("count"+toUser)!=null)
				 count = (Integer)session.getAttribute("count"+toUser);
			count++;
			session.setAttribute("count"+toUser, count);
			if(count>=3){
				
				String sql = "insert into sybilattack (user, ip, date, sourceNode, destinationNode) values "+
							"(?,?, NOW(), ?,?)";
				stmt = connection.prepareStatement(sql);
				stmt.setString(1, name);
				stmt.setString(2, request.getRemoteAddr());
				stmt.setString(3, sourceNode);
				stmt.setString(4, destinationNode);
				
				stmt.executeUpdate();
			}
		}
		//Check to see if a reset request was sent.
		if (action != null && action.equals("reset")) {			
			String sql = "DELETE FROM message WHERE user_name ='" + name+"' and toUser='"+toUser+"'" ;
			statement.executeUpdate(sql);
		}
		xml.append("<root>");
		if (request.getParameter("chat") == null) {
			xml
					.append("Your are not currently in a chat session.  <a href=''>Enter a chat session here</a>");
			xml.append("<message id='0'>");
			xml.append("<user>Admin</user>");
			xml
					.append("<text>Your are not currently in a chat session.  &lt;a href=''&gt;Enter a chat session here&lt;/a&gt;</text>");
			xml.append("<time>' . date('h:i') . '</time>");
			xml.append("</message>");
		} else {
			String sql = "SELECT message_id, user_name, message, date_format(post_time, '%h:%i') as post_time FROM message WHERE ((user_name = '"
					+ name+"' and toUser='"+toUser + "') or (user_name = '"+toUser + "' and toUser='"+ name+"')) AND message_id > " + last;
			System.out.println(sql);
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				xml.append("<message id='" + resultSet.getInt("message_id") + "'>");					
					xml.append("<user>" + resultSet.getString("user_name") + "</user>");
					String text = resultSet.getString("message");				
					xml.append("<text>" + text + "</text>");
					xml.append("<time>" + resultSet.getString("post_time") + "</time>");					
				xml.append("</message>");
			}
		}
		xml.append("</root>");
%>
<%=xml%>
<%	
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if(statement!=null)
			statement.close();
		if(stmt!=null)
			stmt.close();
		if(connection!=null)
			connection.close();
	}
%>