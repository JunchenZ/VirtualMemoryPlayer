package as3_virtual_memory_app;

import java.util.ArrayList;

public class LRU_Policy implements SwapPolicy {

  private ArrayList<Integer> al;
  
  public LRU_Policy() {
    al = new ArrayList<Integer>();
  }
  
  @Override
  public void pageAccessed(int page) {
    if(!al.contains(page)) {
      al.add(page);
    }else {
      al.remove(al.indexOf(page));
      al.add(page);
    }  
  }

  @Override
  public int whichPageShouldBeEvicted() {
    int temp = al.get(0);
    al.remove(0);
    return temp;
    
  }
  
}
