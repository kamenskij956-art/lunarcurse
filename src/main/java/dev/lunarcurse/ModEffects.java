package dev.lunarcurse;

import dev.lunarcurse.effect.LunarFeverEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> LUNAR_FEVER =
            Registry.registerReference(Registries.STATUS_EFFECT, LunarCurse.id("lunar_fever"), new LunarFeverEffect());
    public static void init() {}
}
