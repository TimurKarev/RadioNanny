package timur.karev.radionana.fragments;

import java.util.ArrayList;

import timur.karev.radionana.R;
import timur.karev.radionana.controller.NanaContact;
import timur.karev.radionana.controller.NanaController;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsListFragment extends Fragment {

//	ArrayAdapter<NanaContact> adapter;
	ContactAdapter conAdapter;
//	ArrayList<NanaContact> mContacts;
	
	private static final String TAG = "SettingsListFragment";
	
	private static final String PICKER_CODE = "timur.karev.radionana.pickerCode"; 	
	private NanaController mNana;
	
	private int mParentCode;
	
	private Button mAddButton;
	private ListView mList;
	
	public void onResume(){
		super.onResume();
	}
	
	public void onCreate(Bundle instance){
		super.onCreate(instance);
		mNana = NanaController.getNana(getActivity().getApplicationContext());
		mParentCode = getArguments().getInt(PICKER_CODE, 0);
		conAdapter = new ContactAdapter(mNana.getContactList(mParentCode));
	}	
	
	public static SettingsListFragment newInstance(int code){
		SettingsListFragment fragment = new SettingsListFragment();

		Bundle args = new Bundle();
	    args.putInt(PICKER_CODE, code);
	    fragment.setArguments(args);
		return fragment;
	}
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_list_fragment, container, false);
        
        mAddButton = (Button)v.findViewById(R.id.settings_list_button1);
        mList = (ListView)v.findViewById(R.id.settings_list_listView1);
        mList.setAdapter(conAdapter);
        Log.d(TAG, "ON CREATE VIEW");
        
        mList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
              Log.d(TAG, "itemClick: position = " + position + ", id = "
                  + id);
              mNana.removeContact(mParentCode, position);
              conAdapter.notifyDataSetChanged();
            }
          });

        
        mAddButton.setOnClickListener(new View.OnClickListener() {
      	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	        	Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
	        	startActivityForResult(intent, mParentCode);
			}
		});
        return v;
    }
    
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
            super.onActivityResult(reqCode, resultCode, data);
         
         if (resultCode == getActivity().RESULT_OK){
           mNana.onActivityResult(reqCode, resultCode, data);
         //  View.inflate(getActivity(), R.layout.settings_fragment, null);
           
         }
         conAdapter.notifyDataSetChanged();
         
    }
    private class ContactAdapter extends ArrayAdapter<NanaContact> {
        public ContactAdapter(ArrayList<NanaContact> contacts) {
            super(getActivity(), android.R.layout.simple_list_item_1, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.starting_list_item, null);
            }

            // configure the view for this Crime
            NanaContact contact = getItem(position);

            TextView titleTextView =
                (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(contact.toString());
            
            return convertView;
        }
    }

}
