package as3_virtual_memory_app;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws IOException {
//    verifyTranslations(); 
    for(int i = 10; i <= 100; i = i + 10) {
      System.out.println(i + ": ");
      evaluation(1000, i);
      System.out.println();
    }    
  }
  
  public static void evaluation(int workloadLength, int memory) throws IOException {
    System.out.println("8020: ");
    oneWorkload(Workloads.get8020Workload(100, workloadLength), memory);
    System.out.println("\nrandom: ");
    oneWorkload(Workloads.getRandomWorkload(100, workloadLength), memory);
    System.out.println("\nlooping: ");
    oneWorkload(Workloads.getLoopingWorkload(100, workloadLength), memory);
  }
  
  public static void oneWorkload(ArrayList<Integer> al, int memory) throws IOException {
    Server server; 
    
    server = new Server("songList.bin", memory, new RandomPolicy());
    System.out.print("random ");
    onePolicy(server, al);
    
    server = new Server("songList.bin", memory, new LRU_Policy());
    System.out.print("lru ");
    onePolicy(server, al);
    
    server = new Server("songList.bin", memory, new OptimalPolicy(al));
    System.out.print("optimal ");
    onePolicy(server, al);
  }
  
  public static void onePolicy(Server server, ArrayList<Integer> al) throws IOException {
    Playlist list = server.createFullPlaylist();
    for (int i = 0; i < al.size(); i++) {
      list.getSong(al.get(i), server);
    }
    System.out.println(server.hitCount);
  }
  
  public static void verifyTranslations() throws IOException {
    Server server = new Server("songList.bin", 50, new RandomPolicy());
    for(int i = 0; i < 5; i++) {
      Playlist list = server.makePlaylist();
      System.out.print("playlist " + i + ": ");
      for(int j = 0; j < 10; j++) {
        server.addSong(list);
        System.out.print(list.getSong(j, server).songName + ", ");
      }  
      System.out.println();
    }
  }

}
