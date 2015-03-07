package timur.karev.radionana.batlistener;

import timur.karev.radionana.BatSender;
import android.content.Context;

public class EmptyBatSender implements BatSender{
	
	public EmptyBatSender(Context c){}

	@Override
	public void onBatSender() {
		// TODO Auto-generated method stub
	}

	@Override
	public void offBatSender() {
		// TODO Auto-generated method stub
	};
	

}
