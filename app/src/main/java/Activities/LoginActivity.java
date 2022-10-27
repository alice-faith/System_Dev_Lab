package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system_dev_lab.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Declare UI
     EditText emailEtL, passwordEtL;
     Button signInBtn, registerBtnL;

    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize view
        emailEtL = findViewById(R.id.emailEtL);
        passwordEtL = findViewById(R.id.passwordEtL);
        signInBtn = findViewById(R.id.signInBtn);
        registerBtnL = findViewById(R.id.registerBtnL);

        //Initializing Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCancelable(false);

        //Initializing firebaseauth
        firebaseAuth = FirebaseAuth.getInstance();

        //Handle signin button listener
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();

            }
        });


        //handle registerBtnL listener
        registerBtnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Register Activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    private void validateInput() {
        if (emailEtL.getText().toString().isEmpty()) {
            emailEtL.setError("Please enter email");
            emailEtL.setFocusable(true);
            return;
        }


        if (passwordEtL.getText().toString().isEmpty()) {
            passwordEtL.setError("Please enter password");
            passwordEtL.setFocusable(true);
            return;
        }
        logIn();
    }

    private void logIn() {
        String email = emailEtL.getText().toString();
        String password = passwordEtL.getText().toString();

        progressDialog.setMessage("Login in progress");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Login successfully
                        //Dismiss dialog
                        progressDialog.dismiss();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Dismiss dialog
                        progressDialog.dismiss();

                        Toast.makeText(LoginActivity.this, "Login Unsuccessfully"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }


}