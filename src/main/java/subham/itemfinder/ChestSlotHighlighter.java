package subham.itemfinder;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

public class ChestSlotHighlighter {

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof AbstractContainerScreen<?> containerScreen && ChestFinder.currentSearchItem != null) {
                ScreenEvents.afterRender(screen).register((scr, graphics, mouseX, mouseY, tickDelta) -> {
                    for (Slot slot : containerScreen.getMenu().slots) {
                        if (!slot.getItem().isEmpty() && slot.getItem().is(ChestFinder.currentSearchItem)) {
                            int x = containerScreen.getGuiLeft() + slot.x;
                            int y = containerScreen.getGuiTop() + slot.y;
                            graphics.fill(x, y, x + 16, y + 16, 0x8000FF00);
                        }
                    }
                });
            }
        });
    }
}
