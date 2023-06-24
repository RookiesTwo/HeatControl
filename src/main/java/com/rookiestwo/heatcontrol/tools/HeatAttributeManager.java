package com.rookiestwo.heatcontrol.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
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
            "attribute.heatcontrol.player.min_temperature",16.0,-273.16,2048.0
    ).setTracked(true);

    //public static EntityAttributeModifier biomeBaseMod()
    public static double calculateHeatValue(PlayerEntity player){
        //获取各个参数
        RegistryKey<World> dim =player.getWorld().getRegistryKey();
        Biome biome = player.getWorld().getBiome(player.getBlockPos()).value();
        boolean rain = player.getWorld().isRaining();
        double attitude = player.getPos().getY();
        double temp = BaseEnvTemperature;
        //首先计算基础环境温度
        if(biome.getTemperature()>0)temp = 19.7*biome.getTemperature()+8;
        if(biome.getTemperature()<=0)temp= 26.25*biome.getTemperature()-9.25;
        if(dim==World.OVERWORLD)if(temp>42)temp=42;
        //再叠加海拔的偏移,末地不叠加
        if(dim==World.NETHER){
            temp-=(attitude/100)*0.95;
        }
        if(dim==World.OVERWORLD){
            if(attitude>=63) temp-=((attitude-63)/100)*9.5;
            if(attitude<63&&attitude>0) temp-=(-(attitude-63)/100)*3.2;
            if(attitude<=0) temp-=((attitude-63)/100)*35+24.066;
        }
        //最后叠加天气
        if(dim==World.OVERWORLD) {
            if(rain){
                temp+=biome.getDownfall()*biome.getTemperature()*5;
            }
        }
        return temp;
    }
}
