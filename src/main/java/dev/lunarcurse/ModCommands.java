package dev.lunarcurse;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.lunarcurse.entity.WerewolfEntity;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.Comparator;
import java.util.Optional;

public class ModCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, access, env) -> dispatcher.register(
                CommandManager.literal("lunarcurse")
                        .requires(s -> s.hasPermissionLevel(2))
                        .then(CommandManager.literal("infect").executes(ctx -> infect(ctx, false)))
                        .then(CommandManager.literal("transform").executes(ctx -> infect(ctx, true)))
                        .then(CommandManager.literal("night").executes(ctx -> {
                            ctx.getSource().getWorld().setTimeOfDay(13500L);
                            ctx.getSource().sendFeedback(() -> Text.literal("Night."), false);
                            return 1;
                        }))
                        .then(CommandManager.literal("day").executes(ctx -> {
                            ctx.getSource().getWorld().setTimeOfDay(1000L);
                            ctx.getSource().sendFeedback(() -> Text.literal("Day."), false);
                            return 1;
                        }))
                        .then(CommandManager.literal("priest").executes(ctx -> {
                            ServerCommandSource src = ctx.getSource();
                            ServerPlayerEntity p = src.getPlayerOrThrow();
                            ServerWorld w = src.getWorld();
                            VillagerEntity v = EntityType.VILLAGER.create(w);
                            if (v == null) return 0;
                            v.refreshPositionAndAngles(p.getX() + 1, p.getY(), p.getZ(), 0, 0);
                            v.setVillagerData(v.getVillagerData().withProfession(ModVillagers.PRIEST).withLevel(1));
                            v.setExperience(1); // xp > 0: keeps the profession even without an altar
                            w.spawnEntity(v);
                            src.sendFeedback(() -> Text.literal("Priest spawned."), false);
                            return 1;
                        }))));
    }

    /** infect: tag nearest villager. transform: tag + turn into werewolf right now. */
    private static int infect(CommandContext<ServerCommandSource> ctx, boolean transformNow) throws CommandSyntaxException {
        ServerCommandSource src = ctx.getSource();
        ServerPlayerEntity p = src.getPlayerOrThrow();
        ServerWorld w = src.getWorld();
        Optional<VillagerEntity> nearest = w.getEntitiesByClass(VillagerEntity.class, p.getBoundingBox().expand(16), v -> true)
                .stream().min(Comparator.comparingDouble(v -> v.squaredDistanceTo(p)));
        if (nearest.isEmpty()) {
            src.sendFeedback(() -> Text.literal("No villager within 16 blocks."), false);
            return 0;
        }
        VillagerEntity v = nearest.get();
        v.addCommandTag(LunarCurse.INFECTED_TAG);
        if (transformNow) {
            WerewolfEntity.fromVillager(w, v);
            src.sendFeedback(() -> Text.literal("Villager became a werewolf."), false);
        } else {
            src.sendFeedback(() -> Text.literal("Villager infected."), false);
        }
        return 1;
    }
}
