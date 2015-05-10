package tk.masonx.mc.multiInv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import tk.masonx.mc.util.BetterInventory;

public class InventoryToBase64 {
    public static String toBase64(BetterInventory inv) {
        try {
        	Inventory inventory = inv.getInventory();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            
            //Write armor
            for (ItemStack i : inv.getArmor())
            	dataOutput.writeObject(i);
            
            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());
            
            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++)
                dataOutput.writeObject(inventory.getItem(i));
            
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }    
    }

    public static BetterInventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            
            //Ugly, I know...
            ItemStack[] a = new ItemStack[]{(ItemStack) dataInput.readObject(),
            								(ItemStack) dataInput.readObject(),
            								(ItemStack) dataInput.readObject(),
            								(ItemStack) dataInput.readObject()};

            Inventory inventory = (Inventory) Bukkit.getServer().createInventory(null, dataInput.readInt());
            
            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++)
                inventory.setItem(i, (ItemStack) dataInput.readObject());

            
            dataInput.close();
            BetterInventory r = new BetterInventory(a, inventory);
            return r;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}
