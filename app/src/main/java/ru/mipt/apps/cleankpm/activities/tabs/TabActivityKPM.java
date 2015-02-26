package ru.mipt.apps.cleankpm.activities.tabs;

/**
 * Created by User on 2/23/2015.
 */

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import ru.mipt.apps.cleankpm.R;
import ru.mipt.apps.cleankpm.activities.tabs.event.EventsActivity;

public class TabActivityKPM extends TabActivity {

    final String TABS_TAG_1 = "Tag 1";
    final String TABS_TAG_2 = "Tag 2";
    final String TABS_TAG_3 = "Tag 3";
    TabHost tabHost;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_together);
        tabHost = getTabHost();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec(TABS_TAG_1);
        tabSpec.setContent(TabFactory);
        tabSpec.setIndicator("Вкладка 1");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TABS_TAG_2);
        tabSpec.setContent(TabFactory);
        tabSpec.setIndicator("Вкладка 2");
        tabHost.addTab(tabSpec);

        initTag(EventsActivity.class, TABS_TAG_3, "Events");


    }
    protected void initTag(Class c, String tag, String indicator){
        TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setContent(new Intent(this, c));
        tabSpec.setIndicator(indicator);
        tabHost.addTab(tabSpec);
    }

    TabHost.TabContentFactory TabFactory = new TabHost.TabContentFactory() {

        @Override
        public View createTabContent(String tag) {
            if (tag == TABS_TAG_1) {
                return getLayoutInflater().inflate(R.layout.tab, null);
            } else if (tag == TABS_TAG_2) {
                TextView tv = new TextView(TabActivityKPM.this);
                tv.setText("Это создано вручную");
                return tv;
            }
            return null;
        }
    };
}