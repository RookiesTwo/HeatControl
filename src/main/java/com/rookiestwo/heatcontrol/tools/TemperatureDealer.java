package com.rookiestwo.heatcontrol.tools;

import com.rookiestwo.heatcontrol.mixin.TemperatureTickServer;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.minecraft.world.biome.Biome;

import java.awt.*;

public abstract class TemperatureDealer {
    public final float getTemperature(Dimension dim, Biome biome, int weather,float altitude){
        return (float)37.0;
    }
}
