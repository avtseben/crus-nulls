
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
