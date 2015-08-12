import API.AbstractAction;
import API.LanguageManager;
import API.SettingsManager;

/**
 * Created by lukas on 30.04.15.
 */
public class SimpleQAHelper extends AbstractAction{

    @Override
    public String executeAction(String[] params) {
        return LanguageManager.getAnswer("./addons/actions/SimpleQAHelper/answers."+ LanguageManager.lang,params[0]);
    }

    @Override
    public String getInfo() {
        return null;
    }
}
