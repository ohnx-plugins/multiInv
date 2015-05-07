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
		//args check
		if(split.length<2)
			return false;
		//server check
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+"You have to be a player to run this command!");
			return true;
		}
		//load or save?
		
		if(split[0].equals("load")) {
			sender.sendMessage(ChatColor.AQUA+"Loading your inventory, with name "+ChatColor.GREEN+split[1]+ChatColor.AQUA+" ...");
			Player player = (Player) sender;
			PlayerInventory inventory = null;
			try {
				inventory = InventoryIO.read("plugins\\multiInv\\"+player.getUniqueID().toString().split[1]+".inventory");
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED+"Can't open that inventory file!");
				return true;
			}
			PlayerInventory oldInventory = new PlayerInventory();
			for (int i=0;i<40;i++) {
				oldInventory.setItem(i, new CardboardBox(player.getInventory().getItem(i)));
				player.getInventory().setItem(i, inventory.getInventory().get(i).getItemStack());
			}
			InventoryIO.write("plugins\\multiInv\\"+player.getUniqueID().toString().split[1]+".inventory", oldInventory);
			sender.sendMessage(ChatColor.GREEN+"Done!!");
			return true;
		} else if (split[0].equals("save")) {
			sender.sendMessage(ChatColor.AQUA+"Saving your inventory to the file named");
			Player player = (Player) sender;
			PlayerInventory inventory = new PlayerInventory();
			for (int i=0;i<40;i++) {
				inventory.setItem(i, new CardboardBox(player.getInventory().getItem(i)));
				player.getInventory().setItem(i, new ItemStack(Material.AIR));
			}
			InventoryIO.write(split[1]+".inventory", inventory);
			sender.sendMessage(ChatColor.GREEN+"Done!!");
			return true;
		} else if (split[0].equals("help")) {
			sender.sendMessage(ChatColor.GREEN+"Done!!");
		} else {
			return true;
		}
    }

}
