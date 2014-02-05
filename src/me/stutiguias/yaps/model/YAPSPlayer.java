/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.model;

/**
 *
 * @author Daniel
 */
public class YAPSPlayer {
    
    public YAPSPlayer() {
    }
    
    public YAPSPlayer(String name,Boolean ban,long expTime){ 
        
        this.name = name;
        this.ban = ban;
        this.protectPlace = true;
    }
    
    private String name;
    private Boolean ban;
    private Boolean protectPlace;
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
     * @return the ban
     */
    public Boolean getBan() {
        return ban;
    }

    /**
     * @param ban the ban to set
     */
    public void setBan(Boolean ban) {
        this.ban = ban;
    }

    /**
     * @return the protectPlace
     */
    public Boolean isProtectedPlace() {
        return protectPlace;
    }

    /**
     * @param protectPlace the protectPlace to set
     */
    public void setProtectPlace(Boolean protectPlace) {
        this.protectPlace = protectPlace;
    }

}
