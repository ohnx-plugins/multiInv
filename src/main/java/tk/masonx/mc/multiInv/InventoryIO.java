package tk.masonx.mc.multiInv;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class InventoryIO {
	public static void write(String parent, String file, String b64) throws Exception{
		new File(parent).mkdirs();
		ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(parent+"/"+file)));
		out.writeObject(b64);
		out.close();
	}

	public static String read(String location) throws Exception{
		String e = null;
         	ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(new FileInputStream(location)));
         	e = (String) in.readObject();
         	in.close();
         	return e;
    }
	
	public static List<String> list(String parent) {
		List<String> results = new ArrayList<String>();
		File[] files = new File(parent).listFiles();

		for (File file : files)
		    if (file.isFile())
		        results.add(file.getName());
		return results;
	}
	
	public static void delete(String path) {
		File delFile = new File(path);
		delFile.delete();
	}
}
