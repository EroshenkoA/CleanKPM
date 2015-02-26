package ru.mipt.apps.cleankpm.activities.tabs.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ru.mipt.apps.cleankpm.R;
import ru.mipt.apps.cleankpm.activities.MainActivity;
import ru.mipt.apps.cleankpm.activities.tabs.TabActivityKPM;
import ru.mipt.apps.cleankpm.serverOrders.ServerUpdate;
import ru.mipt.apps.cleankpm.tabObjects.Event;
import ru.mipt.apps.cleankpm.userObjects.User;

/**
 * Created by User on 2/24/2015.
 */
public class EventsActivity extends Activity implements View.OnClickListener {

    private Button addNewBtn;
    private ListView myListView;
    private ArrayAdapter<CharSequence> adapter;
    private static final int getEventRequestCode = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        initializeFields();
        assignFieldListeners();
    }

    private void initializeFields() {
        addNewBtn = (Button) findViewById(R.id.addNewEventButton);
        addNewBtn.setOnClickListener(this);
        myListView = (ListView) findViewById(R.id.eventsListView);
        adapter = new ArrayAdapter<CharSequence>(this, R.layout.event_item, new ArrayList());
        myListView.setAdapter(adapter);
    }
    private void assignFieldListeners(){
        addNewBtn.setOnClickListener(this);
        myListView.setAdapter(adapter);
        setListListeners();
    }
    private void setListListeners(){
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*Log.d(LOG_TAG, "itemClick: position = " + position + ", id = " + id);*/
            }
        });

        myListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                /*Log.d(LOG_TAG, "itemSelect: position = " + position + ", id = " + id);*/
            }

            public void onNothingSelected(AdapterView<?> parent) {
                /*Log.d(LOG_TAG, "itemSelect: nothing");*/
            }
        });

        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
               /* Log.d(LOG_TAG, "scrollState = " + scrollState);*/
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                /*Log.d(LOG_TAG, "scroll: firstVisibleItem = " + firstVisibleItem + ", visibleItemCount" + visibleItemCount + ", totalItemCount" + totalItemCount);*/
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addNewEventButton:
                Intent intent = new Intent(this, AddNewEventActivity.class);
                startActivityForResult(intent, getEventRequestCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case getEventRequestCode:{
                if (data == null) {return;}
                Event event = (Event) data.getSerializableExtra("event created");
                adapter.add(event.getEventName());
                //User user = ((MainActivity) ((/*(TabActivityKPM)*/ getParent()).getParent())).getUser();
                User user = MainActivity.getUser();
                user.addEvent(event);
                ServerUpdate.updateEvent(event);
                ServerUpdate.updateUser(user);
                break;
            }
        }

    }
}