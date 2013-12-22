/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.model;

import org.bukkit.Location;

/**
 *
 * @author Daniel
 */
public class BlockProtected {
    
    public BlockProtected(Location location,String owner,String block) {
        this.location = location;
        this.Owner = owner;
        this.block = block;
    }
    
    private Location location;
    private String Owner;
    private String block;

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the Owner
     */
    public String getOwner() {
        return Owner;
    }

    /**
     * @param Owner the Owner to set
     */
    public void setOwner(String Owner) {
        this.Owner = Owner;
    }

    /**
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * @param block the block to set
     */
    public void setBlock(String block) {
        this.block = block;
    }
}
