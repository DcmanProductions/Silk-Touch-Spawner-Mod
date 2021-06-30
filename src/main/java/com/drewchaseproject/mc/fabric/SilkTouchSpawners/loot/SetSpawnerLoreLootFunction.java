package com.drewchaseproject.mc.fabric.SilkTouchSpawners.loot;

import com.drewchaseproject.mc.fabric.SilkTouchSpawners.SilkTouchSpawners;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SetSpawnerLoreLootFunction extends ConditionalLootFunction {

	protected SetSpawnerLoreLootFunction(LootCondition[] conditions) {
		super(conditions);
	}

	@Override
	protected ItemStack process(ItemStack stack, LootContext context) {
		CompoundTag beData = stack.getSubTag("BlockEntityTag");
		if (beData != null) {

			if (beData.contains("SpawnData", NbtType.COMPOUND)) {
				CompoundTag spawnData = beData.getCompound("SpawnData");
				if (spawnData.contains("id", NbtType.STRING)) {
					ListTag lore = getLoreForMerge(stack);
					String mobName = CapWords(spawnData.getString("id").replace("minecraft:", "").replace("_", " "));
					Text entityID = new LiteralText(String.format("%s Spawner", mobName)).formatted(Formatting.AQUA);
					lore.add(StringTag.of(Text.Serializer.toJson(entityID)));
				}
			}
		}
		return stack;
	}

	private String CapWords(String unformatted) {
		String temp = "";
		for (int i = 0; i < unformatted.split(" ").length; i++) {
			String section = unformatted.split(" ")[i].replace(" ", "");
			temp += (section.charAt(0) + "").toUpperCase() + section.substring(1) + (i == unformatted.split(" ").length - 1 ? "" : " ");
		}
		return temp;
	}

	@Override
	public LootFunctionType getType() {
		return SilkTouchSpawners.SET_SPAWNER_LORE_LOOT_FUNCTION;
	}

	private ListTag getLoreForMerge(ItemStack stack) {
		CompoundTag display = stack.getOrCreateSubTag("display");
		if (display.contains("Lore", NbtType.LIST)) {
			return display.getList("Lore", NbtType.STRING);
		} else {
			ListTag listTag = new ListTag();
			display.put("Lore", listTag);
			return listTag;
		}
	}

	public static class Serializer extends ConditionalLootFunction.Serializer<SetSpawnerLoreLootFunction> {

		@Override
		public SetSpawnerLoreLootFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
			return new SetSpawnerLoreLootFunction(conditions);
		}
	}
}
