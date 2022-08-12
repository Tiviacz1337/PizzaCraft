package com.tiviacz.pizzacraft.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class RenderUtils
{
    public static double[] getPosRandomAboveBlockHorizontal(Level level, BlockPos pos)
    {
        double d0 = 0.5D;
        double d5 = 0.5D - d0;
        double d6 = (double)pos.getX() + d5 + level.random.nextDouble() * d0 * 2.0D;
        //double d7 = (double)pos.getY() + world.rand.nextDouble() * y;
        double d8 = (double)pos.getZ() + d5 + level.random.nextDouble() * d0 * 2.0D;

        return new double[] {d6, d8};
    }

   /* public static BlockHitResult getBlockHitResult(Player player, Level level)
    {
        float f = player.xRot;
        float f1 = player.yRot;
        Vec3 vec3 = player.getEyePosition(1.0F);
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);

        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    } */
}