package ru.mipt.apps.cleankpm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import ru.mipt.apps.cleankpm.R;
import ru.mipt.apps.cleankpm.asyncTasks.AsyncTasksFactory;
import ru.mipt.apps.cleankpm.constants.Config;
import ru.mipt.apps.cleankpm.userObjects.User;
import ru.mipt.apps.cleankpm.userObjects.UserInitials;

/*authorization screen activity
    uses AsyncTask produced by AsyncTaskFactory to communicate with server
    gets back acknowledgement of name+pasword pair. TODO: should get user class, actually
*/

public class MainActivity extends ActionBarActivity implements View.OnClickListener, AsyncTaskCaller{
    Button signIn;
    Button signUp;
    EditText editNameText;
    EditText editPasswordText;
    CheckBox rememberMeCheckBox;
    protected static User user=null;
    public static User getUser(){
        return user;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorize_log_in);
        initializeFields();
        assignFieldListeners();

    }
    private void initializeFields(){
        signIn = (Button) findViewById(R.id.confirmLoginButton);
        signUp = (Button) findViewById(R.id.signUpButton);
        editNameText = (EditText) findViewById(R.id.personName);
        editPasswordText = (EditText) findViewById(R.id.passwordFieldText);
        rememberMeCheckBox = (CheckBox) findViewById(R.id.loginCheckBox);
    }
    private void assignFieldListeners(){
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        rememberMeCheckBox.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO: change password, change user
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpButton: {
                (AsyncTasksFactory.createTask(this, Config.SIGN_UP)).execute();
                break;
            }
            case R.id.confirmLoginButton: {
                (AsyncTasksFactory.createTask(this, Config.SIGN_IN)).execute();
                break;
            }
            case R.id.loginCheckBox: {
                //TODO:
            }
        }
    }

    @Override
    public Intent getIntent(Class argumentClass) {
        return (new Intent(this, argumentClass));
    }

    @Override
    public Serializable transmitObject() {
        UserInitials userInitials = new UserInitials(editNameText.getText().toString(), editPasswordText.getText().toString());
        return userInitials;
    }

    @Override
    public void launchIntent(Class c, Object obj) {
        user = (User) obj;
        this.startActivity(getIntent(c));
    }

    @Override
    public void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}