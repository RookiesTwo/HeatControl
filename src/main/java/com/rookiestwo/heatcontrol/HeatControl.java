package com.rookiestwo.heatcontrol;

import com.rookiestwo.heatcontrol.tools.HeatAttributeManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public class HeatControl implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	//创建并实例化模组物品组和物品
	public static final ItemGroup HC_ITEM_GROUP= FabricItemGroupBuilder.build(
			new Identifier("heatcontrol","hc_item_group"),
			()->new ItemStack(HCItemRegistry.heat_control_icon)
	);
	public static final Logger LOGGER= LoggerFactory.getLogger("Heat_Control");
	public static final EntityAttribute env_temperature=Registry.register(Registry.ATTRIBUTE,new Identifier("heatcontrol","env_temperature"),HeatAttributeManager.ENV_ATTRIBUTE);

	//tick的计数器以及几个tick执行一次
	private int tickCounter=0;
	private final int ticksPerTask=10;

	@Override
	public void onInitialize(){
		//注册模组物品
	HCItemRegistry.registryItem();
		//注册一个每10tick刷新一次玩家温度的定时事件
		ServerTickEvents.START_SERVER_TICK.register(server -> {
			tickCounter++;
			if(tickCounter>=ticksPerTask){
				tickCounter=0;
				Iterable<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
				//遍历玩家并刷新玩家的环境温度值
				for (ServerPlayerEntity player : players) {
					player.getAttributeInstance(HeatControl.env_temperature).setBaseValue(HeatAttributeManager.calculateHeatValue(player));
				}
				LOGGER.info("Player Temperature set!");
			}
		});

	}
//	private static final ItemGroup ITEM_GROUP= FabricItemGroupBuilder.create()

}
