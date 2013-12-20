package me.stutiguias.yaps.db;

import me.stutiguias.yaps.db.connection.WALConnectionPool;
import me.stutiguias.yaps.db.connection.WALConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import me.stutiguias.yaps.init.Yaps;

public class MySQLDataQueries extends Queries {

        private WALConnectionPool pool;
        
	public MySQLDataQueries(Yaps plugin, String dbHost, String dbPort, String dbUser, String dbPass, String dbName) {
		super(plugin);
                try {
                        Yaps.logger.log(Level.INFO, "{0} Starting pool....", plugin.prefix);
                        pool = new WALConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://"+ dbHost +":"+ dbPort +"/"+ dbName, dbUser, dbPass);
                }catch(InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
                        Yaps.logger.log(Level.WARNING, "{0} Exception getting mySQL WALConnection", plugin.prefix);
			Yaps.logger.warning(e.getMessage());
                }
	}

        @Override
	public WALConnection getConnection() {
		try {
			return pool.getConnection();
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Exception getting mySQL WALConnection", plugin.prefix);
			Yaps.logger.warning(e.getMessage());
		}
		return null;
	}
        
	public boolean tableExists(String tableName) {
		boolean exists = false;
		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SHOW TABLES LIKE ?");
			st.setString(1, tableName);
			rs = st.executeQuery();
			while (rs.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to check if table exists: {1}", new Object[]{plugin.prefix, tableName});
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return exists;
	}

        @Override
	public void initTables() {
		if (!tableExists("YAPS_Players")) {
			Yaps.logger.log(Level.INFO, "{0} Creating table YAPS_Players", plugin.prefix);
			executeRawSQL("CREATE TABLE YAPS_Players (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), name VARCHAR(255), banned INT);");
		}
		if (!tableExists("YAPS_Areas")) {
			Yaps.logger.log(Level.INFO, "{0} Creating table YAPS_Areas", plugin.prefix);
			executeRawSQL("CREATE TABLE YAPS_Areas (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), name VARCHAR(255), first VARCHAR(255), second VARCHAR(255), owner VARCHAR(255), flags VARCHAR(255), exit VARCHAR(255) );");
		}
                if (!tableExists("YAPS_DbVersion")) {
                        Yaps.logger.log(Level.INFO, "{0} Creating table YAPS_DbVersion", plugin.prefix);
                        executeRawSQL("CREATE TABLE YAPS_DbVersion (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), dbversion INT);");
                        executeRawSQL("INSERT INTO YAPS_DbVersion (dbversion) VALUES (1)");
                }                
	}
    
}
