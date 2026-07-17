package subham.itemfinder;

import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ChestHighlightRenderer {

    public static void register() {
        LevelRenderEvents.END_MAIN.register(context -> {
            if (ChestFinder.matchedChests.isEmpty()) return;
            if (context.bufferSource() == null) return;

            Vec3 camPos = context.camera().getPosition();

            for (BlockPos pos : ChestFinder.matchedChests) {
                AABB box = new AABB(pos).inflate(0.02).move(-camPos.x, -camPos.y, -camPos.z);
                LevelRenderer.renderLineBox(context.poseStack(),
                        context.bufferSource().getBuffer(RenderType.lines()),
                        box, 0.0F, 1.0F, 0.0F, 1.0F);
            }
        });
    }
}
