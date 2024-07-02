package azmalent.terraincognita.core.datagen.server;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.fruit.AbstractFruitBlock;
import azmalent.terraincognita.common.block.plant.SmallLilyPadBlock;
import azmalent.terraincognita.common.block.plant.SourBerryBushBlock;
import azmalent.terraincognita.common.block.woodset.TIVerticalSlabBlock;
import azmalent.terraincognita.common.block.woodset.TIVerticalSlabBlock.VerticalSlabType;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.core.registry.ModItems;
import azmalent.terraincognita.core.registry.ModWoodTypes;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.ItemPredicate;
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
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
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
    //This is why java devs need a wide monitor
    private static final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> TABLES = ImmutableList.of(
        Pair.of(TIBlockLoot::new, LootContextParamSets.BLOCK)
    );

    public TILootTableProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    @NotNull
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return TABLES;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {
        //NO-OP
    }

    @SuppressWarnings("DataFlowIssue")
    private static class TIBlockLoot extends BlockLoot {
        private static final float[] SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

        @Override
        @NotNull
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block.getRegistryName().getNamespace().equals(TerraIncognita.MODID))
                .filter(block -> block != ModBlocks.BASKET.get())
                .collect(Collectors.toSet());
        }

        @Override
        protected void addTables() {
            for (TIWoodType type : ModWoodTypes.VALUES) {
                addWoodTypeTables(type);
            }

            add(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get(), block -> createOakLeavesDrops(block, ModWoodTypes.APPLE.SAPLING.get(), SAPLING_CHANCES));
            dropSelf(ModWoodTypes.APPLE.BLOSSOMING_HEDGE.get());
            dropSelf(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET.get());

            add(ModBlocks.APPLE.get(), TIBlockLoot::createFruitItemTable);
            add(ModBlocks.HAZELNUT.get(), TIBlockLoot::createFruitItemTable);

            ModBlocks.FLOWERS.stream().map(BlockEntry::get).forEach(flower -> {
                if (flower instanceof DoublePlantBlock) {
                    add(flower, block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
                } else {
                    dropSelf(flower);
                }

                dropWhenPotted(flower);
            });

            ModBlocks.LOTUSES.stream().map(BlockEntry::get).forEach(this::dropSelf);
            ModBlocks.SWEET_PEAS.stream().map(BlockEntry::get).forEach(block -> add(block, BlockLoot::createShearsOnlyDrop));

            add(ModBlocks.CARIBOU_MOSS.get(), BlockLoot::createShearsOnlyDrop);
            add(ModBlocks.CARIBOU_MOSS_WALL.get(), block -> BlockLoot.createShearsOnlyDrop(ModBlocks.CARIBOU_MOSS));
            dropWhenPotted(ModBlocks.CARIBOU_MOSS.get());

            add(ModBlocks.HANGING_MOSS.get(), BlockLoot::createShearsOnlyDrop);

            dropSelf(ModBlocks.SMOOTH_CACTUS.get());

            dropSelf(ModBlocks.SWAMP_REEDS.get());
            dropWhenPotted(ModBlocks.SWAMP_REEDS.get());

            add(ModBlocks.SMALL_LILY_PAD.get(), block -> LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                applyExplosionDecay(ModBlocks.SMALL_LILY_PAD, LootItem.lootTableItem(block)
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SmallLilyPadBlock.LILY_PADS, 2))))
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SmallLilyPadBlock.LILY_PADS, 3))))
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SmallLilyPadBlock.LILY_PADS, 4))))))));

            add(ModBlocks.SOUR_BERRY_BUSH.get(), (block) -> applyExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SOUR_BERRY_BUSH.get()).setProperties(
                StatePropertiesPredicate.Builder.properties().hasProperty(SourBerryBushBlock.AGE, 3))).add(LootItem.lootTableItem(ModItems.SOUR_BERRIES))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                    .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.SOUR_BERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SourBerryBushBlock.AGE, 2))).add(LootItem.lootTableItem(ModItems.SOUR_BERRIES))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));

            dropSelf(ModBlocks.SOUR_BERRY_SPROUTS.get());

            dropSelf(ModBlocks.HAZELNUT_SACK.get());
            dropSelf(ModBlocks.SOUR_BERRY_SACK.get());

            add(ModBlocks.FLOWERING_GRASS.get(), block -> BlockLoot.createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
            dropSelf(ModBlocks.PEAT.get());
            dropOther(ModBlocks.TILLED_PEAT.get(), ModBlocks.PEAT);
            add(ModBlocks.MOSSY_GRAVEL.get(), (block) -> createSilkTouchDispatchTable(block, applyExplosionCondition(block,
                LootItem.lootTableItem(Items.FLINT)
                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F,0.25F, 1.0F))
                    .otherwise(LootItem.lootTableItem(block)))));

            dropSelf(ModBlocks.WICKER_MAT.get());
            dropSelf(ModBlocks.WICKER_LANTERN.get());
            dropSelf(ModBlocks.CALTROPS.get());
        }

        private void addWoodTypeTables(TIWoodType type) {
            dropSelf(type.SAPLING.get());
            dropWhenPotted(type.SAPLING.get());

            if (type == ModWoodTypes.APPLE) {
                add(type.LEAVES.get(), block -> createOakLeavesDrops(block, type.SAPLING.get(), SAPLING_CHANCES));
            } else {
                add(type.LEAVES.get(), block -> createLeavesDrops(block, type.SAPLING.get(), SAPLING_CHANCES));
            }

            dropSelf(type.LOG.get());
            dropSelf(type.STRIPPED_LOG.get());
            dropSelf(type.WOOD.get());
            dropSelf(type.STRIPPED_WOOD.get());

            dropSelf(type.PLANKS.get());
            dropSelf(type.STAIRS.get());
            add(type.SLAB.get(), BlockLoot::createSlabItemTable);
            dropSelf(type.FENCE.get());
            dropSelf(type.FENCE_GATE.get());
            dropSelf(type.SIGN.get());
            dropOther(type.WALL_SIGN.get(), type.SIGN);
            add(type.DOOR.get(), BlockLoot::createDoorTable);
            dropSelf(type.TRAPDOOR.get());
            dropSelf(type.BUTTON.get());
            dropSelf(type.PRESSURE_PLATE.get());
            add(type.CHEST.get(), BlockLoot::createNameableBlockEntityTable);
            add(type.TRAPPED_CHEST.get(), BlockLoot::createNameableBlockEntityTable);
            add(type.BEEHIVE.get(), BlockLoot::createBeeHiveDrop);

            dropSelf(type.VERTICAL_PLANKS.get());
            add(type.VERTICAL_SLAB.get(), TIBlockLoot::createVerticalSlabItemTable);
            add(type.BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
            dropSelf(type.LADDER.get());
            dropSelf(type.POST.get());
            dropSelf(type.STRIPPED_POST.get());
            dropSelf(type.HEDGE.get());
            dropSelf(type.LEAF_CARPET.get());

            dropSelf(type.BOARDS.get());
            add(type.LEAF_PILE.get(), TIBlockLoot::createLeafPileItemTable);

            add(type.CABINET.get(), BlockLoot::createNameableBlockEntityTable);
        }

        private static LootTable.Builder createVerticalSlabItemTable(Block slab) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(slab, LootItem.lootTableItem(slab)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(slab).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TIVerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE)))))));
        }

        private static LootTable.Builder createFruitItemTable(Block fruit) {
            return LootTable.lootTable().withPool(applyExplosionCondition(fruit, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(fruit).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(fruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractFruitBlock.AGE, 7))))));
        }

        private void dropWhenPotted(Block plant) {
            ResourceLocation pottedId = TerraIncognita.prefix("potted_" + plant.getRegistryName().getPath());
            Block potted = ForgeRegistries.BLOCKS.getValue(pottedId);

            dropPottedContents(potted);
        }

        private static LootTable.Builder createLeafPileItemTable(Block block) {
            return LootTable.lootTable().withPool(LootPool.lootPool().add((LootPoolEntryContainer.Builder)applyExplosionDecay(block, ((LootPoolSingletonContainer.Builder)LootItem.lootTableItem(block).when(BlockLoot.HAS_SHEARS)).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.EAST, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.WEST, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.NORTH, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.SOUTH, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.UP, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.DOWN, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true)))));
        }
    }
}
