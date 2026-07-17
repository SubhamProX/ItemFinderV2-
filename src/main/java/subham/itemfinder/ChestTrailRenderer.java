package subham.itemfinder;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;

public class ChestTrailRenderer {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level == null || client.player == null) return;
            if (ChestFinder.matchedChests.isEmpty()) return;

            Vec3 playerPos = client.player.position().add(0, 1, 0);

            for (BlockPos pos : ChestFinder.matchedChests) {
                Vec3 target = Vec3.atCenterOf(pos);
                Vec3 direction = target.subtract(playerPos).normalize();
                Vec3 particlePos = playerPos.add(direction.scale(2));

                client.level.addParticle(ParticleTypes.END_ROD,
                        particlePos.x, particlePos.y, particlePos.z,
                        direction.x * 0.1, direction.y * 0.1, direction.z * 0.1);
            }
        });
    }
                                         }
