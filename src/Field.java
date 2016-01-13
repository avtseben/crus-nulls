/**
 * Created by avtseben on 04.01.2016.
 */
public class Field {

    private char field[][];
    public static int FIELD_SIZE = 10;
    public static int WIN_LENGTH = FIELD_SIZE;//Длина последовательности при которой наступает выйгрыш

    public Field() {
        if (FIELD_SIZE > 4) WIN_LENGTH = 4;//Какое бы большое поле ни было, выйгрышная последовательность не более 4
        field = new char[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = '*';
            }
    }

    public void showField() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(field[i][j] + "  ");
            }
            System.out.print("\n");
        }
    }

    public boolean setNode(int _x, int _y, char _Fig) {
        if (field[_y][_x] == '*') {
            field[_y][_x] = _Fig;
            return true;
        } else
            return false;

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

    public boolean checkWinner(char _ch) {

        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == _ch)//Встретили символ
                {
                    int counter = 1;//Устанавливаем счетчик в 1 (один символ уже есть)
                    if ((FIELD_SIZE - j) >= WIN_LENGTH) {//Есть ли смысл смотреть на "восток" если до конца поля не наберется победной длинны
                        for (int n = 1; n < WIN_LENGTH; n++) {
                            if (field[i][j + n] == _ch) counter++;//Идем влево
                            else break;
                        }
                        if (counter == WIN_LENGTH)
                            return true;//Если просмотрели влево и собрали победную последовательность
                        else if ((FIELD_SIZE - i) >= WIN_LENGTH)//Иначе смотрим на юго-восток, если мы не у южных гранц
                        {
                            counter = 1;
                            for (int n = 1; n < WIN_LENGTH; n++) {
                                if (field[i + n][j + n] == _ch) counter++;//Идем по диагонали вперел и вниз
                                else break;
                            }
                        }
                        if (counter == WIN_LENGTH) return true;//Победила диагональ слева вниз
                    }
                    counter = 1;
                    if ((FIELD_SIZE - i) >= WIN_LENGTH) {//Есть ли смысл смотреть на "юг" если до конца поля не наберется победной длинны
                        for (int n = 1; n < WIN_LENGTH; n++) {
                            if (field[i + n][j] == _ch) counter++;//Идем вниз
                            else break;
                        }
                        if (counter == WIN_LENGTH) return true;
                        else if ((j + 1) >= WIN_LENGTH)//Иначе смотрим на юго-запад, Если мы не около западных границ
                        {
                            counter = 1;
                            for (int n = 1; n < WIN_LENGTH; n++) {
                                if (field[i + n][j - n] == _ch) counter++;//Идем по диагонали назад и вниз
                                else break;
                            }
                        }
                        if (counter == WIN_LENGTH) return true;
                    }

                }
            }
        return false;
    }

    public LineObj lineBuilder(char _ch) {
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] == _ch)//Встретили символ
                {
                    int length = 1;
                    String type = "bidir";
                    String dir;
                    for (int n = 1; n <= (FIELD_SIZE - j); n++) {
                        if (field[i][j + n] == _ch) length++;//Идем влево
                        else if (length >= 1)//Finish to search line end, line at least 2 points
                        {
                            dir = "east";
                            LineObj l = new LineObj(i, j, length, dir, type);
                            return l;
                        }
                    }
                }
            }
        return null;
    }
}
