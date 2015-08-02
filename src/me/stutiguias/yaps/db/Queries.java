/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.stutiguias.yaps.db;

import me.stutiguias.yaps.db.connection.WALConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.init.Util;
import me.stutiguias.yaps.model.Area;
import me.stutiguias.yaps.model.BlockProtected;
import org.bukkit.Location;

/**
 *
 * @author Daniel
 */
public class Queries extends Util implements IDataQueries {

	protected WALConnection connection;
	protected Integer found;

	public Queries(Yaps plugin) {
		super(plugin);
	}

	@Override
	public void initTables() {
		throw new UnsupportedOperationException("Implement On Children.");
	}

	@Override
	public Integer getFound() {
		return found;
	}

	@Override
	public WALConnection getConnection() {
		throw new UnsupportedOperationException("Implement On Children.");
	}

	public void closeResources(WALConnection conn, Statement st, ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (null != st) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		if (null != conn)
			conn.close();
	}

	public int tableVersion() {
		int version = 0;
		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT dbversion FROM YAPS_DbVersion");
			rs = st.executeQuery();
			while (rs.next()) {
				version = rs.getInt("dbversion");
			}
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to check if table version ", plugin.prefix);
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return version;
	}

	public void executeRawSQL(String sql) {
		WALConnection conn = getConnection();
		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Exception executing raw SQL {1}", new Object[] { plugin.prefix, sql });
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
	}

	@Override
	public boolean InsertArea(Area area) {
		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			String query = "INSERT INTO YAPS_Areas (`name`, `first`, `second`, `owner`, `flags`, `exit`) VALUES ('{1}','{2}','{3}','{4}','{5}','{6}')"
					.replace("{1}", area.getName()).replace("{2}", ToString(area.getFirstSpot()))
					.replace("{3}", ToString(area.getSecondSpot())).replace("{4}", area.getOwner())
					.replace("{5}", area.getFlags()).replace("{6}", ToString(area.getExit()));
			// Yaps.logger.info(query);
			st = conn.prepareStatement(query);
			// st.setString(1, area.getName());
			// st.setString(2, ToString(area.getFirstSpot()));
			// st.setString(3, ToString(area.getSecondSpot()));
			// st.setString(4, area.getOwner());
			// st.setString(5, area.getFlags());
			// st.setString(6, ToString(area.getExit()));
			st.executeUpdate();
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to insert area", plugin.prefix);
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return true;
	}

	@Override
	public List<Area> getAreas() {
		List<Area> areas = new ArrayList<>();

		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM YAPS_Areas");
			rs = st.executeQuery();
			while (rs.next()) {
				Area area = new Area();
				area.setName(rs.getString("name"));
				area.setFirstSpot(toLocation(rs.getString("first")));
				area.setSecondSpot(toLocation(rs.getString("second")));
				area.setFlags(rs.getString("flags"));
				area.setOwner(rs.getString("owner"));
				area.setExit(toLocation(rs.getString("exit")));
				areas.add(area);
			}
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to get areas", new Object[] { plugin.prefix });
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return areas;
	}

	@Override
	public boolean Delete(Area area) {
		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int result = 0;

		try {
			st = conn.prepareStatement("DELETE FROM YAPS_Areas WHERE name = ?");
			st.setString(1, area.getName());
			result = st.executeUpdate();
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to delete", new Object[] { plugin.prefix });
		} finally {
			closeResources(conn, st, rs);
		}
		return result != 0;
	}

	@Override
	public boolean UpdateArea(Area area) {
		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int result = 0;
		try {
			st = conn.prepareStatement(
					"UPDATE YAPS_Areas SET first = ?, second = ?, owner = ?, flags = ? WHERE name = ?");
			st.setString(1, ToString(area.getFirstSpot()));
			st.setString(2, ToString(area.getSecondSpot()));
			st.setString(3, area.getOwner());
			st.setString(4, area.getFlags());
			st.setString(5, area.getName());
			result = st.executeUpdate();
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to update DB", plugin.prefix);
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return result != 0;
	}

	@Override
	public boolean SetExit(Area area) {
		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("UPDATE YAPS_Areas SET exit = ? WHERE name = ?");
			st.setString(1, ToString(area.getExit()));
			st.setString(2, area.getName());
			st.executeUpdate();
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to update DB", plugin.prefix);
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return true;
	}

	@Override
	public boolean InsertProtect(BlockProtected blockProtected) {
		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("INSERT INTO YAPS_Protected (location, owner, block, time) VALUES (?,?,?,?)");
			st.setString(1, ToString(blockProtected.getLocation()));
			st.setString(2, blockProtected.getOwner());
			st.setString(3, blockProtected.getBlock());
			st.setTimestamp(4, new Timestamp(new Date().getTime()));
			st.executeUpdate();
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to insert block", plugin.prefix);
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return true;
	}

	@Override
	public BlockProtected GetProtect(Location location) {
		BlockProtected blockProtected = null;
		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT location,owner,block FROM YAPS_Protected where location = ?");
			st.setString(1, ToString(location));
			rs = st.executeQuery();
			while (rs.next()) {
				blockProtected = new BlockProtected();
				blockProtected.setLocation(toLocation(rs.getString("location")));
				blockProtected.setOwner(rs.getString("owner"));
				blockProtected.setBlock(rs.getString("block"));
			}
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to get block", new Object[] { plugin.prefix });
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return blockProtected;
	}

	@Override
	public HashMap<Location, BlockProtected> GetAllProtect() {
		HashMap<Location, BlockProtected> protectList = new HashMap<>();

		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT location,owner,block,time FROM YAPS_Protected");
			rs = st.executeQuery();
			while (rs.next()) {
				BlockProtected blockProtected = new BlockProtected();
				blockProtected.setLocation(toLocation(rs.getString("location")));
				blockProtected.setOwner(rs.getString("owner"));
				blockProtected.setBlock(rs.getString("block"));
				blockProtected.setTime(rs.getTimestamp("time"));
				protectList.put(blockProtected.getLocation(), blockProtected);
			}
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to get areas", new Object[] { plugin.prefix });
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return protectList;
	}

	@Override
	public boolean RemoveProtect(Location location) {
		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int result = 0;
		try {
			st = conn.prepareStatement("DELETE FROM YAPS_Protected where location = ?");
			st.setString(1, ToString(location));
			result = st.executeUpdate();
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to update DB", plugin.prefix);
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return result != 0;
	}

	@Override
	public int RemoveOld(Timestamp time) {

		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		int result = 0;
		try {
			st = conn.prepareStatement("DELETE FROM YAPS_Protected where time < ?");
			st.setTimestamp(1, time);
			result = st.executeUpdate();
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to update DB", plugin.prefix);
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return result;
	}

	@Override
	public List<BlockProtected> GetAllProtectList() {
		List<BlockProtected> protectList = new ArrayList<>();

		WALConnection conn = getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT location,owner,block,time FROM YAPS_Protected");
			rs = st.executeQuery();
			while (rs.next()) {
				BlockProtected blockProtected = new BlockProtected();
				blockProtected.setLocation(toLocation(rs.getString("location")));
				blockProtected.setOwner(rs.getString("owner"));
				blockProtected.setBlock(rs.getString("block"));
				blockProtected.setTime(rs.getTimestamp("time"));
				protectList.add(blockProtected);
			}
		} catch (SQLException e) {
			Yaps.logger.log(Level.WARNING, "{0} Unable to get areas", new Object[] { plugin.prefix });
			Yaps.logger.warning(e.getMessage());
		} finally {
			closeResources(conn, st, rs);
		}
		return protectList;
	}

}
