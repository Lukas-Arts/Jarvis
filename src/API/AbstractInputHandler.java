package API;

/**
 * Created by lukas on 16.04.15.
 *
 * implement this interface if you want to get a direct response from the user
 * you can request the next input to be directed to this class by calling
 * QuestionListener.redirectNextInput(this);
 * note that only the next input is directed to this class. all inputs after that
 * are handled normally if you don't call redirectNextInput again..
 */
public abstract class AbstractInputHandler
{
    protected static AbstractInputHandler ih=null;
    public abstract void newInput(String s);
    public static void redirectNextInput(AbstractInputHandler handler)
    {
        ih=handler;
    }
    public static boolean isNextInputRedirected(){
        return ih==null;
    }
}
