package subham.itemfinder;

import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChestHighlightRenderer {

    public static void register() {
        LevelRenderEvents.END_MAIN.register(context -> {
            if (ChestFinder.matchedChests.isEmpty()) return;

            // Fetch the proper camera translation coordinates from the context's camera state
            Vec3 cameraPos = context.camera().getPosition();

            for (BlockPos pos : ChestFinder.matchedChests) {
                AABB box = new AABB(pos).inflate(0.025);

                context.poseStack().pushPose();
                context.poseStack().translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

                // Replaced broken LevelRenderer/ShapeRenderer imports with DebugRenderer's functional line box tool
                DebugRenderer.renderFilledBox(
                    context.poseStack(),
                    context.bufferSource(),
                    box,
                    0.0F, 1.0F, 0.0F, 0.4F // Using filled box with transparency for high visibility, or line rendering below
                );

                context.poseStack().popPose();
            }
        });
    }
                    }
