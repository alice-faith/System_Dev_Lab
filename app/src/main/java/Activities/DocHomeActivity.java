package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.system_dev_lab.R;

public class DocHomeActivity extends AppCompatActivity {

    //declare views.
    private TextView appointmentsTV, fullDetailsHTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_home);

        //initialize views
        appointmentsTV = findViewById(R.id.appointmentsTV);
        fullDetailsHTv = findViewById(R.id.fullDetailsHTv);

        //handle appointmentsTV button
        appointmentsTV.setOnClickListener( e->{
            startActivity(new Intent(DocHomeActivity.this, AppointmentsActivity.class));
        });

        //handling fullDetailsHTv button
        fullDetailsHTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DocHomeActivity.this, NotifyPatientsActivity.class);
                startActivity(intent);
            }
        });

    }
}