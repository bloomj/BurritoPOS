/**
 * 
 */
package com.burritopos.service.dao.jdbc;

import java.io.*;
import org.apache.log4j.*;

import com.burritopos.domain.Employee;
import com.burritopos.service.dao.IEmployeeSvc;

import java.sql.*;

/**
 * @author james.bloom
 *
 */
public class EmployeeSvcImpl implements IEmployeeSvc {

	private static Logger dLog = Logger.getLogger(EmployeeSvcImpl.class);
    // TODO: Come back and move connection string to encrypted config file or some other method
	private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Employee getEmployee(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info("Entering method getEmployee | Employee ID: "+id);
		Employee e = null;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "SELECT * FROM Employee WHERE employeeID = ? AND isManager = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());
			stmt.setBoolean(2, Boolean.FALSE);
			ResultSet rs = stmt.executeQuery ();

			//ensure we were passed a valid object before attempting to write
			if(rs.next()) {
				e = new Employee();
				e.setEmployeeID(id);
				e.setFirstName(rs.getString("firstname"));
				e.setLastName(rs.getString("lastname"));
			}
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in getEmployee: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e2) {
			dLog.error("Exception in getEmployee: "+e2.getMessage());
			e2.printStackTrace();
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

		return e;
	}

	@Override
	public boolean storeEmployee(Employee e) throws IOException, Exception {
		dLog.info("Entering method storeEmployee | Employee ID: "+e.getEmployeeID());
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(e.validate()) {
				conn = DriverManager.getConnection(connString);
				String sql = "SELECT COUNT(1) FROM Employee WHERE employeeID = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, e.getEmployeeID().toString());
				ResultSet rs = stmt.executeQuery ();
				rs.first();

				if(rs.getInt(1) > 0) {
					//if first is a valid row, then we need to do an update
					dLog.info("Updating employee in database");

					if(stmt != null) {stmt.close();}

					sql = "UPDATE Employee SET firstname=?,lastname=?,isManager=? WHERE employeeID = ?";
				}
				else {
					//if first is null, then we need to do an insert
					dLog.info("Inserting employee into database");

					if(stmt != null) {stmt.close();}

					sql = "INSERT INTO Employee (firstname,lastname,isManager,employeeID) VALUES (?,?,?,?)";
				}

				dLog.info("SQL Statement: "+sql);
				stmt = conn.prepareStatement(sql);

				stmt.setString(1, e.getFirstName());
				stmt.setString(2, e.getLastName());
				stmt.setBoolean(3, Boolean.FALSE);
				stmt.setString(4, e.getEmployeeID().toString());

				if(stmt.executeUpdate() > 0) {
					result = true;
				}
			}
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in storeEmployee: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e2) {
			dLog.error("Exception in storeEmployee: "+e2.getMessage());
			e2.printStackTrace();
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
	public boolean deleteEmployee(Integer id) throws Exception {
		dLog.info("Entering method deleteEmployee | Employee ID:"+id);
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "DELETE FROM Employee WHERE employeeID = ? AND isManager = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());
			stmt.setBoolean(2, Boolean.FALSE);

			if(stmt.executeUpdate() > 0)
				result = true;
		}
		catch (SQLException e1) {
			dLog.error("SQLException in deleteEmployee: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e) {
			dLog.error("Exception in deleteEmployee: "+e.getMessage());
			e.printStackTrace();
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
