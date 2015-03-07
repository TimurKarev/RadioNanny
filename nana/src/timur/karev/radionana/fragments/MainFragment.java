package timur.karev.radionana.fragments;

import java.util.ArrayList;
import java.util.List;

import timur.karev.radionana.MainService;
import timur.karev.radionana.R;
import timur.karev.radionana.controller.NanaController;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment {
	private static final String TAG = "MainFragment";
	
	private NanaController mNana; 
	
	private Button mButton1;
	private Button mMasterPhoneB;
	private Button mExtraPhoneB;
	
	private TextView mMasterPhoneET;
	private TextView mExtraPhoneET;

	private void updateButtonState(){
        if (mNana.isMyServiceRunning(MainService.class)){
        	  mButton1.setText(R.string.stop);
        	  makeAllViewsEnable(false);
        } else {
        	  mButton1.setText(R.string.start);
         	  makeAllViewsEnable(true);
//        	Log.d(TAG,"start" + MainService.isWorking());
        }
	}
	
	@Override
	public void onCreate(Bundle saved){
		super.onCreate(saved);
		
		mNana = NanaController.getNana(getActivity().getApplicationContext());
	}
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        
        mMasterPhoneET = (TextView)v.findViewById(R.id.editText1);
        mExtraPhoneET = (TextView)v.findViewById(R.id.editText2);

        mMasterPhoneB = (Button)v.findViewById(R.id.buttonGetMasterPhone);
        mMasterPhoneB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showContactPicer(PICK_MASTER_CONTACT);
			}
		});
        
        mExtraPhoneB = (Button)v.findViewById(R.id.buttonGetExtraPhone1);
        mExtraPhoneB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showContactPicer(PICK_EXTRA_CONTACT);
			}
		});
 
        mButton1 = (Button)v.findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mNana.isMyServiceRunning(MainService.class)){
					mNana.stopNana();
				} else {
					// Запуск Няни								********************************************
					String str = mMasterPhoneET.getText().toString();
					if(str.isEmpty() == false){
						List<String> list = new ArrayList<String>();
						list.add(str);
						str = mExtraPhoneET.getText().toString();
						if (str.isEmpty() == false){
							list.add(str);
						}
						//mNana.setNumbers(list);
						mNana.startNana();
					}
				}
				updateButtonState();

				Log.d(TAG, " " + mNana.isMyServiceRunning(MainService.class));
			}
        });
        
        updateButtonState();
        
        return v;
    }

    private boolean makeAllViewsEnable(boolean state) {
		// TODO Auto-generated method stub
    	mMasterPhoneB.setEnabled(state);
    	mMasterPhoneET.setEnabled(state);
    	mExtraPhoneB.setEnabled(state);
    	mExtraPhoneET.setEnabled(state);
		
		return true;
	}
   
    @Override
    public void onPause(){
    	//getActivity().stopService(new Intent(getActivity().getApplicationContext(), MainService.class));
    	super.onPause();
    }
}
