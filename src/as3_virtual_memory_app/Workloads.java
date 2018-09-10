package as3_virtual_memory_app;

import java.util.ArrayList;
import java.util.Random;

public class Workloads {

  public static ArrayList<Integer> get8020Workload(int numSongs, int workloadLength) {
    ArrayList<Integer> ret = new ArrayList<>();
    Random r = new Random();
    int cutoff = (int) (.2 * numSongs);
    int rest = numSongs - cutoff;
    for (int i = 0; i < workloadLength; ++i) {
      if (r.nextDouble() < .8) {
        // pick from first 20%
        ret.add(r.nextInt(cutoff));
      } else {
        // pick from last 80%
        ret.add(r.nextInt(rest) + cutoff);
      }
    }
    return ret;
  }

  public static ArrayList<Integer> getRandomWorkload(int numSongs, int workloadLength) {
    ArrayList<Integer> ret = new ArrayList<>();
    Random r = new Random();
    for (int i = 0; i < workloadLength; ++i) {
      ret.add(r.nextInt(numSongs));
    }
    return ret;
  }
  
  public static ArrayList<Integer> getLoopingWorkload(int numSongs, int workloadLength) {
    ArrayList<Integer> ret = new ArrayList<>();
    for (int i = 0; i < workloadLength; ++i) {
      ret.add(i % numSongs);
    }
    return ret;
  }
}
