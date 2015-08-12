package API;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lukas on 09.04.15.
 */
public interface SpeechRecognitionInterface
{
    public String processAudio(String audioFile)throws IOException;
    public String processAudio(InputStream in)throws IOException;
}
