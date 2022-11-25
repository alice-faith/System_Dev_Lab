package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.system_dev_lab.R;

import java.util.ArrayList;

import Activities.SpecificPatientFullDetailsActivity;
import Models.ModelFullDetails;

public class AdapterFullDetails extends RecyclerView.Adapter<AdapterFullDetails.HolderFullDetails>{

    Context context;
    ArrayList<ModelFullDetails> modelFullDetailsArrayList;

    public AdapterFullDetails(Context context, ArrayList<ModelFullDetails> modelFullDetailsArrayList) {
        this.context = context;
        this.modelFullDetailsArrayList = modelFullDetailsArrayList;
    }

    @NonNull
    @Override
    public HolderFullDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_full_details, parent, false);
        return new HolderFullDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderFullDetails holder, int position) {

        ModelFullDetails modelFullDetails = modelFullDetailsArrayList.get(position);
        String patientName, patientPhone, patientEmail, nameFrame, framePrize, uid, diagnosis, date, lensAmount, totalAmount;

        patientName = modelFullDetails.getPatientName();
        patientPhone = modelFullDetails.getPatientPhone();
        patientEmail = modelFullDetails.getPatientEmail();
        uid = modelFullDetails.getUid();
        diagnosis = modelFullDetails.getDiagnosis();
        date = modelFullDetails.getDate();


        holder.nameFDTv.setText(patientName);
        holder.dateFDTv.setText(date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpecificPatientFullDetailsActivity.class);
                //Using intent to pass data from adapter to FullDetailsActivity
                intent.putExtra("Name", patientName);
                intent.putExtra("Phone", patientPhone);
                intent.putExtra("Email", patientEmail);
                intent.putExtra("UserID", uid);
                intent.putExtra("Diagnosis", diagnosis);
                intent.putExtra("Date", date);


                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelFullDetailsArrayList.size();
    }

    class HolderFullDetails extends RecyclerView.ViewHolder {
        TextView nameFDTv, dateFDTv;

        public HolderFullDetails(@NonNull View itemView) {
            super(itemView);

            nameFDTv = itemView.findViewById(R.id.nameFDTv);
            dateFDTv = itemView.findViewById(R.id.dateFDTv);

        }
    }



}

