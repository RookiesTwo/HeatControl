package com.rookiestwo.heatcontrol;

import com.rookiestwo.heatcontrol.gui.TemperatureDisplayHUD;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeatControl implements ModInitializer {

	public static final String MOD_ID="heatcontrol";
	public static final Logger LOGGER= LoggerFactory.getLogger("Heat_Control");


	//创建并实例化模组物品组和物品
	public static final ItemGroup HC_ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier(MOD_ID, "hc_item_group"),
			() -> new ItemStack(HCRegistry.heat_control_icon)
	);

	//注册EntityAttribute
	public static final EntityAttribute env_temperature = Registry.register(Registry.ATTRIBUTE, new Identifier(MOD_ID, "env_temperature"), HeatAttributeManager.ENV_ATTRIBUTE);
	public static final EntityAttribute min_temperature = Registry.register(Registry.ATTRIBUTE, new Identifier(MOD_ID, "min_temperature"), HeatAttributeManager.MIN_TEMPERATURE);
	public static final EntityAttribute max_temperature = Registry.register(Registry.ATTRIBUTE, new Identifier(MOD_ID, "max_temperature"), HeatAttributeManager.MAX_TEMPERATURE);
	private final int ticksPerTask = 10;
	//tick的计数器以及几个tick执行一次
	public static int tickCounter = 0;

	@Override
	public void onInitialize() {
		//注册模组物品

		HCRegistry.registerItems();

		//注册Attributes
		HCRegistry.registerAttributes();

		//注册温度刷新事件
		HCRegistry.registerEvents();

		//注册HUD
		HudRenderCallback.EVENT.register(new TemperatureDisplayHUD());


	}

}
