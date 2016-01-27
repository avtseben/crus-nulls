
import java.util.Scanner;

/**
 * Крестики - Нолики. Поле - объект  класса Field, где * - пустые игрок расставляет X , комьпютер  O.
 * Игра - бесконечны цикл. при каждой итерации проверяется наличие пустых ячеек. Перед ходом проверяется не занята ли клетка
 * Компьютер первый ход ставит на угад. Вторым шагом комьютер проверяет не ли угрозы проигрыша на след ход из за посторения полной линии
 * противником. Третьим проверяет не выйграет сам комьютер на след ход построив полную линию. Если три первых варианта не выполняются,
 * комьютер спокойно сторит свои линии.
 * Интелект в том чтобы в классе Field метод lineBuilder из существующих крестиков и ноликов на поле формирует объекты класса LineObj.
 * Объекты - линии имеют характеристики: начальная точка (x, y), направление роста и кол-во своих фишек в этой линии, а так же содержит
 * String переменную, в которой скопированая сама строка (например *О*О).
 * Потом, из списка объектов-линий выбирается та в которой больше всего своих фишек, и в нее дописывается следующая.
 * В качестве развития. Предполагается что комьпютер будет иметь дело с двумя списками ArrayList хранящими свои линии (O) и игрока(X) и основываясь
 * на максимально длинной линии принимать решение либо блокировать линию игрока либо наращивать свою
 */
public class MainClass {

    public static void prt(String s) {System.out.println(s);}
    public static int stepCounter = 0;//Счетчик ходов


    public static void main (String[] args)
    {

        Scanner sc = new Scanner(System.in);
        boolean myTurn = true;
        char Fig1 = 'X';
        char Fig2 = 'O';
        Field f1 = new Field();
        Player p1 = new CompPlayer(Fig1, f1);
        Player p2 = new CompPlayer(Fig2, f1);
	boolean p1LinePosibility = true;
	boolean p2LinePosibility = true;

        prt("Выбирите вариант игры:");
        prt("   1. Игрок против компьютера");
        prt("   2. Компьютер против компьютера");
        prt("   3. Человек против человека");
        int v = sc.nextInt();
        if(v == 1) {
            prt("Игра Крестики-Нолики. Вы играете крестиками, ваш ход первый");
            p1 = new HumanPlayer(Fig1, f1);
            //p2 = new CompPlayer(Fig2, f1);
        }
	if(v == 2) {
            prt("Игра Крестики-Нолики. Комьпютер против компьютера");
            //p1 = new CompPlayer(Fig1, f1);
            //p2 = new CompPlayer(Fig2, f1);
        }

        if(v == 3) {
            prt("Игра Крестики-Нолики. Человек против человека");
            p1 = new HumanPlayer(Fig1, f1);
            p2 = new HumanPlayer(Fig2, f1);
        }

        f1.showField();
	prt("=====Начинаем игру. Игрок№1 ходит крестиками======");
        while(f1.checkFreeNode()) {//Перед каждым ходом проверяе есть ли еще свободные ячейки
	if(stepCounter > 900) { prt("Сработала защита от бесконечного цикла"); break; }

	if(!(p1LinePosibility && p2LinePosibility)) {
	    prt("Ничья, никто не может построить свою линию");
	    break; 
	}
            stepCounter++;
            prt("---------Ход номер: " + stepCounter );
            if(myTurn) {
                p1LinePosibility = p1.doStep();
                if(f1.checkWinner(Fig1))
                {
                    prt("Игрок №1, " + p1.getType() + " , победил!" );
                    f1.showField();
                    break;
                }
                myTurn = false; //Ход переходит
            }
            else
            {
                p2LinePosibility = p2.doStep();//Компьютер ходит
                if(f1.checkWinner(Fig2))
                {
                    prt("Игрок №2, " + p2.getType() + " , победил!" );
                    f1.showField();
                    break;
                }
                f1.showField();
                myTurn = true;
            }
        }
    }
}
