package com.submissionBOT;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyListener extends ListenerAdapter {

    Counter ctn;
    FileWriter fw;
    File folder;

    @Override
    public void onReady(ReadyEvent event) {
        //once the bot starts this part will be executed ( only once )

        //choose wich file we'll use to log submissions
        File log = new File("./log.txt");

        //choose wich folder we'll use to download submissions
        folder = new File("./files/");

        //if missing create the folder
        folder.mkdirs();

        //if previous files are in there get the amount of them
        long last_ctn =  folder.listFiles().length;

        //update the counter whit the old value ^^
        ctn = new Counter(last_ctn);

        try {
            //open a writer on the log file ( this is needed to ba able to write something on it )
            fw = new FileWriter(log,true);// the true is a flag to say we'll append the text instead overwriting the existent one
        } catch (IOException e) {
            e.printStackTrace();
        }
        //log the ready event to console
        System.out.println("BOT Ready");
        System.out.println();
    }


    //this will be executed every time a message is sent ( as a DM or in a server )
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        //if this is not a DM
        if(!(event.getChannelType()==ChannelType.PRIVATE))
            return; //ignore the message

        //if we're answering to ourselves
        if(event.getAuthor().isBot())
            return; //do not lol

        //if it has no attachments
        if(event.getMessage().getAttachments().size()==0) {
            //advise the user
            event.getPrivateChannel().sendMessage("Please submit something ( attach the file to the message )").queue();
            //log the event to console
            logToConsole("User "+
                    user.getName() + "#" + user.getDiscriminator() + " (" + user.getId() + ")" +
                    " sent a message without attachments");
            return; // and ignore the event
        }

        //if it has attachements
        event.getMessage().getAttachments().forEach(a -> { //for each attachment
            //save the file name
            StringBuilder filename = new StringBuilder(a.getFileName());

            //replace the name keeping the extension (all before the las dot ) whit a incremental number
            filename.replace(0,filename.lastIndexOf("."),Long.toString(ctn.inc()));

            //select where to save the file
            File to_download = new File(folder.getPath()+"/"+filename.toString());

            //download the file
            a.download(to_download);

            StringBuilder log = new StringBuilder();

            try {
                //create the log string for the download in format
                log.append("User ").append(user.getName()).append("#").append(user.getDiscriminator());
                log.append(" (").append(user.getId()).append(")");
                log.append(" submitted ").append(filename.toString());
                //write the log string to file
                fw.append(log.toString());
                fw.append("\r\n");
                //write the log string to console
                logToConsole(log.toString());
                //EXAMPLE:
                // User: mattymatty#9621 (402567749479432195) submitted 1.jpg
            }catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            //as we are writing to a file the program automatically bufferize ( accumulates ) the things to write
            //the fulsh is needed to force it to write down on the file
            fw.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }

        //for beeing polite lets say a thanks to who submitted the file :smirk:

        event.getPrivateChannel().sendMessage("Thanks for your submission").queue();

    }

    private static final DateFormat stf = new SimpleDateFormat("HH:mm:ss");
    //funtion to log messages to console
    private void logToConsole(String msg){
        StringBuilder sb = new StringBuilder();

        //get current time in format
        String time = stf.format(new Date());
        //create the log string
        sb.append("[").append(time).append("]\t");
        sb.append(msg);
        //write down the log
        System.out.println(sb.toString());
    }


    //ignore this part
    private class Counter {
        long ctn;
        public synchronized long get(){
            return ctn;
        }

        public synchronized long inc(){
            return ++ctn;
        }

        Counter(long init){
            ctn=init;
        }
    }

    //close the file for compatibility reasons
    public void shutdown(){
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
