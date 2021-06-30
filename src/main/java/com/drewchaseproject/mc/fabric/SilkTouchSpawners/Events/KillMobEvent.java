package com.drewchaseproject.mc.fabric.SilkTouchSpawners.Events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface KillMobEvent {
	Event<KillMobEvent> event = EventFactory.createArrayBacked(KillMobEvent.class, (listeners) -> (player, mob) -> {
		for (KillMobEvent listener : listeners) {
			ActionResult result = listener.kill(player, mob);
			if (result != ActionResult.PASS) {
				return result;
			}

		}
		return ActionResult.PASS;
	});

	ActionResult kill(PlayerEntity player, Entity mob);
}
