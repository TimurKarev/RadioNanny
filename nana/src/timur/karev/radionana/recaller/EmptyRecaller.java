package timur.karev.radionana.recaller;

import timur.karev.radionana.controller.informer.RecallInformer;
import android.content.Context;
import android.telephony.PhoneStateListener;

public class EmptyRecaller extends RecallInformer implements Recaller {
	
	public EmptyRecaller(Context c){
	}
	
	@Override
	public boolean onRecaller() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offRecaller() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
