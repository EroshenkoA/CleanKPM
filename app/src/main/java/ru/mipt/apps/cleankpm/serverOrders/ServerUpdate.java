package ru.mipt.apps.cleankpm.serverOrders;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;

import ru.mipt.apps.cleankpm.activities.AsyncTaskCaller;
import ru.mipt.apps.cleankpm.activities.tabs.event.EventsActivity;
import ru.mipt.apps.cleankpm.asyncTasks.AsyncTasksFactory;
import ru.mipt.apps.cleankpm.constants.Config;
import ru.mipt.apps.cleankpm.tabObjects.Event;
import ru.mipt.apps.cleankpm.userObjects.User;

/**
 * Created by User on 2/25/2015.
 */
public class ServerUpdate implements AsyncTaskCaller {
    private  int switcher;
    private final  int EventSwitched = 1;
    private final int UserSwitched = 2;
    private  Event event;
    private User user;
    private Activity activity;
    public  void updateEvent(Event eventGot){
        switcher = EventSwitched;
        event = eventGot;
        AsyncTasksFactory.createTask(this, Config.ADD_EVENT).execute();
    }
    public  void updateUser(User user){
        switcher = UserSwitched;
    }
    public ServerUpdate(Activity a){
        activity = a;
    }

    @Override
    public Intent getIntent(Class argumentClass) {
        return null;
    }

    @Override
    public Serializable transmitObject() {
        Log.d("", "switcher is"+switcher);
        switch(switcher){
            case EventSwitched:{
                Log.d("", "switched! transimtting event");
                return event;
            }
            case UserSwitched:{
                return null;
            }
        }
        return null;
    }

    @Override
    public void launchIntent(Class c, Object obj) {
        ((EventsActivity) activity).addEventToEventsListInActivity(event);
    }

    @Override
    public void makeToast(String text) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }
}
