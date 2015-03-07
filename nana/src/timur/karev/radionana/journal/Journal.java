package timur.karev.radionana.journal;

import java.util.ArrayList;

import timur.karev.radionana.observers.FragmentObserver;
import timur.karev.radionana.observers.JournalSubject;

public class Journal implements JournalSubject{

	private FragmentObserver mObserver;
	private static Journal sJournal;
	private ArrayList<JournalSession> mSessions;
	
	private Journal(){
		mSessions = new ArrayList<JournalSession>();
	}

	public static Journal getJournal(){
		if (sJournal == null){
			sJournal = new Journal();
		}
		return sJournal; 
	}

	public ArrayList<JournalSession> getSessions() {
		return mSessions;
	}
	
	public void startSession(){
		JournalSession session = new JournalSession();
		mSessions.add(session);
		addEvent(JournalEvent.TYPE_START_NANA,null);
	}
	
	public boolean stopSession(){
		addEvent(JournalEvent.TYPE_STOP_NANA, null);
		JournalSession s = null;
		try {
			s = mSessions.get(mSessions.size()-1);
		} catch (Exception e){
			return false;
		}
		s.closeSession();
		return true;
	}

	public void addEvent(int type, String file){
		try {
			mSessions.get(mSessions.size()-1).addEvent(type, file);
			notifyObservers(type);
		} catch (Exception e){}
	}

	public ArrayList<JournalEvent> getCurrentEvents() {
		// TODO Auto-generated method stub
		ArrayList<JournalEvent> s = null;
		try {
		s = mSessions.get(mSessions.size()-1).getEvents();
		} catch (Exception e){}

		return s;
	}
	@Override
	public void registerObserver(FragmentObserver o) {
		// TODO Auto-generated method stub
		mObserver = o;
	}

	@Override
	public void removeObserver() {
		// TODO Auto-generated method stub
		mObserver = null;
	}

	@Override
	public void notifyObservers(int type) {
		// TODO Auto-generated method stub
		mObserver.update(type);
	}

}
