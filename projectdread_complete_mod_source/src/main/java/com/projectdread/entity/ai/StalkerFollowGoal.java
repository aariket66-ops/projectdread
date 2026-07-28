package com.projectdread.entity.ai;

import com.projectdread.entity.BaseHorrorEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class StalkerFollowGoal extends Goal {

    private final BaseHorrorEntity entity;
    private final double speedModifier;

    public StalkerFollowGoal(BaseHorrorEntity entity, double speedModifier) {
        this.entity = entity;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.entity.getTarget();
        return target != null && target.isAlive() && !this.entity.isFrozen();
    }

    @Override
    public void tick() {
        LivingEntity target = this.entity.getTarget();
        if (target == null) return;

        Vec3 lookVector = target.getViewVector(1.0F).normalize();
        Vec3 blindSpot = target.position().subtract(lookVector.scale(6.0D));

        this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
        this.entity.getNavigation().moveTo(blindSpot.x, blindSpot.y, blindSpot.z, this.speedModifier);
    }
}
