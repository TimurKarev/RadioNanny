package timur.karev.radionana.fragments;

import timur.karev.radionana.R;
import timur.karev.radionana.controller.NanaController;
import timur.karev.radionana.controller.NanaSettings;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class SensitySettingsFragment extends Fragment {
	private static final String TAG = "SensitySettingsFragment";
	
	NanaController mNana;
	
	private RadioButton mLowCheck;
	private RadioButton mMiddleCheck;
	private RadioButton mHiCheck;
	
	public void onCreate(Bundle instance){
		super.onCreate(instance);
		mNana = NanaController.getNana(getActivity().getApplicationContext());
	}
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sensity_settings_fragment, container, false);
        
        mLowCheck = (RadioButton)v.findViewById(R.id.sensity_settings_checkBox1);
        mMiddleCheck = (RadioButton)v.findViewById(R.id.sensity_settings_checkBox2);
        mHiCheck = (RadioButton)v.findViewById(R.id.sensity_settings_checkBox3);
        
        switch(mNana.getSetSensity()){
        	case NanaSettings.SENSITY_LOW:
        		mLowCheck.setChecked(true);
        		break;
        	case NanaSettings.SENSITY_MIDDLE:
        		mMiddleCheck.setChecked(true);
        		break;
        	case NanaSettings.SENSITY_HI:
        		mHiCheck.setChecked(true);
        		break;
        }
        
        mLowCheck.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
        	public void onCheckedChanged(CompoundButton button, boolean isChecked){
        		if (isChecked){
        		mMiddleCheck.setChecked(false);
        		mHiCheck.setChecked(false);
        		mNana.setSetSensity(NanaSettings.SENSITY_LOW);
        		Log.d(TAG,"LOW");
        		}
        	}
        });
        mMiddleCheck.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
        	public void onCheckedChanged(CompoundButton button, boolean isChecked){
        		if (isChecked){
        		mLowCheck.setChecked(false);
        		mHiCheck.setChecked(false);
        		mNana.setSetSensity(NanaSettings.SENSITY_MIDDLE);
        		Log.d(TAG,"MIDDLE");
        		}
        	}
        });
        mHiCheck.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
        	public void onCheckedChanged(CompoundButton button, boolean isChecked){
        		if (isChecked){
        		mMiddleCheck.setChecked(false);
        		mLowCheck.setChecked(false);
        		mNana.setSetSensity(NanaSettings.SENSITY_HI);
        		Log.d(TAG,"HI");
        		}
        	}
        });

        	
       
        
        return v;
    }

}
