import java.util.ArrayList;

public abstract class Player {

    protected char Fig;
    protected char enemyFig;
    protected static ArrayList lineList = new ArrayList();
    protected Field targetField;
    protected String playerType;

    public Player (char _Fig, Field _f)
    {
        Fig = _Fig;
        targetField = _f;
        playerType = "default";
    }
  abstract boolean doStep();
  abstract String getType(); 
} 
