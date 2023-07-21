package com.rookiestwo.heatcontrol.items;

import com.google.common.collect.Multimap;
import com.rookiestwo.heatcontrol.HCRegistry;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class WoolenUnderwear extends TrinketItem {
    public WoolenUnderwear(Settings settings){super(settings);}
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(
            ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid){
        var modifiers=super.getModifiers(stack,slot,entity,uuid);
        modifiers.put(
                HCRegistry.MAX_TEMPERATURE, new EntityAttributeModifier(uuid, "heatcontrol:max_temperature", -3.0, EntityAttributeModifier.Operation.ADDITION));
        modifiers.put(
                HCRegistry.MIN_TEMPERATURE, new EntityAttributeModifier(uuid, "heatcontrol:min_temperature", -10.0, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }
}
