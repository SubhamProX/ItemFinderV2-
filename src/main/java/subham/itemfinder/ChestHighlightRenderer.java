package subham.itemfinder;

import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChestHighlightRenderer {

    public static void register() {
        LevelRenderEvents.END_MAIN.register(context -> {
            if (ChestFinder.matchedChests.isEmpty()) return;

            // Use the context's camera render state for position
            Vec3 cameraPos = context.levelState().cameraRenderState.pos;

            for (BlockPos pos : ChestFinder.matchedChests) {
                AABB box = new AABB(pos).inflate(0.02);

                context.poseStack().pushPose();
                context.poseStack().translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

                // FIX: Use context.consumers() directly instead of the old Minecraft.getInstance().renderBuffers()
                // If RenderType.lines() still throws an import error, use the full path or uppercase RenderType.LINES
                LevelRenderer.renderLineBox(
                    context.poseStack(),
                    context.consumers().getBuffer(net.minecraft.client.renderer.RenderType.lines()),
                    box,
                    0.0F, 1.0F, 0.0F, 1.0F   // Green color (R, G, B, Alpha)
                );

                context.poseStack().popPose();
            }
        });
    }
}
