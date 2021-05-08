package com.example.office_task_mangement;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.office_task_mangement.model.model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;


public class HomeActivity extends AppCompatActivity {

    public FirebaseAuth mauth;
    public DatabaseReference mdatabase;
    private RecyclerView recyclerView;
    TimePicker timepicker;
    FirebaseRecyclerAdapter<model, MyViewHolder> adapter;
    Button checkBox;
    private EditText titleUp;
    private EditText noteUp;
    private Button btnDeleteUp;
    int count;
    private  Button Cancel;
    private Button btnUpdateUp;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private String title;
    final Calendar c = Calendar.getInstance();
    boolean timevalue=false;
    String time1;
    Integer mYear1 = c.get(Calendar.YEAR);
    Integer mMonth1 = c.get(Calendar.MONTH);
    Integer mDay1 = c.get(Calendar.DAY_OF_MONTH);
    Integer mHour1 = c.get(Calendar.HOUR_OF_DAY);
    Integer mMinute1   = c.get(Calendar.MINUTE);
    Integer minute1;
    String date1;
    Integer date2;
    String date56;
    Integer date3;
    Integer date4;
    DatePickerDialog datePickerDialog;
    private String note;
    private String post_key;
    Button getBtnDeleteUp;
    ImageView imageView;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FloatingActionButton floatingbt = findViewById(R.id.floating_bt);
        FloatingActionButton floatingbt1 = findViewById(R.id.floating_bt1);
        mauth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu);
        final FirebaseUser muser = mauth.getCurrentUser();
        String uid = muser.getUid();
        recyclerView = findViewById(R.id.Recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Task Note").child(uid);
        mdatabase.keepSynced(true);
        getBtnDeleteUp = findViewById(R.id.button_floating1);
        final FloatingActionButton float1 = findViewById(R.id.floating_bt);
        floatingbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,login.class);
                startActivity(i);
            }
        });
        float1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
                final View myview = inflater.inflate(R.layout.custominputfield, null);
                mydialog.setView(myview);
                final AlertDialog dialog = mydialog.create();
                dialog.show();
                final EditText text1 = myview.findViewById(R.id.title_edit);
                final EditText text2 = myview.findViewById(R.id.note_edit);
                final Button date = myview.findViewById(R.id.datebt);
                final Button time = myview.findViewById(R.id.timebt);
                Button button_flt = myview.findViewById(R.id.button_floating);

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date1 = dayOfMonth + "-" + (month + 1) + "-" + year;
                            }
                        }, mYear1, mMonth1, mDay1);
                        datePickerDialog.show();
                    }
                });
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(HomeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time1 = hourOfDay + ":" + minute;
                            }
                        }, mHour1, mMinute1, false);
                        timePickerDialog.show();
                        timevalue = true;
                    }
                });

                button_flt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String text_title = text1.getText().toString().trim();
                        final String text_note = text2.getText().toString().trim();
                        if (TextUtils.isEmpty(text_title)) {
                            text1.setError("Title Required");
                            return;
                        }
                        if (TextUtils.isEmpty(text_note)) {
                            text2.setError("Note Required");
                            return;
                        }

                            Intent alarmIntent = new Intent(getApplicationContext(), MyBroadCastReceiver.class);
                            alarmIntent.putExtra("key1", text_title);
                            alarmIntent.putExtra("key2", text_note);
                            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, alarmIntent, 0);
                            String datetime = date1 + " " + time1;

                            DateFormat format = new SimpleDateFormat("d-M-yyyy hh:mm");
                            try {
                                Date date5 = format.parse(datetime);
                                 alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, date5.getTime(), pendingIntent);
                            } catch (ParseException e) {
                                Toast.makeText(getApplicationContext(),"nhk",Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                        String id = mdatabase.push().getKey();
                        Date date = new Date();

                         date56 = date.getDate()+"-"+Month.of(date.getMonth()+1)+"-"+date.getYear();
                        model model1 = new model(text_title, text_note, date1, id);
                        model model2 = new model(text_title, text_note, String.valueOf(date56), id);
                        mdatabase.child(id).setValue(model2);

                        dialog.dismiss();
                    }
                });

            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }




    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<model, MyViewHolder> adapter = new FirebaseRecyclerAdapter<model, MyViewHolder>(model.class, R.layout.item_data, MyViewHolder.class, mdatabase) {
            protected void populateViewHolder(final MyViewHolder myViewHolder, final model model, final int i) {
                myViewHolder.setTitle(model.getTitle());
                myViewHolder.setNote(model.getNote());
                myViewHolder.setDate(model.getData());

                myViewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post_key = getRef(i).getKey();
                        title = model.getTitle();
                        note = model.getNote();
                        updateData();
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View myview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void setTitle(String title) {
            TextView mtitle = myview.findViewById(R.id.title1);
            mtitle.setText(title);
        }

        public void setNote(String note) {
            TextView mnote = myview.findViewById(R.id.note1);
            mnote.setText(note);
        }

        public void setDate(String date) {
            TextView mdate = myview.findViewById(R.id.date1);
            mdate.setText(date);
        }
    }

    public void updateData() {

        AlertDialog.Builder mydialog = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);

        View myview = inflater.inflate(R.layout.updateinputfield, null);
        mydialog.setView(myview);

        final AlertDialog dialog = mydialog.create();

        titleUp = myview.findViewById(R.id.title_edit1);
        noteUp = myview.findViewById(R.id.note_edit1);

        titleUp.setText(title);
        titleUp.setSelection(title.length());

        noteUp.setText(note);
        noteUp.setSelection(note.length());
        getBtnDeleteUp = myview.findViewById(R.id.button_floating1);
        btnUpdateUp = myview.findViewById(R.id.button_floating2);


        btnUpdateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleUp.getText().toString().trim();
                note = noteUp.getText().toString().trim();
                model data = new model(title, note, date56, post_key);
                mdatabase.child(post_key).setValue(data);
                dialog.dismiss();
            }
        });
        dialog.show();
        getBtnDeleteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase.child(post_key).removeValue();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}


