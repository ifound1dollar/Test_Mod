package net.dollar.testmod.datagen;

import com.mojang.logging.LogUtils;
import net.dollar.testmod.TestMod;
import net.dollar.testmod.block.ModBlocks;
import net.dollar.testmod.item.ModItems;
import net.dollar.testmod.util.ModLegacySmithingRecipeBuilder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    private enum ToolType { AXE, HOE, PICKAXE, SHOVEL, SWORD }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        //BLASTING ONLY RECIPES ARE POSSIBLE BY NOT DEFINING oreSmelting
        oreSmelting(consumer, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot" );
        oreBlasting(consumer, List.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot" );
        oreSmelting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 200, "tin_ingot" );
        oreBlasting(consumer, List.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC,
                ModItems.TIN_INGOT.get(), 0.7f, 100, "tin_ingot" );

        oreBlasting(consumer, List.of(ModBlocks.TUNGSTEN_ORE.get()), RecipeCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(), 1.0f, 100, "tungsten_ingot" );
        oreBlasting(consumer, List.of(ModItems.RAW_TUNGSTEN.get()), RecipeCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(), 1.0f, 100, "tungsten_ingot" );


        //NOTE: FIRST IS FOR BLOCK->ITEM, SECOND IS FOR ITEM->BLOCK
        fourBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY_SHARD.get(),
                RecipeCategory.MISC, ModBlocks.RUBY_BLOCK.get());
        fourBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.SAPPHIRE_SHARD.get(),
                RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get());

        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.TIN_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.TIN_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.BRONZE_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.BRONZE_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.STEEL_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.STEEL_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.TUNGSTEN_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.TUNGSTEN_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ModItems.TUNGSTEN_CARBIDE_INGOT.get(),
                RecipeCategory.MISC, ModBlocks.TUNGSTEN_CARBIDE_BLOCK.get());

        //something
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.INFUSED_GEMSTONE.get(), 1)
                .requires(Items.AMETHYST_SHARD)
                .requires(Items.EMERALD)
                .requires(ModItems.RUBY_SHARD.get())
                .requires(ModItems.SAPPHIRE_SHARD.get())
                .requires(ModItems.NAMELESS_INFUSION_ITEM.get())
                .unlockedBy("has_nameless_infusion_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.NAMELESS_INFUSION_ITEM.get()).build()))
                .save(consumer);

        //armors and tools
        //region BRONZE ARMOR, TOOLS
        armorRecipeBuilder(consumer, EquipmentSlot.HEAD, ModItems.BRONZE_INGOT, ModItems.BRONZE_HELMET,
                "has_bronze_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.CHEST, ModItems.BRONZE_INGOT, ModItems.BRONZE_CHESTPLATE,
                "has_bronze_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.LEGS, ModItems.BRONZE_INGOT, ModItems.BRONZE_LEGGINGS,
                "has_bronze_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.FEET, ModItems.BRONZE_INGOT, ModItems.BRONZE_BOOTS,
                "has_bronze_ingot");

        toolRecipeBuilder(consumer, ToolType.PICKAXE, ModItems.BRONZE_INGOT, ModItems.BRONZE_PICKAXE,
                "has_bronze_ingot");
        //endregion

        //region GILDED BRONZE ARMOR, TOOLS (smithing)
        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_HELMET.get(), Items.GOLD_INGOT,
                RecipeCategory.COMBAT, ModItems.GILDED_BRONZE_HELMET.get(), "has_gold_ingot");
        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_CHESTPLATE.get(), Items.GOLD_INGOT,
                RecipeCategory.COMBAT, ModItems.GILDED_BRONZE_CHESTPLATE.get(), "has_gold_ingot");
        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_LEGGINGS.get(), Items.GOLD_INGOT,
                RecipeCategory.COMBAT, ModItems.GILDED_BRONZE_LEGGINGS.get(), "has_gold_ingot");
        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_BOOTS.get(), Items.GOLD_INGOT,
                RecipeCategory.COMBAT, ModItems.GILDED_BRONZE_BOOTS.get(), "has_gold_ingot");

        legacySmithingRecipeBuilder(consumer, ModItems.BRONZE_PICKAXE.get(), Items.GOLD_INGOT,
                RecipeCategory.TOOLS, ModItems.GILDED_BRONZE_PICKAXE.get(), "has_gold_ingot");
        //endregion

        //region STEEL ARMOR, TOOLS
        armorRecipeBuilder(consumer, EquipmentSlot.HEAD, ModItems.STEEL_INGOT, ModItems.STEEL_HELMET,
                "has_steel_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.CHEST, ModItems.STEEL_INGOT, ModItems.STEEL_CHESTPLATE,
                "has_steel_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.LEGS, ModItems.STEEL_INGOT, ModItems.STEEL_LEGGINGS,
                "has_steel_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.FEET, ModItems.STEEL_INGOT, ModItems.STEEL_BOOTS,
                "has_steel_ingot");
        //endregion

        //region TUNGSTEN ARMOR, TOOLS
        armorRecipeBuilder(consumer, EquipmentSlot.HEAD, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_HELMET,
                "has_tungsten_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.CHEST, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_CHESTPLATE,
                "has_tungsten_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.LEGS, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_LEGGINGS,
                "has_tungsten_ingot");
        armorRecipeBuilder(consumer, EquipmentSlot.FEET, ModItems.TUNGSTEN_INGOT, ModItems.TUNGSTEN_BOOTS,
                "has_tungsten_ingot");
        //endregion

        //region TUNGSTEN-CARBIDE ARMOR, TOOLS (smithing)
        legacySmithingRecipeBuilder(consumer, ModItems.TUNGSTEN_HELMET.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(),
                RecipeCategory.COMBAT, ModItems.TUNGSTEN_CARBIDE_HELMET.get(), "has_tungsten_carbide_ingot");
        legacySmithingRecipeBuilder(consumer, ModItems.TUNGSTEN_CHESTPLATE.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(),
                RecipeCategory.COMBAT, ModItems.TUNGSTEN_CARBIDE_CHESTPLATE.get(), "has_tungsten_carbide_ingot");
        legacySmithingRecipeBuilder(consumer, ModItems.TUNGSTEN_LEGGINGS.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(),
                RecipeCategory.COMBAT, ModItems.TUNGSTEN_CARBIDE_LEGGINGS.get(), "has_tungsten_carbide_ingot");
        legacySmithingRecipeBuilder(consumer, ModItems.TUNGSTEN_BOOTS.get(), ModItems.TUNGSTEN_CARBIDE_INGOT.get(),
                RecipeCategory.COMBAT, ModItems.TUNGSTEN_CARBIDE_BOOTS.get(), "has_tungsten_carbide_ingot");
        //endregion

        //region INFUSED DIAMOND ARMOR, TOOLS (smithing)
        legacySmithingRecipeBuilder(consumer, Items.DIAMOND_HELMET, ModItems.INFUSED_GEMSTONE.get(),
                RecipeCategory.COMBAT, ModItems.INFUSED_DIAMOND_HELMET.get(), "has_infused_gemstone");
        legacySmithingRecipeBuilder(consumer, Items.DIAMOND_HELMET, ModItems.INFUSED_GEMSTONE.get(),
                RecipeCategory.COMBAT, ModItems.INFUSED_DIAMOND_CHESTPLATE.get(), "has_infused_gemstone");
        legacySmithingRecipeBuilder(consumer, Items.DIAMOND_HELMET, ModItems.INFUSED_GEMSTONE.get(),
                RecipeCategory.COMBAT, ModItems.INFUSED_DIAMOND_LEGGINGS.get(), "has_infused_gemstone");
        legacySmithingRecipeBuilder(consumer, Items.DIAMOND_HELMET, ModItems.INFUSED_GEMSTONE.get(),
                RecipeCategory.COMBAT, ModItems.INFUSED_DIAMOND_BOOTS.get(), "has_infused_gemstone");
        //endregion




        //BELOW IS HOW TO MANUALLY CREATE SHAPELESS RECIPES (third shapeless() param is quantity)
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RUBY_SHARD.get(), 9)
//                .requires(ModBlocks.RUBY_BLOCK.get())
//                .unlockedBy("has_ruby_block", inventoryTrigger(ItemPredicate.Builder.item()
//                        .of(ModBlocks.RUBY_BLOCK.get()).build()))
//                .save(consumer);

        //BELOW IS HOW TO MANUALLY CREATE SHAPED RECIPES (third shaped() param is quantity)
//        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BRONZE_PICKAXE.get(), 1)
//                .define('d', ModItems.BRONZE_INGOT.get())
//                .define('i', Items.STICK)
//                .pattern("ddd")
//                .pattern(" i ")
//                .pattern(" i ")
//                .unlockedBy("has_bronze_ingot", inventoryTrigger(ItemPredicate.Builder.item()
//                        .of(ModItems.BRONZE_INGOT.get()).build()))
//                .save(consumer);
    }


    /**
     * Helper to automatically generate shaped recipes for the four armor slots
     * @param consumer Consumer of FinishedRecipe
     * @param slot This armor piece's equipment slot
     * @param ingredient Crafting ingredient
     * @param result Crafting result
     * @param unlockedByString Tag used for unlocking crafting recipe
     */
    private void armorRecipeBuilder(Consumer<FinishedRecipe> consumer, EquipmentSlot slot,
                                    RegistryObject<Item> ingredient, RegistryObject<Item> result,
                                    String unlockedByString) {
        if (slot == EquipmentSlot.HEAD) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get(), 1)  //third param is quantity
                    .define('d', ingredient.get())
                    .pattern("ddd")
                    .pattern("d d")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (slot == EquipmentSlot.CHEST) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get(), 1)
                    .define('d', ingredient.get())
                    .pattern("d d")
                    .pattern("ddd")
                    .pattern("ddd")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (slot == EquipmentSlot.LEGS) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get(), 1)
                    .define('d', ingredient.get())
                    .pattern("ddd")
                    .pattern("d d")
                    .pattern("d d")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (slot == EquipmentSlot.FEET) {
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get(), 1)
                    .define('d', ingredient.get())
                    .pattern("d d")
                    .pattern("d d")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else {
            LogUtils.getLogger().debug("Invalid EquipmentSlot provided to armorRecipeBuilder.");
        }
    }

    /**
     * Helper to automatically generate shaped recipes for the five vanilla tool types
     * @param consumer Consumer of FinishedRecipe
     * @param toolType Type of tool defined in ToolType enum
     * @param ingredient Crafting ingredient
     * @param result Crafting result
     * @param unlockedByString Tag used for unlocking crafting recipe
     */
    private void toolRecipeBuilder(Consumer<FinishedRecipe> consumer, ToolType toolType,
                                   RegistryObject<Item> ingredient, RegistryObject<Item> result,
                                   String unlockedByString) {
        if (toolType == ToolType.AXE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)  //third param is quantity
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("dd")
                    .pattern("di")
                    .pattern(" i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (toolType == ToolType.HOE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("dd")
                    .pattern(" i")
                    .pattern(" i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (toolType == ToolType.PICKAXE) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("ddd")
                    .pattern(" i ")
                    .pattern(" i ")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (toolType == ToolType.SHOVEL) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("d")
                    .pattern("i")
                    .pattern("i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        } else if (toolType == ToolType.SWORD) {
            ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get(), 1)
                    .define('d', ingredient.get())
                    .define('i', Items.STICK)
                    .pattern("d")
                    .pattern("d")
                    .pattern("i")
                    .unlockedBy(unlockedByString, inventoryTrigger(ItemPredicate.Builder.item()
                            .of(ingredient.get()).build()))
                    .save(consumer);
        }
    }


    /**
     * Helper to automatically generate LEGACY smithing recipes for versions before 1.20
     * @param consumer Consumer of FinishedRecipe
     * @param upgradeItem Item being upgraded
     * @param ingredient Ingredient to upgrade item
     * @param category Recipe category
     * @param result Smithing result
     * @param unlockedByString Tag used for unlocking smithing recipe
     */
    private void legacySmithingRecipeBuilder(Consumer<FinishedRecipe> consumer, Item upgradeItem,
                                                      Item ingredient, RecipeCategory category, Item result,
                                                      String unlockedByString) {
        ModLegacySmithingRecipeBuilder.smithing(Ingredient.of(upgradeItem), Ingredient.of(ingredient),
                category, result).unlocks(unlockedByString, has(ingredient))
                .save(consumer, new ResourceLocation(TestMod.MOD_ID, getItemName(result)) + "_smithing");
    }

    /**
     * Helper to automatically generate smithing recipes
     * @param consumer Consumer of FinishedRecipe
     * @param template Required upgrade template
     * @param upgradeItem Item being upgraded
     * @param ingredient Ingredient to upgrade item
     * @param category Recipe category
     * @param result Smithing result
     * @param unlockedByString Tag used for unlocking smithing recipe
     */
    private void smithingRecipeBuilder(Consumer<FinishedRecipe> consumer, Item template, Item upgradeItem,
                                                Item ingredient, RecipeCategory category, Item result,
                                                String unlockedByString) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(upgradeItem),
                        Ingredient.of(ingredient), category, result)
                .unlocks(unlockedByString, has(ingredient))
                .save(consumer, new ResourceLocation(TestMod.MOD_ID, getItemName(result)) + "_smithing");
    }


    protected static void fourBlockStorageRecipes(Consumer<FinishedRecipe> p_249580_, RecipeCategory p_251203_, ItemLike p_251689_, RecipeCategory p_251376_, ItemLike p_248771_) {
        fourBlockStorageRecipes(p_249580_, p_251203_, p_251689_, p_251376_, p_248771_, getSimpleRecipeName(p_248771_), (String)null, getSimpleRecipeName(p_251689_), (String)null);
    }
    protected static void fourBlockStorageRecipes(Consumer<FinishedRecipe> p_250423_, RecipeCategory p_250083_, ItemLike p_250042_, RecipeCategory p_248977_, ItemLike p_251911_, String p_250475_, @Nullable String p_248641_, String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 4).requires(p_251911_).group(p_250414_).unlockedBy(getHasName(p_251911_), has(p_251911_)).save(p_250423_, new ResourceLocation(TestMod.MOD_ID, p_252237_));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_).define('#', p_250042_).pattern("##").pattern("##").group(p_248641_).unlockedBy(getHasName(p_250042_), has(p_250042_)).save(p_250423_, new ResourceLocation(TestMod.MOD_ID, p_250475_));
    }





    //region COPIED METHODS TO FIX RECIPES GENERATING UNDER MINECRAFT INSTEAD OF MOD

    //To fix generation issue: Copy over all methods used in this class and then edit all instances
    // of 'new ResourceLocation()' to first take TestMod.MOD_ID, THEN the actual value.
    //  ex. 'new ResourceLocation(p_252237_)' -> 'new ResourceLocation(TestMod.MOD_ID, p_252237_)'
    protected static void oreSmelting(Consumer<FinishedRecipe> p_250654_, List<ItemLike> p_250172_, RecipeCategory p_250588_, ItemLike p_251868_, float p_250789_, int p_252144_, String p_251687_) {
        oreCooking(p_250654_, RecipeSerializer.SMELTING_RECIPE, p_250172_, p_250588_, p_251868_, p_250789_, p_252144_, p_251687_, "_from_smelting");
    }
    protected static void oreBlasting(Consumer<FinishedRecipe> p_248775_, List<ItemLike> p_251504_, RecipeCategory p_248846_, ItemLike p_249735_, float p_248783_, int p_250303_, String p_251984_) {
        oreCooking(p_248775_, RecipeSerializer.BLASTING_RECIPE, p_251504_, p_248846_, p_249735_, p_248783_, p_250303_, p_251984_, "_from_blasting");
    }
    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        for(ItemLike itemlike : p_249619_) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), p_251154_, p_250066_, p_251871_, p_251316_, p_251817_).group(p_251450_).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(p_250791_, new ResourceLocation(TestMod.MOD_ID, getItemName(p_250066_)) + p_249236_ + "_" + getItemName(itemlike));
        }
        //must use new ResourceLocation in this method before getItemName(p_250066_) so it uses mod's package
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_249580_, RecipeCategory p_251203_, ItemLike p_251689_, RecipeCategory p_251376_, ItemLike p_248771_) {
        nineBlockStorageRecipes(p_249580_, p_251203_, p_251689_, p_251376_, p_248771_, getSimpleRecipeName(p_248771_), (String)null, getSimpleRecipeName(p_251689_), (String)null);
    }
    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_250423_, RecipeCategory p_250083_, ItemLike p_250042_, RecipeCategory p_248977_, ItemLike p_251911_, String p_250475_, @Nullable String p_248641_, String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 9).requires(p_251911_).group(p_250414_).unlockedBy(getHasName(p_251911_), has(p_251911_)).save(p_250423_, new ResourceLocation(TestMod.MOD_ID, p_252237_));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_).define('#', p_250042_).pattern("###").pattern("###").pattern("###").group(p_248641_).unlockedBy(getHasName(p_250042_), has(p_250042_)).save(p_250423_, new ResourceLocation(TestMod.MOD_ID, p_250475_));
    }

    //endregion
}
