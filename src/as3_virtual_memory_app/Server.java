package as3_virtual_memory_app;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;
import javafx.util.Pair;

public class Server {
  Song[] songs;
  String fileName;
  RandomAccessFile raf;
  int listNum;
  int memory;
  int size;
  int hitCount;
  SwapPolicy sp;
  HashMap<Pair<Integer, Integer>, Integer> pageTable;

  public Server(String dataFile, int max, SwapPolicy sp) throws IOException {
    fileName = dataFile;
    raf = new RandomAccessFile("songList.bin", "r");
    raf.seek(0);
    byte[] num = new byte[4];
    raf.readFully(num);
    
    for(int i = 0; i < num.length / 2; i++) {
      byte temp = num[i];
      num[i] = num[num.length - 1 - i];
      num[num.length - 1 - i] = temp;
    }
    
    songs = new Song[(new BigInteger(num)).intValue()];
    this.sp = sp;
    listNum = 0;
    memory = max;
    hitCount = 0;
    size = 0;
    pageTable = new HashMap<Pair<Integer, Integer>, Integer>();
  }

  public Playlist makePlaylist() {
    return new Playlist(listNum++);
  }

  public void addSong(Playlist list) {
    list.allocateSong();
    Random rand = new Random();
    pageTable.put(new Pair<Integer, Integer>(list.id, list.songNum), rand.nextInt(songs.length));
  }

  public int translateAddress(int playlistID, int virtualSongNumber) {
    return pageTable.get(new Pair<Integer, Integer>(playlistID, virtualSongNumber));
  }

  public Song getSong(int playlistID, int virtualSongNumber) throws IOException {
    int physicalSongNumber = translateAddress(playlistID, virtualSongNumber);
    if(songs[physicalSongNumber] == null) {
      if(size >= memory) {
        int evict = sp.whichPageShouldBeEvicted();
        songs[evict] = null;
        size--;
      }
      loadSong(physicalSongNumber);
    }else {
      hitCount++;
    }
    sp.pageAccessed(physicalSongNumber);
    return songs[physicalSongNumber];
  }

  public void loadSong(int physicalSongNumber) throws IOException {
    int pos = 4 + physicalSongNumber * 256;
    raf.seek(pos);
    byte[] buff = new byte[raf.readByte()];
    raf.seek(pos + 1);
    raf.readFully(buff);
    songs[physicalSongNumber] = new Song(new String(buff));
    size++;
  }
  
  public Playlist createFullPlaylist(){
    Playlist list = new Playlist(listNum++);
    for (int i = 0; i < songs.length; i++) {
      list.allocateSong();
      pageTable.put(new Pair<Integer, Integer>(list.id, i), i);
    }
    return list;
  }
}
