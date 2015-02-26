package ru.mipt.apps.cleankpm.asyncTasks;

import java.io.IOException;
import java.io.Serializable;

import ru.mipt.apps.cleankpm.activities.AsyncTaskCaller;
import ru.mipt.apps.cleankpm.activities.tabs.TabActivityKPM;
import ru.mipt.apps.cleankpm.constants.Config;
import ru.mipt.apps.cleankpm.statics.BufWrapper;
import ru.mipt.apps.cleankpm.userObjects.User;

/**
 * Created by User on 2/23/2015.
 */
public class AsyncTasksFactory {
    public static DefaultAsyncTask createTask(final AsyncTaskCaller a, int commandGot){
        final int commandFromActivity=commandGot;

        DefaultAsyncTask task = new DefaultAsyncTask(a) {
            User user;
            @Override
            protected Void doInBackground(Void... params) {
                setBuf();
                initSocket();
                buf = (BufWrapper.convertToBuf(commandFromActivity));
                switch(commandFromActivity){

                    case Config.SIGN_IN:
                    case Config.SIGN_UP:
                    case Config.ADD_EVENT:
                    case Config.UPDATE_USER:
                    {
                        Serializable serialObject =  a.transmitObject();
                        try {
                            oos.write(buf);
                            oos.writeObject(serialObject);
                            oos.flush();
                            ois.read(buf);
                            int t = BufWrapper.convertToInt(buf);
                            switch(t){
                                case Config.SIGNED_IN:{
                                    user = (User) ois.readObject();
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    /*case Config.SIGN_IN:
                    case Config.SIGN_UP:{
                        UserInitials userInitials = (UserInitials) a.transmitObject();
                        try {
                            oos.write(buf);
                            oos.writeObject(userInitials);
                            oos.flush();
                            ois.read(buf);
                            int t = BufWrapper.convertToInt(buf);
                            switch(t){
                                case Config.SIGNED_IN:{
                                    user = (User) ois.readObject();
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case Config.ADD_EVENT:{
                        Event event = (Event) a.transmitObject();
                        try{
                            oos.write(buf);
                            oos.writeObject(event);
                            oos.flush();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        break;
                    }*/

                }
                endStreams();
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                //super.onPostExecute(result);
                //buf = (BufWrapper.convertToBuf(b));
                int commandFromServer=BufWrapper.convertToInt(buf);


                switch(commandFromServer){

                    case Config.SIGNED_IN: {
                        a.launchIntent(TabActivityKPM.class, user);
                        break;
                    }

                    case Config.WRONG_PASSWORD: {
                        a.makeToast("wrong password");
                        break;
                    }

                    case Config.DOESNOT_EXIST: {
                        a.makeToast("user doesn't exist, try sign up first");
                        break;
                    }

                    case Config.NAME_EXISTS: {
                        a.makeToast("name occupied");
                        break;
                    }

                }
            }
        };

        return task;
    }
}
