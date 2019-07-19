package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenden.DataModels.TransactionModel;
import com.example.lenden.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class customer_latest_trans_adapter extends RecyclerView.Adapter<customer_latest_trans_adapter.viewholder> {
Context context;
ArrayList<TransactionModel> arrayList;

    public customer_latest_trans_adapter(Context context, ArrayList<TransactionModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Log.i("aaja aayi bahar",String.valueOf(arrayList.size()));
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_transaction_card,viewGroup,false);
        return  new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
        int pos = arrayList.size()-i-1;
        viewholder.cust_phone.setText("Phone no. : "+arrayList.get(pos).getCust_mobileNumber());
        viewholder.trans_id.setText("Trans id : "+arrayList.get(pos).getTrans_id());
        viewholder.trans_type.setText("Trans type :"+arrayList.get(pos).getTrans_type());
        viewholder.trans_amt.setText("Amount :"+arrayList.get(pos).getTrans_amt());
        String timestamp[] = arrayList.get(pos).getTimestamp().split(" ");
        viewholder.trans_date.setText("Date :"+timestamp[0]);
        viewholder.trans_time.setText("Time :"+timestamp[1]+" "+timestamp[2]);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView trans_id,cust_phone,trans_amt,trans_type,trans_date,trans_time;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            trans_id=itemView.findViewById(R.id.cust_name_crd);
            cust_phone=itemView.findViewById(R.id.cust_phone_crd);
            trans_amt=itemView.findViewById(R.id.trans_amt);
            trans_date=itemView.findViewById(R.id.trans_date);
            trans_type=itemView.findViewById(R.id.trans_type);
            trans_time=itemView.findViewById(R.id.trans_time);

        }
    }
}
