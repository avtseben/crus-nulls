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

    public void doStep()
    {
        int x,y;

/*        if (Fig == 'X') MainClass.prt("I say: Enemy is " + enemyFig);
*        if (Fig == 'O') MainClass.prt("Comp say: Enemy is " + enemyFig);
*        lineList = MainClass.f1.lineBuilder(enemyFig);
*        int longestLineIPos = ((LineObj) lineList.get(getLongest())).getI();
*        int longestLineJPos = ((LineObj) lineList.get(getLongest())).getJ();
*/
       //1. Случайно подбираем координаты
        do {
            x = MainClass.rand.nextInt(Field.FIELD_SIZE);
            y = MainClass.rand.nextInt(Field.FIELD_SIZE);
        } while (!MainClass.f1.isCellEmpty(y, x));


        //2.Простейший интелект
        for(int i =0;i<Field.FIELD_SIZE;i++)
            for(int j =0;j<Field.FIELD_SIZE;j++)
            {
                if(MainClass.f1.isCellEmpty(i, j)) {
                    MainClass.f1.setNode(i, j, enemyFig);//Пробуем поставить на угад за противника
                    if(MainClass.f1.checkWinner(enemyFig))//И если видим что он при этом выйграет. То сразу же блокируем
                    {
                        y = i;
                        x = j;
                    }

                    MainClass.f1.setNode(i, j, '*');
                }
            }

        for(int i =0;i<Field.FIELD_SIZE;i++)
            for(int j =0;j<Field.FIELD_SIZE;j++)
            {
                if(MainClass.f1.isCellEmpty(i,j)) {
                    MainClass.f1.setNode(i, j, Fig);
                    if(MainClass.f1.checkWinner(Fig))
                    {
                        y = i;
                        x = j;
                    }
                    MainClass.f1.setNode(i, j, '*');
                }
            }
        MainClass.f1.setNode(y, x, Fig);
//-------New
        MainClass.f1.listLineBuilder(Fig);

        //dot d = new dot(i,j);
        //return d;
    }
    public int getLongest()//Возвращает номер(в списке) самой длинной линии
    {
        int len;
        int li = 0;
        int maxlen = 0;
        lineList = MainClass.f1.lineBuilder(Fig);
        for (int i = 0; i < lineList.size(); i++) {
            len = ((LineObj)lineList.get(i)).getLength();
            if(len > maxlen) {
                maxlen = len;
                li = i;
            }
        }
        return li;
    }

    public void showLines() {
        lineList = MainClass.f1.lineBuilder(Fig);
        MainClass.prt("Lines = " + lineList.size());
        MainClass.prt("=======================================");
        for (int i = 0; i < lineList.size(); i++) {
            MainClass.prt("Line " + i);
            MainClass.prt("Length is " + ((LineObj) lineList.get(i)).getLength());
            MainClass.prt("Type is " + ((LineObj) lineList.get(i)).getlineString());
            MainClass.prt("Direction is " + ((LineObj) lineList.get(i)).getDir());
            MainClass.prt("i " + ((LineObj) lineList.get(i)).getI());
            MainClass.prt("j " + ((LineObj) lineList.get(i)).getJ());
            MainClass.prt("----------------------------------");
        }
    }
}
