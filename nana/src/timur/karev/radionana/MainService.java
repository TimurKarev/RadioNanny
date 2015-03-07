package timur.karev.radionana;

import java.util.Timer;
import java.util.TimerTask;

import timur.karev.radionana.audio.LoudListener;
import timur.karev.radionana.audio.LoudListenerFactory;
import timur.karev.radionana.batlistener.EmptyBatSender;
import timur.karev.radionana.batlistener.NanaBatSender;
import timur.karev.radionana.controller.NanaController;
import timur.karev.radionana.incomingcall.IncomingCallListener;
import timur.karev.radionana.incomingcall.MisSenderFactory;
import timur.karev.radionana.journal.Journal;
import timur.karev.radionana.recaller.EmptyRecaller;
import timur.karev.radionana.recaller.Recaller;
import timur.karev.radionana.recaller.StandartRecaller;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainService extends Service {
	
	private Timer mMainTimer;
	private Journal mJournal;
	
	private Recaller mRecaller;
	private BatSender mBatSender;
	private IncomingCallListener mMisSender;
	
	private LoudListener mLoudListener;
	private LoudListener mRecallListener;

	private int mCurrentVolume;
	
	private PhoneStateListener mIncomingListener;
	private TelephonyManager tm;
	
	private NanaController mNana;

	private final String TAG = "MainService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override 
    public void onCreate() 
	{ 
        Log.d(TAG, "������ �������");
        mNana = NanaController.getNana();
        mJournal = Journal.getJournal();
        mMainTimer = new Timer();
//		mNumber = mNana.getMainContact().getNumber();
		mBatSender = getSetBatSender();
		mMisSender = MisSenderFactory.getSetMisSender();
		mRecaller = getSetRecaller();
		mLoudListener = LoudListenerFactory.getSetJournalRecorder();
		mRecallListener = LoudListenerFactory.getSetRecallRecorder();

		tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		mIncomingListener = new PhoneStateListener(){
			@Override
			public void onCallStateChanged(int state, String incomingNumber){
				switch(state){
			    
				case TelephonyManager.CALL_STATE_IDLE:
					mRecallListener = LoudListenerFactory.getSetRecallRecorder();
			    break;

			    case TelephonyManager.CALL_STATE_OFFHOOK:
			    break;
			    
			    case TelephonyManager.CALL_STATE_RINGING:
					mRecallListener = LoudListenerFactory.getEmptyRecallRecorder();
			    break;
			    }
			}
		};
    } 
	
	private BatSender getSetBatSender() {
		// TODO Auto-generated method stub
		if (mNana.getIslowSetting()){
			return new NanaBatSender(this);
		}
		return new EmptyBatSender(null);
	}

	@Override 
    public int onStartCommand(Intent intent, int flag, int startid)
	{
		mRecaller = getSetRecaller();
		mRecaller.onRecaller();
		
		mBatSender = getSetBatSender();
		mBatSender.onBatSender();
		
		mMisSender = MisSenderFactory.getSetMisSender();
		mMisSender.onCallListener();
		mLoudListener = LoudListenerFactory.getSetJournalRecorder();
		mRecallListener = LoudListenerFactory.getSetRecallRecorder();
		tm.listen(mIncomingListener, PhoneStateListener.LISTEN_CALL_STATE);
		mJournal.startSession();
		
	    Log.d(TAG, "������ ��������");
		mMainTimer.schedule(new TimerTask(){
		    @Override
		    public void run() {
		    	Log.d(TAG,"���� + ");
		    	int a = mLoudListener.perfomAction();
		    	int b = mRecallListener.perfomAction();
		    	if (a >= b){
		    		mCurrentVolume = a;
		    	} else {
		    		mCurrentVolume = b;
		    	}
//		    	mNana.setCurrentVolume(mCurrentVolume);
		    	mNana.changeTimer(mCurrentVolume);
		    }
		}, 0, 1000);

        return 0;
    }
	
    private Recaller getSetRecaller() {
		// TODO Auto-generated method stub
    	if (mNana.getIsRecallSetting()){
    		return new StandartRecaller(this);
    	}
		return new EmptyRecaller(null);
	}

	@Override 
    public void onDestroy() 
	{ 
        Log.d(TAG, "������ �����������");
		tm.listen(mIncomingListener, PhoneStateListener.LISTEN_NONE);
		mJournal.stopSession();
        mRecaller.offRecaller();
        mBatSender.offBatSender();
        mBatSender.offBatSender();
		mMisSender.offCallListener();
		mLoudListener.offListener();
		mRecallListener.offListener();
        mMainTimer.cancel();
        super.onDestroy();
    }
}