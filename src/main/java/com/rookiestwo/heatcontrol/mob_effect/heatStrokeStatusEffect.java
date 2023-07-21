package com.rookiestwo.heatcontrol.mob_effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class heatStrokeStatusEffect extends StatusEffect {
    public heatStrokeStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x3399FF);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }
}
