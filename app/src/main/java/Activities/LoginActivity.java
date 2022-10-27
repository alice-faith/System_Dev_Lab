package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.system_dev_lab.R;

public class LoginActivity extends AppCompatActivity {

    //Declare UI
    private TextView emailEtL, passwordEtL, signInBtn, registerBtnL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize view
        emailEtL = findViewById(R.id.emailEtL);
        passwordEtL = findViewById(R.id.passwordEtL);
        signInBtn = findViewById(R.id.signInBtn);
        registerBtnL = findViewById(R.id.registerBtnL);


    }
}