public class dot
{
    int i,j;
    public dot(int _i,int _j) {
        i = _i;
        j = _j;
    }
    public int getI()
    {
        return i;
    }
    public int getJ()
    {
        return j;
    }
    public void showPosition()
    {
        MainClass.prt("Dot position: i = " + i + " j = " + j);
    }
}
