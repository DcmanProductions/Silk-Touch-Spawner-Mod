package com.drewchaseproject.mc.fabric.SilkTouchSpawners.Events;

import java.util.ArrayList;
import java.util.Random;

import com.drewchaseproject.mc.fabric.SilkTouchSpawners.SilkTouchSpawners;

import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class Events {

	public static void register() {
		registerMobDrop();
		registerLootTableLoading();
	}

	private static void registerLootTableLoading() {
		Identifier spawnerLootTable = new Identifier("blocks/spawner");
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, fabricLootSupplierBuilder, lootTableSetter) -> {
			if (spawnerLootTable.equals(identifier)) {
				FabricLootSupplier additionalLoot = (FabricLootSupplier) lootManager.getTable(new Identifier(SilkTouchSpawners.MODID, "blocks/spawner"));
				fabricLootSupplierBuilder.withPools(additionalLoot.getPools());
			}
		});
	}

	private static void registerMobDrop() {

		KillMobEvent.event.register((player, mob) -> {

			int ods = 0;
			final int looting_multiplier = 3;
			for (net.minecraft.nbt.Tag ench : player.getMainHandStack().getEnchantments()) {
				String Enchantment = ench.asString().replace("{", "").replace("}", "").split(",")[1].replace("\"", "").replace("id:", "").replace(",", "");
				String levelString = ench.asString().replace("{", "").replace("}", "").split(",")[0].replace("\"", "").replace("lvl:", "").replace("s", "").replace(",", "");
				int level = 0;
				try {
					level = Integer.parseInt(levelString);
				} catch (NumberFormatException e) {
					continue;
				}
				if (Enchantment.contains("minecraft:looting")) {
					ods = level * looting_multiplier;
				}
			}
			final ArrayList<String> illigalEntities = new ArrayList<String>();
			illigalEntities.add("villager");

			if (new Random().nextInt(999) <= 0 + ods || player.isCreative()) {
				if (!illigalEntities.contains(mob.getDisplayName().getString().toLowerCase())) {
					try {
						for (SpawnEggItem egg : SpawnEggItem.getAll()) {
							String mobName = egg.asItem().getTranslationKey().replace("item.minecraft.", "").replace("_spawn_egg", "");
							if (mobName.equalsIgnoreCase(mob.getDisplayName().getString())) {
								ItemStack stack = new ItemStack(egg);
								ItemEntity itemEntity = new ItemEntity(player.world, mob.getX(), mob.getY(), mob.getZ(), stack);
								player.world.spawnEntity(itemEntity);
								break;
							}
						}
					} catch (Exception e) {

					}
				}
			}
			return ActionResult.FAIL;
		});
	}

}
