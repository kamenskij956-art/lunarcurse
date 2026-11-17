package dev.lunarcurse.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class LunarFeverEffect extends StatusEffect {
    public LunarFeverEffect() { super(StatusEffectCategory.HARMFUL, 0x6B3FA0); }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) { return duration % 40 == 0; }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 120));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 120));
        return true;
    }
}
