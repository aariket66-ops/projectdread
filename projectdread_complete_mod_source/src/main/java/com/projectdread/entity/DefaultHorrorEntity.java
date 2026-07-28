package com.projectdread.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DefaultHorrorEntity extends BaseHorrorEntity {

    public DefaultHorrorEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
}
