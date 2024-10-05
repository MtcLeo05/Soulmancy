package com.leo.soulmancy.mixin;

import com.leo.soulmancy.init.ModRecipes;
import com.leo.soulmancy.recipe.AnvilCrushRecipe;
import com.leo.soulmancy.util.Utils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin extends FallingBlock {

    public AnvilBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(
        remap = false,
        method = "onLand",
        at = @At(
            value = "TAIL"
        )
    )
    public void addRecipeProcessing(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock, CallbackInfo ci){
        if(!(level instanceof ServerLevel sLevel)) return;

        AABB area = new AABB(pos).inflate(1f);

        List<ItemEntity> itemEntities = sLevel.getEntitiesOfClass(ItemEntity.class, area);

        if(itemEntities.isEmpty()) return;

        RecipeManager manager = sLevel.getRecipeManager();

        List<RecipeHolder<AnvilCrushRecipe>> recipes = manager.getAllRecipesFor(ModRecipes.ANVIL_CRUSH_TYPE.get());

        for (ItemEntity itemEntity : itemEntities) {
            ItemStack potentialInput = itemEntity.getItem();
            Vec3 posBck = itemEntity.position();

            for (RecipeHolder<AnvilCrushRecipe> recipe : recipes) {
                AnvilCrushRecipe value = recipe.value();
                ItemStack inputStack = value.getInputStack();
                ItemStack outputStack = value.getOutputStack();

                int toSpawn = 0;

                while(Utils.isItemStackValid(potentialInput, inputStack)){
                    toSpawn++;
                    potentialInput.shrink(inputStack.getCount());
                }

                Containers.dropItemStack(level, posBck.x, posBck.y + 1, posBck.z,
                    new ItemStack(outputStack.getItem(), outputStack.getCount() * toSpawn)
                );
            }
        }
    }


    @Shadow
    public MapCodec<? extends FallingBlock> codec() {
        return null;
    }
}
