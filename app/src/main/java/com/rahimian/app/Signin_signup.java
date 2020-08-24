package com.rahimian.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


import com.irozon.sneaker.Sneaker;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;

import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


import java.util.List;


public class Signin_signup extends AppCompatActivity {

    private EditText phone, pass, name;
    private TextView time;
    private Switch changepasstype;
    private ConstraintLayout getname;
    private Button btn, edit;
    private CountDownTimer timer;
    private int code;
    private String STATICPASS = "uzsLYe+kFgY5Eq#?jfPf4XM4M9$f%u8$ayCTPg44@%au!k@&QD4jzFQ%Cz_q2+WB5XB-DvkTJF+pS6e*Z3xeC27D#_N2xbAj?ajjw=_cAZ9TGN%hT!e9F6+r&xtc+U=ceAdLDVqG^RaWxppCEJbJaCuSmcvrsT-gqet?GbxdFv2pwH%nxyRHfN-b^Lnps&ALX+vmjSzzPXaYs5mtE3spZ%Vr@2D4JhHE3HCqyw&rzxEK+aXwtG-^3*-mJwVpRWY+5E8q7HybE^-FxjwevpuQ6rTM&E#BxVZ5HVbCta%$CNuLFWzQd%wd9DKbFr+Hrc*!WG45F?d55Lex3d4?@ns^#Umhs*hG3Kp*GuD*UKj=*wqCxky=GT?pk=q*k+wV^dQd&$9TLHyFmm4P-^X3KUA%_ZUw3WaBTw9_?9Dhnc5csjPg4ZDDb8##6RkzD$E4*TgQbLcFw8hxn5y%cHSgU=m?DH_xEngV2nkGK6-8UAN8XTQ3N?we9S7^KLLYGQn%NFE!UVtazg%ru2ELPcgPR9*crEX*4wgZ+s7!3WcDsc*Gj_RWwZ#HbST6KA-q*8E5zs4ZeXRs5^wM$4Y_J+5xBSBVSn?J%2a?%zReCy=Mdc6PefFp%meL=fRCDk^cQUkPX8kwbkmz?2Xf-!YQB-HBKEaxhWQZN6SXtEm8=^D5dKMCA$=6NWyk-9J7a&PVCCahnY&E!Vj-VyvJzcNQdv3MKTG-?w7qqJprkH%ASy5sByY7%-45g?fs$cMU9==fknS3ReZL?ch&Hm3LH#bJE2ktV8NNEq^Wpe?8KXFwrD-$vRSuS&8hMawj4AK=@pftrL2%tfygx%J#7kfa9!WVWF?6qgFPWC5%n6-*AJ+UnsxS?Z3s!K+bNG*tvMj^VXJ5RGAtR&ULX$p=HMHuA*QC?@X8&4TV&KT7sn#6C4cMawDjpw3S3hEzPkFF#zsharkC&$XwUeRErP9nUtMQB584Nhm_^M2r$$nJ+M8@&P2cn_+Q&!@n7A6A_M+ZUefTZSvV#x8RC3Fp";
    private boolean ISPASSCHANGE =false ;

    enum State {Signup, Login}

    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup);

        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }
//------UI
        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    btn.callOnClick();
                }
                return false;
            }
        };
        getname = findViewById(R.id.getname);
        changepasstype = findViewById(R.id.changepasstype);
        Guideline guideline = findViewById(R.id.guideline);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pass.setBackgroundResource(R.drawable.text_area);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn = findViewById(R.id.btnIn);
        edit = findViewById(R.id.edit);
        time = findViewById(R.id.time);
        phone.setOnKeyListener(onKeyListener);
        pass.setOnKeyListener(onKeyListener);
        name.setOnKeyListener(onKeyListener);
        changepasstype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked ) {// have pass
                    if (ISPASSCHANGE){
                        timer.cancel();
                        time.setVisibility(View.GONE);
                        pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        pass.setHint("Your Password");
                    }else {
                        changepasstype.setChecked(false);
                        Sneaker.with(Signin_signup.this)
                                .setTitle("NO PassWord founded", R.color.colorPrimary)
                                .setMessage("you never set a password for your account ! ", R.color.colorPrimaryDark)
                                .setCornerRadius(30)
                                .sneakError();
                    }

                } else { // generic pass
                    generaitcode();
                    starttimer();
                    time.setVisibility(View.VISIBLE);
                    pass.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    pass.setHint("varify code");
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass.getVisibility() == View.GONE) {
                    if ((phone.length() == 11)) {
                        generaitcode();
                        starttimer();
                        phone.setBackgroundResource(R.drawable.text_area);
                        pass.setVisibility(View.VISIBLE);
                        time.setVisibility(View.VISIBLE);
                        phone.setEnabled(false);
                        edit.animate().translationX(0).alpha(1f);
                        btn.setText(R.string.confirm);
                    } else {
                        phone.setBackgroundResource(R.drawable.text_area_error);
                        Sneaker.with(Signin_signup.this)
                                .setTitle("NOT Valid Phone.Num", R.color.colorPrimary)
                                .setMessage("not a valid phone number!", R.color.colorPrimaryDark)
                                .setCornerRadius(30)
                                .sneakError();
                    }
                } else if (btn.getText().equals("Resend")) { //time off
                    generaitcode();
                    starttimer();
                } else if (!btn.getText().equals("Log In")) { // VERIFY
                    final String passtr = pass.getText().toString();

                    if (changepasstype.isChecked()) {
                        login(phone.getText().toString().trim(),passtr);
                    } else {
                        int passint = 0;
                        if (!passtr.equals("")) {
                            passint = Integer.parseInt(passtr);
                        }
                        if (passint == code) {
                            timer.cancel();
                            time.setVisibility(View.GONE);
                            edit.setVisibility(View.GONE);
                            ///////////////// CHECK SING UP OR IN //////////////////////
                            if (state == State.Signup) {
                                getname.setVisibility(View.VISIBLE);
                                btn.setText("Log In");
                            } else {
                                // LOG IN  ///////////////////////////////
                                login(phone.getText().toString().trim(),STATICPASS);
                            }
                            //////////////////////////////////////////////////////
                        }else {
                            pass.setBackgroundResource(R.drawable.text_area_error);
                            Sneaker.with(Signin_signup.this)
                                    .setTitle("NOT Correct", R.color.colorPrimary)
                                    .setMessage("try again !", R.color.colorPrimaryDark)
                                    .setCornerRadius(30)
                                    .sneakError();
                        }
                    }
                } else {//GET NAME
                    if (name.getText().length() > 6) {
                        if ((name != null) && name.getText().toString().matches("[A-Za-z0-9_]+")) {
                            //SING UP  //////////////////////////
                            ParseUser parseUser = new ParseUser();
                            parseUser.setUsername(phone.getText().toString().trim());
                            parseUser.put("name", name.getText().toString().trim());
                            parseUser.setPassword(STATICPASS);
                            parseUser.put("genericpass", code);
                            //
                            Sneaker sneaker = Sneaker.with(Signin_signup.this);sneaker.autoHide(false);
                            View view = LayoutInflater.from(Signin_signup.this).inflate(R.layout.waiting_dialog,  sneaker.getView(), false);
                            sneaker.sneakCustom(view);
                            //
                            parseUser.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Intent intent = new Intent(Signin_signup.this, Home.class);
                                        startActivity(intent);
                                    } else {
                                        Sneaker.with(Signin_signup.this)
                                                .setTitle(e.getMessage() + "  :  " + e.getCode(), R.color.colorPrimary)
                                                .setCornerRadius(30)
                                                .sneakError();
                                    }
                                }
                            });

                        } else {
                            Sneaker.with(Signin_signup.this)
                                    .setTitle("NOT Valid", R.color.colorPrimary)
                                    .setCornerRadius(30)
                                    .sneakError();
                        }
                    } else {
                        Sneaker.with(Signin_signup.this)
                                .setTitle("NOT Valid", R.color.colorPrimary)
                                .setMessage("need to be at last 6 character . try again !", R.color.colorPrimaryDark)
                                .setCornerRadius(30)
                                .sneakError();
                    }
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                changepasstype.setVisibility(View.GONE);
                changepasstype.setChecked(false);
                ISPASSCHANGE = false;
                phone.setEnabled(true);
                timer.cancel();
                edit.animate().translationX(50).alpha(0f);
                pass.setText("");
                btn.setText("SIGNUP | LOGIN");
            }
        });
    }

    private void generaitcode() {
        code = (int) ((Math.random() * (9999 - 3000)) + 3000);
        Sneaker.with(Signin_signup.this)
                .setTitle("SMS", R.color.colorPrimary)
                .setMessage("Verify code :  " + code, R.color.colorPrimaryDark)
                .setIcon(R.drawable.ic_baseline_sms_24)
                .setCornerRadius(30)
                .setDuration(8000)
                .sneak(R.color.colorAccent);
        /*chane state */
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", phone.getText().toString().trim());
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    if (users.size() == 0) {
                        state = State.Signup;
                        changepasstype.setVisibility(View.GONE);
                        Log.e("ERROR", state + " -> " + code + "");
                    } else {
                        state = State.Login;
                        if (users.get(0).getBoolean("ispasschange")) { ISPASSCHANGE =true;}
                        changepasstype.setVisibility(View.VISIBLE);
                        Log.e("ERROR", state + " -> " + code + "");
                    }
                } else {
                    Log.e("ERROR", e.getMessage() + "[" + e.getCode() + "]");
                }
            }
        });
    }

    public void starttimer() {
        btn.setText(R.string.confirm);
        pass.setEnabled(true);
        timer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText((millisUntilFinished / 1000) + "");
            }

            public void onFinish() {
                Sneaker.with(Signin_signup.this)
                        .setTitle("Time out ", R.color.colorPrimary)
                        .setMessage("try 'Resend'", R.color.colorPrimaryDark)
                        .setCornerRadius(30)
                        .sneakWarning();
                time.setText(R.string.resend);
                btn.setText(R.string.resend);
                pass.setEnabled(false);
                timer.cancel();
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.callOnClick();
                    }
                });
            }

        }.start();
    }

    public void rootClick(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow((getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void login(String username, final String password) {
        //
        Sneaker sneaker = Sneaker.with(Signin_signup.this);sneaker.autoHide(false);
        View view = LayoutInflater.from(Signin_signup.this).inflate(R.layout.waiting_dialog,  sneaker.getView(), false);
        sneaker.sneakCustom(view);
        //
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null && e == null) {
                    user.put("genericpass", code);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (Integer.parseInt(ParseUser.getCurrentUser().get("genericpass").toString()) == code) {
                                Intent intent = new Intent(Signin_signup.this, Home.class);
                                startActivity(intent);
                            }
                        }
                    });

                } else {
                    if (e.getCode()==101){
                        pass.setBackgroundResource(R.drawable.text_area_error);
                    }
                    Sneaker.with(Signin_signup.this)
                            .setTitle(e.getMessage() + "  :  " + e.getCode(), R.color.colorPrimary)
                            .setCornerRadius(30)
                            .sneakError();
                }
            }
        });
    }

}