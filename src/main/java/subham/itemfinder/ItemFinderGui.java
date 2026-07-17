package subham.itemfinder;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import net.minecraft.text.Text;

public class ItemFinderGui extends LightweightGuiDescription {
	public ItemFinderGui(){
		WGridPanel root = new WGridPanel(); // Fixed typo: WGridePanel -> WGridPanel
		setRootPanel(root);                  // Fixed case-sensitivity: SetRootPanel -> setRootPanel
		root.setSize(150, 130);
		
		WLabel helloLabel = new WLabel(Text.literal("Hello"));
		root.add(helloLabel, 0, 0, 4, 1);
		
		WTextField searchBox = new WTextField(Text.literal("Search..."));
		root.add(searchBox, 0, 1, 4, 1);
		root.validate(this);
	}
}
