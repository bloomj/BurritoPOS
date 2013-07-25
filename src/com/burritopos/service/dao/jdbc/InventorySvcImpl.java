/**
 * 
 */
package com.burritopos.service.dao.jdbc;

import java.io.*;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Inventory;
import com.burritopos.service.dao.IInventorySvc;

import java.sql.*;

/**
 * @author james.bloom
 *
 */
public class InventorySvcImpl implements IInventorySvc {
	private static Logger dLog = Logger.getLogger(InventorySvcImpl.class);
    // TODO: Come back and move connection string to encrypted config file or some other method
	private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Inventory getInventory(Integer id) throws IOException, ClassNotFoundException, Exception {
		dLog.info("Entering method getInventory | Inventory ID: "+id);
		Inventory i = null;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "SELECT * FROM Inventory WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());
			ResultSet rs = stmt.executeQuery ();

			//ensure we were passed a valid object before attempting to write
			if(rs.next()) {
				i = new Inventory();
				i.setId(id);
				i.setBeefQty(rs.getInt("beefQty"));
				i.setBlackBeansQty(rs.getInt("blackBeansQty"));
				i.setBrownRiceQty(rs.getInt("brownRiceQty"));
				i.setChickenQty(rs.getInt("chickenQty"));
				i.setChiliTortillaQty(rs.getInt("chiliTortillaQty"));
				i.setCucumberQty(rs.getInt("cucumberQty"));
				i.setFlourTortillaQty(rs.getInt("flourTortillaQty"));
				i.setGuacamoleQty(rs.getInt("guacamoleQty"));
				i.setHerbGarlicTortillaQty(rs.getInt("herbGarlicTortillaQty"));
				i.setHummusQty(rs.getInt("hummusQty"));
				i.setJalapenoCheddarTortillaQty(rs.getInt("jalapenoCheddarTortillaQty"));
				i.setJalapenosQty(rs.getInt("jalapenosQty"));
				i.setLettuceQty(rs.getInt("lettuceQty"));
				i.setOnionQty(rs.getInt("onionQty"));
				i.setPintoBeansQty(rs.getInt("pintoBeansQty"));
				i.setSalsaPicoQty(rs.getInt("salsaPicoQty"));
				i.setSalsaSpecialQty(rs.getInt("salsaSpecialQty"));
				i.setSalsaVerdeQty(rs.getInt("salsaVerdeQty"));
				i.setTomatoBasilTortillaQty(rs.getInt("tomatoBasilTortillaQty"));
				i.setTomatoesQty(rs.getInt("tomatoesQty"));
				i.setWheatTortillaQty(rs.getInt("wheatTortillaQty"));
				i.setWhiteRiceQty(rs.getInt("whiteRiceQty"));
			}
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in getInventory", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in getInventory", e2);
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

		return i;
	}

	@Override
	public boolean storeInventory(Inventory i) throws IOException, Exception {
		dLog.info("Entering method storeInventory | Inventory ID: "+i.getId());
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			//ensure we were passed a valid object before attempting to write
			if(i.validate()) {
				conn = DriverManager.getConnection(connString);
				String sql = "SELECT COUNT(1) FROM Inventory WHERE id = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, i.getId().toString());
				ResultSet rs = stmt.executeQuery ();
				rs.first();

				if(rs.getInt(1) > 0) {
					//if first is a valid row, then we need to do an update
					dLog.info("Updating inventory in database");

					if(stmt != null) {stmt.close();}

					sql = "UPDATE Inventory SET beefQty=?,blackbeansQty=?,brownriceQty=?,chickenQty=?,chiliTortillaQty=?,cucumberQty=?,";
					sql += "flourTortillaQty=?,guacamoleQty=?,herbGarlicTortillaQty=?,hummusQty=?,jalapenoCheddarTortillaQty=?,jalapenosQty=?,";
					sql += "lettuceQty=?,onionQty=?,pintoBeansQty=?,salsaPicoQty=?,salsaSpecialQty=?,salsaVerdeQty=?,tomatoBasilTortillaQty=?,";
					sql += "tomatoesQty=?,wheatTortillaQty=?,whiteRiceQty=? WHERE id = ?";
				}
				else {
					//if first is null, then we need to do an insert
					dLog.info("Inserting inventory into database");

					if(stmt != null) {stmt.close();}

					sql = "INSERT INTO Inventory (beefQty,blackbeansQty,brownriceQty,chickenQty,chiliTortillaQty,cucumberQty,";
					sql += "flourTortillaQty,guacamoleQty,herbGarlicTortillaQty,hummusQty,jalapenoCheddarTortillaQty,jalapenosQty,";
					sql += "lettuceQty,onionQty,pintoBeansQty,salsaPicoQty,salsaSpecialQty,salsaVerdeQty,tomatoBasilTortillaQty,";
					sql += "tomatoesQty,wheatTortillaQty,whiteRiceQty,id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				}

				dLog.info("SQL Statement: "+sql);
				stmt = conn.prepareStatement(sql);

				stmt.setInt(1, i.getBeefQty());
				stmt.setInt(2, i.getBlackBeansQty());
				stmt.setInt(3, i.getBrownRiceQty());
				stmt.setInt(4, i.getChickenQty());
				stmt.setInt(5, i.getChiliTortillaQty());
				stmt.setInt(6, i.getCucumberQty());
				stmt.setInt(7, i.getFlourTortillaQty());
				stmt.setInt(8, i.getGuacamoleQty());
				stmt.setInt(9, i.getHerbGarlicTortillaQty());
				stmt.setInt(10, i.getHummusQty());
				stmt.setInt(11, i.getJalapenoCheddarTortillaQty());
				stmt.setInt(12, i.getJalapenosQty());
				stmt.setInt(13, i.getLettuceQty());
				stmt.setInt(14, i.getOnionQty());
				stmt.setInt(15, i.getPintoBeansQty());
				stmt.setInt(16, i.getSalsaPicoQty());
				stmt.setInt(17, i.getSalsaSpecialQty());
				stmt.setInt(18, i.getSalsaVerdeQty());
				stmt.setInt(19, i.getTomatoBasilTortillaQty());
				stmt.setInt(20, i.getTomatoesQty());
				stmt.setInt(21, i.getWheatTortillaQty());
				stmt.setInt(22, i.getWhiteRiceQty());
				stmt.setString(23, i.getId().toString());

				if(stmt.executeUpdate() > 0) {
					result = true;
				}
				dLog.trace("Success: " + result);
			}
		} 
		catch (SQLException e1) {
			dLog.error("SQLException in storeInventory", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in storeInventory", e2);
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
	public boolean deleteInventory(Integer id) throws Exception {
		dLog.info("Entering method deleteInventory | Inventory ID:"+id);
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			String sql = "DELETE FROM Inventory WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id.toString());

			if(stmt.executeUpdate() > 0) {
				result = true;
			}
		}
		catch (SQLException e1) {
			dLog.error("SQLException in deleteInventory", e1);
		}
		catch(Exception e2) {
			dLog.error("Exception in deleteInventory", e2);
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
