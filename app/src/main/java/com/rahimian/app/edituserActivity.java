package com.rahimian.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.irozon.sneaker.Sneaker;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class edituserActivity extends AppCompatActivity {

    private ImageView prof, btnBack ,btnSave;
    private Button btnChangeProf;
    private TextView nameError, idError, biolen;
    private EditText id, bio, name;
    private boolean isValidId = true, isValidName = true;
    private ParseUser User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);

        prof = findViewById(R.id.profile_img);
        id = findViewById(R.id.idText);
        bio = findViewById(R.id.bioText);
        name = findViewById(R.id.profileName);
        nameError = findViewById(R.id.nameError);
        biolen = findViewById(R.id.biolen);
        idError = findViewById(R.id.idError);
        User = ParseUser.getCurrentUser();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        getData();
        biolen.setText(bio.getText().toString().length() + "/70");

        btnChangeProf = findViewById(R.id.btnChangePhoto);
        btnChangeProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSave = findViewById(R.id.btn_save);
        btnSave.setAlpha(0.5f);
        btnSave.setEnabled(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idValid();
                nameValid();
                if (isValidId&isValidName) {
                    User.put("name", name.getText().toString());
                    User.put("userID", id.getText().toString());
                    User.put("bio", bio.getText().toString());
                    //
                    Sneaker sneaker = Sneaker.with(edituserActivity.this);
                    sneaker.autoHide(false);
                    View view = LayoutInflater.from(edituserActivity.this).inflate(R.layout.waiting_dialog, sneaker.getView(), false);
                    sneaker.sneakCustom(view);
                    //
                    User.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                onBackPressed();
                            } else {
                                Sneaker.with(edituserActivity.this)
                                        .setTitle(e.getMessage(), R.color.colorPrimary)
                                        .setCornerRadius(30)
                                        .sneakError();
                            }
                        }
                    });
                }else
                {
                    btnSave.setAlpha(0.5f);
                    btnSave.setEnabled(false);
                }
            }
        });
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {// name change
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                btnSave.setAlpha(0.5f);
                btnSave.setEnabled(false);
                nameValid();
                if (isValidName) {
                    btnSave.setAlpha(1f);
                    btnSave.setEnabled(true);
                }
            }
        });

        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {// id change
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                btnSave.setAlpha(0.5f);
                btnSave.setEnabled(false);
                id.setText((id.getText().toString().contains("@")) ? id.getText().toString() : "@" + id.getText().toString());
                idValid();
                if (isValidId) {
                    btnSave.setAlpha(1f);
                    btnSave.setEnabled(true);
                }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                id.setTextColor(getResources().getColor(R.color.colorAccent));
                name.setTextColor(getResources().getColor(R.color.colorAccent));
                nameError.setVisibility(View.GONE);
                idError.setVisibility(View.GONE);
                if (isValidId&&isValidName) {
                    btnSave.setAlpha(1f);
                    btnSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        id.addTextChangedListener(textWatcher);
        name.addTextChangedListener(textWatcher);

        bio.addTextChangedListener(new TextWatcher() {//bio change
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnSave.setAlpha(1f);
                btnSave.setEnabled(true);
                biolen.setText(bio.getText().toString().length() + "/70");
                if (bio.getText().toString().length() >=70){
                    biolen.setTextColor(getResources().getColor(R.color.error));
                    Sneaker.with(edituserActivity.this)
                            .setTitle("only 70 characters allowed!", R.color.colorPrimary)
                            .setCornerRadius(30)
                            .sneakError();
                }else {biolen.setTextColor(getResources().getColor(R.color.colorAccent)); }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private void nameValid() {

        if ((!name.getText().toString().matches("")) && name.getText().toString().matches("^[\\p{L} .'-]+$"))
        {
            isValidName = true ;
            nameError.setVisibility(View.GONE);
            name.setTextColor(getResources().getColor(R.color.colorAccent));
        }else  {
            isValidName = false ;
            nameError.setVisibility(View.VISIBLE);
            name.setTextColor(getResources().getColor(R.color.error));
        }
    }

    private void getData() {
        name.setText(User.get("name").toString());
        id.setText(((User.get("userID") == null) ? "" : User.get("userID").toString()));
        bio.setText(((User.get("bio") == null) ? "Bio" : User.get("bio").toString()));
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

    private void idValid() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", User.getUsername());
        query.whereEqualTo("userID", id.getText().toString().trim());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    if (users.size() == 0) {
                        isValidId = true;
                        idError.setVisibility(View.GONE);
                        id.setTextColor(getResources().getColor(R.color.colorAccent));
                    } else {
                        isValidId = false;
                        id.setTextColor(getResources().getColor(R.color.error));
                        idError.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.e("ERROR", e.getMessage() + "[" + e.getCode() + "]");
                }
            }
        });

    }

}
