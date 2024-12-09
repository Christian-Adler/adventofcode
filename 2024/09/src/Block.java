public class Block {
  private int size;
  private final long fileId;

  public Block(int size, long fileId) {
    this.fileId = fileId;
    this.size = size;
  }

  public boolean isFile() {
    return fileId >= 0;
  }

  public boolean isSpace() {
    return fileId < 0;
  }

  public void reduceSize() {
    size--;
    isEmpty();
  }

  public boolean isEmpty() {
    return size <= 0;
  }

  public int getSize() {
    return size;
  }

  public long getFileId() {
    return fileId;
  }

  @Override
  public String toString() {
    String s = isFile() ? String.valueOf(fileId) : ".";
    return s.repeat(Math.max(0, size));
  }
}
