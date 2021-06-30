package com.drewchaseproject.mc.fabric.SilkTouchSpawners.Events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface BreakSpawnerEvent {
	Event<BreakSpawnerEvent> event = EventFactory.createArrayBacked(BreakSpawnerEvent.class, (listeners) -> (player, block) -> {
		for (BreakSpawnerEvent listener : listeners) {
			ActionResult result = listener.destroy(player, block);
			if (result != ActionResult.PASS) {
				return result;
			}

		}
		return ActionResult.PASS;
	});

	ActionResult destroy(PlayerEntity player, SpawnerBlock block);
}
