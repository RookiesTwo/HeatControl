package com.rookiestwo.heatcontrol.tools;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class HeatAttributeManager {

    public static double BaseEnvTemperature = 25.0;

    private static double zeroStageTemperature = 19.7;

    private static double bedRockStageTemperature = 42.0;

    public static final EntityAttribute ENV_ATTRIBUTE=new ClampedEntityAttribute(
            "attribute.heatcontrol.player.env_temperature",37.0,-273.16,2048.0
    ).setTracked(true);
    public static final EntityAttribute MAX_TEMPERATURE=new ClampedEntityAttribute(
            "attribute.heatcontrol.player.max_temperature",40.0,-273.16,2048.0
    ).setTracked(true);
    public static final EntityAttribute MIN_TEMPERATURE=new ClampedEntityAttribute(
            "attribute.heatcontrol.player.min_temperature",16.0,-273.16,2048.0
    ).setTracked(true);

    //计算温度值
    public static double calculateTemperatureValue(PlayerEntity player){
        //获取各个参数
        RegistryKey<World> dim =player.getWorld().getRegistryKey();
        Biome biome = player.getWorld().getBiome(player.getBlockPos()).value();
        boolean rain = player.getWorld().isRaining();
        double attitude = player.getPos().getY();
        double temp = BaseEnvTemperature;
        int blockLightLevel=player.getWorld().getLightLevel(LightType.BLOCK,player.getBlockPos());
        int skyLightLevel=player.getWorld().getLightLevel(LightType.SKY,player.getBlockPos())-player.getWorld().getAmbientDarkness();
        //首先计算基础环境温度
        if(biome.getTemperature()>0)temp = 19.7*biome.getTemperature()+8;
        if(biome.getTemperature()<=0)temp= 26.25*biome.getTemperature()-9.25;
        if(dim==World.OVERWORLD)if(temp>42)temp=42;
        //叠加光照影响,根据生物群系的潮湿程度来影响昼夜温差,降雨量越大温度差越小
        if(dim==World.OVERWORLD){
            temp-=(10-skyLightLevel)*
                    ((2.5/*此参数为一个影响潮湿与干燥差距大小的参数*/-biome.getDownfall())*5/*此参数为直接影响昼夜温度的参数*/)/12;
        }
        //再叠加海拔的偏移,末地不叠加
        if(dim==World.NETHER){
            temp-=(attitude/100)*0.95;
        }
        if(dim==World.OVERWORLD){
            if(attitude>=63) temp-=((attitude-63)/100)*9.5;
            if(attitude<63&&attitude>0) temp=zeroStageTemperature+((temp-zeroStageTemperature)/63)*attitude;
            if(attitude<=0) temp=-((bedRockStageTemperature-zeroStageTemperature)/64)*attitude+zeroStageTemperature;
        }
        //叠加天气
        if(dim==World.OVERWORLD) {
            if(rain&&attitude>=63){
                temp-=biome.getDownfall()*biome.getTemperature()*5;
            }
        }
        return temp;
    }
}
