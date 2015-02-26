package ru.mipt.apps.cleankpm.serverOrders;

import android.content.Intent;

import java.io.Serializable;

import ru.mipt.apps.cleankpm.activities.AsyncTaskCaller;
import ru.mipt.apps.cleankpm.asyncTasks.AsyncTasksFactory;
import ru.mipt.apps.cleankpm.tabObjects.Event;
import ru.mipt.apps.cleankpm.userObjects.User;

/**
 * Created by User on 2/25/2015.
 */
public class ServerUpdate implements AsyncTaskCaller {
    private static int switcher;
    private final static int EventSwitched = 1;
    private final static int UserSwitched = 2;
    private static Event event;
    private static User user;
    public static void updateEvent(Event eventGot){
        switcher = EventSwitched;
        event = eventGot;
    }
    public static void updateUser(User user){
        switcher = UserSwitched;
    }

    @Override
    public Intent getIntent(Class argumentClass) {
        return null;
    }

    @Override
    public Serializable transmitObject() {
        switch(switcher){
            case EventSwitched:{
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

    }

    @Override
    public void makeToast(String text) {

    }
}
