package com.rookiestwo.heatcontrol;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HeatControl implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static final Logger LOGGER= LoggerFactory.getLogger("Heat_Control");
	public static final Item thermometer=new Item(new FabricItemSettings());
	@Override
	public void onInitialize(){
		Registry.register(Registry.ITEM,new Identifier("heatcontrol","thermometer"),thermometer);
	}
}
