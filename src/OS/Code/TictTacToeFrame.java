package OS.Code;

/**
 * Created by Kaheem on 11/14/2017.
 */
public class TictTacToeFrame {

    private int id;
    private TicTacToe frame;


    public TictTacToeFrame(int id, TicTacToe frame)
    {
        this.id=id;
        this.frame=frame;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TicTacToe getFrame() {
        return frame;
    }

    public void setFrame(TicTacToe frame) {
        this.frame = frame;
    }
}
