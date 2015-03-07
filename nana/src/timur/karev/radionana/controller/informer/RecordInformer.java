package timur.karev.radionana.controller.informer;

import java.util.ArrayList;

import android.util.Log;

public class RecordInformer extends RecallInformer implements LoudInformer {
	private static final String  TAG = "RecordInformer";

	boolean mFlag;
	
	public RecordInformer(){
		mVolume = new ArrayList<Integer>();
		mValue = 7000;
		mCount = 5;
		mFlag = false;
	}
	
	public boolean inform(int volume){

		mVolume.add(volume);
		
		if (volume > mValue){
			mFlag = true;
		}

		boolean flag = false;
		if (mCount <= mVolume.size()){
			for (int i : mVolume){
				if (i>mValue){
					 flag = true;
				}
			}
			mVolume.clear();
			mFlag = flag;
		}
		Log.d(TAG, "Flag = " + mFlag);
		return mFlag;
	}
}
