package subham.itemfinder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ItemFinderScreen extends Screen {

    public ItemFinderScreen() {
        super(Component.literal("Item Finder"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
