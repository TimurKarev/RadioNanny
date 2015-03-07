package timur.karev.radionana.fragments;

import java.util.ArrayList;

import timur.karev.radionana.R;
import timur.karev.radionana.controller.NanaController;
import timur.karev.radionana.journal.Journal;
import timur.karev.radionana.journal.JournalEvent;
import timur.karev.radionana.journal.JournalSession;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
	private static final String TAG = "MainMenuFragment";
	
	private Button mBeginButton;
	private Button mSettingsButton;
	private Button mJournalButton;
	
	private AlertDialog mMainPhoneDialog;
		
	private NanaController mNana;
	
	@Override
	public void onCreate(Bundle saved){
		super.onCreate(saved);
		
		mNana = NanaController.getNana(getActivity().getApplicationContext());
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        
        mBeginButton = (Button)v.findViewById(R.id.button1);
        mSettingsButton = (Button)v.findViewById(R.id.button2);
        mJournalButton = (Button)v.findViewById(R.id.fragment_menu_button_Journal);
        
        mBeginButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				onClickStartButton(v);
			}
		});
        
        mSettingsButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction = getFragmentManager().beginTransaction();

				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				transaction.replace(R.id.container, new SettingsFragment());
				transaction.addToBackStack(null);

				// Commit the transaction
				transaction.commit();
			}
		});

        mJournalButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logAllJournal();
			}
		});
        return v;
    }

/*  Print into log all journal information
     */
    private void logAllJournal() {
		// TODO Auto-generated method stub
    	Journal journ = Journal.getJournal();
    	ArrayList<JournalSession> sessions = journ.getSessions();
    	Log.d(TAG,"Journal  - " +journ+ " - "+ sessions);
    	for(JournalSession s : sessions){
    		ArrayList<JournalEvent> events = s.getEvents();
    		Log.d(TAG,"Session - " + s.toString());
    		for(JournalEvent e : events){
    			Log.d(TAG, "Event = "+e.getName() + " " + e.getTime());
    		}
    	}
	}
    
    private void onClickStartButton (View v){

		// TODO Auto-generated method stub
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.container, new StartingFragment());
		transaction.addToBackStack(null);
		
		// Commit the transaction
		transaction.commit();
	}
/*
   Show dialog box, asking to enter telephone.
 */
	private void showMainSettingsAlert() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.text_need_main_phone)
				.setMessage("��������� ����!")
				.setCancelable(false)
				.setNegativeButton("��, ��� �� �����",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
