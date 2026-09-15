package subham.itemfinder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemFinderScreen extends Screen {

    private EditBox searchBox;
    private final List<Item> matchedItems = new ArrayList<>();

    private int scrollOffset = 0;
    private static final int ROW_HEIGHT = 20;
    private static final int VISIBLE_ROWS = 8;
    private static final int ICON_SIZE = 16;

    private int listX, listY, listWidth, listHeight;


    private static final int OVERLAY_COLOR = 0xC0000814;
    private static final int TITLE_COLOR = 0xFFFDB813;
    private static final int PANEL_BG = 0xE6202634;
    private static final int PANEL_BORDER = 0xFF3A4258;
    private static final int ROW_BG_A = 0x662A3040;
    private static final int ROW_BG_B = 0x66222633;
    private static final int ROW_HOVER = 0x8036507A;
    private static final int SCROLLBAR_TRACK = 0x662A3040;
    private static final int SCROLLBAR_THUMB = 0xFF5EB0EF;
    private static final int TEXT_COLOR = 0xFFE8E6E1;
    private static final int SUBTEXT_COLOR = 0xFF9A9690;

    public ItemFinderScreen() {
        super(Component.literal("Item Finder"));
    }

    @Override
    protected void init() {
        int panelWidth = 280;
        int boxWidth = 200;
        int boxHeight = 20;
        int startX = this.width / 2 - panelWidth / 2 + 10;
        int startY = this.height / 4 + 34;

        listX = this.width / 2 - panelWidth / 2 + 10;
        listY = startY + 46;
        listWidth = panelWidth - 20;
        listHeight = VISIBLE_ROWS * ROW_HEIGHT;

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
        scrollOffset = 0;

        if (query == null || query.isEmpty()) return;

        String lower = query.toLowerCase();
        for (Item item : BuiltInRegistries.ITEM) {
            String name = item.getDefaultInstance().getHoverName().getString().toLowerCase();
            if (name.contains(lower)) {
                matchedItems.add(item);
            }
        }
    }

    private int maxScroll() {
        return Math.max(0, matchedItems.size() - VISIBLE_ROWS);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (mouseX >= listX && mouseX <= listX + listWidth && mouseY >= listY && mouseY <= listY + listHeight) {
            scrollOffset -= (int) Math.signum(verticalAmount);
            scrollOffset = Math.max(0, Math.min(scrollOffset, maxScroll()));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseX >= listX && mouseX <= listX + listWidth && mouseY >= listY && mouseY <= listY + listHeight) {
            int row = (int) ((mouseY - listY) / ROW_HEIGHT) + scrollOffset;
            if (row >= 0 && row < matchedItems.size()) {
                Item item = matchedItems.get(row);
                String itemName = item.getDefaultInstance().getHoverName().getString();
                this.searchBox.setValue(itemName);
                ChestFinder.scanForItem(item);
                Minecraft.getInstance().setScreen(null);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, this.width, this.height, OVERLAY_COLOR);

        int panelWidth = 280;
        int panelX = this.width / 2 - panelWidth / 2;
        int panelY = this.height / 4 - 20;
        int panelHeight = listY + listHeight - panelY + 16;

        graphics.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, PANEL_BG);
        graphics.outline(panelX, panelY, panelWidth, panelHeight, PANEL_BORDER);

        String title = "ITEM FINDER";
        graphics.text(this.font, title, this.width / 2 - this.font.width(title) / 2, panelY + 8, TITLE_COLOR, true);

        super.extractRenderState(graphics, mouseX, mouseY, delta);

        String status = matchedItems.isEmpty()
                ? "Type to search items"
                : matchedItems.size() + " items match - click one, or scroll for more";
        graphics.text(this.font, status, listX, listY - 12, SUBTEXT_COLOR, false);

        
        graphics.enableScissor(listX, listY, listX + listWidth, listY + listHeight);
        graphics.fill(listX, listY, listX + listWidth, listY + listHeight, 0x40000000);

        for (int i = 0; i < VISIBLE_ROWS; i++) {
            int index = scrollOffset + i;
            if (index >= matchedItems.size()) break;

            Item item = matchedItems.get(index);
            int rowY = listY + i * ROW_HEIGHT;
            boolean hovered = mouseX >= listX && mouseX <= listX + listWidth
                    && mouseY >= rowY && mouseY <= rowY + ROW_HEIGHT;

            int bg = hovered ? ROW_HOVER : (index % 2 == 0 ? ROW_BG_A : ROW_BG_B);
            graphics.fill(listX, rowY, listX + listWidth, rowY + ROW_HEIGHT, bg);

            
            ItemStack stack = new ItemStack(item);
            graphics.renderItem(stack, listX + 4, rowY + (ROW_HEIGHT - ICON_SIZE) / 2);

            String itemName = item.getDefaultInstance().getHoverName().getString();
            graphics.text(this.font, itemName, listX + 4 + ICON_SIZE + 6, rowY + (ROW_HEIGHT - 8) / 2, TEXT_COLOR, false);
        }

        graphics.disableScissor();

        
        if (matchedItems.size() > VISIBLE_ROWS) {
            int barX = listX + listWidth + 4;
            graphics.fill(barX, listY, barX + 4, listY + listHeight, SCROLLBAR_TRACK);

            float visibleRatio = (float) VISIBLE_ROWS / matchedItems.size();
            int thumbHeight = Math.max(10, (int) (listHeight * visibleRatio));
            int thumbY = listY + (int) ((listHeight - thumbHeight) * ((float) scrollOffset / maxScroll()));

            graphics.fill(barX, thumbY, barX + 4, thumbY + thumbHeight, SCROLLBAR_THUMB);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    }
