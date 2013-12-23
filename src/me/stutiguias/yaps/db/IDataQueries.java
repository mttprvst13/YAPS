/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.stutiguias.yaps.db;

import java.util.HashMap;
import me.stutiguias.yaps.db.connection.WALConnection;
import java.util.List;
import me.stutiguias.yaps.model.Area;
import me.stutiguias.yaps.model.BlockProtected;
import org.bukkit.Location;

/**
 *
 * @author Daniel
 */
public interface IDataQueries {

        void initTables(); // Init Tables
        Integer getFound(); // Found On Last Search
        WALConnection getConnection();
        
        boolean InsertArea(Area area);
        List<Area> getAreas();
        boolean Delete(Area area);
        boolean UpdateArea(Area area);
        boolean SetExit(Area area);
        boolean InsertProtect(BlockProtected blockProtected);
        BlockProtected GetProtect(Location location);
        HashMap<Location,BlockProtected> GetAllProtect();
        boolean RemoveProtect(Location location);
}
