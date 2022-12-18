package Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.system_dev_lab.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import Constant.Constant;

public class BookSelectTimeActivity extends AppCompatActivity {
    //Declaring Views and Variables
    private TextView timeTv, errorTimeMsgTv, completeTimeTv, dateBKTv, proceedTv;
    private Button pickTimeBtn;
    String bookedTimeSlot;
    String name, email, phone, uid;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_select_time);

        //initializing views
        timeTv = findViewById(R.id.timeTv);
        errorTimeMsgTv = findViewById(R.id.errorTimeMsgTv);
        pickTimeBtn = findViewById(R.id.pickTimeBtn);
        completeTimeTv = findViewById(R.id.completeTimeTv);
        dateBKTv = findViewById(R.id.dateBKTv);
        proceedTv = findViewById(R.id.proceedTv);

        //initializing firebase
        firebaseAuth = FirebaseAuth.getInstance();


        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Saving Users Data");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        dateBKTv.setText(Constant.AppointmentDate);

        //Handling dateBKTv Button
        dateBKTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookSelectTimeActivity.this, BookSelectDateActivity.class));
            }
        });

        //handle pickTimeBtn button
        pickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTimeSlot ();
            }
        });

        //handle completeTimeTv button
        completeTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validating whether a timeslot has been selected
                validate();
            }
        });

        //Handling proceedTv
        proceedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnMyDb();
            }
        });



    }

    private void validate() {
        //it one has not selected timeslot, show error message
        if ( timeTv.getText().toString().equals("Time")) {
            errorTimeMsgTv.setText("You have not selected a timeslot");
            errorTimeMsgTv.setVisibility(View.VISIBLE);
        }
        else {
            loadPersonalInformation();

        }
    }


    private void loadPersonalInformation() {
        //retrieve personal data
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.orderByChild("uid").equalTo(firebaseAuth.getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            name = ""+ds.child("name").getValue();
                            phone = ""+ds.child("phone").getValue();
                            email = ""+ds.child("email").getValue();
                            uid = ""+ds.child("uid").getValue();

                            saveOnClinicsDb();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void saveOnClinicsDb() {
        //save data in Hospitals ID in firebase
        progressDialog.show();
        String timeSlot = Constant.TimeSlot;
        String date = Constant.AppointmentDate;
        String service = Constant.service;
        String timeStamp = ""+System.currentTimeMillis();

        HashMap <String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("date", date);
        hashMap.put("name", name);
        hashMap.put("phone", phone);
        hashMap.put("email", email);
        hashMap.put("timeSlot", timeSlot);
        hashMap.put("timeStamp", timeStamp);
        hashMap.put("status", "pending");
        hashMap.put("service", service);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child("Appointments")
                .child(Constant.AppointmentDate)
                .child(firebaseAuth.getUid())
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //success
                        Toast.makeText(BookSelectTimeActivity.this, "Saved OnMyClinic", Toast.LENGTH_SHORT).show();

                        //saveOnMyDb();
                        progressDialog.dismiss();
                        completeTimeTv.setVisibility(View.GONE);
                        proceedTv.setVisibility(View.VISIBLE);



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(BookSelectTimeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onBackPressed() {

        //on pressing backpress btn, dont go back... ie remain in the same activity

        //super.onBackPressed();
    }

    private void pickTimeSlot() {

        //pick timeslot
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SELECT TIMESLOT")
                .setItems(Constant.TimeSlots, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        bookedTimeSlot = Constant.TimeSlots[which];

                        Constant.TimeSlot = bookedTimeSlot;

                        checkTimeslotAvailability(bookedTimeSlot);
                    }
                }).show();
    }

    private void checkTimeslotAvailability(String bookedTimeSlot) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TimeSlot")
                .child(Constant.AppointmentDate).child(bookedTimeSlot);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //Appointment exists
                    if (snapshot.getChildrenCount() <= 1) //There are two booked appointments i.e 0 and 1
                    {
                        timeTv.setVisibility(View.VISIBLE);
                        timeTv.setText(bookedTimeSlot);
                        errorTimeMsgTv.setVisibility(View.INVISIBLE);
                        Toast.makeText(BookSelectTimeActivity.this, "" + snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                    } else if (snapshot.getChildrenCount() > 1) {
                        errorTimeMsgTv.setText("ERROR: This timeSlot is full\n Please select another time slot");
                        errorTimeMsgTv.setVisibility(View.VISIBLE);
                        timeTv.setVisibility(View.INVISIBLE);
                        timeTv.setText("");
                        Toast.makeText(BookSelectTimeActivity.this, "error: TimeSlot is full\n Count = " + snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

                    }
                } else if (!snapshot.exists()) {
                    //Appointment does not exist
                    timeTv.setVisibility(View.VISIBLE);
                    timeTv.setText(bookedTimeSlot);
                    errorTimeMsgTv.setVisibility(View.INVISIBLE);
                    Toast.makeText(BookSelectTimeActivity.this, "Empty", Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void saveOnMyDb() {
        //save data on My ID in firebase

        //progressDialog.show();

        String timeSlot = Constant.TimeSlot;
        String date = Constant.AppointmentDate;
        String service = Constant.service;
        String timeStamp = ""+System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("date", date);
        hashMap.put("timeSlot", timeSlot);
        hashMap.put("timeStamp", timeStamp);
        hashMap.put("status", "pending");
        hashMap.put("service", service);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid())
                .child("Appointments")
                .child(Constant.AppointmentDate)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Success
                        saveTimeSlotCount();
                        progressDialog.dismiss();

                        startActivity(new Intent(BookSelectTimeActivity.this, HomeActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed
                        Toast.makeText(BookSelectTimeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveTimeSlotCount() {
        long timeStamp = System.currentTimeMillis();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TimeSlot")
                .child(Constant.AppointmentDate).child(Constant.TimeSlot).child(timeStamp+"");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Date", Constant.AppointmentDate);
        hashMap.put("TimeSlot", Constant.TimeSlot);
        hashMap.put("Name", name);
        databaseReference.setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(BookSelectTimeActivity.this, "Saved Count", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(BookSelectTimeActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}