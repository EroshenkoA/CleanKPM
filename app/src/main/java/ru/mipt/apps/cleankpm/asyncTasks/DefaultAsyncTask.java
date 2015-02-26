package ru.mipt.apps.cleankpm.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ru.mipt.apps.cleankpm.activities.AsyncTaskCaller;
import ru.mipt.apps.cleankpm.constants.Config;
import ru.mipt.apps.cleankpm.statics.BufWrapper;

/**
 * Created by User on 2/23/2015.
 */
public abstract class DefaultAsyncTask extends AsyncTask<Void, Void, Void> {
    protected Socket socket;
    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;
    protected byte[] buf;
    protected AsyncTaskCaller parentActivity;
    public DefaultAsyncTask(AsyncTaskCaller a){
        parentActivity = a;

    }
    protected void setBuf(){
        buf = BufWrapper.setSocketPreBuf();
    }
    protected void initSocket(){
        try{
            socket = new Socket(Config.ipAddress,Config.port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPreExecute(){
    }
    protected void endStreams(){
        try {
            /*oos.flush();
            ois.read(buf);*/
            oos.close();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}