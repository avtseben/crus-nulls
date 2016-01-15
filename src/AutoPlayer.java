import java.util.ArrayList;
import java.util.*;

public class AutoPlayer {

    private char Fig;
    private char enemyFig;
    private static ArrayList lineList = new ArrayList();

    public AutoPlayer(char _Fig)
    {
        Fig = _Fig;
        if(Fig == 'X') enemyFig = 'O';
	else enemyFig = 'X';
    }

    public dot getStep()
    {

        lineList = MainClass.f1.lineBuilder(Fig);
        dot d = ().getDot();

        return d;
    }
    public LineObj getLongest()
    {
	LineObj l = new LineObj();
	int i = 0;
	int len = 0;
	int maxlen = 0;
	lineList = MainClass.f1.lineBuilder(Fig);
	for (int i = 0; i < lineList.size(); i++) {
            len = ((LineObj) lineList.get(i)).getLength();
	    MainClass.prt("Length is " + len);
        }	
	l = lineList.get(i);
	return l; 
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
