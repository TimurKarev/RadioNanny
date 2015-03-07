package timur.karev.radionana.fragments;

/*
 * Window shows user when Start Timer is working
 * */

import timur.karev.radionana.MainService;
import timur.karev.radionana.R;
import timur.karev.radionana.controller.NanaController;
import timur.karev.radionana.controller.Subject;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartingFragment extends Fragment implements Observer{
	private static final String TAG = "StartingFragment";
	
	private static final int VOLUME_TIMER_MESSAGE = 0;
	
	private NanaController mNana;

	private Subject mSubject;
	
	private ProgressBar mVolumeBar;
	
	private CheckBox mJournCheck;
	private CheckBox mLoudCheck;
	private CheckBox mRecallCheck;
	private CheckBox mLowCheck;
	private CheckBox mMisCheck;

	private TextView mJournText;
	private TextView mLoudText;
	private TextView mLoudVolumeText;
	private TextView mRecallText;
	private TextView mLowText;
	private TextView mMisText;
	private TextView mStatusText;
	
	private ImageView mStartButton;
	
	private TextView mTimerText;
	
	private Handler mHandler;
	
	public void onCreate(Bundle instance){
		super.onCreate(instance);
		mNana = NanaController.getNana(getActivity().getApplicationContext());

		mSubject = (Subject)mNana;
		mSubject.registerObserver(this);
		
		mHandler = new  Handler(){
			public void handleMessage(Message msg){
				try{
					mVolumeBar.setProgress(msg.arg1);
					mTimerText.setText(NanaController.fromSecToTimer(msg.arg2));
					if (msg.arg2 == 0){
		           		FragmentTransaction transaction = getFragmentManager().beginTransaction();
	            		WorkingFragment fragment = new  WorkingFragment();//.newInstance(getResources().getInteger(R.integer.PICK_MASTER_CONTACT));
	            		transaction.replace(R.id.container, fragment);
	            		transaction.addToBackStack(null);
	            		transaction.commit();
//	            		mSubject.removeObserver();
					}
					updateView();
				} catch (Exception e){}
			}
		};
	}
	
	public void onResume(){
		super.onResume();
		updateView();
	}
	
	private void updateView(){
		mJournCheck.setChecked(mNana.getIsJournSetting());
		mLoudCheck.setChecked(mNana.getIsLoudSetting());
		mRecallCheck.setChecked(mNana.getIsRecallSetting());
		mLowCheck.setChecked(mNana.getIslowSetting());
		mMisCheck.setChecked(mNana.getIsMisSetting());
		mStatusText.setText(R.string.txt_start_pause);
		

		if (mNana.isMyServiceRunning() == true){
			if (mNana.isStarting()){
				mStatusText.setText(R.string.txt_nana_starting);
					makeAllButtonsEnable(false);
			}else {
					mStatusText.setText(R.string.txt_nana_working);
					makeAllButtonsEnable(false);
			} 
		} else {
	        mTimerText.setText(NanaController.fromSecToTimer(mNana.getSetPause()));
			makeAllButtonsEnable(true);
		}
	}

	
    private void makeAllButtonsEnable(boolean b) {
		// TODO Auto-generated method stub
		mJournCheck.setEnabled(b);
		mLoudCheck.setEnabled(b);
		mRecallCheck.setEnabled(b);
		mLowCheck.setEnabled(b);
		mMisCheck.setEnabled(b);
		mJournText.setClickable(b);
		mLoudText.setClickable(b);
		mRecallText.setClickable(b);
		mLowText.setClickable(b);
		mMisText.setClickable(b);
		mStatusText.setClickable(b);
		mVolumeBar.setClickable(b);
		mTimerText.setClickable(b);
		mLoudVolumeText.setClickable(b);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.starting_fragment, container, false);
 
        mVolumeBar = (ProgressBar)v.findViewById(R.id.progress_bar);
        mLoudVolumeText = (TextView)v.findViewById(R.id.staring_fragment_textView_volume);
        
        mJournCheck = (CheckBox)v.findViewById(R.id.starting_fragment_check_box0);
    	mJournText = (TextView)v.findViewById(R.id.starting_fragment_textView0);

        mLoudCheck = (CheckBox)v.findViewById(R.id.starting_fragment_check_box1);
    	mLoudText = (TextView)v.findViewById(R.id.starting_fragment_textView1);

        mRecallCheck = (CheckBox)v.findViewById(R.id.starting_fragment_check_box2);
    	mRecallText = (TextView)v.findViewById(R.id.starting_fragment_textView2);

        mLowCheck = (CheckBox)v.findViewById(R.id.starting_fragment_check_box3);
    	mLowText = (TextView)v.findViewById(R.id.starting_fragment_textView3);

    	mMisText = (TextView)v.findViewById(R.id.starting_fragment_textView4);
    	mMisCheck = (CheckBox)v.findViewById(R.id.starting_fragment_check_box4);

        mStatusText = (TextView)v.findViewById(R.id.starting_textView_pause);
        mTimerText = (TextView)v.findViewById(R.id.starting_textView_timer);

    	mStartButton = (ImageView)v.findViewById(R.id.starting_fragment_imageView1);
        
    	
        mVolumeBar.setMax(getResources().getColor(R.integer.MAX_VOLUME));
        
        mStartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG,"mNana.isMyServiceRunning()" + mNana.isMyServiceRunning());
				if (mNana.isMyServiceRunning() == false ){
					mNana.startNana();
				} else {
					mVolumeBar.setProgress(0);
					mNana.stopNana();
				}
				updateView();
			}
		});
        
        mJournCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	mNana.setIsJournSetting(mJournCheck.isChecked());
            }
        });

        mLoudCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if (mNana.isMainPhoneEmpty()){
            		mLoudCheck.setChecked(false);
            		FragmentTransaction transaction = getFragmentManager().beginTransaction();
            		SettingsListFragment fragment = SettingsListFragment.newInstance(getResources().getInteger(R.integer.PICK_MASTER_CONTACT));
            		transaction.replace(R.id.container, fragment);
            		transaction.addToBackStack(null);
            		transaction.commit();
            	}
            	mNana.setIsLoudSetting(mLoudCheck.isChecked());
            }
        });

        mRecallCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          	
            	if (mNana.isExtraListEmpty()){
            		mRecallCheck.setChecked(false);
            		FragmentTransaction transaction = getFragmentManager().beginTransaction();
            		SettingsListFragment fragment = SettingsListFragment.newInstance(getResources().getInteger(R.integer.PICK_EXTRA_CONTACT));
            		transaction.replace(R.id.container, fragment);
            		transaction.addToBackStack(null);
            		transaction.commit();
            		if (mNana.isExtraListEmpty() == false){
            			mRecallCheck.setChecked(true);
            			Log.d(TAG, "You are right!");
            		}
            		Log.d(TAG,"Afrer commit");
            	}
            	mNana.setIsRecallSetting(mRecallCheck.isChecked());  
            }
        });

        mLowCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            	if (mNana.isLowListEmpty()){
            		mLowCheck.setChecked(false);
            		FragmentTransaction transaction = getFragmentManager().beginTransaction();
            		SettingsListFragment fragment = SettingsListFragment.newInstance(getResources().getInteger(R.integer.PICK_SMS_LOW_CONTACT));
            		transaction.replace(R.id.container, fragment);
            		transaction.addToBackStack(null);
            		transaction.commit();
            	}
            	mNana.setIsLowSetting(mLowCheck.isChecked());
            }
        });
        mMisCheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

            	if (mNana.isMisListEmpty()){
            		mMisCheck.setChecked(false);
            		FragmentTransaction transaction = getFragmentManager().beginTransaction();
            		SettingsListFragment fragment = SettingsListFragment.newInstance(getResources().getInteger(R.integer.PICK_SMS_MIS_CONTACT));
            		transaction.replace(R.id.container, fragment);
            		transaction.addToBackStack(null);
            		transaction.commit();
            	}
            	mNana.setIsMissSetting(mMisCheck.isChecked());				
			}
		});
      
        updateView();
        
    return v;
    }

    @Override
    public void onPause(){
    	//getActivity().stopService(new Intent(getActivity().getApplicationContext(), MainService.class));
    	//mNana.stopNana();
    	//mSubject.removeObserver(this);
    	super.onPause();
    }

	@Override
	public void update(int volume, long timer) {
		// TODO Auto-generated method stub
		Message message = Message.obtain(mHandler, VOLUME_TIMER_MESSAGE, volume, (int)timer);
		mHandler.sendMessage(message);
//		Log.d(TAG, "Volume = " + volume);
	}
}
