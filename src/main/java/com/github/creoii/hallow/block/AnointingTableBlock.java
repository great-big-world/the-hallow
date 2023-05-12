package com.github.creoii.hallow.block;

import com.github.creoii.creolib.api.util.block.CBlockSettings;
import com.github.creoii.hallow.main.registry.HallowStats;
import com.github.creoii.hallow.screen.AnointingScreenHandler;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class AnointingTableBlock extends HorizontalFacingBlock {
    private static final VoxelShape BODY = Block.createCuboidShape(0d, 2d, 0d, 16d, 12d, 16d);
    private static final VoxelShape LEG_1 = Block.createCuboidShape(0d, 0d, 0d, 3d, 2d, 3d);
    private static final VoxelShape LEG_2 = Block.createCuboidShape(0d, 0d, 13d, 3d, 2d, 16d);
    private static final VoxelShape LEG_3 = Block.createCuboidShape(13d, 0d, 0d, 16d, 2d, 3d);
    private static final VoxelShape LEG_4 = Block.createCuboidShape(13d, 0d, 13d, 16d, 2d, 16d);
    private static final VoxelShape SHAPE = VoxelShapes.union(BODY, LEG_1, LEG_2, LEG_3, LEG_4);
    public static final DirectionProperty HORIZONTAL_FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVATED = BooleanProperty.of("activated");

    public AnointingTableBlock() {
        super(CBlockSettings.of(Material.STONE, MapColor.PURPLE).requiresTool().strength(9f, 1200f).luminance(state -> state.get(ACTIVATED) ? 7 : 0));
        setDefaultState(getStateManager().getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(ACTIVATED, false));
    }

    @SuppressWarnings("deprecation")
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, state.with(ACTIVATED, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            player.incrementStat(HallowStats.INTERACT_WITH_ANOINTING_TABLE);
            return ActionResult.CONSUME;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new AnointingScreenHandler(i, playerInventory, ScreenHandlerContext.create(world, pos)), Text.translatable("container.anoint"));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, ACTIVATED);
    }
}
