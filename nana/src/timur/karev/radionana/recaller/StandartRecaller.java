package timur.karev.radionana.recaller;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class StandartRecaller implements Recaller {

	private MyPhoneSL mMyPhoneSL;
	private TelephonyManager tm;
	
	private Context mContext;

	public StandartRecaller(Context c){
		mContext = c;
		tm = (TelephonyManager)mContext.getSystemService(mContext.TELEPHONY_SERVICE);
		mMyPhoneSL = new MyPhoneSL(mContext);
	}

	@Override
	public boolean onRecaller() {
		// TODO Auto-generated method stub
		tm.listen(mMyPhoneSL, PhoneStateListener.LISTEN_CALL_STATE);
		return false;
	}

	@Override
	public boolean offRecaller() {
		// TODO Auto-generated method stub
		tm.listen(mMyPhoneSL, PhoneStateListener.LISTEN_NONE);
		return false;
	}
	
}
