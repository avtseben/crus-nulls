import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by avtseben on 04.01.2016.
 */
public class Field {

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


    public ArrayList lineBuilder(char _ch) {
        ArrayList lineList = new ArrayList();//List of lines

        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == _ch)//Встретили символ
                {
                    /*С этого момента от точки просматриваются 4 направления(вправо,диагональ вправо-вниз, горизонталь вниз, дагональ влево-вниз)
                    * В начале устанавливается тип линии bidir (неограничена ни с одной стороны), направление (default),
                    * Далее циклом просматриваются все клетки вправо dir устанавливается gorizontal есть встречается препятствие тип
                    * становится endBlocked и если длины до победы не хватает анализируем влевую сторону есть ли теоретическая возможность достроить
                    * линию до WIN_LENGTH если нет такой возможности линия получает тип dead и объект не создается. Компьютер
                    * в своей стратегии не додлжен рассматривать такие линии
                    */
                    int length = 1;
                    String type = "bidir";
                    String dir = "default";
                    boolean lineEnd = false;
                    //--------------Горизонт--------
                    if ((j == 0) || (field[i][j - 1] != _ch)) {//Идем вправо, если слева нет нашего символа(если он есть значит линия уже была создана) и если есть перспектива построения линии
                        int n = 1;
                        dir = "gorizontal";
                        if (j + 1 == FIELD_SIZE) type = "endBlocked";//Если у правого края
                        for (n = 1; n < (FIELD_SIZE - j); n++) {
                            if (field[i][j + n] == _ch && lineEnd == false) length++;
                            else {
                                lineEnd = true;
                                if (field[i][j + n] != '*' && field[i][j + n] != _ch || (j + 1) == FIELD_SIZE) {
                                    type = "endBlocked";//Уперлись справа
                                    break;
                                }
                            }
                        }
                        if (type == "endBlocked" && j == 0 && length < WIN_LENGTH)
                            type = "dead";//Если стоим у левого края и прижаты
                        else if (type == "endBlocked" && j > 0 && field[i][j - 1] != '*' && field[i][j - 1] != _ch && length < WIN_LENGTH)
                            type = "dead";//Если справа ограничены и слева враг
                        else if (type == "endBlocked" && j + length < WIN_LENGTH) type = "dead";//Не наберем длины
                        else if (j > 0 && type == "endBlocked") {//Заглянем влево
                            for (n = 1; n <= (FIELD_SIZE - (FIELD_SIZE - j)); n++) {
                                if ((field[i][j - n] != '*' && field[i][j - n] != _ch || j == 0) && length < WIN_LENGTH) {//Если  длина блокируется вражеским символом, то лининя считается не перспективной
                                    type = "dead";//Уперлись слева
                                    break;
                                }
                            }
                        }
                        if (type != "dead")
                            lineList.add(new LineObj(i, j, length, dir, type));
                    }
                    //------Диагональ-----
                    if ((i == 0) || (j == 0) || (field[i - 1][j - 1] != _ch)) {//!!!!убрал лишнее
                        int n = 1;
                        dir = "diagonal";
                        lineEnd = false;
                        if (i + 1 == FIELD_SIZE || j + 1 == FIELD_SIZE) type = "endBlocked";
                        for (n = 1; (n < FIELD_SIZE - i) && (n < FIELD_SIZE - j); n++) {
                            if (field[i + n][j + n] == _ch && lineEnd == false) length++;
                            else {
                                lineEnd = true;
                                if (field[i + n][j + n] != '*' && field[i + n][j + n] != _ch || (i + n + 1) == FIELD_SIZE || (j + n + 1) == FIELD_SIZE) {//!!!!Добавил + n
                                    type = "endBlocked";//Уперлись
                                    break;
                                }
                            }
                        }
                        if (type == "endBlocked" && (i == 0 || j == 0) && length < WIN_LENGTH)
                            type = "dead";//Если стоим у левого края и прижаты
                        else if (type == "endBlocked" && (i > 0 || j > 0) && field[i - 1][j - 1] != '*' && field[i - 1][j - 1] != _ch && length < WIN_LENGTH)
                            type = "dead";
                        else if (type == "endBlocked" && (i + length < WIN_LENGTH || j + length < WIN_LENGTH))
                            type = "dead";//Не наберем длины
                        else if ((i > 0 || j > 0) && type == "endBlocked") {//Заглянем влево
                            for (n = 1; (n <= (FIELD_SIZE - (FIELD_SIZE - i))) && (n <= (FIELD_SIZE - (FIELD_SIZE - j))); n++) {
                                if ((field[i - n][j - n] != '*' && field[i - n][j - n] != _ch || j == 0) && length < WIN_LENGTH) {//Если  длина блокируется вражеским символом, то лининя считается не перспективной
                                    type = "dead";//Уперлись слева
                                    break;
                                }
                            }
                        }
                        if (type != "dead")
                            lineList.add(new LineObj(i, j, length, dir, type));
                    }
                    //------Вертикаль-----
                    if ((i == 0) || (field[i - 1][j] != _ch)) {//Идем вправо, если слева нет нашего символа(если он есть значит линия уже была создана) и если есть перспектива построения линии
                        int n = 1;
                        dir = "vertical";
                        if (i + 1 == FIELD_SIZE) type = "endBlocked";
                        for (n = 1; n < (FIELD_SIZE - i); n++) {
                            if (field[i + n][j] == _ch && lineEnd == false) length++;
                            else {
                                lineEnd = true;
                                if (field[i + n][j] != '*' && field[i + n][j] != _ch || (i + 1) == FIELD_SIZE) {
                                    type = "endBlocked";//Уперлись справа
                                    break;
                                }
                            }
                        }
                        if (type == "endBlocked" && i == 0 && length < WIN_LENGTH)
                            type = "dead";//Если стоим у левого края и прижаты
                        else if (type == "endBlocked" && i > 0 && field[i - 1][j] != '*' && field[i - 1][j] != _ch && length < WIN_LENGTH)
                            type = "dead";//Если справа ограничены и слева враг
                        else if (type == "endBlocked" && j + length < WIN_LENGTH) type = "dead";//Не наберем длины
                        else if (i > 0 && type == "endBlocked") {//Заглянем влево
                            for (n = 1; n <= (FIELD_SIZE - (FIELD_SIZE - i)); n++) {
                                if ((field[i - n][j] != '*' && field[i - n][j] != _ch || i == 0) && length < WIN_LENGTH) {//Если  длина блокируется вражеским символом, то лининя считается не перспективной
                                    type = "dead";//Уперлись слева
                                    break;
                                }
                            }
                        }
                        if (type != "dead")
                            lineList.add(new LineObj(i, j, length, dir, type));
                    }
                    //------Обратная Диагональ-----
                    if ((i == 0) || (j == FIELD_SIZE - 1) || (field[i - 1][j + 1] != _ch)) {//!!!!убрал лишнее
                        int n = 1;
                        dir = "rediagonal";
                        lineEnd = false;
                        if (i + 1 == FIELD_SIZE || j == 0) type = "endBlocked";
                        for (n = 1; (n < FIELD_SIZE - i) && (n <= (FIELD_SIZE - (FIELD_SIZE - j))); n++) {
                            if (field[i + n][j - n] == _ch && lineEnd == false) length++;
                            else {
                                lineEnd = true;
                                if (field[i + n][j - n] != '*' && field[i + n][j - n] != _ch || (i + 1) == FIELD_SIZE || j == 0) {
                                    type = "endBlocked";//Уперлись
                                    break;
                                }
                            }
                        }
                        if (type == "endBlocked" && (i == 0 || j + 1 == FIELD_SIZE) && length < WIN_LENGTH)
                            type = "dead";
                        else if (type == "endBlocked" && (i > 0 || j + 1 < FIELD_SIZE) && field[i - 1][j + 1] != '*' && field[i - 1][j + 1] != _ch && length < WIN_LENGTH)
                            type = "dead";
                        else if (type == "endBlocked" && (i + length < WIN_LENGTH || j + length < WIN_LENGTH))
                            type = "dead";
                        else if ((i > 0 || j + 1 < FIELD_SIZE) && type == "endBlocked") {
                            for (n = 1; (n <= (FIELD_SIZE - (FIELD_SIZE - i))) && (n < FIELD_SIZE - j); n++) {
                                if ((field[i - n][j + n] != '*' && field[i - n][j + n] != _ch || j + 1 == FIELD_SIZE) && length < WIN_LENGTH) {
                                    type = "dead";//Уперлись слева
                                    break;
                                }
                            }
                        }
                        if (type != "dead")
                            lineList.add(new LineObj(i, j, length, dir, type));
                    }
                    //-----Конец поисков-----
                }

            }
        return lineList;
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

    public boolean checkPotentialLine(int _y, int _x, int _vy, int _vx, int _l ,char _ch) {
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
    public boolean lineBuilder2(int _y, int _x, int _vy, int _vx, int _l, char _ch) {

        int length = 0;
        String lineString = "l";

         for (int i = 0; i < _l; i++) {
             if (field[_y + i * _vy][_x + i * _vx] == _ch) length++;
             lineString += field[_y + i * _vy][_x + i * _vx];

         }
        MainClass.prt("lineString " + lineString + "length " + length);
        return true;
    }

    public void listLineBuilder(char _ch) {
        //ArrayList lineList = new ArrayList();//List of lines
        //int length;
        //String dir;
        //String lineString;

        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                if(checkPotentialLine(i,j, 0, 1, WIN_LENGTH, _ch)) lineBuilder2(i,j, 0, 1, WIN_LENGTH, _ch);
                if(checkPotentialLine(i,j, 1, 1, WIN_LENGTH, _ch)) lineBuilder2(i,j, 1, 1, WIN_LENGTH, _ch);
                if(checkPotentialLine(i,j, 1, 0, WIN_LENGTH, _ch)) lineBuilder2(i,j, 1, 0, WIN_LENGTH, _ch);
                if(checkPotentialLine(i,j, 1, -1, WIN_LENGTH, _ch)) lineBuilder2(i,j, 1, -1, WIN_LENGTH, _ch);
                   // lineList.add(new LineObj(i, j, length, dir, lineString));

            }
    }
}