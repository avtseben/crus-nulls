import java.util.ArrayList;
import java.util.Scanner;


public class Field {//В этом классе поле и все методы для манипуляции в ним

    private Scanner sc = new Scanner(System.in);
    private char field[][];
    public static int FIELD_SIZE = 10;
    public static int WIN_LENGTH;//Длина последовательности при которой наступает выйгрыш


    public Field() {
        MainClass.prt("Введите размер поля!");
        FIELD_SIZE = sc.nextInt(); //Игрок указывает координаты считая от 1-цы
        if (FIELD_SIZE > 4) WIN_LENGTH = 5;//Какое бы большое поле ни было, выйгрышная последовательность не более 4
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