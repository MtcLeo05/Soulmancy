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
        this.add(ModBlocks.SOUL_MANIPULATOR.get(), "Soul Manipulator");
        this.add(ModBlocks.SOUL_SMELTERY.get(), "Soul Smeltery");
        this.add(ModBlocks.SOUL_CANALIZER.get(), "Soul Canalizer");

        this.add(ModBlocks.ONYX_ORE.get(), "Onyx Ore");
        this.add(ModBlocks.DEEPSLATE_ONYX_ORE.get(), "Deepslate Onyx Ore");

        this.add(ModItems.ONYX.get(), "Onyx");
        this.add(ModItems.ARTIFICIAL_ONYX.get(), "Artificial Onyx");
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

        this.add(Soulmancy.MODID + ".item.moreInfo", "----Press Shift for info----");
        this.add(Soulmancy.MODID + ".item.soulConsume", "%d Soul/s");
        this.add(Soulmancy.MODID + ".item.soulChunk", "%d if taken from chunk");
        this.add(Soulmancy.MODID + ".item.talisman.effectList", "Effects:");
        this.add(Soulmancy.MODID + ".item.talisman.effect", "%d %d");

        this.add(Soulmancy.MODID + ".item.noBiomeFound", "Nothing important found...");

        this.add(Soulmancy.MODID + ".jei.anvilCrush", "Anvil Crushing");
        this.add(Soulmancy.MODID + ".jei.soulTransform", "Soul Transformation");
        this.add(Soulmancy.MODID + ".jei.soulBurn", "Soul Burning");
        this.add(Soulmancy.MODID + ".jei.vesselStrengthen", "Vessel Strengthen");

        this.add(Soulmancy.MODID + ".jei.duration", "Recipe Duration: %d");
        this.add(Soulmancy.MODID + ".jei.consumeSoul", "Soul Consumed: %d");
        this.add(Soulmancy.MODID + ".jei.produceSoul", "Soul Produced: %d");
        this.add(Soulmancy.MODID + ".jei.vesselIncrease", "Vessel Increase: %d");

        this.add("curios.identifier.eye", "Eye Slot");
        this.add("curios.identifier.talisman", "Talisman Slot");

        this.add("enchantment." + Soulmancy.MODID + ".soul_reaping", "Soul Reaping");
        this.add("enchantment." + Soulmancy.MODID + ".soul_reaping.desc", "Converts killed entities' max HP to soul usable in recipes");
    }
}