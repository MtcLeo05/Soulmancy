package com.leo.soulmancy.init;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.menu.SoulManipulatorMenu;
import com.leo.soulmancy.menu.SoulSmelteryMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Soulmancy.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<SoulManipulatorMenu>> SOUL_MANIPULATOR = MENUS.register(
        "soul_manipulator",
        () ->  IMenuTypeExtension.create(SoulManipulatorMenu::new)
    );

    public static final DeferredHolder<MenuType<?>, MenuType<SoulSmelteryMenu>> SOUL_SMELTERY = MENUS.register(
        "soul_smeltery",
        () ->  IMenuTypeExtension.create(SoulSmelteryMenu::new)
    );
}
