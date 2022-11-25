package Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.system_dev_lab.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Adapter.AdapterFullDetails;
import Constant.Constants;
import Models.ModelFullDetails;
import Services.DatePicker;

public class NotifyPatientsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView datePickerFDTv, searchDateFDTv;
    private RecyclerView fullDetailsRv;
    FirebaseAuth firebaseAuth;
    ArrayList<ModelFullDetails> fullDetailsArrayList;
    AdapterFullDetails adapterFullDetails;

    private String name, phone, email, uid, date, diagnosis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_patients);

        datePickerFDTv = findViewById(R.id.datePickerFDTv);
        searchDateFDTv = findViewById(R.id.searchDateFDTv);
        fullDetailsRv = findViewById(R.id.fullDetailsRv);

        firebaseAuth = FirebaseAuth.getInstance();

        //datepickerFD set onclick listener
        datePickerFDTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = new DatePicker();
                datePicker.show(getSupportFragmentManager(),"datePick");
            }
        });

        //searchFD onclick listener
        searchDateFDTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFullDetails();
            }
        });

    }

    private void loadFullDetails() {
        fullDetailsArrayList = new ArrayList<>();

        String appointmentDate = datePickerFDTv.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("FullDetails");
        reference.child(""+appointmentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clear
                fullDetailsArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ModelFullDetails modelFullDetails = ds.getValue(ModelFullDetails.class);
                    fullDetailsArrayList.add(modelFullDetails);
                    adapterFullDetails = new AdapterFullDetails(NotifyPatientsActivity.this, fullDetailsArrayList);
                    fullDetailsRv.setAdapter(adapterFullDetails);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
        String dateTime = simpleDateFormat.format(mCalendar.getTime()).toString();

        //Setting date to UI
        //dateTv.setText(selectDate);
        datePickerFDTv.setText(dateTime);


        //save data as static
        Constants.AppointmentDate = dateTime;
        Constants.AppointmentDateLong = mCalendar.getTime();
    }


}