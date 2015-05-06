import java.io.*;

public class InventoryIO {
   public static void main(String location) {
      PlayerInventory inventory = new PlayerInventory();
      try {
         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/plugins/multiInv/playerwhatever/inventory"));
         out.writeObject(inventory);
         out.close();
         System.out.printf("Serialized data is saved in /tmp/employee.ser");
      } catch(IOException i) {
          i.printStackTrace();
      }
   }

   public static void deSerilaize(String [] args)
   {
      Employee e = null;
      try
      {
         FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         e = (Employee) in.readObject();
         in.close();
         fileIn.close();
      }catch(IOException i)
      {
         i.printStackTrace();
         return;
      }catch(ClassNotFoundException c)
      {
         System.out.println("Employee class not found");
         c.printStackTrace();
         return;
      }
      System.out.println("Deserialized Employee...");
      System.out.println("Name: " + e.name);
      System.out.println("Address: " + e.address);
      System.out.println("SSN: " + e.SSN);
      System.out.println("Number: " + e.number);
    }
}
