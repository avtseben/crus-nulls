import java.util.Random;

public class CompPlayer extends Player {

    private Random rand = new Random();
    private String playerType;

    public CompPlayer (char _Fig, Field _f)
    {
        super(_Fig, _f);
        if(Fig == 'X') enemyFig = 'O';
        else enemyFig = 'X';
        playerType = "Компьютер";
    }

    public String getType() { return playerType; }
/*     private boolean myFirstStep()
    {
        LineObj line = (LineObj) lineList.get(getLongest());
        if(line.getLength() == 0) return true;
        else return false;
    }*/
    private boolean myFirstStep()
    {
        if (MainClass.stepCounter < 3) return true;//Первые ходы Компьютера
        else return false;
    }


     public boolean doStep()
    {
        int x,y;
        String stepDesition = "";
        lineList = targetField.listLineBuilder(Fig);//Собирает информацию о линиях на поле
        if(myFirstStep()) {
            //1. Если для это мой первый ход. Случайно подбираем координаты
            stepDesition = "random";
            do {
                x = rand.nextInt(Field.FIELD_SIZE);
                y = rand.nextInt(Field.FIELD_SIZE);
            } while (!targetField.isCellEmpty(y, x));
            targetField.setNode(y, x, Fig);
            MainClass.prt("Comp Step Desition is: " + stepDesition);
	    return true;
        }
        else {
            //2.Проверка на шах и мат
            for (int i = 0; i < Field.FIELD_SIZE; i++)
                for (int j = 0; j < Field.FIELD_SIZE; j++) {
                    if (targetField.isCellEmpty(i, j)) {
                        targetField.setNode(i, j, enemyFig);//Пробуем поставить на угад за противника
                        if (stepDesition != "StopEnemy!" && targetField.checkWinner(enemyFig))//И если видим что он при этом выйграет. То сразу же блокируем
                        {
                            y = i;
                            x = j;
                            stepDesition = "StopEnemy!";
                            targetField.setNode(y, x, Fig);
                            MainClass.prt("Comp Step Desition is: " + stepDesition);
                            MainClass.prt("Block in:" + (j+1) + " " + (i+1));
			    return true;
                        } else
                            targetField.setNode(i, j, '*');
                    }
                }
            for (int i = 0; i < Field.FIELD_SIZE; i++)
                for (int j = 0; j < Field.FIELD_SIZE; j++) {
                    if (targetField.isCellEmpty(i, j)) {
                        targetField.setNode(i, j, Fig);
                        if (stepDesition != "StopEnemy!" && stepDesition != "CheckMate!" && targetField.checkWinner(Fig)) {
                            y = i;
                            x = j;
                            stepDesition = "CheckMate!";
                            targetField.setNode(y, x, Fig);
                            MainClass.prt("Comp Step Desition is: " + stepDesition);
                            MainClass.prt("Mate in:" + (j+1) + " " + (i+1));
			    return true;
                        } else
                            targetField.setNode(i, j, '*');
                    }
                }

        }
        if(!(stepDesition == "random" || stepDesition == "CheckMate!" || stepDesition == "StopEnemy!" || lineList.isEmpty() )) {
            stepDesition = "fillMyLine"; //Если это не первый мой ход и мат не поставишь и нет нужды блокировать протиdника
            fillLineOnField();//Заполняем свои линии
            MainClass.prt("Comp Step Desition is: " + stepDesition);
	    return true;
        }
	//Ходы кончились
        if(lineList.isEmpty()) MainClass.prt("Нет возможности построить линию");
	return false;
    }

    private int getLongest()//Возвращает номер(в списке) самой длинной линии
    {
        int len;
        int li = 0;
        int maxlen = 0;
        //lineList = targetField.listLineBuilder(Fig);
        for (int i = 0; i < lineList.size(); i++) {
            len = ((LineObj)lineList.get(i)).getLength();
            if(len > maxlen) {
                maxlen = len;
                li = i;
            }
        }
        return li;
    }
    private void pushChar(int _y, int _x, int _vy, int _vx, char _ch, String _line)
    {
        for(int n = 0; n < Field.WIN_LENGTH; n++)
        {
            if(_line.charAt(n) == '*') {//Вставляем там где пусто
                targetField.setNode((_y + _vy * n), (_x + _vx * n), Fig);
                MainClass.prt("My step is: " + ((_x + _vx * n)+1) + " " + ((_y + _vy * n)+1));
                break;
            }
        }
    }

    private void fillLineOnField()//Метод построения линии
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
