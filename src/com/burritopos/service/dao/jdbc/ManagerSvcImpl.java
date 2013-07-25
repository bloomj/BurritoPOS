/**
 * 
 */
package com.burritopos.service.dao.jdbc;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Manager;
import com.burritopos.service.dao.IManagerSvc;

import java.sql.*;

/**
 * @author james.bloom
 *
 */
public class ManagerSvcImpl implements IManagerSvc {
	private static Logger dLog = Logger.getLogger(ManagerSvcImpl.class);
    // TODO: Come back and move connection string to encrypted config file or some other method
	private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Manager getManager(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info("Entering method getManager | Manager ID: "+id);
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
			dLog.error("SQLException in getManager", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in getManager", e2);
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
		dLog.info("Entering method storeManager | Manager ID: "+m.getEmployeeID());
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
					dLog.info("Updating manager in database");

					if(stmt != null) {stmt.close();}

					sql = "UPDATE Employee SET firstname=?,lastname=?,isManager=? WHERE employeeID = ?";
				}
				else {
					//if first is null, then we need to do an insert
					dLog.info("Inserting manager into database");

					if(stmt != null) {stmt.close();}

					sql = "INSERT INTO Employee (firstname,lastname,isManager,employeeID) VALUES (?,?,?,?)";
				}

				dLog.info("SQL Statement: "+sql);
				stmt = conn.prepareStatement(sql);

				stmt.setString(1, m.getFirstName());
				stmt.setString(2, m.getLastName());
				stmt.setBoolean(3, Boolean.TRUE);
				stmt.setString(4, m.getEmployeeID().toString());

				if(stmt.executeUpdate() > 0) {
					result = true;
				}
			}
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in storeManager", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in storeManager", e2);
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
		dLog.info("Entering method deleteManger | Manager ID:"+id);
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "DELETE FROM Employee WHERE employeeID = ? AND isManager = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());
			stmt.setBoolean(2, Boolean.TRUE);

			if(stmt.executeUpdate() > 0) {
				result = true;
			}
		}
		catch (SQLException e1) {
			dLog.error("SQLException in deleteManager", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in deleteManger", e2);
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
