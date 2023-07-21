package com.rookiestwo.heatcontrol.mob_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class heatStrokeStatusEffect extends StatusEffect {

    private static final EntityAttributeModifier MAX_HEALTH_DECREASE = new EntityAttributeModifier("max_health_decrease", -4.0, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier MOVEMENT_SPEED_DECREASE = new EntityAttributeModifier("movement_speed_decrease", -0.2, EntityAttributeModifier.Operation.MULTIPLY_BASE);

    public heatStrokeStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xB80000);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            if (player.getMainHandStack().getItem().isDamageable()) {
                if (!entity.world.isClient()) {
                    player.dropItem(entity.getMainHandStack(), false, false);
                    player.getInventory().setStack(player.getInventory().selectedSlot, ItemStack.EMPTY);
                    player.setOnFireFor(3);
                }
            }
            if (!player.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH_DECREASE.getId())) {
                player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(MAX_HEALTH_DECREASE);
                player.damage(DamageSource.ON_FIRE, 4);
            }
            if (!player.getAttributes().hasModifierForAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED_DECREASE.getId())) {
                player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(MOVEMENT_SPEED_DECREASE);
            }
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(entity instanceof PlayerEntity player) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(MAX_HEALTH_DECREASE);
            player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(MOVEMENT_SPEED_DECREASE);
            if(player.getHealth()>player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getValue())
                player.damage(DamageSource.ON_FIRE, 4);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier){
        if(entity instanceof PlayerEntity player) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(MOVEMENT_SPEED_DECREASE);
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).removeModifier(MAX_HEALTH_DECREASE);
        }
    }
}
