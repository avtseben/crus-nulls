
import java.util.Random;
import java.util.Scanner;

/**
 * Крестики - Нолики. Поле - объект  класса Field, где * - пустые игрок расставляет X , комьпютер  O.
 * Игра - бесконечны цикл. при каждой итерации проверяется наличие пустых ячеек. Перед ходом проверяется не занята ли клетка
 * Компьютер пока ствит на угад. Интелект в процессе разработки, задумка в том чтобы в классе Field метод lineBuilder
 * из существующих крестиков и ноликов на поле формировал объекты класса LineObj. Объекты - линии имеют характеристики:
 * начальная точка (x, y), направление роста и длина. Создан алгоритм создания объектов  LineObj, при этом не создаётся объект если
 * линию построить невозможно (например она блокирована с двух сторон или не хватит ячеек для постройки полной линии).
 * Предполагается что комьпютер будет иметь дело с двумя списками ArrayList хранящими свои линии (O) и игрока(X) и основываясь
 * на максимально длинной линии принимать решение либо блокировать линию игрока либо наращивать свою
 */
public class MainClass {

    public static Random rand = new Random();
    public static void prt(String s) {System.out.println(s);}
    public static Field f1 = new Field();
    public static void main (String[] args)
    {

        Scanner sc = new Scanner(System.in);
        System.out.println("Игра Крестики-Нолики. Вы играете крестиками, ваш ход первый");
        boolean myTurn = true;
        char myFig = 'X';
        char compFig = 'O';

        //AutoPlayer ap1 = new AutoPlayer(myFig);
        AutoPlayer ap2 = new AutoPlayer(compFig);
        f1.showField();

        while(f1.checkFreeNode()) {//Перед каждым ходом проверяе есть ли еще свободные ячейки
            //dot d;
            int x,y;
            if(myTurn) {
                do {
                    prt("Введите координаты в формате x y");
                    x = sc.nextInt() - 1; //Игрок указывает координаты считая от 1-цы
                    y = sc.nextInt() - 1; //Конвертируем это в нумерацию элементов массива
                } while (!f1.isCellEmpty(y, x));
                f1.setNode(y, x, myFig);
                if(f1.checkWinner(myFig))
                {
                    prt("Игрок победил!");
                    f1.showField();
                    break;
                }
                myTurn = false; //Ход переходит
            }
            else
            {
                ap2.doStep();//Компьютер ходит
                if(f1.checkWinner(compFig))
                {
                    prt("Компьютер победил!");
                    f1.showField();
                    break;
                }
                f1.showField();
                //------------------
                //d = ap2.getStep();
                //d.showPosition();
                //------------------
                myTurn = true;
            }
        }
    }
}
