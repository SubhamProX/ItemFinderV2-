package subham.itemfinder;

import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChestHighlightRenderer {

    public static void register() {
        LevelRenderEvents.END_MAIN.register(context -> {
            if (ChestFinder.matchedChests.isEmpty()) return;

            Vec3 cameraPos = context.levelState().cameraRenderState.pos;  // New way to get camera

            for (BlockPos pos : ChestFinder.matchedChests) {
                AABB box = new AABB(pos).inflate(0.02);

                // Translate relative to camera for correct positioning
                context.poseStack().pushPose();
                context.poseStack().translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

                LevelRenderer.renderLineBox(
                    context.poseStack(),
                    context.bufferSource().getBuffer(net.minecraft.client.renderer.RenderType.lines()),  // Still works
                    box,
                    0.0F, 1.0F, 0.0F, 1.0F  // Green
                );

                context.poseStack().popPose();
            }
        });
    }
}
