package logic;

import API.SettingsManager;
import API.SpeechRecognitionInterface;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lukas on 09.04.15.
 */
public class GoogleSpeechRecognition implements SpeechRecognitionInterface{

    private static String apiKey= SettingsManager.getProperty("google_api_key");
    @Override
    public String processAudio(String audioFile) throws IOException{
        if(apiKey.equalsIgnoreCase("")){
            System.err.println("No Google-API-Key!");
            throw new IOException();
        }
        //speech recognition
        System.out.println("sending speech recognition request..");
        HttpURLConnection connection= (HttpURLConnection) new URL("https://www.google.com/speech-api/v2/recognize?output=json&lang=de-de&key="+apiKey).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("data-binary", "@" + audioFile);
        connection.setRequestProperty("Content-Type","audio/l16; rate=16000;");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        //send audiofile
        OutputStream out=connection.getOutputStream();
        FileInputStream fi=new FileInputStream(audioFile);
        byte b[]=new byte[fi.available()];
        fi.read(b);
        out.write(b);
        out.flush();
        fi.close();
        out.close();
        //get response
        BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String s=null;
        String s2="";
        while((s=in.readLine())!=null){
            //System.out.println(s);
            s2+=" "+s;
        }
        String finalstring="";
        try{
            //System.out.println(s2);
            //s2 format:
            //s2="{\"result\":[]}\n" +
            // "{\"result\":[{\"alternative\":[{\"transcript\":\"test test\",\"confidence\":0.85473549},{\"transcript\":\"test Test\"},{\"transcript\":\"testtest\"},{\"transcript\":\"test test test\"},{\"transcript\":\"Test Test\"}],\"final\":true}],\"result_index\":0}";
            String s3[]=s2.split("\\[")[3].split("\\{");
            ArrayList<String> answers=new ArrayList<>();
            for(String s4:s3){
                String s5[]=s4.split("\"");
                if(s5.length>4)s4=s5[3];
                System.out.println(s4);
                answers.add(s4);
                if(s5.length>6)if(!(s5[6].contains("true")||s5[6].contains("false")))
                    System.out.println(" | confidence: "+s5[6].replace(":","").replace("},", ""));
            }
            System.out.println();
            String s4[]=s2.split("\"");
            String s5=s4[s4.length-1].replace(":", "");
            s5=s5.replace("}","");//result_index
            finalstring=answers.get(Integer.parseInt(s5) + 1);
            //System.out.println(s5);
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Unable to extract answer! \nFull Response: \n"+s2);
            throw new IOException();
        }
        System.out.println(finalstring);
        return finalstring;
    }

    @Override
    public String processAudio(InputStream in) throws IOException {
        return null;
    }
    /**
     * String strUrl = (request.getParameter("urls")+"" != null)?request.getParameter("urls")+"":SPEECH_TEXT_TO_SERVICE;
     //SPEECH_TEXT_TO_SERVICE;
     //
     String USER_AGENT =  "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:11.0) " + "Gecko/20100101 Firefox/11.0";

     URL url = new URL(strUrl);
     Proxy proxy =new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.53.29", 3128));
     HttpURLConnection connection=(HttpURLConnection) url.openConnection(proxy);
     connection.setRequestMethod("GET");
     connection.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.70 Safari/537.36");

     out.println(new Date().toString().substring(11,20).replaceAll("IST 2013 ","")+" =====> Starting downloading <br>");
     connection.connect();
     for (int i=0;connection.getHeaderField(i) != null;i++)
     {
     //out.println(connection.getContentType());
     }
     Long time = connection.getDate();
     InputStream inputStream = connection.getInputStream();
     File input = new File(time+"_input.wav");

     FileOutputStream f = new FileOutputStream(input);
     OutputStream outputStream = f;
     int read = 0;
     byte[] bytes = new byte[1024];
     while ((read = inputStream.read(bytes)) != -1)
     {
     outputStream.write(bytes, 0, read);
     }
     outputStream.close();
     AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(input);
     AudioFormat audioFormat1 = audioInputStream1.getFormat();
     int sampleRate =(int) audioFormat1.getSampleRate();

     out.println(new Date().toString().substring(11,20)+" =====> Create file <br>");
     File output = new File(time+"output.flac");
     out.println(new Date().toString().substring(11,20)+" =====> FLAC Convert Start <br>");

     StreamConfiguration streamConfiguration = new StreamConfiguration();
     streamConfiguration.setSampleRate(sampleRate);
     streamConfiguration.setBitsPerSample(16);
     streamConfiguration.setChannelCount(1);

     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(input);
     AudioFormat format = audioInputStream.getFormat();

     int frameSize = format.getFrameSize();

     FLACEncoder flacEncoder = new FLACEncoder();
     FLACFileOutputStream flacOutputStream = new FLACFileOutputStream(output);

     flacEncoder.setStreamConfiguration(streamConfiguration);
     flacEncoder.setOutputStream(flacOutputStream);

     flacEncoder.openFLACStream();

     int[] sampleData = new int[(int) audioInputStream.getFrameLength()];
     byte[] samplesIn = new byte[frameSize];

     int x = 0;

     while (audioInputStream.read(samplesIn, 0, frameSize) != -1) {
     if (frameSize != 1)
     {
     ByteBuffer bb = ByteBuffer.wrap(samplesIn);
     bb.order(ByteOrder.LITTLE_ENDIAN);
     short shortVal = bb.getShort();
     sampleData[x] = shortVal;
     } else {
     sampleData[x] = samplesIn[0];
     }

     x++;
     }

     flacEncoder.addSamples(sampleData, x);
     flacEncoder.encodeSamples(x, false);
     flacEncoder.encodeSamples(flacEncoder.samplesAvailableToEncode(), true);

     audioInputStream.close();
     flacOutputStream.close();
     out.println(new Date().toString().substring(11,20)+" =====> Convet End <br>");
     out.println(new Date().toString().substring(11,20)+" =====> Send google api Start <br>");

     StringBuilder sb = new StringBuilder("https://www.google.com/speech-api/v1/recognize?client=chromium&lang=en-US&maxresults=10");
     */
}
