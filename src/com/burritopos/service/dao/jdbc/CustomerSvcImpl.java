/**
 * 
 */
package com.burritopos.service.dao.jdbc;

import org.apache.log4j.*;

import com.burritopos.domain.Customer;
import com.burritopos.service.dao.ICustomerSvc;

import java.sql.*;

/**
 * @author james.bloom
 *
 */
public class CustomerSvcImpl implements ICustomerSvc {
    private static Logger dLog = Logger.getLogger(CustomerSvcImpl.class);
    // TODO: Come back and move connection string to encrypted config file or some other method
    private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Customer getCustomer(Integer id) throws Exception {
		dLog.info("Entering method getCustomer | Customer ID: "+id);
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
            	dLog.trace("getting customer");
                c.setId(Integer.parseInt(id.toString()));
                c.setEmailAddress(rs.getString("emailaddress"));
                c.setFirstName(rs.getString("firstname"));
                c.setLastName(rs.getString("lastname"));
            }
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in getCustomer: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e2) {
			dLog.error("Exception in getCustomer: "+e2.getMessage());
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
		
		return c;
	}

	@Override
	public boolean storeCustomer(Customer c) throws Exception {
		dLog.info("Entering method storeCustomer | Customer ID: "+c.getId());
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
					dLog.info("Updating customer in database");

					if(stmt != null) {stmt.close();}

					sql = "UPDATE Customer SET emailaddress=?,firstname=?,lastname=? WHERE id = ?";
				}
				else {
					//if first is null, then we need to do an insert
					dLog.info("Inserting customer into database");

					if(stmt != null) {stmt.close();}

					sql = "INSERT INTO Customer (emailaddress,firstname,lastname,id) VALUES (?,?,?,?)";
				}

				dLog.info("SQL Statement: "+sql);
				stmt = conn.prepareStatement(sql);

				stmt.setString(1, c.getEmailAddress());
				stmt.setString(2, c.getFirstName());
				stmt.setString(3, c.getLastName());
				stmt.setString(4, c.getId().toString());

				if(stmt.executeUpdate() > 0) {
					result = true;
				}
			}
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in storeCustomer: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e2) {
			dLog.error("Exception in storeCustomer: "+e2.getMessage());
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
	public boolean deleteCustomer(Integer id) throws Exception {
		dLog.info("Entering method deleteCustomer | Customer ID:"+id);
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "DELETE FROM Customer WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());

			if(stmt.executeUpdate() > 0) {
				result = true;
			}
		}
		catch (SQLException e1) {
			dLog.error("SQLException in deleteCustomer: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e2) {
			dLog.error("Exception in deleteCustomer: "+e2.getMessage());
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

}
