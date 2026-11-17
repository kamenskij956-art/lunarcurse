package dev.lunarcurse.entity;

import dev.lunarcurse.ModEffects;
import dev.lunarcurse.ModEntities;
import dev.lunarcurse.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/** Uses the vanilla zombie model/renderer. TODO: custom model later. */
public class WerewolfEntity extends ZombieEntity {
    @Nullable private NbtCompound storedVillager;

    public WerewolfEntity(EntityType<? extends ZombieEntity> type, World world) {
        super(type, world);
        this.setPersistent();
    }

    public static DefaultAttributeContainer.Builder createWerewolfAttributes() {
        return ZombieEntity.createZombieAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0);
    }

    // ---- villager <-> werewolf ----

    public static void fromVillager(ServerWorld world, VillagerEntity villager) {
        NbtCompound saved = new NbtCompound();
        villager.writeNbt(saved);
        WerewolfEntity w = ModEntities.WEREWOLF.create(world);
        if (w == null) return;
        w.storedVillager = saved;
        w.refreshPositionAndAngles(villager.getX(), villager.getY(), villager.getZ(), villager.getYaw(), villager.getPitch());
        if (villager.hasCustomName()) w.setCustomName(villager.getCustomName());
        world.spawnParticles(ParticleTypes.LARGE_SMOKE, villager.getX(), villager.getY() + 1.0, villager.getZ(), 25, 0.3, 0.6, 0.3, 0.03);
        world.playSound(null, villager.getBlockPos(), SoundEvents.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1.2f, 0.7f);
        villager.discard();
        world.spawnEntity(w);
    }

    private void revert(ServerWorld world) {
        VillagerEntity v = EntityType.VILLAGER.create(world);
        if (v == null || storedVillager == null) return;
        v.readNbt(storedVillager); // restores profession, trades, xp, infection tag
        v.refreshPositionAndAngles(getX(), getY(), getZ(), getYaw(), getPitch());
        v.setVelocity(0, 0, 0);
        world.spawnParticles(ParticleTypes.POOF, getX(), getY() + 1.0, getZ(), 20, 0.3, 0.6, 0.3, 0.02);
        world.playSound(null, getBlockPos(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 1f, 1.2f);
        this.discard();
        world.spawnEntity(v);
    }

    @Override
    public void tick() {
        super.tick();
        if (!getWorld().isClient && storedVillager != null && age % 20 == 0 && getWorld() instanceof ServerWorld sw) {
            // MVP: revert any time it's not night (covers 0..1000 and missed windows)
            if (sw.getTimeOfDay() % 24000L < 13000L) revert(sw);
        }
    }

    // TODO: when werewolf dies, villager is lost. Maybe drop nothing / convert to zombie villager.
    // TODO: override canConvertInWater (drowned conversion) if it becomes a problem.
    @Override
    protected boolean burnsInDaylight() { return false; }

    @Override protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_WOLF_GROWL; }
    @Override protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.ENTITY_WOLF_HURT; }
    @Override protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_WOLF_DEATH; }

    // ---- combat ----

    @Override
    public boolean tryAttack(Entity target) {
        if (target instanceof PlayerEntity victim) {
            for (Hand h : Hand.values()) {
                ItemStack s = victim.getStackInHand(h);
                if (s.isOf(ModItems.HOLY_CROSS)) {
                    s.damage(1, victim, h == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    getWorld().playSound(null, victim.getBlockPos(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1f, 1.2f);
                    return false;
                }
            }
        }
        boolean hit = super.tryAttack(target);
        if (hit && target instanceof PlayerEntity p2 && this.random.nextFloat() < 0.30f) {
            p2.addStatusEffect(new StatusEffectInstance(ModEffects.LUNAR_FEVER, 20 * 120));
        }
        return hit;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof LivingEntity a && a.getMainHandStack().isOf(ModItems.SILVER_SWORD)) {
            amount *= 2.5f;
        }
        return super.damage(source, amount);
    }

    // ---- NBT ----

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (storedVillager != null) nbt.put("StoredVillager", storedVillager);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        storedVillager = nbt.contains("StoredVillager", NbtElement.COMPOUND_TYPE) ? nbt.getCompound("StoredVillager") : null;
    }
}
