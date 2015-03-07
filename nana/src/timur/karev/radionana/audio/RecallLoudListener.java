package timur.karev.radionana.audio;

import timur.karev.radionana.controller.NanaContact;
import timur.karev.radionana.controller.NanaController;
import timur.karev.radionana.controller.informer.LoudInformer;
import timur.karev.radionana.controller.informer.RecallInformer;
import android.content.Intent;
import android.net.Uri;

public class RecallLoudListener extends BaseLoudListener implements LoudListener {

	private static final String  TAG = "RecordLoudListener";
	
	private NanaController mNana;
	private LoudInformer mInformer;
	
	public RecallLoudListener(){
		super();
		mNana = NanaController.getNana();
		mInformer = new RecallInformer();
	}
	
	@Override
	public int perfomAction() {
		
		if (mRecorder == null){
			createListener();
		}
		
		int volume = 0;
		try{
			volume = mRecorder.getMaxAmplitude();
		} catch (Exception e){}

		if (mInformer.inform(volume) == true && mNana.isMainPhoneEmpty() == false){
    	    NanaContact cont = mNana.getMainContact();
    		Intent intent = new Intent(Intent.ACTION_CALL);
    		intent.setData(Uri.parse("tel:" + cont.getNumber()));
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
    		mNana.getAppContext().startActivity(intent);
	}

		// TODO Auto-generated method stub
		return volume;
	}

}
