package tk.masonx.multiInv;

import java.io.*;

public class InventoryIO {
	public static void write(String location, PlayerInventory inventory) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(location));
			out.writeObject(inventory);
			out.close();
		} catch(IOException i) {
		}
	}

	public static PlayerInventory read(String location) throws Exception{
		PlayerInventory e = null;
         	ObjectInputStream in = new ObjectInputStream(new FileInputStream(location));
         	e = (PlayerInventory) in.readObject();
         	in.close();
         	return e;
    }
}
