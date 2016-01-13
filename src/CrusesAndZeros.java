import java.util.Random;
import java.util.Scanner;

/**
 * Created by avtseben on 04.01.2016.
 */
public class CrusesAndZeros {

    public static Random rand = new Random();
    public static void prt(String s) {System.out.println(s);}

    public static void main (String[] args)
    {
        Scanner sc = new Scanner(System.in);
        Field f1 = new Field();
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
                LineObj l = f1.lineBuilder(myFig);
                prt("TEST = " + l.getLength());
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