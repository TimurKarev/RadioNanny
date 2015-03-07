package timur.karev.radionana.audio;

import timur.karev.radionana.controller.NanaController;

public class LoudListenerFactory {

	public static LoudListener getSetJournalRecorder() {
		// TODO Auto-generated method stub
		NanaController Nana = NanaController.getNana();
		
		if(Nana.getIsJournSetting()){
			return new RecordLoudListener();
		}
		return new EmptyLoudListener();
	}

	public static LoudListener getSetRecallRecorder() {
		// TODO Auto-generated method stub
		NanaController Nana = NanaController.getNana();
		
		if(Nana.getIsLoudSetting()){
			return new RecallLoudListener();
		}
		return getEmptyRecallRecorder();
	}

	public static LoudListener getEmptyRecallRecorder() {
		// TODO Auto-generated method stub
		return new EmptyLoudListener();
	}
}