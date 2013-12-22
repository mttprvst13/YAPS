/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.configs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import me.stutiguias.yaps.init.Yaps;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Daniel
 */
public class Config {
    
    private ConfigAccessor config;
        
    public boolean AllowMoveInside;
    
    public boolean UpdaterNotify;
    
    public String DataBaseType;
    public String Host;
    public String Username;
    public String Password;
    public String Port;
    public String Database;
    public List<String> Protected;
    public boolean AllowProtectedBlockInsideArea;
    
    public Config(Yaps plugin) {
 
        try {
            config = new ConfigAccessor(plugin,"config.yml");
            config.setupConfig();
            FileConfiguration fc = config.getConfig();   
                        
            if(!fc.isSet("configversion") || fc.getInt("configversion") != 1){ 
                config.MakeOld();
                config.setupConfig();
                fc = config.getConfig();  
            }
            
            DataBaseType = fc.getString("DataBase.Type");
            Host  = fc.getString("MySQL.Host");
            Username = fc.getString("MySQL.Username");
            Password = fc.getString("MySQL.Password");
            Port = fc.getString("MySQL.Port");
            Database = fc.getString("MySQL.Database");
            
            UpdaterNotify = fc.getBoolean("UpdaterNotify");
            
            AllowMoveInside = fc.getBoolean("AllowMoveInside");
            
            Protected = new ArrayList<>();
            for(String block:fc.getStringList("Protected")) {
                Protected.add(block);
            }
            
            AllowProtectedBlockInsideArea = fc.getBoolean("AllowProtectedBlockInsideArea");
            
        }catch(IOException ex){
            ex.printStackTrace();
            plugin.getLogger().log(Level.WARNING, "Erro Loading Config");
        }
    }
    
    public void reloadConfig() {
        config.reloadConfig();
    }
    
}
