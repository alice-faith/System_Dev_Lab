package Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.system_dev_lab.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DiagnosisActivity extends AppCompatActivity {

    private TextView nameTvAF, phoneTvAF, emailTvAF, homeAFTv;
    private EditText diagnosisEt;

    private Button saveBtn;

    private LinearLayout  layoutSaveData, layoutSaveSuccess;

    ProgressDialog progressDialog;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);


        nameTvAF = findViewById(R.id.nameTvAF);
        phoneTvAF = findViewById(R.id.phoneTvAF);
        emailTvAF = findViewById(R.id.emailTvAF);
        saveBtn = findViewById(R.id.saveBtn);
        layoutSaveData = findViewById(R.id.layoutSaveData);
        layoutSaveSuccess = findViewById(R.id.layoutSaveSuccess);
        homeAFTv = findViewById(R.id.homeAFTv);
        diagnosisEt = findViewById(R.id.diagnosisEt);

        //get data from AdapterAppointments
        String name = getIntent().getStringExtra("name");
        uid = getIntent().getStringExtra("uid");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("SAVING DATA");
        //progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        //hide sendNotificationTv
        layoutSaveSuccess.setVisibility(View.INVISIBLE);

        //set data on UI
        nameTvAF.setText(name);
        phoneTvAF.setText(phone);
        emailTvAF.setText(email);

        homeAFTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiagnosisActivity.this, DocHomeActivity.class));
            }
        });

        //Handing the save button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveData();
            }
        });

    }

    private void saveData() {
        progressDialog.show();

        //get data from UI

        String pName = nameTvAF.getText().toString();
        String pPhone = phoneTvAF.getText().toString();
        String pEmail = emailTvAF.getText().toString();


        String pattern = "dd_MM_yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());


        //use hashmaps
        HashMap<String, Object> hashMap =new HashMap<>();
        hashMap.put("patientName", pName);
        hashMap.put("patientPhone", pPhone);
        hashMap.put("patientEmail", pEmail);
        //hashMap.put("numberFrame", numberFrame);
        hashMap.put("date",""+date);
        hashMap.put("uid", uid);

        saveEntireData();
    }

    private void saveEntireData() {


        String pattern = "dd_MM_yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());

        //get data from UI

        String pName = nameTvAF.getText().toString();
        String pPhone = phoneTvAF.getText().toString();
        String pEmail = emailTvAF.getText().toString();
        String diagnosis = diagnosisEt.getText().toString();


        //use hashmaps
        HashMap<String, Object> hashMap =new HashMap<>();
        hashMap.put("patientName", pName);
        hashMap.put("patientPhone", pPhone);
        hashMap.put("patientEmail", pEmail);

        //hashMap.put("numberFrame", numberFrame);
        hashMap.put("uid", uid);
        hashMap.put("diagnosis", diagnosis);
        hashMap.put("date",""+date);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("FullDetails");
        reference.child(""+date).child(uid).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        //hide layoutSaveData
                        layoutSaveData.setVisibility(View.INVISIBLE);
                        layoutSaveSuccess.setVisibility(View.VISIBLE);
                        Toast.makeText(DiagnosisActivity.this, "2. successfully Saved Data", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DiagnosisActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}