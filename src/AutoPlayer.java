import java.util.ArrayList;
import java.util.*;

public class AutoPlayer {

    private char Fig;
    private static ArrayList lineList = new ArrayList();

    public AutoPlayer(char _Fig)
    {
        Fig = _Fig;
    }

    public dot getStep()
    {

        lineList = MainClass.f1.lineBuilder(Fig);
        dot d = ((LineObj)lineList.get(0)).getDot();

        return d;
    }
    public void showLines() {
        lineList = MainClass.f1.lineBuilder(Fig);
        MainClass.prt("Lines = " + lineList.size());
        MainClass.prt("=======================================");
        for (int i = 0; i < lineList.size(); i++) {
            MainClass.prt("Line " + i);
            MainClass.prt("Length is " + ((LineObj) lineList.get(i)).getLength());
            MainClass.prt("Type is " + ((LineObj) lineList.get(i)).getType());
            MainClass.prt("Direction is " + ((LineObj) lineList.get(i)).getDir());
            MainClass.prt("i " + ((LineObj) lineList.get(i)).getI());
            MainClass.prt("j " + ((LineObj) lineList.get(i)).getJ());
            MainClass.prt("----------------------------------");
        }
    }
}
