/**
 * 
 */
package com.burritopos.service;

//import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Customer;

import java.util.Date;
import java.sql.*;


/**
 * @author james.bloom
 *
 */
public class CustomerSvcJDBCImpl implements ICustomerSvc {

    private static Logger dLog = Logger.getLogger(CustomerSvcJDBCImpl.class);
    private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Customer getCustomer(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method getCustomer | Customer ID: "+id);
		Customer c = new Customer();
        Connection conn = null;
        PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(connString);
            String sql = "SELECT * FROM Customer WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery ();

            //ensure we were passed a valid object before attempting to write
            if(rs.next()) {
            	System.out.println("getting customer");
                c.setId(Integer.parseInt(id.toString()));
                c.setEmailAddress(rs.getString("emailaddress"));
                c.setFirstName(rs.getString("firstname"));
                c.setLastName(rs.getString("lastname"));
            }
		} 
		catch (SQLException e1) {
			dLog.error(new Date() + " | SQLException in getCustomer: "+e1.getMessage());
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in getCustomer: "+e2.getMessage());
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
		
		return c;
	}

	@Override
	public boolean storeCustomer(Customer c) throws Exception {
		dLog.info(new Date() + " | Entering method storeCustomer | Customer ID: "+c.getId());
		boolean result = false;
        Connection conn = null;
        PreparedStatement stmt = null;
		
		try {
			//ensure we were passed a valid object before attempting to write
			if(c.validate()) {
				conn = DriverManager.getConnection(connString);
				String sql = "SELECT COUNT(1) FROM Customer WHERE id = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, c.getId().toString());
				ResultSet rs = stmt.executeQuery ();
				rs.first();

				if(rs.getInt(1) > 0) {
					//if first is a valid row, then we need to do an update
					dLog.info(new Date() + " | Updating customer in database");
					System.out.println("Updating customer in database");

					if(stmt != null) {stmt.close();}

					sql = "UPDATE Customer SET emailaddress=?,firstname=?,lastname=? WHERE id = ?";
				}
				else {
					//if first is null, then we need to do an insert
					dLog.info(new Date() + " | Inserting customer into database");
					System.out.println("Inserting customer into database");

					if(stmt != null) {stmt.close();}

					sql = "INSERT INTO Customer (emailaddress,firstname,lastname,id) VALUES (?,?,?,?)";
				}

				dLog.info(new Date() + " | SQL Statement: "+sql);
				stmt = conn.prepareStatement(sql);

				stmt.setString(1, c.getEmailAddress());
				stmt.setString(2, c.getFirstName());
				stmt.setString(3, c.getLastName());
				stmt.setString(4, c.getId().toString());

				if(stmt.executeUpdate() > 0)
					result = true;
				else
					result = false;
			}
		} 
		catch (SQLException e1) {
			dLog.error(new Date() + " | SQLException in storeCustomer: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in storeCustomer: "+e2.getMessage());
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
	public boolean deleteCustomer(Integer id) throws Exception {
		dLog.info(new Date() + " | Entering method deleteCustomer | Customer ID:"+id);
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "DELETE FROM Customer WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());

			if(stmt.executeUpdate() > 0)
				result = true;
			else
				result = false;
		}
		catch (SQLException e1) {
			dLog.error(new Date() + " | SQLException in deleteCustomer: "+e1.getMessage());
			result = false;
		}
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in deleteCustomer: "+e2.getMessage());
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
