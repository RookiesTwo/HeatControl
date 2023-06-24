package com.rookiestwo.heatcontrol.tools;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;

public class HeatAttributeManager {
    public static final EntityAttribute HEAT_ATTRIBUTE=new ClampedEntityAttribute(
            "attribute.heatcontrol.player.env_temperature",37.0,-273.16,2048.0
    ).setTracked(true);

    public static void addHeatAttribute(PlayerEntity player){

    }

    private static double calculateHeatValue(PlayerEntity player){
        return 37.0;
    }
}
