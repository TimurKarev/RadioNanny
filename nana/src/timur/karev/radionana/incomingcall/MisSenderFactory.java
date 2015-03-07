package timur.karev.radionana.incomingcall;

import timur.karev.radionana.controller.NanaController;

public class MisSenderFactory {
	
	public static IncomingCallListener getSetMisSender(){
		NanaController nana = NanaController.getNana();
		if (nana.isMisListEmpty() == false && nana.getIsMisSetting() == true){
			return new MisSmsSender(nana.getAppContext());
		}
		return new EmptyIncomingCallListener();
	}

}
