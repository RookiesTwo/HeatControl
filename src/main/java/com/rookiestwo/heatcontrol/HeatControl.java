package com.rookiestwo.heatcontrol;


import com.rookiestwo.heatcontrol.items.BlazingVine;
import com.rookiestwo.heatcontrol.items.HeatControlIcon;
import com.rookiestwo.heatcontrol.items.Thermometer;
import com.rookiestwo.heatcontrol.tools.HeatAttributeManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HeatControl implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	//创建并实例化模组物品组和物品
	public static final ItemGroup HC_ITEM_GROUP= FabricItemGroupBuilder.build(
			new Identifier("heatcontrol","hc_item_group"),
			()->new ItemStack(HeatControl.heat_control_icon)
	);
	public static final Logger LOGGER= LoggerFactory.getLogger("Heat_Control");
	public static final Thermometer thermometer=new Thermometer(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP));
	public static final BlazingVine blazing_vine=new BlazingVine(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP));
	public static final HeatControlIcon heat_control_icon=new HeatControlIcon(new FabricItemSettings());
	public static final EntityAttribute env_temperature=Registry.register(Registry.ATTRIBUTE,new Identifier("heatcontrol","env_temperature"),HeatAttributeManager.ENV_ATTRIBUTE);


	@Override
	public void onInitialize(){
		//注册模组物品
		Registry.register(Registry.ITEM,new Identifier("heatcontrol","thermometer"),thermometer);
		Registry.register(Registry.ITEM,new Identifier("heatcontrol","blazing_vine"),blazing_vine);
		Registry.register(Registry.ITEM,new Identifier("heatcontrol","heat_control_icon"),heat_control_icon);
	}
//	private static final ItemGroup ITEM_GROUP= FabricItemGroupBuilder.create()

}
