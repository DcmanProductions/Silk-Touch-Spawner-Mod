package com.drewchaseproject.mc.fabric.SilkTouchSpawners.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.drewchaseproject.mc.fabric.SilkTouchSpawners.SilkTouchSpawners;

import net.minecraft.block.BlockState;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

@Mixin(SpawnerBlock.class)
public abstract class SpawnerBlockMixin {

    @Inject(method = "onStacksDropped", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockWithEntity;onStacksDropped(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER), cancellable = true)
    private void silkyspawners_onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, CallbackInfo ci) {
        if (stack.getItem().isIn(SilkTouchSpawners.ALLOWED_TOOLS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) > 0) {
            ci.cancel();
        }
    }
    
}