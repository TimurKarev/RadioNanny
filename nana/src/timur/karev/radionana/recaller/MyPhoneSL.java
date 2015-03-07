package timur.karev.radionana.recaller;

import java.util.ArrayList;
import java.util.List;

import timur.karev.radionana.controller.NanaController;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;



	public class MyPhoneSL extends PhoneStateListener {
		
		private static final String TAG = "MyPhoneSL";
		
		private boolean mNeedRecall = false;
		private Context mContext;
		private ArrayList<String> mNumbers;
		private String mRecallNumber;
		
		private NanaController mNana;

		
		public  MyPhoneSL (Context c) {
			mNana = NanaController.getNana();
			mContext = c;
			mNumbers = mNana.getExtlaListContacts();
		}
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber){
			String inc = incomingNumber;

			Log.d(TAG, "inc lenght " + inc.length()+" Number 1 " + mNumbers.get(0));

			if (inc.length() > 0 ){
				inc = inc.substring(2);
				Log.d(TAG, "inc " + inc);
			}
			int  length = inc.length();
			
			switch(state){
			    case TelephonyManager.CALL_STATE_IDLE:
				    if (mNeedRecall){
				    	mNeedRecall = false;
			    		Intent intent = new Intent(Intent.ACTION_CALL);
			    		intent.setData(Uri.parse("tel:" + mRecallNumber));
			    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    		intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
			    		mContext.startActivity(intent);
				    }
			    break;
			    
			    case TelephonyManager.CALL_STATE_OFFHOOK:
			    break;
			    
			    case TelephonyManager.CALL_STATE_RINGING:
					for (int i=0; i < mNumbers.size(); i++){
						int l = mNumbers.get(i).length();
						if (l > 0){
							if (length > l){
								if (mNumbers.get(i).equals(inc.substring(length-l))){
									mRecallNumber = incomingNumber;
									mNeedRecall = true;
								}
							}
							if (length < l){
								if (inc.equals(mNumbers.get(i).substring(l-length))){
									mRecallNumber = incomingNumber;
									mNeedRecall = true;
								}
							}
							if (length == l){
								if (inc.equals(mNumbers.get(i))){
									mRecallNumber = incomingNumber;
									mNeedRecall = true;
								}
							}
						}
					}
			    break;
			    }
		  } 
	}