package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import model.DBConnection;

public class LoginControl {
	DBConnection db = new DBConnection();
	public boolean login(String username, String password) {
		boolean status = false;
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		
		try(Connection conn = db.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);) {
				{
				stmt.setString(1, username);
				stmt.setString(2, password);
				
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					status = true;
				
				}
				}
			conn.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			return status;	
	}

}
