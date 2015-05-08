package tk.masonx.multiInv;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
		
		if (split[0].equals("help")) {
			sender.sendMessage(ChatColor.AQUA+plugin.pdfFile.getName()+" version "+plugin.pdfFile.getVersion()+" help");
			sender.sendMessage(ChatColor.DARK_AQUA+"Multiple inventories for a player. Made by ohnx");
			sender.sendMessage(ChatColor.GREEN+"/inv save <name>\n"+ChatColor.WHITE+"    Save an inventory with the name <name>");
			sender.sendMessage(ChatColor.GREEN+"/inv load <name>\n"+ChatColor.WHITE+"    Load an inventory with the name <name>");
			sender.sendMessage(ChatColor.GREEN+"/inv list \n"+ChatColor.WHITE+"    List your inventories");
			if(sender.hasPermission("multiInv.editOthers")) {
				sender.sendMessage(ChatColor.GREEN+"/inv sload <player name> <name>\n"+ChatColor.WHITE+"    Load an inventory with the name <name> that belongs to player <player>");
				sender.sendMessage(ChatColor.GREEN+"/inv ssave <player name> <name>\n"+ChatColor.WHITE+"    Save your current inventory with the name <name> that belongs to player <player>");
				return true;
			}
			sender.sendMessage(ChatColor.GREEN+"/inv help\n"+ChatColor.WHITE+"    Get help");
			return true;
		}
		
		//args check
		if(split.length<2)
			return false;
		
		//server check
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+"You have to be a player to run this command!");
			return true;
		}
		
		Player player = (Player) sender;
		PlayerInventory inventory = null;
		//load or save?
		if(split[0].equals("load")) {
			sender.sendMessage(ChatColor.AQUA+"Loading your inventory with name "+ChatColor.GREEN+split[1]+ChatColor.AQUA+"...");
			try {
				inventory = InventoryIO.read("plugins/multiInv/inventories/"+player.getUniqueId().toString()+"/"+split[1]+".inventory");
			} catch (Exception e) {
				
				sender.sendMessage(ChatColor.RED+"Can't open that inventory file!");
				return true;
			}
			PlayerInventory oldInventory = new PlayerInventory();
			//Don't bother swapping inventories if the inventory is empty.
			boolean emptyInv = true;
			for (int i=0;i<40;i++) {
				try {
					if(!player.getInventory().getItem(i).equals(new ItemStack(Material.AIR)))
						emptyInv=false;
				} catch (Exception e) {}
				oldInventory.setItem(i, new CardboardBox(player.getInventory().getItem(i)));
				player.getInventory().setItem(i, inventory.getInventory().get(i).getItemStack());
			}
			
			try {
				if(!emptyInv)
					InventoryIO.write("plugins/multiInv/inventories/"+player.getUniqueId().toString(), split[1]+".inventory", oldInventory);
				else	//Delete the old file if the inventory was empty, no need for duping items or the such...
					InventoryIO.delete("plugins/multiInv/inventories/"+player.getUniqueId().toString()+"/"+split[1]+".inventory");
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED+"Can't open that inventory file!");
				plugin.getLogger().severe("Failed to save the file!");
				e.printStackTrace();
			}
			sender.sendMessage(ChatColor.GREEN+"Done!!");
			return true;
		} else if (split[0].equals("save")) {
			sender.sendMessage(ChatColor.AQUA+"Saving your inventory to the file named "+ChatColor.GREEN+split[1]+ChatColor.AQUA+"...");
			inventory = new PlayerInventory();
			//Don't bother saving inventories if the inventory is empty.
			boolean emptyInv = true;
			for (int i=0;i<40;i++) {
				try {
					if(!player.getInventory().getItem(i).equals(new ItemStack(Material.AIR)))
						emptyInv=false;
				} catch (Exception e) {}
				inventory.setItem(i, new CardboardBox(player.getInventory().getItem(i)));
				player.getInventory().setItem(i, new ItemStack(Material.AIR));
			}
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
				for (int i=0;i<40;i++) {
					player.getInventory().setItem(i, inventory.getInventory().get(i).getItemStack());
				}
			}
			sender.sendMessage(ChatColor.GREEN+"Done!!");
			return true;
		} else if (split[0].equals("list")) {
			for (String name : InventoryIO.list("plugins/multiInv/inventories/"+player.getUniqueId().toString()))
				sender.sendMessage(ChatColor.GREEN+name);
			return true;
		} else {
			
			return false;
		}
		
    }

}
