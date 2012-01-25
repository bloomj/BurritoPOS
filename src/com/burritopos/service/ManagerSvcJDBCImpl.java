/**
 * 
 */
package com.burritopos.service;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Manager;

import java.util.Date;
import java.sql.*;


/**
 * @author james.bloom
 *
 */
public class ManagerSvcJDBCImpl implements IManagerSvc {

	private static Logger dLog = Logger.getLogger(ManagerSvcJDBCImpl.class);
	private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Manager getManager(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getManager | Manager ID: "+id);
		Manager m = null;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "SELECT * FROM Employee WHERE employeeID = ? AND isManager = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());
			stmt.setBoolean(2, Boolean.TRUE);
			ResultSet rs = stmt.executeQuery ();

			//ensure we were passed a valid object before attempting to write
			if(rs.next()) {
				m = new Manager();
				m.setEmployeeID(id);
				m.setFirstName(rs.getString("firstname"));
				m.setLastName(rs.getString("lastname"));
			}
		} 
		catch (SQLException e1) {
			dLog.error(new Date() + " | SQLException in getManager: "+e1.getMessage());
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in getManager: "+e2.getMessage());
		}
		finally {
			//ensure that conn/stmt is close regardless of the errors in try/catch
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}

		return m;
	}

	@Override
	public boolean storeManager(Manager m) throws IOException, Exception {
		dLog.info(new Date() + " | Entering method storeManager | Manager ID: "+m.getEmployeeID());
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(m.validate()) {
				conn = DriverManager.getConnection(connString);
				String sql = "SELECT COUNT(1) FROM Employee WHERE employeeID = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, m.getEmployeeID().toString());
				ResultSet rs = stmt.executeQuery ();
				rs.first();

				if(rs.getInt(1) > 0) {
					//if first is a valid row, then we need to do an update
					dLog.info(new Date() + " | Updating manager in database");
					System.out.println("Updating manager in database");

					if(stmt != null) {stmt.close();}

					sql = "UPDATE Employee SET firstname=?,lastname=?,isManager=? WHERE employeeID = ?";
				}
				else {
					//if first is null, then we need to do an insert
					dLog.info(new Date() + " | Inserting manager into database");
					System.out.println("Inserting manager into database");

					if(stmt != null) {stmt.close();}

					sql = "INSERT INTO Employee (firstname,lastname,isManager,employeeID) VALUES (?,?,?,?)";
				}

				dLog.info(new Date() + " | SQL Statement: "+sql);
				stmt = conn.prepareStatement(sql);

				stmt.setString(1, m.getFirstName());
				stmt.setString(2, m.getLastName());
				stmt.setBoolean(3, Boolean.TRUE);
				stmt.setString(4, m.getEmployeeID().toString());

				if(stmt.executeUpdate() > 0)
					result = true;
				else
					result = false;
			}
		} 
		catch (SQLException e1) {
			dLog.error(new Date() + " | SQLException in storeManager: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in storeManager: "+e2.getMessage());
			result = false;
		}
		finally {
			//ensure that conn/stmt is close regardless of the errors in try/catch
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}

		return result;
	}

	@Override
	public boolean deleteManager(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteManger | Manager ID:"+id);
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "DELETE FROM Employee WHERE employeeID = ? AND isManager = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());
			stmt.setBoolean(2, Boolean.TRUE);

			if(stmt.executeUpdate() > 0)
				result = true;
			else
				result = false;
		}
		catch (SQLException e1) {
			dLog.error(new Date() + " | SQLException in deleteManager: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in deleteManger: "+e2.getMessage());
			result = false;
		}
		finally {
			//ensure that conn/stmt is close regardless of the errors in try/catch
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}

		return result;
	}
}
