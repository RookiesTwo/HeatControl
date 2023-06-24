package com.rookiestwo.heatcontrol.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class HeatAttributeManager {

    public static double BaseEnvTemperature = 25.0;

    public static final EntityAttribute ENV_ATTRIBUTE=new ClampedEntityAttribute(
            "attribute.heatcontrol.player.env_temperature",37.0,-273.16,2048.0
    ).setTracked(true);
    public static final EntityAttribute MAX_TEMPERATURE=new ClampedEntityAttribute(
            "attribute.heatcontrol.player.max_temperature",40.0,-273.16,2048.0
    ).setTracked(true);
    public static final EntityAttribute MIN_TEMPERATURE=new ClampedEntityAttribute(
            "attribute.heatcontrol.player.min_temperature",20.0,-273.16,2048.0
    ).setTracked(true);

    public static void addHeatAttribute(PlayerEntity player){

    }
    public static double calculateHeatValue(PlayerEntity player){
        //获取各个参数
        RegistryKey<World> dim =player.getWorld().getRegistryKey();
        Biome biome = player.getWorld().getBiome(player.getBlockPos()).value();
        boolean rain = player.getWorld().isRaining();
        double attitude = player.getPos().getY();
        //首先计算基础环境温度
        //地狱单独计算
        if(dim==World.NETHER){
            return 47.8;
        }

        return 37.0;
    }
}
