import java.util.ArrayList;

public class AutoPlayer {

    private char Fig = 'X';

    public dot getStep()
    {
        ArrayList lineList = new ArrayList();//List of Lines
        lineList = MainClass.f1.lineBuilder(Fig);
        dot d = ((LineObj)lineList.get(0)).getDot();
        System.out.print(lineList + "\n");
        return d;

    }

}
