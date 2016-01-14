import java.util.ArrayList;
import java.util.*;

public class AutoPlayer {

    private char Fig = 'X';
    private ArrayList lineList = new ArrayList();

    public dot getStep()
    {
        ;//List of Lines
        lineList = MainClass.f1.lineBuilder(Fig);
        dot d = ((LineObj)lineList.get(0)).getDot();
        //System.out.print(lineList + "\n");
        System.out.println("hi = "+ lineList.size() );
        for(int i = 0; i < lineList.size(); i++) {
            //((LineObj)lineList.get(i)).getLength();
            System.out.println("hi");
            MainClass.prt("lineLength is " + ((LineObj) lineList.get(i)).getLength());
        }
        return d;

    }
    public void showLines()
    {

    }
}
