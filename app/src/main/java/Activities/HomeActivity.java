package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system_dev_lab.R;

public class HomeActivity extends AppCompatActivity {
    //Declare UI
    private TextView historyTv, bookTv, callTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialize view
        bookTv = findViewById(R.id.bookTv);
        historyTv = findViewById(R.id.historyTv);
        callTv = findViewById(R.id.callTv);

        //handle bookTv listener
        bookTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start BookSelectDate Activity
                Intent intent = new Intent(HomeActivity.this, BookSelectDateActivity.class);
                startActivity(intent);

            }
        });

        //handle HistoryTv listener
        historyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start History Activity
                Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        //Handling CALL TV
        callTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhone();

            }
        });
    }

    private void dialPhone() {
        String phone = "0745061732";
        //call
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(phone))));
        Toast.makeText(this, ""+phone, Toast.LENGTH_SHORT).show();
    }
}