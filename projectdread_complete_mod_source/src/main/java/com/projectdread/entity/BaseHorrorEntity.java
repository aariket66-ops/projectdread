package com.projectdread.entity;

import com.projectdread.entity.ai.FreezeOnLookGoal;
import com.projectdread.entity.ai.StalkerFollowGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class BaseHorrorEntity extends Monster {

    private boolean isFrozen = false;

    protected BaseHorrorEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createBaseHorrorAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FreezeOnLookGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new StalkerFollowGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 15.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide && this.getTarget() != null && !this.isFrozen) {
            LivingEntity target = this.getTarget();
            BlockPos currentPos = this.blockPosition();

            if (this.level().getMaxLocalRawBrightness(currentPos) == 0 && this.random.nextFloat() < 0.02F) {
                attemptJumpscareTeleport(target);
            }
        }
    }

    private void attemptJumpscareTeleport(LivingEntity target) {
        Vec3 lookVec = target.getLookAngle();
        double teleportX = target.getX() - (lookVec.x * 3.0D);
        double teleportZ = target.getZ() - (lookVec.z * 3.0D);
        double teleportY = target.getY();

        BlockPos targetPos = BlockPos.containing(teleportX, teleportY, teleportZ);

        if (this.level().getBlockState(targetPos).isAir()) {
            this.teleportTo(teleportX, teleportY, teleportZ);
            this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 0.2F);
        }
    }

    public boolean isFrozen() {
        return this.isFrozen;
    }

    public void setFrozen(boolean frozen) {
        this.isFrozen = frozen;
        if (frozen) {
            this.getNavigation().stop();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMBIENT_CAVE.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.PHANTOM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }
}
