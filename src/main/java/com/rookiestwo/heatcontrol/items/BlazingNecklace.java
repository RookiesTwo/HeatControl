package com.rookiestwo.heatcontrol.items;

import com.google.common.collect.Multimap;
import com.rookiestwo.heatcontrol.HCRegistry;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

import java.util.UUID;

public class BlazingNecklace extends TrinketItem {

    public BlazingNecklace(Settings settings){
        super(settings);
    }
    @Override
    public Rarity getRarity(ItemStack stack){
        return Rarity.UNCOMMON;
    }
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(
            ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid){
        var modifiers=super.getModifiers(stack,slot,entity,uuid);
        modifiers.put(
                HCRegistry.MIN_TEMPERATURE, new EntityAttributeModifier(uuid, "heatcontrol:min_temperature", -273.16, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }
}
