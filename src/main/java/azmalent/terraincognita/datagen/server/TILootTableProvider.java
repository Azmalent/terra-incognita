package azmalent.terraincognita.datagen.server;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.plant.SmallLilyPadBlock;
import azmalent.terraincognita.common.block.plant.SourBerryBushBlock;
import azmalent.terraincognita.common.block.woodset.TIVerticalSlabBlock;
import azmalent.terraincognita.common.block.woodset.TIVerticalSlabBlock.VerticalSlabType;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TILootTableProvider extends LootTableProvider {
    public TILootTableProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    @NotNull
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return List.of(
            Pair.of(TIBlockLoot::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        //NO-OP
    }

    private static class TIBlockLoot extends BlockLoot {
        private static final float[] SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

        @Override
        protected void addTables() {
            ModWoodTypes.VALUES.forEach(type -> {
                dropSelf(type.SAPLING.get());
                add(type.LEAVES.get(), block -> createLeavesDrops(block, type.SAPLING.get(), SAPLING_CHANCES));

                dropSelf(type.LOG.get());
                dropSelf(type.STRIPPED_LOG.get());
                dropSelf(type.WOOD.get());
                dropSelf(type.STRIPPED_WOOD.get());
                dropSelf(type.POST.get());
                dropSelf(type.STRIPPED_POST.get());
                dropSelf(type.PLANKS.get());
                dropSelf(type.VERTICAL_PLANKS.get());
                dropSelf(type.BOARDS.get());
                dropSelf(type.STAIRS.get());
                dropSelf(type.FENCE.get());
                dropSelf(type.FENCE_GATE.get());
                dropSelf(type.BUTTON.get());
                dropSelf(type.PRESSURE_PLATE.get());
                dropSelf(type.TRAPDOOR.get());
                dropSelf(type.LADDER.get());
                dropSelf(type.HEDGE.get());
                dropSelf(type.LEAF_CARPET.get());
                add(type.LEAF_PILE.get(), BlockLoot::createGlowLichenDrops);

                dropSelf(type.SIGN.get());
                dropOther(type.WALL_SIGN.get(), type.SIGN);

                add(type.CHEST.get(), BlockLoot::createNameableBlockEntityTable);
                add(type.TRAPPED_CHEST.get(), BlockLoot::createNameableBlockEntityTable);
                add(type.CABINET.get(), BlockLoot::createNameableBlockEntityTable);

                add(type.BEEHIVE.get(), BlockLoot::createBeeHiveDrop);

                add(type.SLAB.get(), BlockLoot::createSlabItemTable);
                add(type.VERTICAL_SLAB.get(), TIBlockLoot::createVerticalSlabItemTable);
                add(type.BOOKSHELF.get(), block -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
                add(type.DOOR.get(), BlockLoot::createDoorTable);
            });

            add(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get(), block -> createLeavesDrops(block, ModWoodTypes.APPLE.SAPLING.get(), SAPLING_CHANCES));
            add(ModWoodTypes.APPLE.BLOSSOMING_LEAF_PILE.get(), BlockLoot::createGlowLichenDrops);
            dropSelf(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET.get());
            dropSelf(ModWoodTypes.APPLE.BLOSSOMING_HEDGE.get());

            ModBlocks.SMALL_FLOWERS.forEach(flower -> dropSelf(flower.get()));
            ModBlocks.TALL_FLOWERS.forEach(flower -> add(flower.get(), createSinglePropConditionTable(flower.get(), DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)));
            ModBlocks.FLOWER_POTS.forEach((name, pot) -> dropPottedContents(pot.get()));

            ModBlocks.LOTUSES.forEach(lotus -> dropSelf(lotus.get()));
            ModBlocks.SWEET_PEAS.forEach(sweetPea -> add(sweetPea.get(), BlockLoot::createShearsOnlyDrop));

            add(ModBlocks.HANGING_MOSS.get(), BlockLoot::createShearsOnlyDrop);
            add(ModBlocks.CARIBOU_MOSS.get(), BlockLoot::createShearsOnlyDrop);
            add(ModBlocks.CARIBOU_MOSS_WALL.get(), BlockLoot::createShearsOnlyDrop);

            dropSelf(ModBlocks.CACTUS_FLOWER.get());
            dropSelf(ModBlocks.SMOOTH_CACTUS.get());
            dropSelf(ModBlocks.SWAMP_REEDS.get());
            add(ModBlocks.SMALL_LILY_PAD.get(), block -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, LootItem.lootTableItem(block).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SmallLilyPadBlock.LILY_PADS, 2)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SmallLilyPadBlock.LILY_PADS, 3)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SmallLilyPadBlock.LILY_PADS, 4))))))));

            dropSelf(ModBlocks.HAZELNUT_SACK.get());

            add(ModBlocks.SOUR_BERRY_BUSH.get(), block -> applyExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SourBerryBushBlock.AGE, 3))).add(LootItem.lootTableItem(ModItems.SOUR_BERRIES)).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SourBerryBushBlock.AGE, 2))).add(LootItem.lootTableItem(ModItems.SOUR_BERRIES)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
            dropSelf(ModBlocks.SOUR_BERRY_SPROUTS.get());
            dropSelf(ModBlocks.SOUR_BERRY_SACK.get());

            add(ModBlocks.FLOWERING_GRASS.get(), block -> createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
            dropSelf(ModBlocks.PEAT.get());
            dropOther(ModBlocks.TILLED_PEAT.get(), ModBlocks.PEAT.get());
            dropSelf(ModBlocks.MOSSY_GRAVEL.get());

            dropSelf(ModBlocks.SWAMP_REEDS_BUNDLE.get());
            dropSelf(ModBlocks.WICKER_LANTERN.get());
            dropSelf(ModBlocks.WICKER_MAT.get());

            dropSelf(ModBlocks.CALTROPS.get());
        }

        protected static LootTable.Builder createVerticalSlabItemTable(Block block) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(applyExplosionDecay(block, LootItem.lootTableItem(block)
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TIVerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE))))))
            );
        }

        @Override
        @NotNull
        @SuppressWarnings("ConstantConditions")
        protected Iterable<Block> getKnownBlocks() {
            //TODO: add proper loot tables
            var excludedBlocks = Sets.newHashSet(ModBlocks.APPLE.get(), ModBlocks.HAZELNUT.get(), ModBlocks.BASKET.get());
            return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block.getRegistryName().getNamespace().equals(TerraIncognita.MODID) && !excludedBlocks.contains(block)).collect(Collectors.toSet());
        }
    }
}
