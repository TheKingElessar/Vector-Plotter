package space.thenathan.vectorplotter;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.argument;

public class VectorPlotter implements ModInitializer
{
    
    @Override
    public void onInitialize()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
                {
                    dispatcher.register(CommandManager.literal("plot").then(argument("v1", DoubleArgumentType.doubleArg()).then(argument("v2", DoubleArgumentType.doubleArg()).then(argument("v3", DoubleArgumentType.doubleArg()).executes(VectorPlotter.this::plotVector)))));
                }
        );
        
        ServerTickEvents.END_SERVER_TICK.register(new OnServerTick());
    }
    
    private int plotVector(CommandContext<ServerCommandSource> objectCommandContext) throws CommandSyntaxException
    {
        Entity sender = objectCommandContext.getSource().getEntity();
        if (sender instanceof ServerPlayerEntity serverPlayerEntity)
        {
            double v1 = DoubleArgumentType.getDouble(objectCommandContext, "v1");
            double v2 = DoubleArgumentType.getDouble(objectCommandContext, "v2");
            double v3 = DoubleArgumentType.getDouble(objectCommandContext, "v3");
            
            serverPlayerEntity.sendMessage(new LiteralText("§3[§fVECTOR PLOTTER§3] Plotting vector!"), false);
            
            ParticleVector particleVector = new ParticleVector(v1, v2, v3, serverPlayerEntity.getServerWorld());
            particleVector.spawnParticles();
            
            serverPlayerEntity.sendMessage(new LiteralText("§3Vector: <§f%s§3, §f%s§3, §f%s§3> as ID %s".formatted(Double.toString(Math.round(v1 * 100.0) / 100.0), Double.toString(Math.round(v2 * 100.0) / 100.0), Double.toString(Math.round(v3 * 100.0) / 100.0), particleVector.id)), false);
            
            return 1;
        }
        
        return 0;
    }
    
}
