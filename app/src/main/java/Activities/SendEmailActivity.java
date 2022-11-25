package Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.system_dev_lab.R;

import Constant.Constants;
import Constant.Constants2;

public class SendEmailActivity extends AppCompatActivity {
    private EditText sendToEt, subjectEt, bodyEt;
    private ImageButton subjectDD, bodyDD;
    private Button sendBtn;
    private TextView homeSETv;
    private LinearLayout layoutSendEmail, layoutEmailSuccess;
    String name, uid, email;
    String receiver, subject, body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        sendToEt = findViewById(R.id.sendToEt);
        subjectEt = findViewById(R.id.subjectEt);
        bodyEt = findViewById(R.id.bodyEt);
        subjectDD = findViewById(R.id.subjectDD);
        bodyDD = findViewById(R.id.bodyDD);
        sendBtn = findViewById(R.id.sendBtn);
        homeSETv = findViewById(R.id.homeSETv);
        layoutSendEmail = findViewById(R.id.layoutSendEmail);
        layoutEmailSuccess = findViewById(R.id.layoutEmailSuccess);

        layoutEmailSuccess.setVisibility(View.INVISIBLE);

        //getting data from diagnosis and assign frames activity
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        uid = getIntent().getStringExtra("uid");
        Constants2.pname = name;
        Toast.makeText(this, "Name "+name, Toast.LENGTH_LONG).show();

        //set email to sendToEt

        sendToEt.setText(email);

        subjectDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSubject();
            }
        });

        //Handling bodyDD
        bodyDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBody();
            }
        });

        //Handling the send button
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        //Handling homeSe event listener
        homeSETv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SendEmailActivity.this, DocHomeActivity.class));
            }
        });

    }


    private void sendEmail() {
        //Getting data from UI
        receiver = sendToEt.getText().toString();
        subject = subjectEt.getText().toString();
        body = bodyEt.getText().toString();


        //Use intent to send email
        Intent intent = new Intent(Intent.ACTION_SEND);

        //Moving with the data from this activity to gmail
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {receiver});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        //Set type of intent
        intent.setType("message/rfc822");

        try {
            //start activity with intent with chooser
            startActivity(Intent.createChooser(intent,"Choose An Email Client:"));
            clearUI();
        }
        catch (android.content.ActivityNotFoundException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void clearUI() {
        //show
        layoutEmailSuccess.setVisibility(View.VISIBLE);
        //Hide
        layoutSendEmail.setVisibility(View.INVISIBLE);
    }

    private void showBody() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(Constants.emailBody, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //Set email body from dropdown options
                String body = Constants.emailBody[which];
                bodyEt.setText(body);
            }
        }).show();

    }

    private void showSubject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(Constants.emailSubject, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //Set email subject from dropdown options
                String subject = Constants.emailSubject[which];
                subjectEt.setText(subject);
            }
        }).show();
    }
}