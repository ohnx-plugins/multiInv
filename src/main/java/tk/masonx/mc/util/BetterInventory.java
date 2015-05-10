package tk.masonx.mc.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/***
 * 
 * @author ohnx
 * 
 * A wrapper class for an inventory and armor slots
 * I made this because stuff didn't like working sometimes, and this was my solution.
 *
 */
public class BetterInventory {
	protected ItemStack[] a;
	protected Inventory i;
	
	public BetterInventory(ItemStack[] a, Inventory i) {
		this.a = a;
		this.i = i;
	}
	
	public Inventory getInventory() {
		return i;
	}
	
	public ItemStack[] getArmor() {
		return a;
	}
}
