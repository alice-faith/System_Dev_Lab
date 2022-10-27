package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.system_dev_lab.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Button signUpBtn;
    EditText nameEt, phoneEt, emailEt, passwordEt, confirmPasswordEt;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize view
        signUpBtn = findViewById(R.id.signUpBtn);
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        confirmPasswordEt= findViewById(R.id.confirmPasswordEt);

        //Initializing Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registration in progress");
        progressDialog.setCancelable(false);

        //Initializing firebaseauth
        firebaseAuth = FirebaseAuth.getInstance();

        //Getting data from UI
        String name = nameEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        String confirmPassword = confirmPasswordEt.getText().toString();

        //Handle signup button listener
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();

            }
        });

    }

    private void validateInput() {
        //Validating the data
        if (nameEt.getText().toString().isEmpty()) {
            nameEt.setError("Please enter name");
            nameEt.setFocusable(true);
            return;
        }
        if (emailEt.getText().toString().isEmpty()) {
            emailEt.setError("Please enter email");
            emailEt.setFocusable(true);
            return;
        }
        if (phoneEt.getText().toString().isEmpty()) {
            phoneEt.setError("Please enter phone");
            phoneEt.setFocusable(true);
            return;
        }

        if (passwordEt.getText().toString().isEmpty()) {
            passwordEt.setError("Please enter password");
            passwordEt.setFocusable(true);
            return;
        }
        if (confirmPasswordEt.getText().toString().isEmpty()) {
            confirmPasswordEt.setError("Please confirm your password");
            confirmPasswordEt.setFocusable(true);
            return;
        }
        if ( !passwordEt.getText().toString().equals(confirmPasswordEt.getText().toString())) {
            confirmPasswordEt.setError("Passwords don't match");
            confirmPasswordEt.setFocusable(true);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailEt.getText().toString()).matches()){
            emailEt.setError("Invalid email format");
            emailEt.setFocusable(true);
            return;
        }

        createAccountWithGmail();

    }

    private void createAccountWithGmail() {
        //show dialog
        progressDialog.show();

        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        //Saving users data
                        savingData();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Dismiss dialog
                        progressDialog.dismiss();

                        Toast.makeText(RegisterActivity.this, "Register unsuccessful"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void savingData() {

        progressDialog.setMessage("Saving Users data");


        //Getting data from UI
        String name = nameEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", firebaseAuth.getUid());
        hashMap.put("name", name );
        hashMap.put("phone", phone  );
        hashMap.put("email", email  );


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseAuth.getUid()).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Saving successfully
                        //Dismiss dialog
                        progressDialog.dismiss();

                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Saving failed
                        //Dismiss dialog
                        progressDialog.dismiss();

                        Toast.makeText(RegisterActivity.this, "Data not saved"+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }


}