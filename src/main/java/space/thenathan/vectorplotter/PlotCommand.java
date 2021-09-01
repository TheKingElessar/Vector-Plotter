package space.thenathan.vectorplotter;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class PlotCommand implements Command<Object>
{
    
    @Override
    public int run(CommandContext<Object> context) throws CommandSyntaxException
    {
        return 0;
    }
}