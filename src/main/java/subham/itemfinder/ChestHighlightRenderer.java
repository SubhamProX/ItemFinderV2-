package subham.itemfinder;

import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer; // Moved here in recent versions
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChestHighlightRenderer {

    public static void register() {
        LevelRenderEvents.END_MAIN.register(context -> {
            if (ChestFinder.matchedChests.isEmpty()) return;

            // Updated camera position access matching modern Fabric LevelRenderEvents context
            Vec3 cameraPos = context.camera().getPosition();

            for (BlockPos pos : ChestFinder.matchedChests) {
                AABB box = new AABB(pos).inflate(0.025);

                context.matrixStack().pushPose();
                context.matrixStack().translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

                // LevelRenderer.renderLineBox moved to ShapeRenderer.renderLineBox
                ShapeRenderer.renderLineBox(
                    context.matrixStack(),
                    context.consumers().getBuffer(RenderType.lines()),
                    box,
                    0.0F, 1.0F, 0.0F, 0.9F
                );

                context.matrixStack().popPose();
            }
        });
    }
                    }
