package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.data.PlayerData;
import com.leo.soulmancy.data.SoulData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Soulmancy.MODID);

    public static final Supplier<AttachmentType<SoulData>> SOUL_DATA_ATTACHMENT = ATTACHMENT_TYPES.register(
        "soul_data",
        () -> AttachmentType.builder(() -> new SoulData(0, 0))
            .serialize(SoulData.CODEC)
            .build()
    );

    public static final Supplier<AttachmentType<PlayerData>> PLAYER_DATA_ATTACHMENT = ATTACHMENT_TYPES.register(
        "player_data",
        () -> AttachmentType.builder(PlayerData::new)
            .serialize(PlayerData.CODEC)
            .copyOnDeath()
            .build()
    );
}
