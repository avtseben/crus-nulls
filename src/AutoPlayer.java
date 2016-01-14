import java.util.ArrayList;
import java.util.*;

public class AutoPlayer {

    private char Fig = 'X';
    public dot getStep()
    {
        ArrayList lineList = new ArrayList();
        lineList = MainClass.f1.lineBuilder(Fig);
        dot d = ((LineObj)lineList.get(0)).getDot();
        System.out.println("LineLsit_Size = "+ lineList.size() );
        for(int i = 0; i < lineList.size(); i++) {
            MainClass.prt("lineLength is " + ((LineObj) lineList.get(i)).getLength());
            MainClass.prt("lineType is " + ((LineObj) lineList.get(i)).getType());
        }
        return d;

    }
    public void showLines()
    {

    }
}
