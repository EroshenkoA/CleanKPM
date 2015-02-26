package ru.mipt.apps.cleankpm.userObjects;

        import java.io.Serializable;
        import java.util.ArrayList;

        import ru.mipt.apps.cleankpm.tabObjects.Event;

/**
 * Created by User on 2/23/2015.
 */
public class User extends UserInitials implements Serializable {

    protected ArrayList<Event> userEvents;
    public User(String newName, String newPassword){
        super(newName,newPassword);
        userEvents = new ArrayList<>();
    }
    public User(UserInitials ui){
        super(ui.getName(), ui.getPassword());
        userEvents = new ArrayList<>();
    }

    public void setName(String s){
        name=s;
    }

    public void addEvent(Event event){
        userEvents.add(event);
    }

    public ArrayList<Event> getUserEvents() {
        return userEvents;
    }

}