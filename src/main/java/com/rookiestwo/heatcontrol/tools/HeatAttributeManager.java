package com.rookiestwo.heatcontrol.tools;

import com.rookiestwo.heatcontrol.HeatControl;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class HeatAttributeManager {

    private static double BaseEnvTemperature = 25.0;

    private static double BaseMaxTemperature = 40.0;

    private static double BaseMinTemperature = 16.0;

    private static double zeroYLayerTemperature = 19.7;

    private static double bedRockLayerTemperature = 45.0;

    private static double netherBaseTemperature = 52.3;

    //必须为负数,且绝对值必须小于Min
    private static double perBlockLightTemperature_Max = -0.3;

    //必须为负数
    private static double perBlockLightTemperature_Min = -0.86;




    //应用环境温度
    public static void applyEnvTemperature(PlayerEntity player) {
        player.getAttributeInstance(HeatControl.env_temperature).setBaseValue(calculateTemperatureValue(player));
    }

    //应用方块光照对于温度忍耐限度的影响
    public static void applyBlockLightEffect(PlayerEntity player) {
        int blockLightLevel = player.getWorld().getLightLevel(LightType.BLOCK, player.getBlockPos());//方块光照等级

        player.getAttributeInstance(HeatControl.max_temperature).setBaseValue(
                BaseMaxTemperature + blockLightLevel * perBlockLightTemperature_Max
        );
        player.getAttributeInstance(HeatControl.min_temperature).setBaseValue(
                BaseMinTemperature + blockLightLevel * perBlockLightTemperature_Min
        );
    }

    //计算温度值
    private static double calculateTemperatureValue(PlayerEntity player) {
        //获取各个参数
        RegistryKey<World> dim = player.getWorld().getRegistryKey();
        Biome biome = player.getWorld().getBiome(player.getBlockPos()).value();
        boolean rain = player.getWorld().isRaining();
        double attitude = player.getPos().getY();
        double temp = BaseEnvTemperature;
        int internalSkyLightLevel = player.getWorld().getLightLevel(LightType.SKY, player.getBlockPos()) - player.getWorld().getAmbientDarkness();
        int skyLightLevel = player.getWorld().getLightLevel(LightType.SKY, player.getBlockPos());

        //首先计算基础环境温度(海平面高度)
        if(biome.getTemperature()>0)temp = 19.7*biome.getTemperature()+8;
        if(biome.getTemperature()<=0)temp= 26.25*biome.getTemperature()-9.25;

        //主世界环境温度
        if(dim==World.OVERWORLD){
            if(temp>42)temp=42;

            //计算地表温度
            double surfaceEnvTemperature=temp;

            //叠加昼夜光照影响,根据生物群系的潮湿程度来影响昼夜温差,降雨量越大温度差越小
            surfaceEnvTemperature-=(10-internalSkyLightLevel)*
                    ((2.5/*此参数为一个影响潮湿与干燥差距大小的参数*/-biome.getDownfall())*5/*此参数为直接影响昼夜温度的参数*/)/12;
            if(surfaceEnvTemperature>42)surfaceEnvTemperature=42;

            //海拔
            surfaceEnvTemperature-=((attitude-63)/100)*9.5;

            //叠加天气
            if (rain) surfaceEnvTemperature -= biome.getDownfall() * biome.getTemperature() * 7;

            //计算地下温度
            double underEnvTemperature = temp;
            if (attitude > 63) underEnvTemperature -= ((attitude - 63) / 100) * 14.5;
            if (attitude > 0 && attitude <= 63)
                underEnvTemperature = zeroYLayerTemperature + ((underEnvTemperature - zeroYLayerTemperature) / 63) * attitude;
            if (attitude <= 0)
                underEnvTemperature = -((bedRockLayerTemperature - zeroYLayerTemperature) / 64) * attitude + zeroYLayerTemperature;

            //根据skyLightLevel等级来加权平均以上两个温度
            temp = ((15 - skyLightLevel) * underEnvTemperature + skyLightLevel * surfaceEnvTemperature) / 15;

            //叠加水中状态
            if (player.isTouchingWater()) temp *= 0.75;

            //叠加岩浆中状态
            if (player.isInLava()) temp += 312;
        }
        //地狱环境温度
        if (dim == World.NETHER) {
            temp = netherBaseTemperature;
            temp -= (attitude / 100) * 0.95;
        }

        return temp;
    }
}
