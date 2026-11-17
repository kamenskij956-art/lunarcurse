package dev.lunarcurse.item;

import dev.lunarcurse.ModEffects;
import dev.lunarcurse.entity.WerewolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class HolyWaterItem extends Item {
    public HolyWaterItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (world instanceof ServerWorld sw) {
            Box box = player.getBoundingBox().expand(5.0);
            for (WerewolfEntity w : world.getEntitiesByClass(WerewolfEntity.class, box, e -> true)) {
                w.damage(world.getDamageSources().magic(), 10.0f);
            }
            for (PlayerEntity p : world.getEntitiesByClass(PlayerEntity.class, box, e -> true)) {
                p.removeStatusEffect(ModEffects.LUNAR_FEVER);
            }
            sw.spawnParticles(ParticleTypes.SPLASH, player.getX(), player.getBodyY(0.5), player.getZ(), 60, 2.0, 0.5, 2.0, 0.1);
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 1f, 1f);
        }
        stack.decrementUnlessCreative(1, player);
        return TypedActionResult.success(stack, world.isClient());
    }
}
