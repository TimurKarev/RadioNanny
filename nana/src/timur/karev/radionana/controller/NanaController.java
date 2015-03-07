package timur.karev.radionana.controller;

import java.util.ArrayList;
import java.util.List;

import timur.karev.radionana.MainService;
import timur.karev.radionana.R;
import timur.karev.radionana.controller.informer.EmptyInformer;
import timur.karev.radionana.controller.informer.LoudInformer;
import timur.karev.radionana.controller.informer.RecallInformer;
import timur.karev.radionana.fragments.Observer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

public class NanaController implements Subject{
	
	private static final String  TAG = "NanaController";

	private static NanaController sNana;
	private List mObservers;
	private int mCurrentVolume;
	
	private Intent  mServiceIntent;
	private Context mAppContext;
	private NanaSettings mSettings;
	
	private boolean mIsStarting;
	
//	private int mCurrentVolume;
	
	private long mTimer;

	public long getTimer() {
		return mTimer;
	}

	private NanaController(Context c){
		mAppContext = c;
		mServiceIntent = new Intent(mAppContext.getApplicationContext(), MainService.class);
		mSettings = new NanaSettings();
		mIsStarting = false;
		mTimer = mSettings.getStartPause();
		mObservers = new ArrayList();
	}
	
/*	public String setNextOutputFileName(){
		mOutputFilenameCount ++;
		mOutputMediaFileName = mOutputMediaFileName.substring(3);
		mOutputMediaFileName = mOutputFilenameCount + mOutputMediaFileName;
		return mOutputMediaFileName;
	}*/
	

	public boolean isStarting() {
		return mIsStarting;
	}
	


	private void setStarting(boolean isStarting) {
		mIsStarting = isStarting;
	}

	public static NanaController getNana(Context c){
		if (sNana == null) {
			sNana = new NanaController(c);
		}
		return sNana;
	}
	
	public static NanaController getNana(){
		if (sNana == null){
			return null;
		}
		return sNana;
	}
	
	public boolean startNana(){
		
		if (isMyServiceRunning() == false){
			if(mAppContext.startService(mServiceIntent) == null){
				return false;
			} else { 
				ressetTimer();
				setStarting(true);
			}
		}
		return true;
	}
	
	public boolean stopNana(){
		mAppContext.stopService(mServiceIntent);
		ressetTimer();
		
		if (isMyServiceRunning(MainService.class)){
			return false;
		}
		
		return true;
	}
	
	public void ressetTimer(){
		mTimer = mSettings.mStartPause;
	}
	
	public boolean isMyServiceRunning(Class<?> serviceClass) {
	    ActivityManager manager = (ActivityManager) mAppContext.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceClass.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	public boolean isMyServiceRunning() {
		return isMyServiceRunning(MainService.class);
	}
	
	public boolean isMainPhoneEmpty(){

		if (mSettings.mMainPhone.get(0).getNumber().equals("Empty")){
			return true;
		}
		return false;
	}
	
	public void setMainContact(String name, String number){
		mSettings.setMainContact(name, number);
	}
	
	public NanaContact getMainContact(){
		return mSettings.getMainContact();
	}

	public void changeTimer(int volume){
		mCurrentVolume = volume;
        Log.d(TAG,"Tiner = " + mTimer + "mIsStarting"+ mIsStarting);
		if (mIsStarting == true){
			mTimer --;
			if(mTimer == 0){
				//mTimer = mSettings.getStartPause();
				//mTimer = 1;
				mIsStarting = false;
			}
		} else {
				mTimer ++;
		}
		notifyObservers();
	}
	
	/*
	 * Returns String in hh:mm::SS from second value  if sec = 90; timer will be 00:01:30
	 */
	public static String fromSecToTimer(long sec){
		String timer = " ";
		long h = sec/3600; // Seconds in hour
		long m = (sec%3600)/60;
		long s = (sec%3600)%60;
		
		if (h < 10) {
			timer += ("0"+h+":");
		} else
			timer += (h + ":");
		
		if (m < 10) {
			timer +=("0"+m+":");
		} else {
			timer +=(m+":");
		}
		
		if (s < 10) {
			timer +="0"+s;
		} else {
			timer += s; 
		}
		
//		Log.d(TAG, timer);
		
		return timer;
	}

	public String getStringTimer(){
		return fromSecToTimer(mTimer);
	}

	public void onActivityResult(int reqCode, int resultCode, Intent data) {

        String contactName = null;
        String contactNumber = null;
        String contactID= null;
		
		if (resultCode == Activity.RESULT_OK) {
            Uri uriContact = data.getData();

             // querying contact data store
            Cursor cursor = mAppContext.getContentResolver().query(uriContact, null, null, null, null);
 
            if (cursor.moveToFirst()) {
 
            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.
 
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            cursor.close();
           }
           
            // getting contacts ID
            Cursor cursorID = mAppContext.getContentResolver().query(uriContact,
                    new String[]{ContactsContract.Contacts._ID},
                    null, null, null);
     
            if (cursorID.moveToFirst()) {
     
                contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
            }
     
            cursorID.close();
 		}
        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = mAppContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
 
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
 
                new String[]{contactID},
                null);
 
        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
 
        cursorPhone.close();

        contactNumber = contactNumber.replace("-", "");
        contactNumber = contactNumber.replace(" ", "");
        Log.d(TAG, "Contact Phone Number: " + contactNumber);
        Log.d(TAG, "Contact Name: " + contactName);

        
        if (reqCode == mAppContext.getResources().getInteger(R.integer.PICK_MASTER_CONTACT)){
        	setMainContact(contactName, contactNumber);
           	 Log.d(TAG,"PICK_MASTER" + contactName + contactNumber);
        }
        if (reqCode == mAppContext.getResources().getInteger(R.integer.PICK_EXTRA_CONTACT)){
        	addExtraContact(contactName, contactNumber);
           	 Log.d(TAG,"PICK_MASTER" + contactName + contactNumber);
        }
        if (reqCode == mAppContext.getResources().getInteger(R.integer.PICK_SMS_LOW_CONTACT)){
        	addLowContact(contactName, contactNumber);
           	 Log.d(TAG,"PICK_MASTER" + contactName + contactNumber);
        }
        if (reqCode == mAppContext.getResources().getInteger(R.integer.PICK_SMS_MIS_CONTACT)){
        	addMisContact(contactName, contactNumber);
           	 Log.d(TAG,"PICK_MASTER" + contactName + contactNumber);
        }

	}

	private void addExtraContact(String contactName, String contactNumber) {
		// TODO Auto-generated method stub
		NanaContact Contact = new NanaContact(contactName, contactNumber); 
		if (mSettings.mRecallNumList.get(0).getNumber().equals(NanaSettings.EMPTY_NUMBER)){
				mSettings.mRecallNumList.clear();
		}
		boolean isNew = true;
		for (NanaContact contact : mSettings.mRecallNumList){
			if (contact.equal(Contact)){
				isNew = false;
			}
		}
		if (isNew){
			mSettings.mRecallNumList.add(Contact);
		}
	}

	private void addLowContact(String contactName, String contactNumber) {
		NanaContact Contact = new NanaContact(contactName, contactNumber); 
		if (mSettings.mSMSLowNumList.get(0).getNumber().equals(NanaSettings.EMPTY_NUMBER)){
				mSettings.mSMSLowNumList.clear();
		}
		boolean isNew = true;
		for (NanaContact contact : mSettings.mSMSLowNumList){
			if (contact.equal(Contact)){
				isNew = false;
			}
		}
		if (isNew){
			mSettings.mSMSLowNumList.add(Contact);
		}
	}

	private void addMisContact(String contactName, String contactNumber) {
		NanaContact Contact = new NanaContact(contactName, contactNumber); 
		if (mSettings.mSMSMisNumList.get(0).getNumber().equals(NanaSettings.EMPTY_NUMBER)){
				mSettings.mSMSMisNumList.clear();
		}
		boolean isNew = true;
		for (NanaContact contact : mSettings.mSMSMisNumList){
			if (contact.equal(Contact)){
				isNew = false;
			}
		}
		if (isNew){
			mSettings.mSMSMisNumList.add(Contact);
		}
	}

	public boolean getIsJournSetting() {
		// TODO Auto-generated method stub
		return mSettings.isJourn();
	}

	public boolean getIsLoudSetting() {
		// TODO Auto-generated method stub
		return mSettings.isLoud();
	}

	public boolean getIsRecallSetting() {
		// TODO Auto-generated method stub
		return mSettings.isRecall();
	}

	public boolean getIslowSetting() {
		// TODO Auto-generated method stub
		return mSettings.isLow();
	}

	public boolean getIsMisSetting() {
		// TODO Auto-generated method stub
		return mSettings.isMiss();
	}

	public void setIsRecallSetting(boolean is) {
		// TODO Auto-generated method stub
		mSettings.setRecall(is);
	}
	public void setIsLowSetting(boolean is) {
		// TODO Auto-generated method stub
		mSettings.setLow(is);
	}
	public void setIsLoudSetting(boolean is) {
		// TODO Auto-generated method stub
		mSettings.setLoud(is);
	}
	public void setIsJournSetting(boolean is) {
		// TODO Auto-generated method stub
		mSettings.setJourn(is);
	}
	public void setIsMissSetting(boolean is) {
		// TODO Auto-generated method stub
		mSettings.setMiss(is);
	}

	public ArrayList<NanaContact> getContactList(int code) {
		// TODO Auto-generated method stub
		if (code == mAppContext.getResources().getInteger(R.integer.PICK_MASTER_CONTACT)){
			return mSettings.mMainPhone;
		}
		if (code == mAppContext.getResources().getInteger(R.integer.PICK_EXTRA_CONTACT)){
			return mSettings.mRecallNumList;
		}
		if (code == mAppContext.getResources().getInteger(R.integer.PICK_SMS_LOW_CONTACT)){
			return mSettings.mSMSLowNumList;
		}
		if (code == mAppContext.getResources().getInteger(R.integer.PICK_SMS_MIS_CONTACT)){
			return mSettings.mSMSMisNumList;
		}

		return null;
	}

	public long getSetPause() {
		// TODO Auto-generated method stub
		return mSettings.mStartPause;
	}

	public boolean isExtraListEmpty() {
		// TODO Auto-generated method stub
		if (mSettings.mRecallNumList.get(0).getNumber().equals(NanaSettings.EMPTY_NUMBER)){
			return true;
		}
		return false;
	}
	public boolean isLowListEmpty() {
		// TODO Auto-generated method stub
		if (mSettings.mSMSLowNumList.get(0).getNumber().equals(NanaSettings.EMPTY_NUMBER)){
			return true;
		}
		return false;
	}
	public boolean isMisListEmpty() {
		// TODO Auto-generated method stub
		if (mSettings.mSMSMisNumList.get(0).getNumber().equals(NanaSettings.EMPTY_NUMBER)){
			return true;
		}
		return false;
	}

	public void setStartPause(int i) {
		// TODO Auto-generated method stub
		mSettings.mStartPause = i;
	}

	public int getSetSensity() {
		// TODO Auto-generated method stub
		return mSettings.mSensity;
	}

	public void setSetSensity(int sensity) {
		// TODO Auto-generated method stub
		mSettings.mSensity = sensity;
	}

	public ArrayList<String> getExtlaListContacts() {
		// TODO Auto-generated method stub
		ArrayList<NanaContact> contactList = mSettings.mRecallNumList;
		ArrayList<String> str = new ArrayList<String>();
		for(int i=0;i<contactList.size();i++){
			str.add(contactList.get(i).getNumber());
		}
	return str;
	}

	public ArrayList<String> getLowListContacts() {
		// TODO Auto-generated method stub
		ArrayList<NanaContact> contactList = mSettings.mSMSLowNumList;
		ArrayList<String> str = new ArrayList<String>();
		for(int i=0;i<contactList.size();i++){
			str.add(contactList.get(i).getNumber());
		}
	return str;

	}
	
	public int getSetBatLevel(){
		return mSettings.mLowBatValue;
	}

	public ArrayList<String> getSMSMisListNumber() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ArrayList<NanaContact> contactList = mSettings.mSMSMisNumList;
		ArrayList<String> str = new ArrayList<String>();
		for(int i=0;i<contactList.size();i++){
			str.add(contactList.get(i).getNumber());
		}

		return str;
	}

	public Context getAppContext() {
		// TODO Auto-generated method stub
		return mAppContext;
	}

	public void removeContact(int parentCode, int position) {
		// TODO Auto-generated method stub
		if (parentCode == mAppContext.getResources().getInteger(R.integer.PICK_MASTER_CONTACT)){
			mSettings.mMainPhone.clear();
			mSettings.mMainPhone.add(new NanaContact("Empty"));
		}
		if (parentCode == mAppContext.getResources().getInteger(R.integer.PICK_EXTRA_CONTACT)){
			mSettings.mRecallNumList.remove(position);
			if (mSettings.mRecallNumList.size() <= 0){
				mSettings.mRecallNumList.add(new NanaContact("Empty"));
			}
		}
		if (parentCode == mAppContext.getResources().getInteger(R.integer.PICK_SMS_LOW_CONTACT)){
			mSettings.mSMSLowNumList.remove(position);
			if (mSettings.mSMSLowNumList.size() <= 0){
				mSettings.mSMSLowNumList.add(new NanaContact("Empty"));
			}
		}
		if (parentCode == mAppContext.getResources().getInteger(R.integer.PICK_SMS_MIS_CONTACT)){
			mSettings.mSMSMisNumList.remove(position);
			if (mSettings.mSMSMisNumList.size() <= 0){
				mSettings.mSMSMisNumList.add(new NanaContact("Empty"));
			}
		}
		
	}
	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		mObservers.add(o);
	}

	@Override
	public void removeObserver() {

		mObservers.remove(0);
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for (int i=0; i < mObservers.size(); i++){
			Observer obs = (Observer)mObservers.get(i);
			obs.update(mCurrentVolume, mTimer);
		}
	}

	public boolean isIncomingNumberInRecallList(String number) {
		// TODO Auto-generated method stub
		String listNumber;
		int a,b,l;

		for (NanaContact c : mSettings.mRecallNumList){
			listNumber = c.getNumber();
			listNumber = listNumber.replace("+", "");
			number = number.replace("+", "");
			number = number.replace(" ", "");
			number = number.replace("-", "");
			
			a = number.length();
			b = listNumber.length();
			Log.d(TAG, "inc num "+ number + "ListNumber = " + listNumber);
			
			try{
				if (a > b){
					l = a-b;
					if (number.substring(l).equals(listNumber)){
						return true;
					}
						
				}
				if (a < b){
					l = b-a;
					if(listNumber.substring(l).equals(number)){
						return true;
					}
				}
				if (a == b){
					if(number.equals(listNumber)){
						return true;
					};
				}
			} catch (Exception e){}
		}
		Log.d(TAG, "isString FALSE");
		return false;
	}

}