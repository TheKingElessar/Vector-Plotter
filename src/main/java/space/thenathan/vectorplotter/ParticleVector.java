package space.thenathan.vectorplotter;

import net.minecraft.client.util.math.Vector3d;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

import java.awt.*;
import java.util.List;
import java.util.*;

public class ParticleVector extends Vector3d
{
    public static int nextID = 0;
    public static Map<ParticleVector, Integer> vectorList = new HashMap<>();
    public static double SEGMENT_MULTIPLIER = 0.5;
    
    public int id;
    public ServerWorld serverWorld;
    
    public Color color;
    
    List<Vector3d> segments = new ArrayList<>();
    
    public ParticleVector(double v1, double v2, double v3, ServerWorld serverWorld)
    {
        super(v1, v2, v3);
        
        this.serverWorld = serverWorld;
        
        this.id = nextID;
        nextID += 1;
        
        calculateSegments();
        vectorList.put(this, 0);
        
        Random r = new Random();
        int low = 0;
        int high = 256;
        this.color = new Color(r.nextInt(high - low) + low, r.nextInt(high - low) + low, r.nextInt(high - low) + low);
    }
    
    private void calculateSegments()
    {
        double magnitude = Math.sqrt(x * x + y * y + z * z);
        Vector3d unitVector = new Vector3d(0, 0, 0);
        unitVector.copy(this);
        unitVector.multiply(1D / magnitude);
        unitVector.multiply(0.1D);
        
        Vector3d added = new Vector3d(unitVector.x, unitVector.y, unitVector.z);
        segments.add(new Vector3d(0, 0, 0));
        
        double segmentMultiplier = SEGMENT_MULTIPLIER;
        while (true)
        {
            added = new Vector3d(unitVector.x * segmentMultiplier, unitVector.y * segmentMultiplier, unitVector.z * segmentMultiplier); // increase magnitude by this, not every value
            
            if (added.x > this.x || added.y > this.y || added.z > this.z)
            {
                break;
            }
            
            segments.add(added);
            segmentMultiplier += SEGMENT_MULTIPLIER;
        }
        segments.add(this);
    }
    
    public void spawnParticles()
    {
        for (Vector3d segment : segments)
        {
            serverWorld.spawnParticles(new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(this.color.getRGB())), 1), segment.x, segment.y, segment.z, 1, 0, 0, 0, 0);
        }
    }
}
