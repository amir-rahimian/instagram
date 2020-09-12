package com.rahimian.app;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.irozon.sneaker.Sneaker;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class PassChangeActivity extends AppCompatActivity {

    String specialCharRegex, UpperCaseRegex, NumberRegex;
    boolean[] goodpass = new boolean[4];
    private ImageView btn_back;
    private ParseUser User;
    private String STATICPASS = "PVf@dhCD@1D4@F2E@oX9";
    private boolean OKPASS = false;
    private SwitchMaterial staticPassSwitch;
    private ProgressBar progress;
    private TextView passhint, errortxt;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_change);

        specialCharRegex = ".*[@#$%^&+=].*";
        UpperCaseRegex = ".*[A-Z].*";
        NumberRegex = ".*[0-9].*";

        User = ParseUser.getCurrentUser();

        passhint = findViewById(R.id.passhint);
        errortxt = findViewById(R.id.errortxt);
        progress = findViewById(R.id.prograssvalidpass);
        progress.setVisibility(View.GONE);


        password = findViewById(R.id.password);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String pas = "";
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    staticPassSwitch.setChecked(true);
                }

                return false;
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String textEntered = password.getText().toString();
                if (!textEntered.isEmpty() && textEntered.contains(" ")) {
                    password.setText(textEntered.replace(" ", ""));
                    password.setSelection(password.getText().length());
                }
                if (textEntered.matches("(.*[\u0600-\u06FF].*)|(.*[*/()].*)")) {
                    password.setText(textEntered.substring(0,textEntered.length()-1));
                    password.setSelection(password.getText().length());
                }
                int prog = 0;
                errortxt.setText("");
                String str = "";
                validate();
                for (int i = 0; i < goodpass.length; i++) {
                    if (goodpass[i]) {
                        prog++;

                    } else {
                        str = str + i;
                    }
                }
                if (str.contains("0")) {
                    errortxt.setText(errortxt.getText() + "\nnot enough length \n");
                }
                if (str.contains("1")) {
                    errortxt.setText(errortxt.getText() + "\nstronger with a SpecialChar " + specialCharRegex + "\n");
                }
                if (str.contains("2")) {
                    errortxt.setText(errortxt.getText() + "\nstronger with a UpperCaseLetter " + UpperCaseRegex + "\n");
                }
                if (str.contains("3")) {
                    errortxt.setText(errortxt.getText() + "\nadd numbers to have more strong password \n");
                }
                switch (prog) {
                    case 4:
                        OKPASS = true;
                        progress.getProgressDrawable().setColorFilter(
                                getResources().getColor(R.color.ok), android.graphics.PorterDuff.Mode.SRC_IN);
                        errortxt.setTextColor(getResources().getColor(R.color.ok));
                        errortxt.setText(getResources().getString(R.string.its_good));
                        break;

                    case 3:
                        if (password.getText().toString().length()>=6){
                        OKPASS = true;
                        progress.getProgressDrawable().setColorFilter(
                                getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
                        errortxt.setTextColor(getResources().getColor(R.color.colorAccent));
                        errortxt.setText(errortxt.getText().toString() + getResources().getString(R.string.its_ok));
                        }else {
                            OKPASS = false;
                            progress.getProgressDrawable().setColorFilter(
                                    getResources().getColor(R.color.warning), android.graphics.PorterDuff.Mode.SRC_IN);
                            errortxt.setTextColor(getResources().getColor(R.color.warning));
                            errortxt.setText(errortxt.getText().toString() + getResources().getString(R.string.to_short));
                        }
                        break;
                    case 2:
                        OKPASS = false;
                        progress.getProgressDrawable().setColorFilter(
                                getResources().getColor(R.color.warning), android.graphics.PorterDuff.Mode.SRC_IN);
                        errortxt.setTextColor(getResources().getColor(R.color.warning));
                        break;
                    default:
                        OKPASS = false;
                        progress.getProgressDrawable().setColorFilter(
                                getResources().getColor(R.color.error), android.graphics.PorterDuff.Mode.SRC_IN);
                        errortxt.setTextColor(getResources().getColor(R.color.error));
                        break;
                }
                progress.setProgress(prog);

            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password.setHint("write...");
                staticPassSwitch.setChecked(false);
                progress.setVisibility(View.VISIBLE);
                progress.setProgress(0);
                progress.getProgressDrawable().setColorFilter(
                        getResources().getColor(R.color.error), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });

        staticPassSwitch = findViewById(R.id.staticPassSwitch);
        if (User.getBoolean("ispasschange")) {
            staticPassSwitch.setChecked(true);
            password.setHint("Tap To change...");
        } else {
            staticPassSwitch.setChecked(false);
        }
        if (staticPassSwitch.isChecked()) {
            staticPassSwitch.setText("ON");
        } else {
            staticPassSwitch.setText("OFF");
        }
        staticPassSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (OKPASS) {
                        staticPassSwitch.setText("ON");
                        User.setPassword(password.getText().toString());
                        User.put("ispasschange", true);
                        User.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Sneaker.with(PassChangeActivity.this)
                                            .setTitle("Success", R.color.colorAccent)
                                            .setMessage("you set " + password.getText().toString() + " as your static password", R.color.colorAccent)
                                            .setCornerRadius(30)
                                            .sneakSuccess();
                                }
                            }
                        });
                    } else {
                        staticPassSwitch.setChecked(false);
                        Sneaker.with(PassChangeActivity.this)
                                .setTitle("Error", R.color.colorAccent)
                                .setMessage("please try a better password", R.color.colorAccent)
                                .setCornerRadius(30)
                                .sneakError();
                    }

                } else {
                    staticPassSwitch.setText("OFF");
                    User.setPassword(STATICPASS);
                    User.put("ispasschange", false);
                    User.saveInBackground();
                }
            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    public void validate() {

        String passwordstr = password.getText().toString().trim();

            if (passwordstr.length() > 8) {
                goodpass[0] = true;
            } else {
                goodpass[0] = false;
            }

            if (passwordstr.matches(specialCharRegex)) {
                goodpass[1] = true;
            } else {
                goodpass[1] = false;
            }

            if (passwordstr.matches(UpperCaseRegex)) {
                goodpass[2] = true;
            } else {
                goodpass[2] = false;
            }

            if (passwordstr.matches(NumberRegex)) {
                goodpass[3] = true;
            } else {
                goodpass[3] = false;
            }

    }

}