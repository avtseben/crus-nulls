
import java.util.Random;
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
 * Потом из списка объектов-линий выбирается та в которой больше всего своих фишек, и в нее дописывается следующая.
 * В качестве развития. Предполагается что комьпютер будет иметь дело с двумя списками ArrayList хранящими свои линии (O) и игрока(X) и основываясь
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

         AutoPlayer ap2 = new AutoPlayer(compFig);
        f1.showField();

        while(f1.checkFreeNode()) {//Перед каждым ходом проверяе есть ли еще свободные ячейки
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
                myTurn = true;
            }
        }
    }
}

import java.util.ArrayList;
import java.util.Scanner;


public class Field {//В этом классе поле и все методы для манипуляции в ним

    private Scanner sc = new Scanner(System.in);
    private char field[][];
    public static int FIELD_SIZE;
    public static int WIN_LENGTH;//Длина последовательности при которой наступает выйгрыш


    public Field() {
        MainClass.prt("Введите размер поля!");
        FIELD_SIZE = sc.nextInt(); //Игрок указывает координаты считая от 1-цы
        if (FIELD_SIZE > 4) WIN_LENGTH = 4;//Какое бы большое поле ни было, выйгрышная последовательность не более 4
        else WIN_LENGTH = FIELD_SIZE;
        field = new char[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = '*';
            }
    }

    public void showField() {
        System.out.print(" ");
        for (int j = 0; j < FIELD_SIZE; j++)
            System.out.printf(" %2d", (j + 1));
        System.out.print("\n");
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.printf("%2d ", (i + 1));
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(field[i][j] + "  ");
            }
            System.out.print("\n");
        }
    }

    public void setNode(int _y, int _x, char _Fig) {
        field[_y][_x] = _Fig;
    }

    public boolean checkFreeNode() {
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == '*')
                    return true;
            }
        MainClass.prt("Game over");
        return false;
    }

    public boolean isCellEmpty(int _y, int _x) {
        if (_x >= 0 && _y >= 0 && _x < FIELD_SIZE && _y < FIELD_SIZE)
            if (field[_y][_x] == '*') return true;
        return false;
    }
    public boolean checkWinner(char _ch) {

        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == _ch)//Встретили символ
                {
                    if (checkLine(i, j, 0, 1, WIN_LENGTH, _ch)) return true;
                    if (checkLine(i, j, 1, 1, WIN_LENGTH, _ch)) return true;
                    if (checkLine(i, j, 1, 0, WIN_LENGTH, _ch)) return true;
                    if (checkLine(i, j, 1, -1, WIN_LENGTH, _ch)) return true;
                }
            }
        return false;
    }

    public boolean checkLine(int _y, int _x, int _vy, int _vx, int _l, char _ch) {
        if (_x + _vx * _l > FIELD_SIZE || _y + _vy * _l > FIELD_SIZE || _y + _vy * _l < -1 || _x + _vx * _l < -1)
            return false;
        for (int i = 0; i < _l; i++)
            if (field[_y + i * _vy][_x + i * _vx] != _ch) return false;
        return true;
    }

    public boolean checkPotentialLine(int _y, int _x, int _vy, int _vx, int _l ,char _ch) {//Проверяет можно ли вообще построить здесь свою динию, учитывая границы и фишки противника
        char Fig = _ch;
        char enemyFig;
        if(Fig == 'X') enemyFig = 'O';
        else enemyFig = 'X';

        if (_x + _vx * _l > FIELD_SIZE || _y + _vy * _l > FIELD_SIZE || _y + _vy * _l < -1 || _x + _vx * _l < -1)
            return false;
        for (int i = 0; i < _l; i++)
            if (field[_y + i * _vy][_x + i * _vx] == enemyFig) return false;
        return true;
    }
    public LineObj lineBuilder(int _y, int _x, int _vy, int _vx, int _l, char _ch, String _dir) {//Производство линий.

        int length = 0;
        String lineString = "";

         for (int i = 0; i < _l; i++) {
             if (field[_y + i * _vy][_x + i * _vx] == _ch) length++;
             lineString += field[_y + i * _vy][_x + i * _vx];

         }
        LineObj line = new LineObj(_y, _x, length, _dir, lineString);
        return line;
    }

    public ArrayList listLineBuilder(char _ch) {//Метод анализирует игровое поле и создает список возможных линий
        ArrayList lineList = new ArrayList();

        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                if(checkPotentialLine(i,j, 0, 1, WIN_LENGTH, _ch)) lineList.add(lineBuilder(i,j, 0, 1, WIN_LENGTH, _ch, "gorizont"));//Упаковка линий в коробку
                if(checkPotentialLine(i,j, 1, 1, WIN_LENGTH, _ch)) lineList.add(lineBuilder(i,j, 1, 1, WIN_LENGTH, _ch, "diagonal"));
                if(checkPotentialLine(i,j, 1, 0, WIN_LENGTH, _ch)) lineList.add(lineBuilder(i,j, 1, 0, WIN_LENGTH, _ch, "vertical"));
                if(checkPotentialLine(i,j, 1, -1, WIN_LENGTH, _ch)) lineList.add(lineBuilder(i,j, 1, -1, WIN_LENGTH, _ch, "rdiagonal"));

            }
        return lineList;
    }
}


public class LineObj {//Объект линия. Это потенциальная линия которую можно построить.

    private int y;
    private int x;
    private int length;
    private String dir;
    private String lineString;

    public LineObj(int _y,int _x,int _length,String _dir,String _lineString) {
        y = _y;
        x = _x;
        length = _length;
        dir = _dir;
        lineString = _lineString;
    }
    public int getLength()
    {
        return length;
    }
    public String getlineString() { return lineString; }
    public String getDir()
    {
        return dir;
    }
    public int getI()
    {
        return y;
    }
    public int getJ()
    {
        return x;
    }
}

import java.util.ArrayList;

public class AutoPlayer {//Класс компьютерого игрока. Здесь весь интелект

    private char Fig;
    private char enemyFig;
    private static ArrayList lineList = new ArrayList();

    public AutoPlayer(char _Fig)
    {
        Fig = _Fig;
        if(Fig == 'X') enemyFig = 'O';
        else enemyFig = 'X';
    }
    public boolean myFirstStep()
    {
        LineObj line = (LineObj) lineList.get(getLongest());
        if(line.getLength() == 0) return true;
        else return false;
    }


    public void doStep()
    {
        int x,y;
        String stepDesition = "";
        lineList = MainClass.f1.listLineBuilder(Fig);//Собирает информацию о линиях на поле

        if(myFirstStep()) {
            //1. Если для это мой первый ход. Случайно подбираем координаты
            stepDesition = "random";
            do {
                x = MainClass.rand.nextInt(Field.FIELD_SIZE);
                y = MainClass.rand.nextInt(Field.FIELD_SIZE);
            } while (!MainClass.f1.isCellEmpty(y, x));
            MainClass.f1.setNode(y, x, Fig);
            MainClass.prt("Comp Step Desition is: " + stepDesition);
        }
        else {
           //2.Проверка на шах и мат
            for (int i = 0; i < Field.FIELD_SIZE; i++)
                for (int j = 0; j < Field.FIELD_SIZE; j++) {
                    if (MainClass.f1.isCellEmpty(i, j)) {
                        MainClass.f1.setNode(i, j, enemyFig);//Пробуем поставить на угад за противника
                        if (stepDesition != "StopEnemy!" && MainClass.f1.checkWinner(enemyFig))//И если видим что он при этом выйграет. То сразу же блокируем
                        {
                            y = i;
                            x = j;
                            stepDesition = "StopEnemy!";
                            MainClass.f1.setNode(y, x, Fig);
                        } else
                            MainClass.f1.setNode(i, j, '*');
                    }
                }

            for (int i = 0; i < Field.FIELD_SIZE; i++)
                for (int j = 0; j < Field.FIELD_SIZE; j++) {
                    if (MainClass.f1.isCellEmpty(i, j)) {
                        MainClass.f1.setNode(i, j, Fig);
                        if (stepDesition != "StopEnemy!" && MainClass.f1.checkWinner(Fig)) {
                            y = i;
                            x = j;
                            stepDesition = "CheckMate!";
                            MainClass.f1.setNode(y, x, Fig);
                        } else
                            MainClass.f1.setNode(i, j, '*');
                    }
                }
        }
        if(!(stepDesition == "random" || stepDesition == "CheckMate!" || stepDesition == "StopEnemy!")) {
            stepDesition = "fillMyLine"; //Если это не первый мой ход и мат не поставишь и нет нужды блокировать протизника
            fillLineOnField();//Заполняем свои линии
            MainClass.prt("Comp Step Desition is: " + stepDesition);
        }

    }

    public int getLongest()//Возвращает номер(в списке) самой длинной линии
    {
        int len;
        int li = 0;
        int maxlen = 0;
        //lineList = MainClass.f1.listLineBuilder(Fig);
        for (int i = 0; i < lineList.size(); i++) {
            len = ((LineObj)lineList.get(i)).getLength();
            if(len > maxlen) {
                maxlen = len;
                li = i;
            }
        }
        return li;
    }
    public void pushChar(int _y, int _x, int _vy, int _vx, char _ch, String _line)
    {
        for(int n = 0; n < Field.WIN_LENGTH; n++)
        {
            if(_line.charAt(n) == '*') {//Вставляем там где пусто
                MainClass.f1.setNode((_y + _vy * n), (_x + _vx * n), Fig);
                MainClass.prt("I push in " + (_y + _vy * n) + " " + (_x + _vx * n));
                break;
            }
        }
    }

    public void fillLineOnField()//Метод построения линии
    {
        LineObj line = (LineObj) lineList.get(getLongest());//Берем линию в которой больше всего уже поставлено наших фишек
        int y = line.getI();
        int x = line.getJ();
        String lineString = line.getlineString();
        //И в зависимости от направления приминяем метод заполнения линии
        if(line.getDir() == "gorizont") pushChar(y, x, 0, 1, Fig, lineString);
        if(line.getDir() == "diagonal") pushChar(y, x, 1, 1, Fig, lineString);
        if(line.getDir() == "vertical") pushChar(y, x, 1, 0, Fig, lineString);
        if(line.getDir() == "rdiagonal") pushChar(y, x, 1, -1, Fig, lineString);
    }
}
