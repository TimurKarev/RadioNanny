package timur.karev.radionana.journal;

import java.util.Calendar;

import timur.karev.radionana.R;
import timur.karev.radionana.controller.NanaController;
import android.util.Log;

public class JournalEvent {
	private static final String TAG = "JournalEvent";
	
	public static final int TYPE_START_RECORDING = 0;
	public static final int TYPE_STOP_RECORDING = 1;
	public static final int TYPE_START_NANA = 2;
	public static final int TYPE_STOP_NANA = 3;

	private int mType;
	
	private String mName;
	private String mDate;
	private String mTime;
	private String mDescription;
	
	private String mFileName;
	
	public JournalEvent(int type){
		this(type,null);
	}
	
	public JournalEvent(int type, String filename){
		mFileName = filename;
		mType = type;
		Calendar cal = Calendar.getInstance();
		mDate = String.format("%d.%02d", cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
		Log.d(TAG,mDate);
		mTime = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
		NanaController Nana = NanaController.getNana();
		
		switch(mType){
		case JournalEvent.TYPE_START_RECORDING:
			mName = Nana.getAppContext().getResources().getString(R.string.journal_name_start_recording);
			mDescription = Nana.getAppContext().getResources().getString(R.string.journal_descr_start_recording);
			break;
		
		case JournalEvent.TYPE_STOP_RECORDING:
			mName = Nana.getAppContext().getResources().getString(R.string.journal_name_stop_recording);
			mDescription = Nana.getAppContext().getResources().getString(R.string.journal_descr_stop_recording);
			break;
		case JournalEvent.TYPE_START_NANA:
			mName = Nana.getAppContext().getResources().getString(R.string.journal_name_start_nana);
			mDescription = Nana.getAppContext().getResources().getString(R.string.journal_descr_start_nana);
			break;
		
		case JournalEvent.TYPE_STOP_NANA:
			mName = Nana.getAppContext().getResources().getString(R.string.journal_name_stop_nana);
			mDescription = Nana.getAppContext().getResources().getString(R.string.journal_descr_stop_nana);
			break;
	}

	}

	public int getType() {
		return mType;
	}

	public String getName() {
		return mName;
	}

	public String getDate() {
		return mDate;
	}

	public String getTime() {
		return mTime;
	}

	public String getDescription() {
		return mDescription;
	}

	public String getFileName() {
		return mFileName;
	}

}
