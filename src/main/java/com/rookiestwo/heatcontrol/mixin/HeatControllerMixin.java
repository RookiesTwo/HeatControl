package com.rookiestwo.heatcontrol.mixin;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntity.class)
public class HeatControllerMixin {
    @Inject(
            at=@At("TAIL"),
            method = "writeCustomDataToNbt"
    )
    private void writeCustomDataToNbt(CallbackInfo info){
        PlayerEntity Player=(PlayerEntity)(Object) this;
        Player.getAttributeInstance().
    }

}
