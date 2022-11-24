package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.system_dev_lab.R;

import java.util.ArrayList;

import Models.ModelAppointment;

public class AdapterAppointment extends RecyclerView.Adapter<AdapterAppointment.HolderAdapterAppointment> {

    //declaring
    Context context;
    ArrayList<ModelAppointment> appointmentArrayList;

    public AdapterAppointment(Context context, ArrayList<ModelAppointment> appointmentArrayList) {
        this.context = context;
        this.appointmentArrayList = appointmentArrayList;
    }

    @NonNull
    @Override
    public HolderAdapterAppointment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_appointment,parent, false);

        return new HolderAdapterAppointment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAdapterAppointment holder, int position) {
        //getting data
        ModelAppointment modelAppointment = appointmentArrayList.get(position);
        String date = modelAppointment.getDate();
        String timeSlot = modelAppointment.getTimeSlot();
        String status = modelAppointment.getStatus();
        String service = modelAppointment.getService();

        //setting data
        holder.dateTvHist.setText(date);
        holder.statusTvHist.setText(status);
        holder.timeTvHist.setText(timeSlot);
        holder.serviceTvHist.setText(service);

    }

    @Override
    public int getItemCount() {
        return appointmentArrayList.size();
    }

    class HolderAdapterAppointment extends RecyclerView.ViewHolder{

        //declaring views
        TextView dateTvHist, timeTvHist, statusTvHist, serviceTvHist;

        public HolderAdapterAppointment(@NonNull View itemView) {
            super(itemView);

            //initializing views
            dateTvHist  = itemView.findViewById(R.id.dateTvHist);
            timeTvHist = itemView.findViewById(R.id.timeTvHist);
            statusTvHist = itemView.findViewById(R.id.statusTvHist);
            serviceTvHist = itemView.findViewById(R.id.serviceTvHist);
        }
    }


}
