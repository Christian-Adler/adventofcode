public class Tree {


    final int height;

    int visible = -1;

    int visibleFromTop = -1;
    int visibleFromBottom = -1;
    int visibleFromLeft = -1;
    int visibleFromRight = -1;

    int scenicScore = 1;

    public Tree(int height) {
        this.height = height;
    }
}
