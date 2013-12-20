/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.stutiguias.yaps.db;

import me.stutiguias.yaps.db.connection.WALConnection;
import java.util.List;
import me.stutiguias.yaps.model.Area;

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
}
