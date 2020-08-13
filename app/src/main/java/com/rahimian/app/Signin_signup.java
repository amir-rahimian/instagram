package com.rahimian.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Signin_signup extends AppCompatActivity {

    private TextView phone , pass ,vtext ,time;
    private Button btn , edit;
    private CountDownTimer timer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup);
//------UI
        phone =  findViewById(R.id.phone);
        pass  =  findViewById(R.id.pass);
        btn   =  findViewById(R.id.btnIn);
        edit  =  findViewById(R.id.edit);
        vtext =  findViewById(R.id.vText);
        time  =  findViewById(R.id.time);
        phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    btn.callOnClick();
                }
                return false;
            }
        });
        pass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    btn.callOnClick();
                }
                return false;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getVisibility()==View.GONE){
                    if ((phone.length()==11)){
                        phone.setBackgroundResource(R.drawable.text_area);
                        pass.setVisibility(View.VISIBLE);
                        vtext.setVisibility(View.VISIBLE);
                        time.setVisibility(View.VISIBLE);
                        phone.setEnabled(false);
                        edit.animate().translationX(0).alpha(1f);
                        btn.setText("CONFIRM");
                        starttimer();
                    }else {
                        phone.setBackgroundResource(R.drawable.text_area_error);
                        phone.setError("not a valid phone number!");
                    }
                }else if(btn.getText().equals("Resend")){ //time off
                   starttimer();
                }
                else {
                    String passtr=pass.getText().toString();
                    int passint = 0;
                    if(!passtr.equals("")){
                      passint = Integer.parseInt(passtr);}
                    if (passint==8585){
                        pass.setBackgroundResource(R.drawable.text_area);
                        timer.cancel();
                        time.setVisibility(View.GONE);
                        Toast.makeText(Signin_signup.this,"OK",Toast.LENGTH_LONG).show();
                    }else
                    {
                        pass.setBackgroundResource(R.drawable.text_area_error);
                        pass.setError("not correct");
                    }
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setVisibility(View.GONE);
                vtext.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                phone.setEnabled(true);
                timer.cancel();
                edit.animate().translationX(50).alpha(0f);
                pass.setText("");
                btn.setText("SIGNUP | LOGIN");
            }
        });
    }


    public void starttimer() {
        btn.setText("CONFIRM");
        pass.setEnabled(true);
         timer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText((millisUntilFinished/1000)+ "");
            }

            public void onFinish() {
                time.setText("Resend");
                btn.setText("Resend");
                pass.setEnabled(false);
                timer.cancel();
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.callOnClick();
                    }
                });            }

        }.start();
    }

    public void  rootClick (View view){
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow((getCurrentFocus()).getWindowToken(),0);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}