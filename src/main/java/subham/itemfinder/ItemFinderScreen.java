package subham.itemfinder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ItemFinderScreen extends Screen {

    private EditBox searchBox;

    public ItemFinderScreen() {
        super(Component.literal("Item Finder"));
    }

    @Override
    protected void init() {
        this.searchBox = new EditBox(
            this.font,
            this.width / 2 - 100,
            this.height / 4,
            200, 20,
            Component.literal("Search items...")
        );
        this.searchBox.setHint(Component.literal("Search items..."));
        this.addRenderableWidget(this.searchBox);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
                     }
