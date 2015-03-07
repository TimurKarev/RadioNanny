package timur.karev.radionana.controller.informer;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class RecallInformer implements  LoudInformer{
	
	private static String TAG = "RecallInformer"; 
	
	List<Integer> mVolume;
	int mTime;
	int mValue;
	int mCount;
	
	public RecallInformer(){
		mVolume = new ArrayList<Integer>();
		mTime = 4;
		mCount = 3;
		mValue = 9000;
	}
	
	public boolean inform(int volume){
		int count = 0;
		mVolume.add(volume);
		Log.d(TAG,"Volume "+volume + "size = " + mVolume.size() );	
		
		if (mVolume.size() > mTime){
			for(int i=0;i<mVolume.size();i++){
				if(mVolume.get(i) > mValue){
					count ++;
					Log.d(TAG,"Count  "+ count);
				}
			}
			mVolume.clear();

			if (mCount <= count  ){
				Log.d(TAG,"TRUE");
				return true;
			}
		}
		
//		if (volume > 40000)
//			return true;
		return false;
	}

}
