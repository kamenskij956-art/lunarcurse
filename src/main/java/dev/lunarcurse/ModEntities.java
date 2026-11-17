package dev.lunarcurse;

import dev.lunarcurse.entity.WerewolfEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {
    public static final EntityType<WerewolfEntity> WEREWOLF = Registry.register(Registries.ENTITY_TYPE,
            LunarCurse.id("werewolf"),
            EntityType.Builder.<WerewolfEntity>create(WerewolfEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.95f).maxTrackingRange(8).build("werewolf"));

    public static void init() {
        FabricDefaultAttributeRegistry.register(WEREWOLF, WerewolfEntity.createWerewolfAttributes());
    }
}
