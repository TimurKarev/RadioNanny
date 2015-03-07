package timur.karev.radionana.batlistener;

import java.util.ArrayList;

import timur.karev.radionana.BatSender;
import timur.karev.radionana.R;
import timur.karev.radionana.SmsSender;
import timur.karev.radionana.controller.NanaController;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class NanaBatSender implements BatSender{
	private static final String TAG = "NanaBatSender";
  
	private NanaController mNana;
	private BroadcastReceiver mBatInfoReceiver;
	
	private Context mContext;
	ArrayList<SmsSender> mSmsSenders;
	ArrayList<String> mNumbers;
	
	public NanaBatSender(Context c){
		mContext = c;
		mSmsSenders = new ArrayList<SmsSender>();
		mNana= NanaController.getNana();
    	mNumbers = mNana.getLowListContacts();
    	
 		mBatInfoReceiver = new BroadcastReceiver() {

	        @Override
	          //When Event is published, onReceive method is called
	        public void onReceive(Context c, Intent intent) {
	        	int level = intent.getIntExtra("level", 0);
	        	
	        	if (level < mNana.getSetBatLevel()){
	        		
	        		for(int i=0;i<mNumbers.size();i++){

	        			mSmsSenders.add(new SmsSender(mContext));
	        			mSmsSenders.get(i).send(mNumbers.get(i), mContext.getResources().getString(R.string.sms_battery_low) +	" - " + level);
	        		}
	        		try{
	        			mContext.unregisterReceiver(mBatInfoReceiver);
	        		} catch (Exception e) {}
	        	}
     		
//	        		smsManager.sendTextMessage("89216308858", null, "I love you without a compromise!", null, null);
	        }	
		};
	}

	
	public void onBatSender(){
		mContext.registerReceiver(mBatInfoReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
	}

	public void offBatSender(){
		try{
			mContext.unregisterReceiver(mBatInfoReceiver);
		} catch (Exception e){}
		for(int i=0;i<mSmsSenders.size();i++){
			mSmsSenders.get(i).stopSending();
		}
	}
	
}
