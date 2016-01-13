//import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by avtseben on 04.01.2016.
 */
public class MainClass {

    public static Random rand = new Random();//Random
    public static void prt(String s) {System.out.println(s);}//Short style print
    public static Field f1 = new Field();//Game Field for everybody

    public static void main (String[] args)
    {
        //ArrayList lineListMyFig = new ArrayList();
        AutoPlayer ap1 = new AutoPlayer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Игра Крестики-Нолики. Вы играете крестиками, ваш ход первый");
        boolean myTurn = true;
        char myFig = 'X';
        char compFig = 'O';
        f1.showField();

        while(f1.checkFreeNode()) {//Перед каждым ходом проверяе есть ли еще свободные ячейки
            boolean isSetOk;
            int x,y;
            if(myTurn) {
                do {
                    prt("Введите координаты в формате x y");
                    x = sc.nextInt() - 1; //Игрок указывает координаты считая от 1-цы
                    y = sc.nextInt() - 1; //Конвертируем это в нумерацию элементов массива
                    isSetOk = f1.setNode(x, y, myFig); //setNode проверяет чтобы мы повторно не сходили в одну и ту же ячейку
                } while (!isSetOk);
                if(f1.checkWinner(myFig))
                {
                    prt("Игрок победил!");
                    f1.showField();
                    break;
                }
                dot d = ap1.getStep();//Auto player return coordinates when he step in this time
                //d.showPosition();
                myTurn = false; //Ход переходит
            }
            else
            {
                do {
                    x = rand.nextInt(Field.FIELD_SIZE);
                    y = rand.nextInt(Field.FIELD_SIZE);
                    isSetOk = f1.setNode(x, y, compFig);
                } while (!isSetOk);
                if(f1.checkWinner(compFig))
                {
                    prt("Компьютер победил!");
                    f1.showField();
                    break;
                }
                f1.showField();
                //prt("Комп сходил " + (x+1) + " " + (y+1) + "\n");
                myTurn = true;
            }
        }
    }
}