package com.rookiestwo.heatcontrol;

import com.rookiestwo.heatcontrol.items.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.impl.gui.FabricGuiEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HCItemRegistry {
    public static final BlazingNecklace blazing_necklace=new BlazingNecklace(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final BlazingVine blazing_vine=new BlazingVine(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP));
    public static final FrostSennit frost_sennit =new FrostSennit(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP));
    public static final FrostNecklace frost_necklace=new FrostNecklace(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final HeatControlIcon heat_control_icon=new HeatControlIcon(new FabricItemSettings());
    public static final Thermometer thermometer=new Thermometer(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final IllusionVeinCrystal illusion_vein_crystal=new IllusionVeinCrystal(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final WoolenHat woolen_hat=new WoolenHat(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final WoolenSocks woolen_socks=new WoolenSocks(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final WoolenTrousers woolen_trousers=new WoolenTrousers(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static final WoolenUnderwear woolen_underwear=new WoolenUnderwear(new FabricItemSettings().group(HeatControl.HC_ITEM_GROUP).maxCount(1));
    public static void registryItem(){
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","heat_control_icon"),heat_control_icon);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","thermometer"),thermometer);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","blazing_vine"),blazing_vine);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","frost_sennit"),frost_sennit);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","blazing_necklace"),blazing_necklace);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","frost_necklace"),frost_necklace);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","illusion_vein_crystal"),illusion_vein_crystal);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","woolen_hat"),woolen_hat);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","woolen_underwear"),woolen_underwear);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","woolen_trousers"),woolen_trousers);
        Registry.register(Registry.ITEM,new Identifier("heatcontrol","woolen_socks"),woolen_socks);
    }
}
