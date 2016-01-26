import java.util.ArrayList;

/**
 * Created by avtseben on 25.01.2016.
 */
public class Player {

    protected char Fig;
    protected char enemyFig;
    protected static ArrayList lineList = new ArrayList();
    protected Field targetField;

    public Player (char _Fig, Field _f)
    {
        Fig = _Fig;
       // if(Fig == 'X') enemyFig = 'O';
       // else enemyFig = 'X';
        targetField = _f;
    }
    public void doStep() {}//Без этого я не могу в мэйн сделать  Player p = new HumanPlayer()
}
