package com.rookiestwo.heatcontrol.items;

import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class HeatControlIcon extends Item{
    public HeatControlIcon(Settings settings){
        super(settings);
    }
    Text message=new LiteralText("heat_control_icon");
}
