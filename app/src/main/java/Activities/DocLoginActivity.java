package Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.system_dev_lab.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DocLoginActivity extends AppCompatActivity {

    //Declare UI
    EditText emailEtDocL, passwordEtDocL;
    Button signInDocBtn;
    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_login);

        //initialize view
        emailEtDocL = findViewById(R.id.emailEtDocL);
        passwordEtDocL = findViewById(R.id.passwordEtDocL);
        signInDocBtn = findViewById(R.id.signInDocBtn);

        //Initializing Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCancelable(false);

        //Initializing firebaseauth
        firebaseAuth = FirebaseAuth.getInstance();

        //Handle signup button listener
        signInDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();

            }
        });


    }

    private void validateInput() {
        if (emailEtDocL.getText().toString().isEmpty()) {
            emailEtDocL.setError("Please enter email");
            emailEtDocL.setFocusable(true);
            return;
        }


        if (passwordEtDocL.getText().toString().isEmpty()) {
            passwordEtDocL.setError("Please enter password");
            passwordEtDocL.setFocusable(true);
            return;
        }
        logIn();
    }

    private void logIn() {
        String email = emailEtDocL.getText().toString();
        String password = passwordEtDocL.getText().toString();



        if (email.equals("faithak19@gmail.com")) {

            progressDialog.setMessage("Login in progress");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //Login successfully
                            //Dismiss dialog
                            progressDialog.dismiss();

                            Intent intent = new Intent(DocLoginActivity.this, DocHomeActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Dismiss dialog
                            progressDialog.dismiss();

                            Toast.makeText(DocLoginActivity.this, "Login Unsuccessfully"+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }

        else {
            emailEtDocL.setError("Invalid email");
            emailEtDocL.setFocusable(true);
            return;
        }

    }


}