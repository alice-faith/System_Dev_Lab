package Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.system_dev_lab.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Adapter.AdapterDocAppointments;
import Constant.Constants;
import Models.ModelDocAppointments;
import Services.DatePicker;

public class AppointmentsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //declaring views
    private RecyclerView appointmentsRV;
    private TextView datePickerTv, searchDateTv;

    FirebaseAuth firebaseAuth;


    ArrayList<ModelDocAppointments> modelAppointmentArrayList;
    AdapterDocAppointments adapterAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        //intializing views
        appointmentsRV = findViewById(R.id.appointmentsRV);
        datePickerTv = findViewById(R.id.datePickerTv);
        searchDateTv = findViewById(R.id.searchDateTv);

        firebaseAuth = FirebaseAuth.getInstance();

        //handle datePickerTv click
        datePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pick date
                DatePicker datePicker = new DatePicker();
                datePicker.show(getSupportFragmentManager(),"datePick");
            }
        });

        //handle searchDateTv click
        searchDateTv.setOnClickListener( e -> {
            loadAppointments();
        });

        //invoke method
        verifyUser();

    }

    private void loadAppointments() {
        //intialize arraylist
        modelAppointmentArrayList = new ArrayList<>();

        //save data db
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child("Appointments").child(Constants.AppointmentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear list
                modelAppointmentArrayList.clear();

                //for loop to iterate through data
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelDocAppointments modelAppointment = ds.getValue(ModelDocAppointments.class);

                    modelAppointmentArrayList.add(modelAppointment);

                    adapterAppointments = new AdapterDocAppointments(AppointmentsActivity.this, modelAppointmentArrayList);

                    appointmentsRV.setAdapter(adapterAppointments);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //check or verify user
    private void verifyUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user == null){
            startActivity(new Intent(AppointmentsActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);//set year
        mCalendar.set(Calendar.MONTH, month);//set month
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);// set day

        //use full date format
        String selectDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());

        //specify date format
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateTime = simpleDateFormat.format(mCalendar.getTime()).toString();

        //Setting date to UI
        //dateTv.setText(selectDate);
        datePickerTv.setText(dateTime);


        //save data as static
        Constants.AppointmentDate = dateTime;
        Constants.AppointmentDateLong = mCalendar.getTime();


    }
}