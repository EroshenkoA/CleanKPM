package ru.mipt.apps.cleankpm.activities;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by User on 2/23/2015.
 */
public interface AsyncTaskCaller {
    public Intent getIntent(Class argumentClass);
    public Serializable transmitObject();
    public void launchIntent(Class c, Object obj);
    public void makeToast(String text);
}
