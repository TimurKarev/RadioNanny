package timur.karev.radionana.journal;

import java.util.ArrayList;

import timur.karev.radionana.observers.FragmentObserver;
import timur.karev.radionana.observers.JournalSubject;

public class JournalSession {
	
	private String mDate;
	private String mStartTime;
	private String mStopTime;
	
	private ArrayList<JournalEvent> mEvents;
	
	public ArrayList<JournalEvent> getEvents() {
		return mEvents;
	}

	public JournalSession(){
		mEvents = new ArrayList<JournalEvent>();
	}
	
	public void addEvent(int type){
	   addEvent(type, null);
	}

	public void addEvent(int type, String file){
		JournalEvent event = new JournalEvent(type,file);

		try{
			mEvents.add(event);
		} catch (Exception e){}
	}

	public void closeSession() {
		// TODO Auto-generated method stub
		if (mEvents.size() > 1){
			mDate = mEvents.get(0).getDate();
			mStartTime = mEvents.get(0).getTime();
			mStopTime = mEvents.get(mEvents.size()-1).getTime();
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mDate + " c "+mStartTime+" по "+mStopTime;
	}
}
