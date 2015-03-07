package timur.karev.radionana.fragments;

import timur.karev.radionana.R;
import timur.karev.radionana.R.id;
import timur.karev.radionana.R.layout;
import timur.karev.radionana.R.string;
import timur.karev.radionana.controller.NanaContact;
import timur.karev.radionana.controller.NanaController;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SettingsFragment extends Fragment {
	
	private NanaController mNana;
	private Button mMainPhoneButton;
	private TextView mMainPhoneText;
	
	private Button mStartPauseButton;
	private TextView mStartPauseText;
	private TextView mStartPauseValueText;
	
	private Button mRecallBut;
	private Button mLowBut;
	private Button mMisBut;
	
	
	@Override
	public void onCreate (Bundle instance){
		super.onCreate(instance);
		
		mNana = NanaController.getNana(getActivity().getApplicationContext());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment, container, false);
        
        mMainPhoneButton = (Button)v.findViewById(R.id.set_main_phone);
        mMainPhoneText = (TextView)v.findViewById(R.id.set_textView_main_phone);
    	mStartPauseButton = (Button)v.findViewById(R.id.set_pause);
    	mStartPauseValueText = (TextView)v.findViewById(R.id.set_textView_value);
    	mRecallBut = (Button)v.findViewById(R.id.set_fragment_button_extra);
    	mLowBut = (Button)v.findViewById(R.id.set_fragment_button_sms_low);
    	mMisBut = (Button)v.findViewById(R.id.set_fragment_button_sms_mis);
    	
    	mStartPauseValueText.setText(NanaController.fromSecToTimer(mNana.getSetPause()));
        
        updateView();       
        return v;
	}
        
        @Override
        public void onActivityResult(int reqCode, int resultCode, Intent data) {
                super.onActivityResult(reqCode, resultCode, data);
                
                if (resultCode == getActivity().RESULT_OK){

                mNana.onActivityResult(reqCode, resultCode, data);
                updateView();
                }
        }
        
        private void showContactPicer(int code){
        	Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        	startActivityForResult(intent, code);
        }
        
        private void updateView(){
        	
        	if (mNana.isMainPhoneEmpty()){
        		mMainPhoneButton.setBackgroundColor(Color.RED);
        		mMainPhoneText.setText(R.string.text_need_main_phone);
        		
        	} else {
        		mMainPhoneButton.setBackgroundColor(Color.GREEN);
        		NanaContact c = mNana.getMainContact();
        		mMainPhoneText.setText(c.getName() + "   " + c.getNumber());
        	}
        }
 }
