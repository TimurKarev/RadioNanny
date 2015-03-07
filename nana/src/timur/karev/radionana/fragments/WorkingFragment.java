package timur.karev.radionana.fragments;

import java.util.ArrayList;
import java.util.List;

import timur.karev.radionana.R;
import timur.karev.radionana.journal.Journal;
import timur.karev.radionana.journal.JournalEvent;
import timur.karev.radionana.journal.JournalSession;
import timur.karev.radionana.observers.FragmentObserver;
import timur.karev.radionana.observers.JournalSubject;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WorkingFragment extends Fragment implements FragmentObserver{
	private static final String TAG = "WorkingFragment";
	
	private EventAdapter mEventAdapter;
	private ListView mList;
	
	private Journal mJournal;
	private JournalSubject mSubject;
	private Handler mHandler;

	private ArrayList<JournalEvent> screenList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mJournal = Journal.getJournal();
		ArrayList<JournalEvent> journList = mJournal.getCurrentEvents();
		screenList = new ArrayList<JournalEvent>();
		
		updateList();
		mEventAdapter = new EventAdapter(screenList);
		mSubject = (JournalSubject)mJournal;
		mHandler = new  Handler(){
			public void handleMessage(Message msg){
				updateList();
			}
		};
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		mSubject.removeObserver();
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mSubject.registerObserver(this);
		super.onResume();
	}

	private void updateList(){
		try{
			List<JournalEvent> journList = mJournal.getCurrentEvents();
			screenList.clear();
			if (journList.size() > 0){
				for(int i = journList.size()-1; i>=0;i--){
					screenList.add(journList.get(i));
				}
			}
			for(JournalEvent j : screenList){
				Log.d(TAG,"journ = " + j.getTime());	
			}
			mEventAdapter.notifyDataSetChanged();
			
		} catch (Exception e){
			Log.d(TAG,"e= "+e+" mess"+e.getMessage());
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.working_fargment, container, false);
		
        mList = (ListView)v.findViewById(R.id.working_fragment_listView);
        mList.setAdapter(mEventAdapter);
 
		
		return v;
	}
	
    private class EventAdapter extends ArrayAdapter<JournalEvent> {
        public EventAdapter(ArrayList<JournalEvent> events) {
            super(getActivity(), android.R.layout.simple_list_item_1, events);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.journal_list, null);
            }

            // configure the view for this event
            JournalEvent event = getItem(position);

            TextView Name = (TextView)convertView.findViewById(R.id.journal_list_textView_Name);
            TextView Date = (TextView)convertView.findViewById(R.id.journal_list_textView_Date);
            TextView Time = (TextView)convertView.findViewById(R.id.journal_list_textView_Time);
            TextView Descr = (TextView)convertView.findViewById(R.id.journal_list_textView_Description);
            
            Name.setText(event.getName());
            Date.setText(event.getDate()+" ");
            Time.setText(" "+event.getTime());
            Descr.setText(event.getDescription());
            
            return convertView;
        }
    }

	@Override
	public void update(int type) {
		// TODO Auto-generated method stub
		//Message message = Messageobtain(mHandler, VOLUME_TIMER_MESSAGE, volume, (int)timer);
		mHandler.sendEmptyMessage(type);
		Log.d(TAG,"update");
	}
}
