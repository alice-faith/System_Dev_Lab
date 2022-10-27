package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.system_dev_lab.R;

public class DocLoginActivity extends AppCompatActivity {

    //Declare UI
    private TextView emailEtDocL, passwordEtDocL, signInDocBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_login);

        //initialize view
        emailEtDocL = findViewById(R.id.emailEtDocL);
        passwordEtDocL = findViewById(R.id.passwordEtDocL);
        signInDocBtn = findViewById(R.id.signInDocBtn);
    }
}