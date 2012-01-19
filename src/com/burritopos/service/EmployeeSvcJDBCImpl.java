/**
 * 
 */
package com.burritopos.service;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Employee;

import java.util.Date;
import java.sql.*;


/**
 * @author james.bloom
 *
 */
public class EmployeeSvcJDBCImpl implements IEmployeeSvc {

        private static Logger dLog = Logger.getLogger(EmployeeSvcJDBCImpl.class);
        private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Employee getEmployee(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info(new Date() + " | Entering method getEmployee | Employee ID: "+id);
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
			dLog.error(new Date() + " | SQLException in getEmployee: "+e1.getMessage());
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in getEmployee: "+e2.getMessage());
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
		dLog.info(new Date() + " | Entering method storeEmployee | Employee ID: "+e.getEmployeeID());
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
                                dLog.info(new Date() + " | Updating employee in database");
                                System.out.println("Updating employee in database");

                                if(stmt != null) {stmt.close();}

                                sql = "UPDATE Employee SET firstname=?,lastname=?,isManager=? WHERE employeeID = ?";
                            }
                            else {
                                //if first is null, then we need to do an insert
                                dLog.info(new Date() + " | Inserting employee into database");
                                System.out.println("Inserting employee into database");

                                if(stmt != null) {stmt.close();}

                                sql = "INSERT INTO Employee (firstname,lastname,isManager,employeeID) VALUES (?,?,?,?)";
                            }

                            dLog.info(new Date() + " | SQL Statement: "+sql);
                            stmt = conn.prepareStatement(sql);

                            stmt.setString(1, e.getFirstName());
                            stmt.setString(2, e.getLastName());
                            stmt.setBoolean(3, Boolean.FALSE);
                            stmt.setString(4, e.getEmployeeID().toString());

                            if(stmt.executeUpdate() > 0)
                                result = true;
                            else
                                result = false;
			}
		} 
		catch (SQLException e1) {
			dLog.error(new Date() + " | SQLException in storeEmployee: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in storeEmployee: "+e2.getMessage());
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
	public boolean deleteEmployee(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteEmployee | Employee ID:"+id);
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
                    else
                        result = false;
		}
                catch (SQLException e1) {
			dLog.error(new Date() + " | SQLException in deleteEmployee: "+e1.getMessage());
			result = false;
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteEmployee: "+e.getMessage());
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
