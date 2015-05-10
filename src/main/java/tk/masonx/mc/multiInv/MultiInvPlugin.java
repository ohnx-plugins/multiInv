package tk.masonx.mc.multiInv;

import java.io.File;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

//Actual plugin file
public final class MultiInvPlugin extends JavaPlugin {
	private final MultiInvCommand commandHandler = new MultiInvCommand(this);
	PluginDescriptionFile pdfFile;
	    
	@Override
	public void onDisable() {
	    getLogger().info("Goodbye world!");
	}

	@Override
	public void onEnable() {
		//Load config
		//config.loadConfiguration();
		new File(getDataFolder().toString()+"/inventories").mkdirs();
		//Register player join listener
		//PluginManager pm = getServer().getPluginManager();
		//pm.registerEvents(playerListener, this);

		//Register inv command
		getCommand("inv").setExecutor(commandHandler);
		//getCommand("vpn").setTabCompleter(new CheckVPNTabCompleter());
	        
		//Say loaded!
		pdfFile = this.getDescription();
		getLogger().info(pdfFile.getName() + " version " + pdfFile.getVersion() + " sucessfully loaded!");
	}
}