package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.system_dev_lab.R;

public class SpecificPatientFullDetailsActivity extends AppCompatActivity {

    private TextView nameTvSP, phoneTvSP, emailTvSP, diagnosisTvSP;
    private Button sendEmailSPBtn;
    private String name, phone, email, diagnosis, date, uid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_patient_full_details);

        nameTvSP = findViewById(R.id.nameTvSP);
        phoneTvSP = findViewById(R.id.phoneTvSP);
        emailTvSP = findViewById(R.id.emailTvSP);
        diagnosisTvSP = findViewById(R.id.diagnosisTvSP);
        sendEmailSPBtn = findViewById(R.id.sendEmailSPBtn);

        setDate();

        //Handling sendEmail Btn
        sendEmailSPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpecificPatientFullDetailsActivity.this, SendEmailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("uid", uid);
                startActivity(intent);

            }
        });


    }

    private void setDate() {

        //Getting data from AdapterFullDetails
        name = getIntent().getStringExtra("Name");
        phone = getIntent().getStringExtra("Phone");
        email = getIntent().getStringExtra("Email");
        diagnosis = getIntent().getStringExtra("Diagnosis");
        date = getIntent().getStringExtra("Date");
        uid = getIntent().getStringExtra("UserID");

        //Setting data to UI
        nameTvSP.setText(name);
        phoneTvSP.setText(phone);
        emailTvSP.setText(email);
        diagnosisTvSP.setText(diagnosis);


    }
}