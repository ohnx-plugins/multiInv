package tk.masonx.checkInv;

import java.io.Serializable;
/*0-4 empty
5-8 armour
9-44 inventory*/
public PlayerInventory implements Serializable{
  private ArrayList<CardboardBox> inventory;
  public PlayerInventory () {
    for (int i=0;i<=44;i++)
      inventory.add();
  }
  public void setInventory (ArrayList<CardboardBox> inventory) {
    this.inventory = inventory;
  }
  public ArrayList<CardboardBox> getInventory () {
    return inventory;
  }
  public void addItem(CardboardBox item) {
    inventory.add(item);
  }
  public void setItem(int index, CardboardBox item) {
    inventory.set(index, item);
  }
  public CardboardBox getItem(int index) {
    return inventory.get(index);
  }
}
