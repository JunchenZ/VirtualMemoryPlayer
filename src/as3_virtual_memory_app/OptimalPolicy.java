package as3_virtual_memory_app;

import java.util.ArrayList;

public class OptimalPolicy implements SwapPolicy {

  ArrayList<Integer> future;
  ArrayList<Integer> current;
  int index;
  
  public OptimalPolicy(ArrayList<Integer> songNum) {
    future = songNum;
    current = new ArrayList<Integer>();
    index = 0;
  }
  
  @Override
  public void pageAccessed(int page) {
    if(!current.contains(page)) {
      if(current.size() < 50) {
        current.add(page);
      }else {
        int evict = whichPageShouldBeEvicted();
        current.remove(current.indexOf(evict));
        current.add(page);
      }
    }
    index++; 
  }

  @Override
  public int whichPageShouldBeEvicted() {
    ArrayList<Integer> al = new ArrayList<Integer>();
    for (int i = index + 1; i < future.size(); i++) {
      if(future.get(i) != future.get(index) && current.contains(future.get(i)) && !al.contains(future.get(i))) {
        al.add(future.get(i));
      }
    }
    if(al.size() == current.size()) {
      return al.get(al.size() - 1);
    }else {
      for (int i = 0; i < current.size(); i++) {
        if(!al.contains(current.get(i))) {
          return current.get(i);
        }
      }
    }
    return -1;
  }

}
