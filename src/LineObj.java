/**
 * Created by avtseben on 11.01.2016.
 */
public class LineObj {

    private int x;
    private int y;
    private int length;
    private String dir;
    private String type;

    public LineObj(int _x,int _y,int _length,String _dir,String _type) {
        x = _x;
        y = _y;
        length = _length;
        dir = _dir;
        type = _type;
    }
    public int getLength()
    {
        return length;
    }
    public String getType()
    {  
        return type;
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
        if(type == "bidir")
        {
            if(dir == "gorizontal")
                return d = new dot(x, y+length);
        }
        return null;
*/
        return d = new dot(x, y);
    }
}
