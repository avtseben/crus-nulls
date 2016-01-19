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
