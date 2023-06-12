package com.rookiestwo.heatcontrol;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Thermometer extends Item {
    public Thermometer(Settings settings){
        super(settings);
    }
    Text message=new LiteralText("Thermometer");
    @Override
    public TypedActionResult<ItemStack>use(World world, PlayerEntity playerEntity, Hand hand){
        if(!world.isClient())
            playerEntity.sendMessage(message,false);

        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}
