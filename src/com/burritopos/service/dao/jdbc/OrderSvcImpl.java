/**
 * 
 */
package com.burritopos.service.dao.jdbc;

import java.io.*;
//import java.math.BigDecimal;
import java.util.ArrayList;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Burrito;
import com.burritopos.domain.Order;
import com.burritopos.service.dao.IOrderSvc;

import java.sql.*;

/**
 * @author james.bloom
 *
 */
public class OrderSvcImpl implements IOrderSvc {

	private static Logger dLog = Logger.getLogger(OrderSvcImpl.class);
    // TODO: Come back and move connection string to encrypted config file or some other method
	private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Order getOrder(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info("Entering method getOrder | Order ID: "+id);
		Order o = new Order();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "select *, b.id from Orders LEFT JOIN Burrito on Orders.orderID = Burrito.orderID WHERE Orders.orderID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());
			ResultSet rs = stmt.executeQuery ();

			//ensure we were passed a valid object before attempting to write
			while(rs.next()) {
				o.setBurritos(new ArrayList<Burrito>());
				dLog.trace("Gonna set Order ID");
				//while(rs.next()) {
				o.setOrderID(id);
				o.setIsComplete(rs.getBoolean("iscomplete"));
				o.setOrderDate(new java.util.Date(rs.getDate("orderDate").getTime()));
				o.setIsSubmitted(rs.getBoolean("issubmitted"));
				o.setTotalCost(rs.getBigDecimal("totalCost"));

				//get any burritos from the query
				dLog.trace("Gonna get burrito: " + rs.getRow());
				//String bId = rs.getString("id");
				dLog.trace("Is null: " + rs.wasNull());
				if(!rs.wasNull()) {
					dLog.info("Adding burrito to Order | Burrito ID: "+rs.getString("b.id"));

					Burrito b = new Burrito();
					b.setId(rs.getInt("b.id"));
					b.setBeef(rs.getBoolean("beef"));
					b.setBlackBeans(rs.getBoolean("blackBeans"));
					b.setBrownRice(rs.getBoolean("brownRice"));
					b.setChicken(rs.getBoolean("chicken"));
					b.setChiliTortilla(rs.getBoolean("chiliTortilla"));
					b.setCucumber(rs.getBoolean("cucumber"));
					b.setFlourTortilla(rs.getBoolean("flourTortilla"));
					b.setGuacamole(rs.getBoolean("guacamole"));
					b.setHerbGarlicTortilla(rs.getBoolean("herbGarlicTortilla"));
					b.setHummus(rs.getBoolean("hummus"));
					b.setJalapenoCheddarTortilla(rs.getBoolean("jalapenoCheddarTortilla"));
					b.setJalapenos(rs.getBoolean("jalapenos"));
					b.setLettuce(rs.getBoolean("lettuce"));
					b.setOnion(rs.getBoolean("onion"));
					b.setPintoBeans(rs.getBoolean("pintoBeans"));
					b.setPrice(rs.getBigDecimal("price"));
					b.setSalsaPico(rs.getBoolean("salsaPico"));
					b.setSalsaSpecial(rs.getBoolean("salsaSpecial"));
					b.setSalsaVerde(rs.getBoolean("salsaVerde"));
					b.setTomatoBasilTortilla(rs.getBoolean("tomatoBasilTortilla"));
					b.setTomatoes(rs.getBoolean("tomatoes"));
					b.setWheatTortilla(rs.getBoolean("wheatTortilla"));
					b.setWhiteRice(rs.getBoolean("whiteRice"));

					o.getBurritos().add(b);
				}
				// }
			}
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in getOrder: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e2) {
			dLog.error("Exception in getOrder: "+e2.getMessage());
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

		return o;
	}

	@Override
	public boolean storeOrder(Order o) throws IOException, Exception {
		dLog.info("Entering method storeOrder | Order ID: "+o.getOrderID());
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(o.validate()) {
				conn = DriverManager.getConnection(connString);
				String sql = "SELECT COUNT(1) FROM Orders WHERE orderID = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, o.getOrderID().toString());
				ResultSet rs = stmt.executeQuery ();
				rs.first();

				if(rs.getInt(1) > 0) {
					//if first is a valid row, then we need to do an update
					dLog.info("Updating order in database");

					if(stmt != null) {stmt.close();}

					sql = "UPDATE Orders SET iscomplete=?,orderDate=?,issubmitted=?,totalCost=? WHERE orderID = ?";
				}
				else {
					//if first is null, then we need to do an insert
					dLog.info("Inserting order into database");

					if(stmt != null) {stmt.close();}

					sql = "INSERT INTO Orders (iscomplete,orderDate,issubmitted,totalCost,orderID) VALUES (?,?,?,?,?)";
				}

				dLog.info("SQL Statement: "+sql);
				stmt = conn.prepareStatement(sql);

				stmt.setBoolean(1, o.getIsComplete());
				stmt.setDate(2, new java.sql.Date(o.getOrderDate().getTime()));
				stmt.setBoolean(3, o.getIsSubmitted());
				stmt.setBigDecimal(4, o.getTotalCost());
				stmt.setString(5, o.getOrderID().toString());

				boolean orderResult = false;
				if(stmt.executeUpdate() > 0) {
					orderResult = true;
				}

				if(stmt != null) {stmt.close();}

				dLog.trace("Success: " + orderResult);
				if(orderResult) {
					//now insert the burritos
					dLog.trace("Trying to insert " + o.getBurritos().size() + " burritos");
					for(int n=0; n<o.getBurritos().size(); n++){
						//ensure we were passed a valid object before attempting to write
						Burrito b = o.getBurritos().get(n);

						dLog.trace("Validating burrito object");
						if(b.validate()) {
							sql = "SELECT COUNT(1) FROM Burrito WHERE id = ?";
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, b.getId().toString());
							rs = stmt.executeQuery ();
							rs.first();

							if(rs.getInt(1) > 0) {
								//if first is a valid row, then we need to do an update
								dLog.info("Updating burrito in database");

								if(stmt != null) {stmt.close();}

								sql = "UPDATE Burrito SET beef=?,blackbeans=?,brownrice=?,chicken=?,chiliTortilla=?,cucumber=?,";
								sql += "flourTortilla=?,guacamole=?,herbGarlicTortilla=?,hummus=?,jalapenoCheddarTortilla=?,jalapenos=?,";
								sql += "lettuce=?,onion=?,pintoBeans=?,price=?,salsaPico=?,salsaSpecial=?,salsaVerde=?,tomatoBasilTortilla=?,";
								sql += "tomatoes=?,wheatTortilla=?,whiteRice=?,orderID=? WHERE id = ?";
							}
							else {
								//if first is null, then we need to do an insert
								dLog.info("Inserting burrito into database");

								if(stmt != null) {stmt.close();}

								sql = "INSERT INTO Burrito (beef,blackbeans,brownrice,chicken,chiliTortilla,cucumber,";
								sql += "flourTortilla,guacamole,herbGarlicTortilla,hummus,jalapenoCheddarTortilla,jalapenos,";
								sql += "lettuce,onion,pintoBeans,price,salsaPico,salsaSpecial,salsaVerde,tomatoBasilTortilla,";
								sql += "tomatoes,wheatTortilla,whiteRice,orderID,id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							}

							dLog.info("SQL Statement: "+sql);
							stmt = conn.prepareStatement(sql);

							stmt.setBoolean(1, b.isBeef());
							stmt.setBoolean(2, b.isBlackBeans());
							stmt.setBoolean(3, b.isBrownRice());
							stmt.setBoolean(4, b.isChicken());
							stmt.setBoolean(5, b.isChiliTortilla());
							stmt.setBoolean(6, b.isCucumber());
							stmt.setBoolean(7, b.isFlourTortilla());
							stmt.setBoolean(8, b.isGuacamole());
							stmt.setBoolean(9, b.isHerbGarlicTortilla());
							stmt.setBoolean(10, b.isHummus());
							stmt.setBoolean(11, b.isJalapenoCheddarTortilla());
							stmt.setBoolean(12, b.isJalapenos());
							stmt.setBoolean(13, b.isLettuce());
							stmt.setBoolean(14, b.isOnion());
							stmt.setBoolean(15, b.isPintoBeans());
							stmt.setBigDecimal(16, b.getPrice());
							stmt.setBoolean(17, b.isSalsaPico());
							stmt.setBoolean(18, b.isSalsaSpecial());
							stmt.setBoolean(19, b.isSalsaVerde());
							stmt.setBoolean(20, b.isTomatoBasilTortilla());
							stmt.setBoolean(21, b.isTomatoes());
							stmt.setBoolean(22, b.isWheatTortilla());
							stmt.setBoolean(23, b.isWhiteRice());
							stmt.setString(24, o.getOrderID().toString());
							stmt.setString(25, b.getId().toString());

							if(stmt.executeUpdate() > 0) {
								result = true;
							}
						}
					}
				}
			}
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in storeOrder: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e2) {
			dLog.error("Exception in storeOrder: "+e2.getMessage());
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
	public boolean deleteOrder(Integer id) throws Exception {
		dLog.info("Entering method deleteOrder | Order ID:"+id);
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			//delete order's burritos
			String sql = "SELECT COUNT(1) FROM Burrito WHERE orderID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());
			ResultSet rs = stmt.executeQuery ();
			rs.first();

			if(rs.getInt(1) > 0) {
				//if first is a valid row, then we need to do a delete for all burritos
				dLog.info("Deleting Order's burritos");

				if(stmt != null) {stmt.close();}

				sql = "DELETE FROM Burrito WHERE orderID = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, id.toString());

				stmt.executeUpdate();
			}

			if(stmt != null) {stmt.close();}

			dLog.trace("Success: " + result);
			//delete actual order
			sql = "DELETE FROM Orders WHERE orderID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());

			if(stmt.executeUpdate() > 0) {
				result = true;
			}
		}
		catch (SQLException e1) {
			dLog.error("SQLException in deleteOrder: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e2) {
			dLog.error("Exception in deleteOrder: "+e2.getMessage());
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

	//TODO: come back and reduce number of reads on DB
	@Override
	public ArrayList<Order> getAllOrders() throws Exception {
		dLog.info("Entering method getAllOrders");
		ArrayList<Order> result = new ArrayList<Order>();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(connString);
			String sql = "select * from Orders";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery ();

			//ensure we were passed a valid object before attempting to write
			while(rs.next()) {
				result.add(getOrder(rs.getInt("orderID")));
			}
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in getAllOrders: "+e1.getMessage());
			e1.printStackTrace();
		}
		catch(Exception e2) {
			dLog.error("Exception in getAllOrders: "+e2.getMessage());
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
