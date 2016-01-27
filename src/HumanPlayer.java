import java.util.Scanner;

public class HumanPlayer extends Player {

    Scanner sc = new Scanner(System.in);
    private String playerType;

    public HumanPlayer (char _Fig, Field _f)
    {
        super(_Fig, _f);
        playerType = "Человек";
    }
    public String getType() { return playerType; }
    public boolean doStep()
    {
        int x,y;
        do {
            MainClass.prt("Введите координаты в формате x y");
            x = sc.nextInt() - 1; //Игрок указывает координаты считая от 1-цы
            y = sc.nextInt() - 1; //Конвертируем это в нумерацию элементов массива
        } while (!targetField.isCellEmpty(y, x));
        targetField.setNode(y, x, Fig);
	return true;
    }
}
