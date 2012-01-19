/**
 * 
 */
package com.burritopos.service;

//import java.io.*;
//import java.math.BigDecimal;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.domain.Burrito;

import java.util.Date;
import java.sql.*;

/**
 * @author james.bloom
 *
 */
public class BurritoSvcJDBCImpl implements IBurritoSvc {

        private static Logger dLog = Logger.getLogger(BurritoSvcJDBCImpl.class);
        private static String connString = "jdbc:mysql://localhost/neatoburrito?user=root&password=admin";

	@Override
	public Burrito getBurrito(Integer id) throws SQLException, Exception {
		dLog.info(new Date() + " | Entering method getBurrito | ID: " + id);
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
                	dLog.error(new Date() + " | SQLException in getBurrito: "+e1.getMessage());
                }
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in getBurrito: "+e2.getMessage());
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
		dLog.info(new Date() + " | Entering method storeBurrito | Burrito ID: "+b.getId());
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
                                dLog.info(new Date() + " | Updating burrito in database");
                                System.out.println("Updating burrito in database");

                                if(stmt != null) {stmt.close();}

                                sql = "UPDATE Burrito SET beef=?,blackbeans=?,brownrice=?,chicken=?,chiliTortilla=?,cucumber=?,";
                                sql += "flourTortilla=?,guacamole=?,herbGarlicTortilla=?,hummus=?,jalapenoCheddarTortilla=?,jalapenos=?,";
                                sql += "lettuce=?,onion=?,pintoBeans=?,price=?,salsaPico=?,salsaSpecial=?,salsaVerde=?,tomatoBasilTortilla=?,";
                                sql += "tomatoes=?,wheatTortilla=?,whiteRice=? WHERE id = ?";
                            }
                            else {
                                //if first is null, then we need to do an insert
                                dLog.info(new Date() + " | Inserting burrito into database");
                                System.out.println("Inserting burrito into database");

                                if(stmt != null) {stmt.close();}

                                sql = "INSERT INTO Burrito (beef,blackbeans,brownrice,chicken,chiliTortilla,cucumber,";
                                sql += "flourTortilla,guacamole,herbGarlicTortilla,hummus,jalapenoCheddarTortilla,jalapenos,";
                                sql += "lettuce,onion,pintoBeans,price,salsaPico,salsaSpecial,salsaVerde,tomatoBasilTortilla,";
                                sql += "tomatoes,wheatTortilla,whiteRice,id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            }

                            dLog.info(new Date() + " | SQL Statement: "+sql);
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

                            if(stmt.executeUpdate() > 0)
                                result = true;
                            else
                                result = false;
			}
		}
                catch(SQLException e1) {
 			dLog.error(new Date() + " | SQLException in storeBurrito: "+e1.getMessage());
                        System.out.println("SQLException in storeBurrito: "+e1.getMessage());
			result = false;
                }
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in storeBurrito: "+e2.getMessage());
                        System.out.println("Exception in storeBurrito: "+e2.getMessage());
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
	public boolean deleteBurrito(Integer id) throws SQLException, Exception {
		dLog.info(new Date() + " | Entering method deleteBurrito | Burrito ID:"+id);
		boolean result = false;
                Connection conn = null;
                PreparedStatement stmt = null;
		
		try {
		    conn = DriverManager.getConnection(connString);
                    String sql = "DELETE FROM Burrito WHERE id = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, id.toString());

                    if(stmt.executeUpdate() > 0)
                        result = true;
                    else
                        result = false;
		}
                catch(SQLException e1) {
  			dLog.error(new Date() + " | SQLException in deleteBurrito: "+e1.getMessage());
			result = false;
                }
		catch(Exception e2) {
			dLog.error(new Date() + " | Exception in deleteBurrito: "+e2.getMessage());
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
