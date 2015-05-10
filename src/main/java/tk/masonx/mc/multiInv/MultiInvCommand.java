package tk.masonx.mc.multiInv;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tk.masonx.mc.util.BetterInventory;

public class MultiInvCommand implements CommandExecutor {
	private final MultiInvPlugin plugin;
	
    public MultiInvCommand(MultiInvPlugin plugin) {
        this.plugin = plugin;
    }
    
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		/*if(!sender.hasPermission("checkVPN.runCommand")) {
			sender.sendMessage(ChatColor.RED+"Oops! You don't have the required permissions to access this command.");
			return true;
		}*/
		if(split.length<1)
			return false;
		
		//server check
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+"You have to be a player to run this command!");
			return true;
		}
		
		Player player = (Player) sender;
		String inventory = null;
		
		//No arguments needed for these commands
		if (split[0].equals("help")) {
			sender.sendMessage(ChatColor.AQUA+plugin.pdfFile.getName()+" version "+plugin.pdfFile.getVersion()+" help");
			sender.sendMessage(ChatColor.DARK_AQUA+"Multiple inventories for a player. Made by ohnx");
			sender.sendMessage(ChatColor.GREEN+"/inv save <name>\n"+ChatColor.WHITE+"    Save an inventory with the name <name>");
			sender.sendMessage(ChatColor.GREEN+"/inv load <name>\n"+ChatColor.WHITE+"    Load an inventory with the name <name>");
			sender.sendMessage(ChatColor.GREEN+"/inv list \n"+ChatColor.WHITE+"    List your inventories");
			if(sender.hasPermission("multiInv.editOthers")) {
				sender.sendMessage(ChatColor.GREEN+"/inv sload <player name> <name>\n"+ChatColor.WHITE+"    Load an inventory with the name <name> that belongs to player <player> - NYI");
				sender.sendMessage(ChatColor.GREEN+"/inv ssave <player name> <name>\n"+ChatColor.WHITE+"    Save your current inventory with the name <name> that belongs to player <player> - NYI");
				return true;
			}
			sender.sendMessage(ChatColor.GREEN+"/inv help\n"+ChatColor.WHITE+"    Get help");
			return true;
		} else if (split[0].equals("list")) {
			sender.sendMessage(ChatColor.AQUA+"Your inventories are:");
			for (String name : InventoryIO.list("plugins/multiInv/inventories/"+player.getUniqueId().toString()))
				sender.sendMessage(ChatColor.GREEN+name.replaceAll(".inventory", ""));
			sender.sendMessage("Use "+ChatColor.GREEN+"/inv load <name>"+ChatColor.WHITE+" to load an inventory");
			return true;
		}
		
		//args check
		if(split.length<2)
			return false;

		//f.exists()
		
		//Don't bother swapping inventories if the inventory is empty.
		boolean emptyInv = true;
		for(ItemStack item : player.getInventory().getContents())
			if(item != null)
				emptyInv = false;
		
		//load or save?
		if(split[0].equals("load")) {
			sender.sendMessage(ChatColor.AQUA+"Loading your inventory with name "+ChatColor.GREEN+split[1]+ChatColor.AQUA+"...");
			try {
				inventory = InventoryIO.read("plugins/multiInv/inventories/"+player.getUniqueId().toString()+"/"+split[1]+".inventory");
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED+"Can't open that inventory file!");
				return true;
			}
			String oldInventory = new String();
			if (!emptyInv)
				oldInventory = InventoryToBase64.toBase64(new BetterInventory(player.getInventory().getArmorContents(), player.getInventory()));
			try {
				BetterInventory bi = InventoryToBase64.fromBase64(inventory);
				player.getInventory().setContents(bi.getInventory().getContents());
				player.getInventory().setArmorContents(bi.getArmor());
				if(!emptyInv)
					InventoryIO.write("plugins/multiInv/inventories/"+player.getUniqueId().toString(), split[1]+".inventory", oldInventory);
				else	//Delete the old file if the inventory was empty, no need for duping items or the such...
					InventoryIO.delete("plugins/multiInv/inventories/"+player.getUniqueId().toString()+"/"+split[1]+".inventory");
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED+"Can't open that inventory file!");
				plugin.getLogger().severe("Failed to open the file!");
				e.printStackTrace();
			}
			sender.sendMessage(ChatColor.GREEN+"Done!!");
			return true;
		} else if (split[0].equals("save")) {
			sender.sendMessage(ChatColor.AQUA+"Saving your inventory to the file named "+ChatColor.GREEN+split[1]+ChatColor.AQUA+"...");
			inventory = InventoryToBase64.toBase64(new BetterInventory(player.getInventory().getArmorContents(), player.getInventory()));
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			try {
				if(!emptyInv)
					InventoryIO.write("plugins/multiInv/inventories/"+player.getUniqueId().toString(), split[1]+".inventory", inventory);
				else
					sender.sendMessage(ChatColor.RED+"Your inventory is empty!");
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED+"Failed to save the inventory file! Restoring items...");
				plugin.getLogger().severe("Failed to save the file!");
				e.printStackTrace();
				//Restore the items if saving the inventory failed
				try {
					BetterInventory bi = InventoryToBase64.fromBase64(inventory);
					player.getInventory().setContents(bi.getInventory().getContents());
					player.getInventory().setArmorContents(bi.getArmor());
				} catch (Exception f) {
					//Should never happen...
					plugin.getLogger().severe("Something that should never have happened just did! Failed to restore inventory items for "+player+"!!!\nError stack trace:");
					f.printStackTrace();
					sender.sendMessage(ChatColor.RED+"Fatal error: Failed to restore inventory items. This should never happen.");
				}
			}
			sender.sendMessage(ChatColor.GREEN+"Done!!");
			return true;
		}

		//args check
		if(split.length<3)
			return false;
		
		//TODO: Implement
		//load or save?
		/*if(split[0].equals("sload")) {
			sender.sendMessage(ChatColor.AQUA+"Loading your inventory with name "+ChatColor.GREEN+split[1]+ChatColor.AQUA+"...");
			try {
				inventory = InventoryIO.read("plugins/multiInv/inventories/"+player.getUniqueId().toString()+"/"+split[1]+".inventory");
				BetterInventory bi = InventoryToBase64.fromBase64(inventory);
				player.getInventory().setContents(bi.getInventory().getContents());
				player.getInventory().setArmorContents(bi.getArmor());
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED+"Can't open that inventory file!");
			}
			sender.sendMessage(ChatColor.GREEN+"Done!!");
			return true;
		} else if(split[0].equals("ssave")) {
			
		}*/
		return false;
    }

}
