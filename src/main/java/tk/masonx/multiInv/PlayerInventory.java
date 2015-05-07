package tk.masonx.multiInv;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class PlayerInventory implements Serializable{
	private static final long serialVersionUID = 1333331141142464968L;
	private ArrayList<CardboardBox> inventory;
	@SuppressWarnings("deprecation")
	public PlayerInventory () {
		for (int i=0;i<=44;i++)
			inventory.add(new CardboardBox(new ItemStack(0)));
	}
	public void setInventory (ArrayList<CardboardBox> inventory) {
		this.inventory = inventory;
	}
	public ArrayList<CardboardBox> getInventory () {
		return inventory;
	}
	public void addItem(CardboardBox item) {
		inventory.add(item);
	}
	public void setItem(int index, CardboardBox item) {
		inventory.set(index, item);
	}
	public CardboardBox getItem(int index) {
		return inventory.get(index);
	}
}

/*0-4 empty
5-8 armour
9-44 inventory*/