/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.task;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import me.stutiguias.yaps.init.Util;
import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.model.BlockProtected;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 *
 * @author Daniel
 */
public class PurgeOldRecordsTask extends Util implements Runnable {

    public PurgeOldRecordsTask(Yaps plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();
        int time;
        Date date = new Date();
        if(Yaps.config.PurgeOldRecords.contains("d")) {
            String d = Yaps.config.PurgeOldRecords.replace("d","");
            time = Integer.parseInt(d);
            calendar.add(Calendar.DAY_OF_MONTH, -time);
            date = calendar.getTime();
        }else if(Yaps.config.PurgeOldRecords.contains("m")) {
            String m = Yaps.config.PurgeOldRecords.replace("m","");
            time = Integer.parseInt(m);   
            calendar.add(Calendar.MINUTE, -time);
            date = calendar.getTime();
        }
         
        Timestamp timestamp = new Timestamp(date.getTime());
        
        Yaps.logger.log(Level.INFO,"{0} Remove Old Logs From Database",new Object[] { plugin.prefix });
        Yaps.logger.log(Level.INFO,"{0} {1} Removed",new Object[] { plugin.prefix , Yaps.db.RemoveOld(timestamp) });
    }
    
}
