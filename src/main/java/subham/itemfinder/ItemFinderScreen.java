package subham.itemfinder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemFinderScreen extends Screen {

    private EditBox searchBox;
    private final List<Item> matchedItems = new ArrayList<>();

    private static final int OVERLAY_COLOR = 0xBF000000;
    private static final int TITLE_COLOR = 0xFFFDB813;
    private static final int BOX_BG_COLOR = 0xFF4A4A4A;
    private static final int BOX_BORDER_COLOR = 0xFF000000;

    public ItemFinderScreen() {
        super(Component.literal("Item Finder"));
    }

    @Override
    protected void init() {
        int boxWidth = 200;
        int boxHeight = 20;
        int startX = this.width / 2 - 130;
        int startY = this.height / 4 + 30;

        this.searchBox = new EditBox(this.font, startX, startY, boxWidth, boxHeight, Component.literal("Search"));
        this.searchBox.setResponder(this::onSearchChanged);
        this.addRenderableWidget(this.searchBox);

        Button goButton = Button.builder(Component.literal("GO"), (btn) -> onSearchChanged(this.searchBox.getValue()))
                .bounds(startX + boxWidth + 10, startY, 50, boxHeight)
                .build();
        this.addRenderableWidget(goButton);
    }

    private void onSearchChanged(String query) {
        matchedItems.clear();
        if (query == null || query.isEmpty()) return;

        String lower = query.toLowerCase();
        for (Item item : BuiltInRegistries.ITEM) {
            String name = item.getDefaultInstance().getHoverName().getString().toLowerCase();
            if (name.contains(lower)) {
                matchedItems.add(item);
            }
        }
        // Ab yahan koi auto-scan nahi — sirf list dikhegi, scan tabhi hoga jab click karoge
    }

    private int getListX() {
        return this.width / 2 - 130;
    }

    private int getListStartY() {
        return this.height / 4 + 75;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, this.width, this.height, OVERLAY_COLOR);

        int wrapX = this.width / 2 - 140;
        int wrapY = this.height / 4 + 20;
        graphics.fill(wrapX, wrapY, wrapX + 280, wrapY + 40, BOX_BG_COLOR);
        graphics.outline(wrapX, wrapY, 280, 40, BOX_BORDER_COLOR);

        super.extractRenderState(graphics, mouseX, mouseY, delta);

        graphics.text(this.font, "ITEM FINDER", this.width / 2 - this.font.width("ITEM FINDER") / 2,
                this.height / 4 - 10, TITLE_COLOR, true);

        int listX = getListX();
        int listY = this.height / 4 + 60;
        graphics.text(this.font, matchedItems.size() + " items match - click ek item pe select karne ke liye", listX, listY, 0xFFFFFFFF, true);

        int startY = getListStartY();
        for (int i = 0; i < Math.min(matchedItems.size(), 12); i++) {
            int rowY = startY + i * 12;
            // Mouse hover pe highlight
            boolean hovering = mouseX >= listX && mouseX <= listX + 220 && mouseY >= rowY - 1 && mouseY <= rowY + 10;
            int color = hovering ? 0xFFFFFF00 : 0xFFFFFFFF;
            graphics.text(this.font, matchedItems.get(i).getDefaultInstance().getHoverName().getString(), listX, rowY, color, true);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int listX = getListX();
        int startY = getListStartY();
        for (int i = 0; i < Math.min(matchedItems.size(), 12); i++) {
            int rowY = startY + i * 12;
            if (mouseX >= listX && mouseX <= listX + 220 && mouseY >= rowY - 1 && mouseY <= rowY + 10) {
                Item selected = matchedItems.get(i);
                String name = selected.getDefaultInstance().getHoverName().getString();
                this.searchBox.setValue(name); // autofill: search box mein naam bhar do
                ChestFinder.scanForItem(selected); // sirf isi exact item ko scan karo
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    }
