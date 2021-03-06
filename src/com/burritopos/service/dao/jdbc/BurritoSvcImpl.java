/**
 * 
 */
package com.burritopos.service.dao.jdbc;

import org.apache.log4j.*;

import com.burritopos.domain.Burrito;
import com.burritopos.service.dao.IBurritoSvc;

import java.sql.*;

/**
 * @author james.bloom
 *
 */
public class BurritoSvcImpl implements IBurritoSvc {
	private static Logger dLog = Logger.getLogger(BurritoSvcImpl.class);
	// TODO: Come back and move connection string to encrypted config file or some other method
	private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Burrito getBurrito(Integer id) throws SQLException, Exception {
		dLog.info("Entering method getBurrito | ID: " + id);
		Burrito b = new Burrito();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "SELECT * FROM Burrito WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());
			ResultSet rs = stmt.executeQuery ();

			//ensure we were passed a valid object before attempting to write
			if(rs.next()) {
				b.setId(id);
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
			}
		}
		catch(SQLException e1) {
			dLog.error("SQLException in getBurrito", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in getBurrito", e2);
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

		return b;
	}

	@Override
	public boolean storeBurrito(Burrito b) throws SQLException, Exception {
		dLog.info("Entering method storeBurrito | Burrito ID: "+b.getId());
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(b.validate()) {
				conn = DriverManager.getConnection(connString);
				String sql = "SELECT COUNT(1) FROM Burrito WHERE id = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, b.getId().toString());
				ResultSet rs = stmt.executeQuery ();
				rs.first();

				if(rs.getInt(1) > 0) {
					//if first is a valid row, then we need to do an update
					dLog.info("Updating burrito in database");

					if(stmt != null) {stmt.close();}

					sql = "UPDATE Burrito SET beef=?,blackbeans=?,brownrice=?,chicken=?,chiliTortilla=?,cucumber=?,";
					sql += "flourTortilla=?,guacamole=?,herbGarlicTortilla=?,hummus=?,jalapenoCheddarTortilla=?,jalapenos=?,";
					sql += "lettuce=?,onion=?,pintoBeans=?,price=?,salsaPico=?,salsaSpecial=?,salsaVerde=?,tomatoBasilTortilla=?,";
					sql += "tomatoes=?,wheatTortilla=?,whiteRice=? WHERE id = ?";
				}
				else {
					//if first is null, then we need to do an insert
					dLog.info("Inserting burrito into database");

					if(stmt != null) {stmt.close();}

					sql = "INSERT INTO Burrito (beef,blackbeans,brownrice,chicken,chiliTortilla,cucumber,";
					sql += "flourTortilla,guacamole,herbGarlicTortilla,hummus,jalapenoCheddarTortilla,jalapenos,";
					sql += "lettuce,onion,pintoBeans,price,salsaPico,salsaSpecial,salsaVerde,tomatoBasilTortilla,";
					sql += "tomatoes,wheatTortilla,whiteRice,id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
				stmt.setString(24, b.getId().toString());

				if(stmt.executeUpdate() > 0) {
					result = true;
				}
			}
		}
		catch(SQLException e1) {
			dLog.error("SQLException in storeBurrito", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in storeBurrito", e2);
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
	public boolean deleteBurrito(Integer id) throws SQLException, Exception {
		dLog.info("Entering method deleteBurrito | Burrito ID:"+id);
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "DELETE FROM Burrito WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());

			if(stmt.executeUpdate() > 0) {
				result = true;
			}
		}
		catch(SQLException e1) {
			dLog.error("SQLException in deleteBurrito", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in deleteBurrito", e2);
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
