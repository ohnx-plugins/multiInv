package tk.masonx.multiInv;

import java.io.*;

public class InventoryIO {
	public static void write(String location, PlayerInventory inventory) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(location));
			out.writeObject(inventory);
			out.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
	}

	public static PlayerInventory read(String location) {
		PlayerInventory e = null;
		try {
         	ObjectInputStream in = new ObjectInputStream(new FileInputStream(location));
         	e = (PlayerInventory) in.readObject();
         	in.close();
         	return e;
		} catch(IOException i) {
         	i.printStackTrace();
         	return null;
      	} catch(ClassNotFoundException c) {
         	System.out.println("Employee class not found");
         	c.printStackTrace();
         	return null;
      	}
    }
}
