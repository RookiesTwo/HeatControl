package com.rookiestwo.heatcontrol;

import com.rookiestwo.heatcontrol.items.*;
import com.rookiestwo.heatcontrol.mob_effect.coolStatusEffect;
import com.rookiestwo.heatcontrol.mob_effect.heatStrokeStatusEffect;
import com.rookiestwo.heatcontrol.mob_effect.hypothermiaStatusEffect;
import com.rookiestwo.heatcontrol.tools.HeatAttributeManager;
import com.rookiestwo.heatcontrol.tools.HeatControlConfig;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HCRegistry {
    //Items
    public static final BlazingNecklace blazing_necklace = new BlazingNecklace(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final BlazingVine blazing_vine = new BlazingVine(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP));
    public static final FrostSennit frost_sennit = new FrostSennit(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP));
    public static final FrostNecklace frost_necklace = new FrostNecklace(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final HeatControlIcon heat_control_icon = new HeatControlIcon(new FabricItemSettings());
    public static final Thermometer thermometer = new Thermometer(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final IllusionVeinCrystal illusion_vein_crystal = new IllusionVeinCrystal(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final WoolenHat woolen_hat = new WoolenHat(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final WoolenSocks woolen_socks = new WoolenSocks(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final WoolenTrousers woolen_trousers = new WoolenTrousers(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final WoolenUnderwear woolen_underwear = new WoolenUnderwear(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));

    //EntityAttribute
    public static final EntityAttribute ENV_TEMPERATURE = new ClampedEntityAttribute(
            "attribute.heatcontrol.player.env_temperature", HeatControlConfig.baseEnvironmentTemperature, -273.16, 2048.0
    ).setTracked(true);
    public static final EntityAttribute MAX_TEMPERATURE = new ClampedEntityAttribute(
            "attribute.heatcontrol.player.max_temperature", HeatControlConfig.maxPlayerTemperature, -273.16, 2048.0
    ).setTracked(true);
    public static final EntityAttribute MIN_TEMPERATURE = new ClampedEntityAttribute(
            "attribute.heatcontrol.player.min_temperature", HeatControlConfig.minPlayerTemperature, -273.16, 2048.0
    ).setTracked(true);


    //Effects
    public static final coolStatusEffect effect_cool = new coolStatusEffect();
    public static final hypothermiaStatusEffect effect_hypothermia = new hypothermiaStatusEffect();
    public static final heatStrokeStatusEffect effect_heat_stroke = new heatStrokeStatusEffect();


    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "heat_control_icon"), heat_control_icon);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "thermometer"), thermometer);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "blazing_vine"), blazing_vine);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "frost_sennit"), frost_sennit);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "blazing_necklace"), blazing_necklace);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "frost_necklace"), frost_necklace);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "illusion_vein_crystal"), illusion_vein_crystal);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "woolen_hat"), woolen_hat);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "woolen_underwear"), woolen_underwear);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "woolen_trousers"), woolen_trousers);
        Registry.register(Registry.ITEM, new Identifier(HeatControl.MOD_ID, "woolen_socks"), woolen_socks);
    }

    public static void registerAttributes() {
        Registry.register(Registry.ATTRIBUTE, new Identifier(HeatControl.MOD_ID, "env_temperature"), ENV_TEMPERATURE);
        Registry.register(Registry.ATTRIBUTE, new Identifier(HeatControl.MOD_ID, "max_temperature"), MAX_TEMPERATURE);
        Registry.register(Registry.ATTRIBUTE, new Identifier(HeatControl.MOD_ID, "min_temperature"), MIN_TEMPERATURE);
    }

    public static void registerEffects() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(HeatControl.MOD_ID, "cool"), effect_cool);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(HeatControl.MOD_ID, "hypothermia"), effect_hypothermia);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(HeatControl.MOD_ID, "heat_stroke"), effect_heat_stroke);
    }

    public static void registerEvents() {
        //注册一个每10tick刷新一次玩家温度的定时事件
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            HeatControl.tickCounter++;
            if (HeatControl.tickCounter >= HeatControl.ticksPerTask) {
                HeatControl.tickCounter = 0;
                if (server.getCurrentPlayerCount() > 0) {
                    Iterable<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
                    //遍历玩家并刷新玩家的环境温度值
                    for (ServerPlayerEntity player : players) {
                        HeatAttributeManager.applyEnvTemperature(player);
                        HeatAttributeManager.applyBlockLightEffect(player);
                    }
                }
            }
        });
    }
}
