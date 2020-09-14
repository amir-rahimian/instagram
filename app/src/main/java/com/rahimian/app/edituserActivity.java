package com.rahimian.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.irozon.sneaker.Sneaker;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class edituserActivity extends AppCompatActivity {

    private ConstraintLayout profdialog;
    private ImageView prof, btnBack, btnSave;
    private Button btnChangeProf;
    private TextView nameError, idError, biolen, onprof;
    private TextView btnTakecam, btnSelectgallary, btnRemove;
    private EditText id, bio, name;
    private boolean isValidId = true, isValidName = true;
    private ParseUser User;
    private Bitmap profilebit = null;
    private boolean isprofchange;
    private boolean prossess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);

        profdialog = findViewById(R.id.profdialog);
        profdialog.setTranslationY(20000);
        btnTakecam = findViewById(R.id.takecameOP);
        btnSelectgallary = findViewById(R.id.selectgallaryOP);
        btnRemove = findViewById(R.id.removeOP);

        prof = findViewById(R.id.EDprofile_img);
        onprof = findViewById(R.id.EDonprof);
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
                btnSave.setAlpha(1f);
                btnSave.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
                }
                profdialog.animate().translationY(0);
            }
        });
        // on prof dialog click
        View.OnClickListener howtochangeprof = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                profdialog.animate().translationY(profdialog.getHeight());
                switch (v.getId()) {
                    case R.id.takecameOP:
                        takePic();

                        break;
                    case R.id.selectgallaryOP:
                        getPic();

                        break;
                    case R.id.removeOP:
                        User.put("haveprofile",false);
                        profilebit = null ;
                        isprofchange = false;
                        prof.setImageBitmap(getBitmapFromVectorDrawable(edituserActivity.this, R.drawable.ob_pro));
                        onprof.setVisibility(View.VISIBLE);
                        break;
                }

            }
        };
        btnTakecam.setOnClickListener(howtochangeprof);
        btnRemove.setOnClickListener(howtochangeprof);
        btnSelectgallary.setOnClickListener(howtochangeprof);


        btnSave = findViewById(R.id.btn_save);
        btnSave.setAlpha(0.5f);
        btnSave.setEnabled(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidId && isValidName) {
                    //
                    prossess=true;
                    Sneaker sneaker = Sneaker.with(edituserActivity.this);
                    sneaker.autoHide(false);
                    View view = LayoutInflater.from(edituserActivity.this).inflate(R.layout.waiting_dialog, sneaker.getView(), false);
                    sneaker.sneakCustom(view);
                    //
                    if (isprofchange){ User.put("profilephoto", convertToFile(profilebit));}
                    User.put("name", name.getText().toString());
                    User.put("userID", ((id.getText().toString().length() < 2) ? null : id.getText().toString()));
                    User.put("bio", (bio.getText().toString().matches("")) ? null : bio.getText().toString());

                    User.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            prossess=false;
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
                } else {
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
                if (nameValid()) {
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
                if (id.getText().toString().length() > 0) {
                    id.setText((id.getText().toString().contains("@")) ? id.getText().toString() : "@" + id.getText().toString());
                    if (idValid()) {
                        btnSave.setAlpha(1f);
                        btnSave.setEnabled(true);
                    }
                } else {
                    isValidId = true;
                    idError.setVisibility(View.GONE);
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
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (id.getText().toString().matches("(.*[*/()#$%^&+=].*)|.*[\u0600-\u06FF].*") || (!id.getText().toString().isEmpty() && id.getText().toString().contains(" "))) {
                    id.setText(id.getText().toString().substring(0, id.getText().toString().length() - 1));
                    id.setText(id.getText().toString().replace(" ", ""));
                    id.setSelection(id.getText().length());
                } else {
                    if (idValid() & nameValid()) {
                        btnSave.setAlpha(1f);
                        btnSave.setEnabled(true);
                    }
                }
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
                if (bio.getText().toString().length() >= 70) {
                    biolen.setTextColor(getResources().getColor(R.color.error));
                    Sneaker.with(edituserActivity.this)
                            .setTitle("only 70 characters allowed!", R.color.colorPrimary)
                            .setCornerRadius(30)
                            .sneakError();
                } else {
                    biolen.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private boolean nameValid() {

        if ((!name.getText().toString().matches("")) && name.getText().toString().matches("^[\\p{L} .'-]+$")) {
            isValidName = true;
            nameError.setVisibility(View.GONE);
            name.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            isValidName = false;
            nameError.setVisibility(View.VISIBLE);
            name.setTextColor(getResources().getColor(R.color.error));
        }
        return isValidName;
    }

    private void getData() {
        name.setText(User.get("name").toString());
        if (User.getBoolean("haveprofile")){
            onprof.setText("");
            ParseFile parseFile = User.getParseFile("profilephoto");
            parseFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    profilebit = BitmapFactory.decodeByteArray(data,0,data.length);
                    prof.setImageBitmap(profilebit);
                }
            });
        }else {
            prof.setImageBitmap(getBitmapFromVectorDrawable(edituserActivity.this, R.drawable.ob_pro));
            onprof.setText(name.getText());
        }
        id.setText(((User.get("userID") == null) ? "" : User.get("userID").toString()));
        bio.setText(((User.get("bio") == null) ? "Bio" : User.get("bio").toString()));
    }

    @Override
    public void onBackPressed() {
        if(!prossess)
        super.onBackPressed();
    }

    public void rootClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        profdialog.animate().translationY(profdialog.getHeight());
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow((getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean idValid() {
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
        if (id.getText().toString().length() < 4) {
            isValidId = false;
            id.setTextColor(getResources().getColor(R.color.error));
            idError.setVisibility(View.VISIBLE);
        }

        return isValidId;
    }


    private void getPic() {
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            Intent getprof = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(getprof, 2000);
        }
    }

    private void takePic() {
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 3000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case 3000:
                    profilebit = null;
                    isprofchange = false;
                    try {
                        profilebit = (Bitmap) data.getExtras().get("data");
                        isprofchange = true;
                        User.put("haveprofile",true);
                        if (profilebit != null) {
                            onprof.setVisibility(View.GONE);
                            prof.setImageBitmap(profilebit);
                        } else {
                            onprof.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2000:
                    profilebit = null;
                    isprofchange = false;
                    try {
                        Uri uri = data.getData();
                        profilebit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        isprofchange = true;
                        User.put("haveprofile",true);
                        if (profilebit != null) {
                            onprof.setVisibility(View.GONE);
                            prof.setImageBitmap(profilebit);
                        } else {
                            onprof.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public ParseFile convertToFile(@NotNull Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        return new ParseFile("Profile",byteArrayOutputStream.toByteArray());
    }

}

