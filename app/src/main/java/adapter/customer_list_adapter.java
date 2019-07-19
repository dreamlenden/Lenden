package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenden.DataModels.CustomerModel;
import com.example.lenden.R;

import java.util.ArrayList;

import Activity.Cusotmer_add_transaction;
import Activity.SingleCustomerDetail;

public class customer_list_adapter extends RecyclerView.Adapter<customer_list_adapter.ViewHolder> {

    ArrayList<CustomerModel> arrayList;
    Context context;

    public customer_list_adapter(ArrayList<CustomerModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_customer_card,viewGroup,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cust_name.setText(arrayList.get(i).getName());
        viewHolder.cust_phoneNumber.setText(arrayList.get(i).getPhoneNumber());
        final String phone = arrayList.get(i).getPhoneNumber();
        final String name = arrayList.get(i).getName();
        viewHolder.add_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Cusotmer_add_transaction.class);
                intent.putExtra("cust_phoneNumber",phone);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleCustomerDetail.class);
                intent.putExtra("cust_phoneNumber",phone);
                intent.putExtra("cust_name",name);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cust_name,cust_phoneNumber;
        ImageView add_trans;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cust_name = itemView.findViewById(R.id.cust_name);
            cust_phoneNumber = itemView.findViewById(R.id.cust_phoneNumber);
            add_trans = itemView.findViewById(R.id.add_trans);



        }
    }
}
