package com.partlysunny.entities.type;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class WitherbornFakeWither extends WitherBoss {
    public WitherbornFakeWither(Level world) {
        super(EntityType.WITHER, world);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(0, new MoveTowardsClosestAttackableMob(this));
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damagesource, int i, boolean flag) {
    }

    @Override
    public void performRangedAttack(LivingEntity entityliving, float f) {
    }

    @Override
    public boolean canAttackType(EntityType<?> entitytypes) {
        return false;
    }

    @Override
    public float getScale() {
        return 0.3f;
    }

    @Override
    public void setCustomName(@Nullable Component ichatbasecomponent) {
    }


}

class MoveTowardsClosestAttackableMob extends NearestAttackableTargetGoal<LivingEntity> {

    public MoveTowardsClosestAttackableMob(Mob entityinsentient) {
        super(entityinsentient, LivingEntity.class, 0, false, false, livingEntity -> livingEntity.getType() != EntityType.PLAYER && livingEntity.getType() != EntityType.ARMOR_STAND && livingEntity.getType() != EntityType.ITEM);
    }

}

