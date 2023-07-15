package com.rookiestwo.heatcontrol;

import com.rookiestwo.heatcontrol.gui.TemperatureDisplayHUD;
import com.rookiestwo.heatcontrol.tools.HeatAttributeManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeatControl implements ModInitializer {

	public static final String MOD_ID="heatcontrol";
	public static final Logger LOGGER= LoggerFactory.getLogger("Heat_Control");


	//创建并实例化模组物品组和物品
	public static final ItemGroup HC_ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier(MOD_ID, "hc_item_group"),
			() -> new ItemStack(HCItemRegistry.heat_control_icon)
	);

	//注册EntityAttribute
	public static final EntityAttribute env_temperature = Registry.register(Registry.ATTRIBUTE, new Identifier(MOD_ID, "env_temperature"), HeatAttributeManager.ENV_ATTRIBUTE);
	public static final EntityAttribute min_temperature = Registry.register(Registry.ATTRIBUTE, new Identifier(MOD_ID, "min_temperature"), HeatAttributeManager.MIN_TEMPERATURE);
	public static final EntityAttribute max_temperature = Registry.register(Registry.ATTRIBUTE, new Identifier(MOD_ID, "max_temperature"), HeatAttributeManager.MAX_TEMPERATURE);
	private final int ticksPerTask = 10;
	//tick的计数器以及几个tick执行一次
	private int tickCounter = 0;

	@Override
	public void onInitialize() {
		//注册模组物品
		HCItemRegistry.registryItem();

		//注册HUD
		HudRenderCallback.EVENT.register(new TemperatureDisplayHUD());

		//注册一个每10tick刷新一次玩家温度的定时事件
		ServerTickEvents.START_SERVER_TICK.register(server -> {
			tickCounter++;
			if(tickCounter>=ticksPerTask){
				tickCounter = 0;
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
//	private static final ItemGroup ITEM_GROUP= FabricItemGroupBuilder.create()

}
