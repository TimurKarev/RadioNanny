package timur.karev.radionana.audio;

import java.io.File;

import timur.karev.radionana.controller.OutputFileNameManager;
import timur.karev.radionana.controller.informer.LoudInformer;
import timur.karev.radionana.controller.informer.RecordInformer;
import timur.karev.radionana.journal.Journal;
import timur.karev.radionana.journal.JournalSession;
import timur.karev.radionana.journal.JournalEvent;

public class RecordLoudListener extends BaseLoudListener implements LoudListener {

	private static final String  TAG = "RecordLoudListener";

	private LoudInformer mInformer;
	private boolean mState;
	private OutputFileNameManager fnm;
	private Journal mJournal;
	
	public RecordLoudListener(){
		super();
		mJournal = Journal.getJournal();
		mInformer = new RecordInformer();
		mState = false;
		fnm = new OutputFileNameManager();
	}
	
	@Override
	public int perfomAction() {
		// TODO Auto-generated method stub
		
		if (mRecorder == null){
			createListener();
		}
		
		boolean state;
		int volume = 0;
		try {
			volume = mRecorder.getMaxAmplitude();
			state = mInformer.inform(volume);
		} catch (Exception e){
			state = mState;
		}
		
		if (state != mState){
			mState = state;
			restartListener();
		}
		return volume;
	}

	@Override
	public void offListener() {
		// TODO Auto-generated method stub
		if(mState){
			mJournal.addEvent(JournalEvent.TYPE_START_RECORDING,null);
		} else {
			mJournal.addEvent(JournalEvent.TYPE_STOP_RECORDING,null);
		}
		super.offListener();
	}

	@Override 
	void createListener(){
		
		if (mState){
			mFileName = fnm.getNextFileName();
		} else {
			mFileName = OutputFileNameManager.getEmptyFileName();
		}
		
        File outfile = new File(mFileName);
        if (outfile.exists()){
        	outfile.delete();
        }
        super.createListener();
	}
}
