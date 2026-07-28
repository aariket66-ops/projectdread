package com.projectdread.entity;

import com.projectdread.ProjectDread;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = 
            DeferredRegister.create(Registries.ENTITY_TYPE, ProjectDread.MOD_ID);

    private static DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> registerHorrorEntity(String name) {
        return ENTITIES.register(name, () ->
                EntityType.Builder.of(DefaultHorrorEntity::new, MobCategory.MONSTER)
                        .sized(0.6F, 1.9F)
                        .clientTrackingRange(10)
                        .updateInterval(1)
                        .build(ProjectDread.MOD_ID + ":" + name)
        );
    }

    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_WATCHER     = registerHorrorEntity("the_watcher");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_LURKER      = registerHorrorEntity("the_lurker");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_STALKER     = registerHorrorEntity("the_stalker");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_SHRIEKER    = registerHorrorEntity("the_shrieker");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_PHANTOM     = registerHorrorEntity("the_phantom");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_ANOMALY     = registerHorrorEntity("the_anomaly");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_HOLLOW      = registerHorrorEntity("the_hollow");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_MIMIC       = registerHorrorEntity("the_mimic");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_SHADE       = registerHorrorEntity("the_shade");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_CRAWLER     = registerHorrorEntity("the_crawler");
    
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_SPECTER     = registerHorrorEntity("the_specter");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_HARVESTER   = registerHorrorEntity("the_harvester");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_WEEPER      = registerHorrorEntity("the_weeper");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_STRANGER    = registerHorrorEntity("the_stranger");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_WHISPERER   = registerHorrorEntity("the_whisperer");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_SKINWALKER  = registerHorrorEntity("the_skinwalker");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_NIGHTMARE   = registerHorrorEntity("the_nightmare");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_OBSERVER    = registerHorrorEntity("the_observer");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_CORRUPTED   = registerHorrorEntity("the_corrupted");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_DISTORTED   = registerHorrorEntity("the_distorted");
    
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_TORMENTOR   = registerHorrorEntity("the_tormentor");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_VOID_WALKER = registerHorrorEntity("the_void_walker");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_SLEEPLESS   = registerHorrorEntity("the_sleepless");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_FACELESS    = registerHorrorEntity("the_faceless");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_MARIONETTE  = registerHorrorEntity("the_marionette");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_WANDERER    = registerHorrorEntity("the_wanderer");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_SHADOW      = registerHorrorEntity("the_shadow");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_GLITCH      = registerHorrorEntity("the_glitch");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_ECHO        = registerHorrorEntity("the_echo");
    public static final DeferredHolder<EntityType<?>, EntityType<DefaultHorrorEntity>> THE_DREAD       = registerHorrorEntity("the_dread");

    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(THE_WATCHER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_LURKER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_STALKER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_SHRIEKER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_PHANTOM.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_ANOMALY.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_HOLLOW.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_MIMIC.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_SHADE.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_CRAWLER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());

        event.put(THE_SPECTER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_HARVESTER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_WEEPER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_STRANGER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_WHISPERER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_SKINWALKER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_NIGHTMARE.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_OBSERVER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_CORRUPTED.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_DISTORTED.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());

        event.put(THE_TORMENTOR.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_VOID_WALKER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_SLEEPLESS.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_FACELESS.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_MARIONETTE.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_WANDERER.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_SHADOW.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_GLITCH.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_ECHO.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
        event.put(THE_DREAD.get(), BaseHorrorEntity.createBaseHorrorAttributes().build());
    }
}
