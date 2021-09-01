package space.thenathan.vectorplotter;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.Map;

public class OnServerTick implements ServerTickEvents.EndTick
{
    public static final int COOLDOWN = 20;
    
    @Override
    public void onEndTick(MinecraftServer server)
    {
        for (Map.Entry<ParticleVector, Integer> entry : ParticleVector.vectorList.entrySet())
        {
            if (entry.getValue() <= 0)
            {
                entry.getKey().spawnParticles();
                ParticleVector.vectorList.put(entry.getKey(), COOLDOWN);
            }
            else
            {
                ParticleVector.vectorList.put(entry.getKey(), entry.getValue() - 1);
            }
        }
    }
}
