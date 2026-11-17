package dev.lunarcurse;

import dev.lunarcurse.entity.WerewolfEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class LunarCurse implements ModInitializer {
    public static final String MOD_ID = "lunarcurse";
    /** TODO: replace command tag with a Data Attachment / mixin NBT field if a "real" NBT key is needed. */
    public static final String INFECTED_TAG = "lunar_curse:infected";

    public static Identifier id(String path) { return Identifier.of(MOD_ID, path); }

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModEntities.init();
        ModEffects.init();
        ModItems.init();
        ModVillagers.init();
        ModTrades.init();
        ModCommands.init();

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (world.getRegistryKey() != World.OVERWORLD || world.getTime() % 200L != 0L) return;
            long t = world.getTimeOfDay() % 24000L;
            if (t < 13000L || t > 23000L) return;
            boolean fullMoon = world.getMoonPhase() == 0;
            for (VillagerEntity v : world.getEntitiesByType(EntityType.VILLAGER,
                    e -> e.isAlive() && e.getCommandTags().contains(INFECTED_TAG))) {
                if (fullMoon || world.random.nextFloat() < 0.10f) {
                    WerewolfEntity.fromVillager(world, v);
                }
            }
        });
    }
}
