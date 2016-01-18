/**
 * Created by avtseben on 11.01.2016.
 */
public class LineObj {

    private int x;
    private int y;
    private int length;
    private String dir;
    private String lineString;

    public LineObj(int _x,int _y,int _length,String _dir,String _lineString) {
        x = _x;
        y = _y;
        length = _length;
        dir = _dir;
        lineString = _lineString;
    }
    public int getLength()
    {
        return length;
    }
    public String getlineString()
    {  
        return lineString;
    }
    public String getDir()
    {
        return dir;
    }
    public int getI()
    {
        return x;
    }
    public int getJ()
    {
        return y;
    }




    public dot getDot()
    {
        dot d;
/*        dot d;
        if(lineString == "bidir")
        {
            if(dir == "gorizontal")
                return d = new dot(x, y+length);
        }
        return null;
*/
        return d = new dot(x, y);
    }
}
