package com.leo.soulmancy.datagen;

import com.leo.soulmancy.Soulmancy;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Soulmancy.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeClient(), ModLootTableProvider.create(packOutput, provider));

        ModBlockTagGenerator blockTagGenerator = new ModBlockTagGenerator(packOutput, provider, existingFileHelper);

        generator.addProvider(event.includeClient(), blockTagGenerator);
        generator.addProvider(event.includeClient(), new ModItemTagsProvider(
            packOutput,
            provider,
            blockTagGenerator.contentsGetter()
        ));

        generator.addProvider(event.includeClient(), new ModRecipeProvider(packOutput, provider));

        generator.addProvider(event.includeClient(), new ModMiscDataProvider(packOutput, provider));

        generator.addProvider(event.includeClient(), new ModLanguageProvider(packOutput, "en_us"));
    }
}
