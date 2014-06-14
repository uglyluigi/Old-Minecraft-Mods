package spacewalken.mods.xailite.common;

import spacewalken.mods.xailite.Xailite;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

/**
 * Created with IntelliJ IDEA CE
 * Date of creation: 5/20/13
 */
public class XailiteOreGenerator implements IWorldGenerator
{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        switch (world.provider.dimensionId)
        {
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 1:
                break;
        }
    }

    private void generateNether(World world, Random random, int i, int j)
    {
        for (int k = 0; k < 1; k++)
        {
            (new WorldGenMinable(Xailite.xailiteBlock.blockID, 3, 0)).generate(world, random, i + random.nextInt(48), random.nextInt(255), j + random.nextInt(48));
        }
    }


    private void generateSurface(World world, Random random, int i, int j)
    {
        for (int k = 0; k < 3; ++k)
        {
            (new WorldGenMinable(Xailite.xailiteBlock.blockID, 2, 0)).generate(world, random, i + random.nextInt(48), random.nextInt(15), j + random.nextInt(48));
        }
    }

}
