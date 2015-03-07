package timur.karev.radionana;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsSender {
	private static final String TAG = "SmsSender"; 
	
    private static final String SENT = "SMS_SENT";
    private static final String DELIVERED = "SMS_DELIVERED";
	
	private BroadcastReceiver mSendR;
	private BroadcastReceiver mDeliverR;
	
	private Context mContext;
	private String mNumber;
	private String mText;
	
	
	public SmsSender(Context c){
		mContext = c;
		
		mSendR = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                    	Log.d(TAG,"Sending OK");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                    	Log.d(TAG,"Sending ERROR");
                    	send();
                    	break;
                }
            }
        };
        
        mDeliverR = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        break;
                    case Activity.RESULT_CANCELED:
                    	send();
                    	Log.d(TAG,"Sending ERROR");
                    	break;
                }
            }
        };
	}
	
	private void send(){
		send(mNumber,mText);
	}

	public void send(String number, String text) {
		// TODO Auto-generated method stub
        PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0,
                new Intent(SENT), 0);

            PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0,
                new Intent(DELIVERED), 0);

            mNumber = number;
            mText = text;
	        Log.d(TAG, "Sending " + mNumber);

	        //--- When the SMS has been sent ---
	        mContext.registerReceiver(mSendR, new IntentFilter(SENT));
	        //--- When the SMS has been delivered. ---
	        mContext.registerReceiver(mDeliverR, new IntentFilter(DELIVERED));

	        SmsManager sms = SmsManager.getDefault();
	        sms.sendTextMessage(mNumber, null, mText, sentPI, deliveredPI);
	    }

	public void stopSending() {
		// TODO Auto-generated method stub
		try{
	        mContext.unregisterReceiver(mSendR);
	        mContext.unregisterReceiver(mDeliverR);
		} catch (Exception e){}
	}
}
	
