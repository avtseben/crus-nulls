import java.util.Scanner;

public class HumanPlayer extends Player {

    Scanner sc = new Scanner(System.in);

    public HumanPlayer (char _Fig, Field _f)
    {
        super(_Fig, _f);

    }
    public void doStep()
    {
        int x,y;
        do {
            MainClass.prt("Введите координаты в формате x y");
            x = sc.nextInt() - 1; //Игрок указывает координаты считая от 1-цы
            y = sc.nextInt() - 1; //Конвертируем это в нумерацию элементов массива
        } while (!targetField.isCellEmpty(y, x));
        targetField.setNode(y, x, Fig);
        if(targetField.checkWinner(Fig))
        {
            MainClass.prt("Игрок победил!");
            targetField.showField();
        }
    }
}
