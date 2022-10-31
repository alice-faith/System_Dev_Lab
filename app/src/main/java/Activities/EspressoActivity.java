package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.system_dev_lab.R;

public class EspressoActivity extends AppCompatActivity {

    TextView preferred_language;

    public void onClick(View view) {

        // whenever a user chooses a preferred language
        // by tapping button, it changes the chosen
        // language textView
        switch (view.getId()){
            case R.id.english:
                preferred_language.setText("English");
                break;
            case R.id.french:
                preferred_language.setText("French");
                break;
            case R.id.german:
                preferred_language.setText("German");
                break;
            case R.id.hindi:
                preferred_language.setText("Hindi");
                break;
            case R.id.urdu:
                preferred_language.setText("Urdu");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso);

        // initializing the textview
        preferred_language = findViewById(R.id.preferred_language);
    }
}