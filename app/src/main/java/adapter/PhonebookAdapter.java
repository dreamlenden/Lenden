package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.lenden.DataModels.KeyPairBoolData;
import com.example.lenden.R;

import java.util.ArrayList;
import java.util.List;

public class PhonebookAdapter extends BaseAdapter implements Filterable {

    private int selected = 0;
    List<KeyPairBoolData> arrayList;
    List<KeyPairBoolData> mOriginalValues; // Original Values
    LayoutInflater inflater;
    Context mcontext;
    public PhonebookAdapter(Context context, List<KeyPairBoolData> arrayList) {
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        mcontext=context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView textView;
        CheckBox checkBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_listview_multiple, parent, false);
            holder.textView = (TextView) convertView.findViewById(R.id.alertTextView);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.alertCheckbox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int background = R.color.white;


        final KeyPairBoolData data = arrayList.get(position);

        holder.textView.setText(data.getName());
        holder.textView.setTypeface(null, Typeface.NORMAL);
        holder.checkBox.setChecked(data.isSelected());

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (data.isSelected()) { // deselect
                    selected--;
                } else { // selected
                    selected++;
                }

                final ViewHolder temp = (ViewHolder) v.getTag();
                temp.checkBox.setChecked(!temp.checkBox.isChecked());

                data.setSelected(!data.isSelected());
                Log.i("yhaaaa", "On Click Selected Item : " + data.getName() + " : " + data.isSelected());
                notifyDataSetChanged();
            }
        });
        if (data.isSelected()) {
            holder.textView.setTypeface(null, Typeface.BOLD);
            holder.textView.setTextColor(Color.WHITE);
            convertView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.list_selected));
        } else {
            holder.textView.setTypeface(null, Typeface.NORMAL);
            holder.textView.setTextColor(Color.GRAY);
            convertView.setBackgroundColor(ContextCompat.getColor(mcontext, background));
        }
        holder.checkBox.setTag(holder);

        return convertView;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                arrayList = (List<KeyPairBoolData>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<KeyPairBoolData> FilteredArrList = new ArrayList<>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<>(arrayList); // saves the original data in mOriginalValues
                }
                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        Log.i("idhrrr", "Filter : " + mOriginalValues.get(i).getName() + " -> " + mOriginalValues.get(i).isSelected());
                        String data = mOriginalValues.get(i).getName();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(mOriginalValues.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
    }
}