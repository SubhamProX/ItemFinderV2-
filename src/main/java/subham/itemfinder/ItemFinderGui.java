package subham.itemfinder;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import net.minecraft.network.chat.Component;

public class ItemFinderGui extends LightweightGuiDescription {
	public ItemFinderGui(){
		WGridPanel root = new WGridPanel(); // Fixed typo: WGridePanel -> WGridPanel
		setRootPanel(root);                  // Fixed case-sensitivity: SetRootPanel -> setRootPanel
		root.setSize(150, 130);
		
		WLabel helloLabel = new WLabel(Component.literal("Hello"));
WTextField searchBox = new WTextField(Component.literal("Search..."));
	}
}
