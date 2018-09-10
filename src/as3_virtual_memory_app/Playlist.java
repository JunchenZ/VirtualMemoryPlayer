package as3_virtual_memory_app;

import java.io.IOException;

public class Playlist {
  int songNum;
  int id;
  
  public Playlist(int id) {
    this.id = id;
    songNum = 0;
  }
  
  public void allocateSong() {
    songNum++;
  }
  
  public Song getSong(int virtualSongNumber, Server server) throws IOException {
    return server.getSong(id, virtualSongNumber);
  }
}
