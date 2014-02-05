/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.stutiguias.yaps.db;

import me.stutiguias.yaps.db.connection.WALDriver;
import me.stutiguias.yaps.db.connection.WALConnection;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import me.stutiguias.yaps.init.Yaps;

/**
 *
 * @author Daniel
 */
public class SqliteDataQueries extends Queries {

    public SqliteDataQueries(Yaps plugin) {
        super(plugin);
        initTables();
    }

    @Override
    public WALConnection getConnection() {
            try {
                    Driver driver = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
                    WALDriver jDriver = new WALDriver(driver);
                    DriverManager.registerDriver(jDriver);
                    connection = new WALConnection(DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + File.separator + "data.db"));
                    return connection;
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
                    Yaps.logger.log(Level.SEVERE, "{0} Exception getting SQLite WALConnection", plugin.prefix);
                    Yaps.logger.warning(e.getMessage());
            }
            return null;
    }
	
    private boolean tableExists(String tableName) {
        boolean exists = false;
        WALConnection conn = getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
                st = conn.prepareStatement("SELECT name FROM sqlite_master WHERE type = 'table' and name LIKE ?");
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

    /**
     *
     */
    @Override
    public final void initTables() {
                File dbFile = new File(plugin.getDataFolder() + File.separator +  "data.db");
                if(!dbFile.exists()) {
                    try {
                        dbFile.createNewFile();
                    } catch (IOException ex) {
                        Yaps.logger.log(Level.WARNING,"{0} Can`t create file db", plugin.prefix);
                    }
                }
                if (!tableExists("YAPS_Players")) {
			Yaps.logger.log(Level.INFO, "{0} Creating table YAPS_Players", plugin.prefix);
			executeRawSQL("CREATE TABLE YAPS_Players (id INTEGER PRIMARY KEY, name VARCHAR(255), banned INTEGER);");
		}
		if (!tableExists("YAPS_Areas")) {
			Yaps.logger.log(Level.INFO, "{0} Creating table YAPS_Areas", plugin.prefix);
			executeRawSQL("CREATE TABLE YAPS_Areas (id INTEGER PRIMARY KEY, name VARCHAR(255), first VARCHAR(255), second VARCHAR(255), owner VARCHAR(255), flags VARCHAR(255), exit VARCHAR(255));");
		}
                if (!tableExists("YAPS_Protected")) {
			Yaps.logger.log(Level.INFO, "{0} Creating table YAPS_Protected", plugin.prefix);
			executeRawSQL("CREATE TABLE YAPS_Protected (id INTEGER PRIMARY KEY, location VARCHAR(255), owner VARCHAR(255), block VARCHAR(255) );");
		}
                if (!tableExists("YAPS_DbVersion")) {
                        Yaps.logger.log(Level.INFO, "{0} Creating table YAPS_DbVersion", plugin.prefix);
                        executeRawSQL("CREATE TABLE YAPS_DbVersion (id INTEGER PRIMARY KEY, dbversion INTEGER);");
                        executeRawSQL("INSERT INTO YAPS_DbVersion (dbversion) VALUES (1)");
                }
                if (tableVersion() == 1) {
                        Yaps.logger.log(Level.INFO, "{0} Update DB version to 2", plugin.prefix);
                        executeRawSQL("ALTER TABLE YAPS_Protected ADD COLUMN time TIMESTAMP;");
                        executeRawSQL("UPDATE YAPS_DbVersion SET dbversion = 2 where id = 1");
                }
    }
    
}
