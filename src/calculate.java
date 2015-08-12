import API.AbstractAction;
import API.ActionExecuter;

/**
 * Created by lukas on 26.03.15.
 */
public class calculate extends AbstractAction {
    @Override
    public String executeAction(String[] params) {
        switch (params.length){
            case 1:{
                String formula=params[0].replace(" plus "," + ").replace(" minus "," - ").replace(" devided by "," / ")
                        .replace(" multiplied by "," * ").replace(" power "," ^ ").replace(" equals "," = ")
                        .replace(" fx "," f(x) ").replace(" ","");
                formula=formula.replace("+", "" + "%" + Integer.toHexString('+')).replace("/", "%" + Integer.toHexString('/'))
                        .replace("^", "%" + Integer.toHexString('^')).replace("=", "%" + Integer.toHexString('='))
                        .replace("(", "%" + Integer.toHexString('(')).replace("(", "%" + Integer.toHexString(')'));
                System.out.println(formula);
                ActionExecuter.executeAction("browser",new String[]{"http://www.wolframalpha.com/input/?i="+formula});
                break;
            }
        }
        return "";
    }

    @Override
    public String getInfo() {
        return  "Calculate Action: \n" +
                "uses http://www.wolframalpha.com to calculate mathematical expressions\n"+
                "- 1 parameter: the mathamatical expression which should be solved";
    }

    private String compileResult(String s)
    {
        //TODO: get result from website and render into a html text to display it in a popup/JEditArea
        return s;
    }
}
