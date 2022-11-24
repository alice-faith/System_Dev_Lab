package Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.system_dev_lab.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Constant.Constant;
import Services.DatePicker;

public class BookSelectDateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    ///Declaring the UI components
    private Button pickDateBtn;
    private TextView dateTv, dateTv2, nextTv, errorMsgTv;
    private Spinner servicesSpinner;
    private String service;


    private String [] services = {"General Consultation", "Pap Smear", "Fertility Counselling", "Contraceptives", "Maternal Services"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_select_date);


        //initializing UI views
        pickDateBtn = findViewById(R.id.pickDateBtn);
        dateTv = findViewById(R.id.dateTv);
        dateTv2 = findViewById(R.id.dateTv2);
        nextTv = findViewById(R.id.nextTv);
        errorMsgTv = findViewById(R.id.errorMsgTv);

        //Set selected service
        setSelectedService();

        //Handle pickDateBtn listener
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pick date
                DatePicker datePicker = new DatePicker();
                datePicker.show(getSupportFragmentManager(),"datePick");

            }
        });


        //Handle nextTv listener
        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if one hasnt picked a date, show an error message
                if (dateTv2.getText().toString().equals("Date")){
                    errorMsgTv.setText("ERROR: Please select date to proceed");
                    errorMsgTv.setVisibility(View.VISIBLE);
                }
                else {
                    //one has picked a date

                    //use timestamp to compare between the current date and the picked date
                    long currentTimeStamp = System.currentTimeMillis();
                    long appointmentDateTimeStamp = Constant.AppointmentDateLong.getTime();
                    if (appointmentDateTimeStamp <=currentTimeStamp){
                        errorMsgTv.setText("ERROR: You have picked a passed date");
                        errorMsgTv.setVisibility(View.VISIBLE);
                    }
                    else{
                        Intent intent = new Intent(BookSelectDateActivity.this, BookSelectTimeActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });

    }

    private void setSelectedService() {
        //Initializing spinner view
        servicesSpinner = (Spinner)findViewById(R.id.serviceSpinner);
        //Initializing the adapter that will have the spinners
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_items, services);

        adapter.setDropDownViewResource(R.layout.spinner_items);
        servicesSpinner.setAdapter(adapter);

        //Handling onClicklistener
        servicesSpinner.setOnItemSelectedListener(this);
        String selection = "General Consultation";
        int spinnerPosition = adapter.getPosition(selection);
        servicesSpinner.setSelection(spinnerPosition);


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
        dateTv.setText(selectDate);
        dateTv2.setText(dateTime);

        errorMsgTv.setVisibility(View.INVISIBLE);

        //set the UI visible
        dateTv.setVisibility(View.VISIBLE);
        dateTv2.setVisibility(View.VISIBLE);

        //save data as static
        Constant.AppointmentDate = dateTime;
        Constant.AppointmentDateLong = mCalendar.getTime();



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       service = services[position];
        Toast.makeText(BookSelectDateActivity.this, service, Toast.LENGTH_SHORT).show();
        Constant.service = service;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}