package timur.karev.radionana;

import timur.karev.radionana.controller.NanaController;
import timur.karev.radionana.fragments.MenuFragment;
import timur.karev.radionana.fragments.SensitySettingsFragment;
import timur.karev.radionana.fragments.SettingsListFragment;
import timur.karev.radionana.fragments.StartPauseFragment;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	private NanaController mNana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNana = NanaController.getNana(getApplicationContext());
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MenuFragment())
                    .commit();
        }
    }
    
    public void imageClick(View v){
    	Log.d(TAG, "Привет Мария, Спасибо что ты меня сделала");
    }
    
    public void toSMSMisSettings(View v){
		// TODO Auto-generated method stub
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		SettingsListFragment fragment = SettingsListFragment.newInstance(getResources().getInteger(R.integer.PICK_SMS_MIS_CONTACT));
		transaction.replace(R.id.container, fragment);
		transaction.addToBackStack(null);
		
		// Commit the transaction
		transaction.commit();
    }
    public void toSMSLowSettings(View v){
		// TODO Auto-generated method stub
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		SettingsListFragment fragment = SettingsListFragment.newInstance(getResources().getInteger(R.integer.PICK_SMS_LOW_CONTACT));
		transaction.replace(R.id.container, fragment);
		transaction.addToBackStack(null);
		
		// Commit the transaction
		transaction.commit();
    }
    public void toJournalSettings(View v){
		/*// TODO Auto-generated method stub
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		SettingsListFragment fragment = SettingsListFragment.newInstance(getResources().getInteger(R.integer.PICK_SMS_MIS_CONTACT));
		transaction.replace(R.id.container, fragment);
		transaction.addToBackStack(null);
		
		mNana.setStarting(true);
		// Commit the transaction
		transaction.commit();
    */}
    public void toLoudSettings(View v){
		// TODO Auto-generated method stub
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		SettingsListFragment fragment = SettingsListFragment.newInstance(getResources().getInteger(R.integer.PICK_MASTER_CONTACT));
		transaction.replace(R.id.container, fragment);
		transaction.addToBackStack(null);
		
		// Commit the transaction
		transaction.commit();
    }
    public void toRecallSettings(View v){
		// TODO Auto-generated method stub
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		SettingsListFragment fragment = SettingsListFragment.newInstance(getResources().getInteger(R.integer.PICK_EXTRA_CONTACT));
		transaction.replace(R.id.container, fragment);
		transaction.addToBackStack(null);
		
		// Commit the transaction
		transaction.commit();
    }
    
    public void toStartPauseSettings(View v){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		StartPauseFragment fragment = new StartPauseFragment();
		transaction.replace(R.id.container, fragment);
		transaction.addToBackStack(null);
		
		// Commit the transaction
		transaction.commit();
    }

    public void toSensitySettings(View v){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		SensitySettingsFragment fragment = new SensitySettingsFragment();
		transaction.replace(R.id.container, fragment);
		transaction.addToBackStack(null);
		
		// Commit the transaction
		transaction.commit();
    }


}
