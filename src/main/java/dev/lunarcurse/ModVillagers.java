package dev.lunarcurse;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> ALTAR_POI_KEY =
            RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, LunarCurse.id("altar"));

    public static final PointOfInterestType ALTAR_POI =
            PointOfInterestHelper.register(LunarCurse.id("altar"), 1, 1, ModBlocks.ALTAR);

    // Unemployed villagers claim the altar via vanilla job-site logic.
    // TODO: real profession texture (currently transparent placeholder).
    public static final VillagerProfession PRIEST = Registry.register(Registries.VILLAGER_PROFESSION,
            LunarCurse.id("priest"),
            new VillagerProfession("priest",
                    e -> e.matchesKey(ALTAR_POI_KEY), e -> e.matchesKey(ALTAR_POI_KEY),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_CLERIC));

    public static void init() {}
}
