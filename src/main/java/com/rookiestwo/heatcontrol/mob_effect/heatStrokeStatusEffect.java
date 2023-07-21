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

import java.util.UUID;

public class heatStrokeStatusEffect extends StatusEffect {
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
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID.randomUUID().toString(), -0.2, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, UUID.randomUUID().toString(), -4.0, EntityAttributeModifier.Operation.ADDITION);
        entity.damage(DamageSource.ON_FIRE, 4);
        super.onApplied(entity, attributes, amplifier);
    }
}
