package History;

import javax.swing.*;
import java.io.*;
import java.sql.Struct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.SimpleFormatter;

public class StoryMessage {
    private static final String NAME_FILE = "History_%nick.txt";
    private String namefile;
    private static final int MAX_MESSAGE = 100;
    private boolean isFileExist;

    public StoryMessage(String nick) {
        isFileExist = true;
        namefile = NAME_FILE.replace("%nick",nick.toUpperCase());
        File file = new File(namefile);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                this.isFileExist = false;
                e.printStackTrace();
            }
        }

    }

    public void writeMessage(String nick, String message){

        if (!isFileExist) {
            return;
        }

        BufferedWriter bufferedWriter = null;
        try {
            Date date = new Date();
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("E yyyy.MM.dd hh:mm:ss");
            bufferedWriter  = new BufferedWriter(new FileWriter(namefile,true));

            bufferedWriter.append(simpleFormatter.format(date) + "/ "  +  nick + ": " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage(JTextArea mainchat){
        if (!isFileExist) {
            return "";
        }
        List<String> arrayMessage = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(namefile);
            bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()){
                arrayMessage.add(bufferedReader.readLine().toString());
            }

            if (arrayMessage.size()<100){
                for (String mess:arrayMessage
                ) {
                    if(mess.isEmpty()){
                        continue;
                    }
                    mainchat.append(mess+"\n");
                }
            }else {
               List<String> arrayMessage1 = arrayMessage.subList(arrayMessage.size()-99,arrayMessage.size());
                for (String mess:arrayMessage1
                ) {
                    mainchat.append(mess+"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
