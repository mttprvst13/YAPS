/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.task;

import me.stutiguias.yaps.init.Util;
import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.model.BlockProtected;
import me.stutiguias.yaps.model.Save;

/**
 *
 * @author Daniel
 */
public class SaveTask extends Util implements Runnable {

    public SaveTask(Yaps plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        Save save =  Yaps.SaveList.poll();
        if(save == null) return;
        if(save.getFunction().contains("add")) Yaps.db.InsertProtect(save.getBlockProtected());
        if(save.getFunction().contains("remove")) Yaps.db.RemoveProtect(save.getBlockProtected().getLocation());
    }
    
}
