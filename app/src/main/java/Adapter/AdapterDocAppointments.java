package Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.system_dev_lab.R;

import java.util.ArrayList;

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
