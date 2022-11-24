package Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.system_dev_lab.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.AdapterAppointment;
import Models.ModelAppointment;

public class HistoryActivity extends AppCompatActivity {

    //declaring Firebase auth
    FirebaseAuth firebaseAuth;
    //declaring views
    RecyclerView historyRv;
    ArrayList<ModelAppointment> appointmentArrayList;
    AdapterAppointment adapterAppointment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //initializing
        firebaseAuth = FirebaseAuth.getInstance();
        historyRv = findViewById(R.id.historyRv);

        //invoking method
        loadMyAppointment();
    }

    private void loadMyAppointment() {

        //initializing arraylist
        appointmentArrayList = new ArrayList<>();

        //saving data to firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid())
                .child("Appointments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clear the list
                        appointmentArrayList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()){
                            ModelAppointment modelAppointment = ds.getValue(ModelAppointment.class);
                            appointmentArrayList.add(modelAppointment);
                            adapterAppointment = new AdapterAppointment(HistoryActivity.this, appointmentArrayList);

                            //setting adpter
                            historyRv.setAdapter(adapterAppointment);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}