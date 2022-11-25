package Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.system_dev_lab.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import Activities.DiagnosisActivity;
import Constant.Constants;
import Models.ModelDocAppointments;

public class AdapterDocAppointments extends RecyclerView.Adapter<AdapterDocAppointments.HolderAppointment> {

    //declare vars
    Context context;
    ArrayList<ModelDocAppointments> modelAppointmentArrayList;
    String name, phoneNo, uid, email;
    ProgressDialog progressDialog;

    public AdapterDocAppointments(Context context, ArrayList<ModelDocAppointments> modelAppointmentArrayList) {
        this.context = context;
        this.modelAppointmentArrayList = modelAppointmentArrayList;
    }

    @NonNull
    @Override
    public HolderAppointment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_appointments, parent, false);

        return new HolderAppointment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAppointment holder, int position) {
        ModelDocAppointments modelAppointment = modelAppointmentArrayList.get(position);

        //getting data
        name = modelAppointment.getName();
        email = modelAppointment.getEmail();
        phoneNo = modelAppointment.getPhone();
        String timeSlot = modelAppointment.getTimeSlot();
        String timeStamp = modelAppointment.getTimeStamp();
        String status = modelAppointment.getStatus();
        uid = modelAppointment.getUid();
        String date = modelAppointment.getDate();
        String service = modelAppointment.getService();

        //Initializing Progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCancelable(false);

        //Setting data
        holder.nameTv.setText(name);
        holder.emailTv.setText(email);
        holder.phoneNoTv.setText(phoneNo);
        holder.timeSlotTv.setText(timeSlot);
        holder.statusTv.setText(status);
        holder.dateTv.setText(date);
        holder.serviceTv.setText(service);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateStatusDialog(uid);



            }
        });

    }

    private void showUpdateStatusDialog(String uid) {
        String [] status = {"Attended", "Missed"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update Status")
                .setItems(status, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String myStatus = status[i];




                        updateStatus(myStatus, uid, i);
                    }
                }).show();
    }

    private void updateStatus(String myStatus, String uid, int i) {

        progressDialog.setMessage("Saving data in progress");
        progressDialog.show();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", myStatus);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child("Appointments").child(Constants.AppointmentDate).child(uid)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //progressDialog.dismiss();

                        updatePatientDb(myStatus, uid, i);
                        // Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updatePatientDb(String myStatus, String uid, int i) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", myStatus);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(uid)
                .child("Appointments")
                .child(Constants.AppointmentDate)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();

                        Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        if (i== 0) {
                            // 0 = attended
                            Intent intent = new Intent(context, DiagnosisActivity.class);
                            intent.putExtra("uid", uid);
                            intent.putExtra("name", name);
                            intent.putExtra("phone", phoneNo);
                            intent.putExtra("email", email);
                            context.startActivity(intent);
                        }
                        else if (i == 1) {
                            //1 = missed
                            return;
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @Override
    public int getItemCount() {
        return modelAppointmentArrayList.size();
    }


    public class HolderAppointment extends RecyclerView.ViewHolder{

        private TextView nameTv, emailTv, phoneNoTv, timeTv, dateTv, statusTv, timeSlotTv, serviceTv;

        public HolderAppointment(@NonNull View itemView) {
            super(itemView);

            //Initializing View
            nameTv = itemView.findViewById(R.id.nameTv);
            emailTv = itemView.findViewById(R.id.emailTv);
            phoneNoTv = itemView.findViewById(R.id.phoneNoTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            statusTv = itemView.findViewById(R.id.StatusTv);
            timeSlotTv = itemView.findViewById(R.id.timeSlotTv);
            serviceTv = itemView.findViewById(R.id.serviceTv);


        }
    }
}
