package timur.karev.radionana.incomingcall;

import java.util.ArrayList;

import timur.karev.radionana.SmsSender;
import timur.karev.radionana.controller.NanaController;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MisSmsSender implements IncomingCallListener{
	
	private static final String TAG = "MisSmsSender"; 

	private PhoneStateListener mIncomingListener;
	private TelephonyManager tm;
	
	private Context mContext;
	
	private boolean isIncome = false;
	private boolean isReceive = true;
	
	private NanaController mNana;
	
	public MisSmsSender (Context c){
		mNana = NanaController.getNana(c);
		mContext = c;
		tm = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
		mIncomingListener = new PhoneStateListener(){
			@Override
			public void onCallStateChanged(int state, String incomingNumber){
				switch(state){
			    case TelephonyManager.CALL_STATE_IDLE:
			    	if (isIncome == true && isReceive == false &&
			    	((mNana.isIncomingNumberInRecallList(incomingNumber) != true) && mNana.getIsRecallSetting()) || (mNana.getIsRecallSetting() != true)){
			    		ArrayList<String> str;
			    		str = mNana.getSMSMisListNumber();
			    		SmsSender sender = new SmsSender(mContext);
			    		for(int i=0;i<str.size();i++){
			    		//	sender.send(str.get(i), "Missed call" + incomingNumber);
			    			Log.d(TAG,"Missed call" + incomingNumber);
			    		}
			    	}
			    	Log.d(TAG,"CALL_STATE_IDLE: Call= "+isIncome+" Receive= "+isReceive);
			    	isIncome = false;
			    	isReceive = false;
			    break;
			    
			    case TelephonyManager.CALL_STATE_OFFHOOK:
			    	isReceive = true;
			    	Log.d(TAG,"CALL_STATE_OFFHOOK: Call= "+isIncome+" Receive= "+isReceive);
			    break;
			    
			    case TelephonyManager.CALL_STATE_RINGING:
			    	isIncome = true;
			    	isReceive = false;
			    	Log.d(TAG,"CALL_STATE_RINGING: Call= "+isIncome+" Receive= "+isReceive);
			    break;
			    }
			}
			    	
		};
		
	}
	
	
	@Override
	public void onCallListener() {
		tm.listen(mIncomingListener, PhoneStateListener.LISTEN_CALL_STATE);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void offCallListener() {
		// TODO Auto-generated method stub
		tm.listen(mIncomingListener, PhoneStateListener.LISTEN_NONE);
		
	}

}
