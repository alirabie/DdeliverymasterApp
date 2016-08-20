package app.appsmatic.com.driversapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private EditText user;
    private EditText pass;
    private ImageView logbtn;
    private String msg="";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       // window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        user=(EditText)findViewById(R.id.user);
        pass=(EditText)findViewById(R.id.pass);
        logbtn=(ImageView)findViewById(R.id.lognbutton);



        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = user.getText().toString();
                password = pass.getText().toString();

                //Loading Dialog
                final ProgressDialog mProgressDialog = new ProgressDialog(LoginActivity.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setIcon(android.R.drawable.ic_lock_idle_alarm);
                mProgressDialog.setTitle("Please Wait ..");
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.show();

                Genrator.createService(DriversApi.class).login(username, password).enqueue(new Callback<DriverID>() {
                    @Override
                    public void onResponse(Call<DriverID> call, Response<DriverID> response) {

                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();

                        msg=response.body().getMessage();
                        if(msg==null){

                            LoginActivity.this.finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivty.class).putExtra("DriverID", response.body().getDriverid()));

                        }else{


                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Authentication Error")
                                    .setMessage(msg)
                                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setIcon(android.R.drawable.ic_dialog_alert).show();




                        }


                    }

                    @Override
                    public void onFailure(Call<DriverID> call, Throwable t) {



                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();


                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Connection Error")
                                .setMessage(t.getMessage())
                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                    }
                });


            }


        });






    }
}