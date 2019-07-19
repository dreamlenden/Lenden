package Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenden.DataModels.ContactModel;
import com.example.lenden.DataModels.KeyPairBoolData;
import com.example.lenden.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import adapter.PhonebookAdapter;

public class ImportCustomersPhonebook extends AppCompatActivity {
    Button submit;
    ListView listView;
    private List<KeyPairBoolData> items;
    private PhonebookAdapter adapter;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<ContactModel> contacts = new ArrayList<>();
    ArrayList<ContactModel> selectedContacts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_customers_phonebook);

        submit = findViewById(R.id.submit);
        listView=findViewById(R.id.alertSearchListView);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            requestPermissions(
                    new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 1);
        }

        getContacts();
        final List<String> list = names;
        final List<KeyPairBoolData> listArray0 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            h.setContact(contacts.get(i));
            listArray0.add(h);
        }
        items=listArray0;


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i("idhr bhahahha  "+i,items.get(i).getName());
                        selectedContacts.add(items.get(i).getContact());
                    }
                }
                Intent intent = new Intent(ImportCustomersPhonebook.this, CreateCustomer.class);
                intent.putExtra("from where","import");
                intent.putExtra("contacts",selectedContacts);
                Log.i("size aa jaa bhai me",String.valueOf(contacts.size()));
                intent.putExtra("user_phoneNumber",getIntent().getStringExtra("user_phoneNumber"));
                startActivity(intent);
                //finish();

//                listener.onItemsSelected(items);
            }
        });


        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setFastScrollEnabled(false);
        adapter = new PhonebookAdapter(getApplicationContext(), items);
        listView.setAdapter(adapter);

        final EditText editText = (EditText) findViewById(R.id.alertSearchEditText);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getContacts(){
        contacts.clear();
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                //plus any other properties you wish to query
        }, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        //cursor.moveToFirst();
        Log.i("ababa",String.valueOf(cursor.getCount()));

        HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();
        int indexOfNormalizedNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER);
        while(cursor.moveToNext()){
            String normalizedNumber = cursor.getString(indexOfNormalizedNumber);
            if (normalizedNumbersAlreadyFound.add(normalizedNumber)) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.i("names", name + " //// " + phone + " ******  "+ normalizedNumber );
                if(normalizedNumber.contains("+91")) {
                    normalizedNumbersAlreadyFound.add(normalizedNumber.replace("+91", ""));
                    normalizedNumbersAlreadyFound.add(normalizedNumber.replace("+91", "0"));
                }
                names.add(name);
                contacts.add(new ContactModel(name, phone));
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
