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
public class Save {

    public Save() {}
    public Save(String function,BlockProtected blockProtected) {
        this.function = function;
        this.blockProtected = blockProtected;
    }
    
    private String function;
    private BlockProtected blockProtected;

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * @return the blockProtected
     */
    public BlockProtected getBlockProtected() {
        return blockProtected;
    }

    /**
     * @param blockProtected the blockProtected to set
     */
    public void setBlockProtected(BlockProtected blockProtected) {
        this.blockProtected = blockProtected;
    }
}
