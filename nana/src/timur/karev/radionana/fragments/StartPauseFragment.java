package timur.karev.radionana.fragments;

import timur.karev.radionana.R;
import timur.karev.radionana.controller.NanaController;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;

public class StartPauseFragment extends Fragment {
	
	NanaController mNana;
	
	private NumberPicker mHourPicker;
	private NumberPicker mMinutePicker;
	private NumberPicker mSecondPicker;
	
	public void onCreate(Bundle instance){
		super.onCreate(instance);
		mNana = NanaController.getNana(getActivity().getApplicationContext());
	}
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.time_picker_fragment, container, false);
        
    	mHourPicker = (NumberPicker)v.findViewById(R.id.time_picker_1);
    	mMinutePicker = (NumberPicker)v.findViewById(R.id.time_picker2);
    	mSecondPicker = (NumberPicker)v.findViewById(R.id.time_picker3);

    	mHourPicker.setMaxValue(99);
    	mHourPicker.setMinValue(0);
    	mMinutePicker.setMaxValue(59);
    	mMinutePicker.setMinValue(0);
    	mSecondPicker.setMaxValue(59);
    	mSecondPicker.setMinValue(0);
    	
    	updateView();

        return v;
    }

	private void updateView() {
		// TODO Auto-generated method stub
		long l = mNana.getSetPause();
		int h = (int)l/3600;
		int m = (int)(l%3600)/60;
		int s = (int)(l%3600)%60;
		
		mHourPicker.setValue(h);
		mMinutePicker.setValue(m);
		mSecondPicker.setValue(s);
	}
	
	public void onPause(){
		mNana.setStartPause(mHourPicker.getValue()*3600+mMinutePicker.getValue()*60+mSecondPicker.getValue());
		super.onPause();
	}

}
