package com.projectdread.entity.ai;

import com.projectdread.entity.BaseHorrorEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FreezeOnLookGoal extends Goal {

    private final BaseHorrorEntity entity;

    public FreezeOnLookGoal(BaseHorrorEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.entity.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        return isPlayerLookingAtEntity(target, this.entity);
    }

    @Override
    public void start() {
        this.entity.setFrozen(true);
    }

    @Override
    public void stop() {
        this.entity.setFrozen(false);
    }

    @Override
    public void tick() {
        this.entity.getNavigation().stop();
        this.entity.setDeltaMovement(0, this.entity.getDeltaMovement().y, 0);
    }

    private boolean isPlayerLookingAtEntity(LivingEntity player, BaseHorrorEntity entity) {
        Vec3 playerLook = player.getViewVector(1.0F).normalize();
        Vec3 playerToEntity = entity.position().subtract(player.getEyePosition()).normalize();

        double dotProduct = playerLook.dot(playerToEntity);
        
        return dotProduct > 0.5D && player.hasLineOfSight(entity);
    }
}
