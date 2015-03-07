package timur.karev.radionana.controller;

import java.util.ArrayList;

public class NanaSettings {
	
//	public static final int GET_MAIN_LIST = 0;

	public static final String EMPTY_NUMBER = "Empty";
	
	public static final int SENSITY_LOW = 0;
	public static final int SENSITY_MIDDLE = 1;
	public static final int SENSITY_HI = 2;
	
	ArrayList<NanaContact> mMainPhone;
	
	boolean isJourn;
	boolean isLoud;
	boolean isRecall;
	boolean isLow;
	boolean isMiss;

    long mStartPause;

	ArrayList<NanaContact> mRecallNumList;
	
	int mSensity;

	ArrayList<NanaContact> mSMSMisNumList;
	
	
	public void setMainPhone(String name, String number) {
		mMainPhone.get(0).setContact(name, number);
	}

	ArrayList<NanaContact> mSMSLowNumList;
	int mLowBatValue;
	
	public NanaSettings(){
		
		mRecallNumList = new ArrayList<NanaContact>();
		mSMSMisNumList = new ArrayList<NanaContact>();
		mSMSLowNumList = new ArrayList<NanaContact>();
		mMainPhone = new ArrayList<NanaContact>();

		getDefSettings();
		
	}
	
	private void getDefSettings() {
		// TODO Auto-generated method stub
		mMainPhone.add(new NanaContact("Empty"));
		mRecallNumList.add(new NanaContact("Empty"));
		mSMSMisNumList.add(new NanaContact("Empty"));
		mSMSLowNumList.add(new NanaContact("Empty"));

		mStartPause = 3;
		mSensity = SENSITY_MIDDLE;

		isJourn = false;
		isLoud = false;
		isRecall = false;
		isLow = false;
		isMiss = false;
		
		mLowBatValue = 10;
	}
	
	public boolean isJourn() {
		return isJourn;
	}

	public void setJourn(boolean isJourn) {
		this.isJourn = isJourn;
	}

	public boolean isLoud() {
		return isLoud;
	}

	public void setLoud(boolean isLoud) {
		this.isLoud = isLoud;
	}

	public boolean isRecall() {
		return isRecall;
	}

	public void setRecall(boolean isRecall) {
		this.isRecall = isRecall;
	}

	public boolean isLow() {
		return isLow;
	}

	public void setLow(boolean isLow) {
		this.isLow = isLow;
	}

	public boolean isMiss() {
		return isMiss;
	}

	public void setMiss(boolean isMiss) {
		this.isMiss = isMiss;
	}

	public long getStartPause() {
		return mStartPause;
	}

	public NanaContact getMainContact(){
		if (mMainPhone.get(0).getNumber() == null){
			return null;
		}
		return mMainPhone.get(0);
	}
	
	public void setMainContact(String name, String number){
		mMainPhone.get(0).setContact(name, number);
	}
}
