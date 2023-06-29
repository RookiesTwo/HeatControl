package com.rookiestwo.heatcontrol.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import com.rookiestwo.heatcontrol.HeatControl;
import java.text.DecimalFormat;

public class Thermometer extends Item {
    public Thermometer(Settings settings){
        super(settings);
    }

    private final static int decimalPlaces = 2;
    // 要保留的小数位数
    //private final static DecimalFormat outputFormat = new DecimalFormat("#." + "0".repeat(decimalPlaces));
    //保留小数格式化器
    @Override
    //手持温度计右键：在客户端的聊天栏显示温度数值
    public TypedActionResult<ItemStack>use(World world, PlayerEntity playerEntity, Hand hand){
        if(!world.isClient()){
            //输出调制后的小数
            Text message = new LiteralText(String.format("%.2f",playerEntity.getAttributes().getValue(HeatControl.env_temperature)));
            playerEntity.sendMessage(message,true);
        }
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}
