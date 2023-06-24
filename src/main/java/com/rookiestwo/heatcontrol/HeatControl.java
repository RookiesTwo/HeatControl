package com.rookiestwo.heatcontrol;


import com.ibm.icu.impl.PVecToTrieCompactHandler;
import com.rookiestwo.heatcontrol.items.BlazingVine;
import com.rookiestwo.heatcontrol.items.Thermometer;
import com.rookiestwo.heatcontrol.tools.HeatAttributeManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rookiestwo.heatcontrol.tools.HeatAttributeManager;


public class HeatControl implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static final Logger LOGGER= LoggerFactory.getLogger("Heat_Control");
	public static final Thermometer thermometer=new Thermometer(new FabricItemSettings());

	public static final BlazingVine blazing_vine=new BlazingVine(new FabricItemSettings());
	public static final EntityAttribute env_temperature=Registry.register(Registry.ATTRIBUTE,new Identifier("heatcontrol","env_temperature"),HeatAttributeManager.ENV_ATTRIBUTE);


	@Override
	public void onInitialize(){
		//注册温度计
		Registry.register(Registry.ITEM,new Identifier("heatcontrol","thermometer"),thermometer);
		Registry.register(Registry.ITEM,new Identifier("heatcontrol","blazing_vine"),blazing_vine);

	}
//	private static final ItemGroup ITEM_GROUP= FabricItemGroupBuilder.create()

}
