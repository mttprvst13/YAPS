/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.stutiguias.yaps.model;

import me.stutiguias.yaps.init.Yaps;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Daniel
 */
public class Area {
    
    public Area(){
    }
    
    public Area(Location FirstSpot,Location SecondSpot,String name,String owner,String flag){
        this.FirstSpot = FirstSpot;
        this.SecondSpot = SecondSpot;
        this.name = name;
        this.owner = owner;
        this.flags = flag; 
    }

    private Location FirstSpot;
    private Location SecondSpot;
    private String name;
    private String owner;
    private String flags;
    private Location Exit;
    /**
     * @return the FirstSpot
     */
    public Location getFirstSpot() {
        return FirstSpot;
    }

    /**
     * @param FirstSpot the FirstSpot to set
     */
    public void setFirstSpot(Location FirstSpot) {
        this.FirstSpot = FirstSpot;
    }

    /**
     * @return the SecondSpot
     */
    public Location getSecondSpot() {
        return SecondSpot;
    }

    /**
     * @param SecondSpot the SecondSpot to set
     */
    public void setSecondSpot(Location SecondSpot) {
        this.SecondSpot = SecondSpot;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the tag
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the flags
     */
    public String getFlags() {
        return flags;
    }

    /**
     * @param flags the flags to set
     */
    public void setFlags(String flags) {
        this.flags = flags;
    }

    /**
     * @return the Exit
     */
    public Location getExit() {
        return Exit;
    }

    /**
     * @param Exit the Exit to set
     */
    public void setExit(Location Exit) {
        this.Exit = Exit;
    }
}
