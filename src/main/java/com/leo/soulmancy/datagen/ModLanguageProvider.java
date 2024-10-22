package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.init.ModBlocks;
import com.leo.soulmancy.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, Soulmancy.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        this.add(Soulmancy.MODID + ".itemGroup.main", "Soulmancy");

        this.add(ModBlocks.EBONY_LOG.get(), "Ebony Log");
        this.add(ModBlocks.EBONY_WOOD.get(), "Ebony Wood");

        this.add(ModBlocks.EYED_EBONY_LOG.get(), "Ebony Log");

        this.add(ModBlocks.STRIPPED_EBONY_LOG.get(), "Stripped Ebony Log");
        this.add(ModBlocks.STRIPPED_EBONY_WOOD.get(), "Stripped Ebony Wood");

        this.add(ModBlocks.EBONY_LEAVES.get(), "Ebony Leaves");
        this.add(ModBlocks.EBONY_SAPLING.get(), "Ebony Sapling");

        this.add(ModBlocks.EBONY_PLANKS.get(), "Ebony Planks");
        this.add(ModBlocks.EBONY_STAIRS.get(), "Ebony Stairs");
        this.add(ModBlocks.EBONY_SLAB.get(), "Ebony Slab");
        this.add(ModBlocks.EBONY_FENCE.get(), "Ebony Fence");
        this.add(ModBlocks.EBONY_FENCE_GATE.get(), "Ebony Fence Gate");
        this.add(ModBlocks.EBONY_BUTTON.get(), "Ebony Button");
        this.add(ModBlocks.EBONY_PRESSURE_PLATE.get(), "Ebony Pressure Plate");
        this.add(ModBlocks.EBONY_DOOR.get(), "Ebony Door");
        this.add(ModBlocks.EBONY_TRAPDOOR.get(), "Ebony Trapdoor");

        this.add(ModBlocks.SOUL_STONE.get(), "Soul Stone");
        this.add(ModBlocks.CONDENSED_SOUL.get(), "Condensed Soul");
        this.add(ModBlocks.ARTIFICIAL_ONYX_BLOCK.get(), "Artificial Onyx Block");
        this.add(ModBlocks.ONYX_BLOCK.get(), "Onyx Block");
        this.add(ModBlocks.SOUL_MANIPULATOR.get(), "Soul Manipulator");
        this.add(ModBlocks.SOUL_SMELTERY.get(), "Soul Smeltery");
        this.add(ModBlocks.SOUL_CANALIZER.get(), "Soul Canalizer");
        this.add(ModBlocks.SOUL_SACRIFICER.get(), "Soul Sacrificer");
        this.add(ModBlocks.PEDESTAL.get(), "Pedestal");
        this.add(ModBlocks.RITUAL_PEDESTAL.get(), "Ritual Pedestal");

        this.add(ModBlocks.ONYX_ORE.get(), "Onyx Ore");
        this.add(ModBlocks.DEEPSLATE_ONYX_ORE.get(), "Deepslate Onyx Ore");

        this.add(ModItems.ONYX.get(), "Onyx");
        this.add(ModItems.ARTIFICIAL_ONYX.get(), "Artificial Onyx");
        this.add(ModItems.CRYSTALLIZED_SOUL.get(), "Crystallized Soul");
        this.add(ModItems.OCCULT_COMPASS.get(), "Occult Compass");
        this.add(ModItems.REVEALING_EYE.get(), "Eye Of Revealing");
        this.add(ModItems.SIGHT_LENS.get(), "Lens Of Sight");
        this.add(ModItems.SOUL_CAPSULE.get(), "Soul Capsule");
        this.add(ModItems.CAPSULE_STACK.get(), "Soul Capsule Stack");
        this.add(ModItems.SOUL_SCYTHE.get(), "Soul Reaping Scythe");

        this.add(ModItems.MINERS_TALISMAN1.get(), "Miner's Talisman 1");
        this.add(ModItems.MINERS_TALISMAN2.get(), "Miner's Talisman 2");
        this.add(ModItems.MINERS_TALISMAN3.get(), "Miner's Talisman 3");

        this.add(ModItems.FIGHTERS_TALISMAN1.get(), "Fighter's Talisman 1");
        this.add(ModItems.FIGHTERS_TALISMAN2.get(), "Fighter's Talisman 2");
        this.add(ModItems.FIGHTERS_TALISMAN3.get(), "Fighter's Talisman 3");

        this.add(ModItems.RUNNERS_TALISMAN1.get(), "Runner's Talisman 1");
        this.add(ModItems.RUNNERS_TALISMAN2.get(), "Runner's Talisman 2");

        this.add(ModItems.SUSTENANCE_CHARM.get(), "Charm of Sustenance");

        this.add(ModItems.SOULMANCERS_ROBE.get(), "Soulmancer's Robe");

        this.add(Soulmancy.MODID + ".item.moreInfo", "----Press Shift for info----");
        this.add(Soulmancy.MODID + ".item.soulConsume", "%d Soul every %d ticks");
        this.add(Soulmancy.MODID + ".item.soulChunk", "%d if taken from chunk");
        this.add(Soulmancy.MODID + ".item.rClick", "This item is versatile, use it to switch mode");

        this.add(Soulmancy.MODID + ".item.talisman.effectList", "Effects:");
        this.add(Soulmancy.MODID + ".item.talisman.effect", "%d %d");

        this.add(Soulmancy.MODID + ".item.sight.impure", "Allows gathering information on the world. Has not been perfected");
        this.add(Soulmancy.MODID + ".item.sight.pure", "Allows gathering information on the world. Has been perfected");

        this.add(Soulmancy.MODID + ".item.sustenance", "Replenishes the bearer's hunger, at a price");

        this.add(Soulmancy.MODID + ".item.robeFlavor", "A soulmancer's best friend");
        this.add(Soulmancy.MODID + ".item.robeEffect", "Is able to gather soul from another plane of existence, how to contain it is up to you");

        this.add(Soulmancy.MODID + ".item.robeEffect2", "Not using it as your main protection may lessen some of the effects");

        this.add(Soulmancy.MODID + ".item.noBiomeFound", "Nothing of importance was found...");

        this.add(Soulmancy.MODID + ".jei.anvilCrush", "Anvil Crushing");
        this.add(Soulmancy.MODID + ".jei.soulTransform", "Soul Transformation");
        this.add(Soulmancy.MODID + ".jei.soulBurn", "Soul Burning");
        this.add(Soulmancy.MODID + ".jei.vesselStrengthen", "Vessel Strengthen");
        this.add(Soulmancy.MODID + ".jei.imbuementRitual", "Ritual of Imbuement");

        this.add(Soulmancy.MODID + ".jei.duration", "Recipe Duration: %d");
        this.add(Soulmancy.MODID + ".jei.consumeSoul", "Soul Consumed: %d");
        this.add(Soulmancy.MODID + ".jei.produceSoul", "Soul Produced: %d");
        this.add(Soulmancy.MODID + ".jei.vesselIncrease", "Vessel Increase: %d");

        this.add("accessories.slot.eye", "Eye Slot");
        this.add("accessories.slot.talisman", "Talisman Slot");

        this.add("enchantment." + Soulmancy.MODID + ".soul_reaping", "Soul Reaping");
        this.add("enchantment." + Soulmancy.MODID + ".soul_reaping.desc", "Converts killed entities' max HP to soul usable in recipes");

        this.add("advancements." + Soulmancy.MODID + ".root.title", "Welcome to Soulmancy");
        this.add("advancements." + Soulmancy.MODID + ".root.description", "Hope you're in for a ride");

        this.add("advancements." + Soulmancy.MODID + ".obtainArtificialOnyx.title", "Artificial Onyx");
        this.add("advancements." + Soulmancy.MODID + ".obtainArtificialOnyx.description", "Obtain an artificial onyx");

        this.add("advancements." + Soulmancy.MODID + ".visitForest.title", "Ebony Forest");
        this.add("advancements." + Soulmancy.MODID + ".visitForest.description", "The place where soul gathers");

        this.add("advancements." + Soulmancy.MODID + ".basicMaterials.title", "Basic Materials");
        this.add("advancements." + Soulmancy.MODID + ".basicMaterials.description", "A collection of materials you'll need");

        this.add("advancements." + Soulmancy.MODID + ".soulManipulator.title", "Soul Manipulator");
        this.add("advancements." + Soulmancy.MODID + ".soulManipulator.description", "The base for a successful soulmancer");
        this.add("advancements." + Soulmancy.MODID + ".pedestal.title", "Pedestal");
        this.add("advancements." + Soulmancy.MODID + ".pedestal.description", "Glorified item display");

        this.add("advancements." + Soulmancy.MODID + ".soulSmeltery.title", "Soul Smeltery");
        this.add("advancements." + Soulmancy.MODID + ".soulSmeltery.description", "H.O.T.T.O.G.O.");
        this.add("advancements." + Soulmancy.MODID + ".soulCanalizer.title", "Soul Canalizer");
        this.add("advancements." + Soulmancy.MODID + ".soulCanalizer.description", "Every mods needs a crop grower");
        this.add("advancements." + Soulmancy.MODID + ".soulSacrificer.title", "Soul Sacrificer");
        this.add("advancements." + Soulmancy.MODID + ".soulSacrificer.description", "Killing mobs with style");
        this.add("advancements." + Soulmancy.MODID + ".revealingEye.title", "Eye of Revealing");
        this.add("advancements." + Soulmancy.MODID + ".revealingEye.description", "Totally not a discount thaumometer");

        this.add("advancements." + Soulmancy.MODID + ".ritualPedestal.title", "Ritual Pedestal");
        this.add("advancements." + Soulmancy.MODID + ".ritualPedestal.description", "Something something big craftings something something");

        this.add("advancements." + Soulmancy.MODID + ".sightLens.title", "Lens of Sight");
        this.add("advancements." + Soulmancy.MODID + ".sightLens.description", "Now 100% less ourple");
        this.add("advancements." + Soulmancy.MODID + ".sustenanceCharm.title", "Charm of Sustenance");
        this.add("advancements." + Soulmancy.MODID + ".sustenanceCharm.description", "Eating? What's that?");
        this.add("advancements." + Soulmancy.MODID + ".soulScythe.title", "Soul Scythe");
        this.add("advancements." + Soulmancy.MODID + ".soulScythe.description", "Who doesn't like scythes???");
        this.add("advancements." + Soulmancy.MODID + ".soulmancersRobe.title", "Soulmancer's Robe");
        this.add("advancements." + Soulmancy.MODID + ".soulmancersRobe.description", "Damn you got drip");

        this.add("advancements." + Soulmancy.MODID + ".minersTalisman1.title", "Miner's Talisman 1");
        this.add("advancements." + Soulmancy.MODID + ".minersTalisman1.description", "A beacon's worst nightmare");
        this.add("advancements." + Soulmancy.MODID + ".minersTalisman2.title", "Miner's Talisman 2");
        this.add("advancements." + Soulmancy.MODID + ".minersTalisman2.description", "Who needs efficiency?");
        this.add("advancements." + Soulmancy.MODID + ".minersTalisman3.title", "Miner's Talisman 3");
        this.add("advancements." + Soulmancy.MODID + ".minersTalisman3.description", "Why...?");

        this.add("advancements." + Soulmancy.MODID + ".fightersTalisman1.title", "Fighter's Talisman 1");
        this.add("advancements." + Soulmancy.MODID + ".fightersTalisman1.description", "Offense is the best defence");
        this.add("advancements." + Soulmancy.MODID + ".fightersTalisman2.title", "Fighter's Talisman 2");
        this.add("advancements." + Soulmancy.MODID + ".fightersTalisman2.description", "See them rain their blood down!");
        this.add("advancements." + Soulmancy.MODID + ".fightersTalisman3.title", "Fighter's Talisman 3");
        this.add("advancements." + Soulmancy.MODID + ".fightersTalisman3.description", "OP? Nah");

        this.add("advancements." + Soulmancy.MODID + ".runnersTalisman1.title", "Runner's Talisman 1");
        this.add("advancements." + Soulmancy.MODID + ".runnersTalisman1.description", "It's not cowardice...");
        this.add("advancements." + Soulmancy.MODID + ".runnersTalisman2.title", "Runner's Talisman 2");
        this.add("advancements." + Soulmancy.MODID + ".runnersTalisman2.description", "...it's a strategical retreat!");
    }
}