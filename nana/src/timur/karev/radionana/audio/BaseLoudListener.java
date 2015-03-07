package timur.karev.radionana.audio;

import java.io.IOException;

import timur.karev.radionana.controller.OutputFileNameManager;
import android.media.MediaRecorder;
import android.util.Log;

public abstract class BaseLoudListener implements LoudListener {
	private static final String  TAG = "BaseLoudListener";

	MediaRecorder mRecorder;
	String mFileName;

	@Override
	abstract public int perfomAction();
	
	public BaseLoudListener(){
		mFileName = OutputFileNameManager.getEmptyFileName();
	}

	@Override
	public void offListener() {
		// TODO Auto-generated method stub
		try{
	        mRecorder.stop();
	        mRecorder.release();
	        mRecorder = null;
			} catch (Exception e){}
	}
	
	void createListener(){
		mRecorder = new MediaRecorder();
	      try {
	     	  mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
	          mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	          mRecorder.setOutputFile(mFileName);
	          mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	          mRecorder.prepare();
	          mRecorder.start();
	          mRecorder.getMaxAmplitude();
	          } catch (IOException e) {
	              Log.d(TAG, "RECORDER prepare() failed  " + e.getMessage());
	          }

	}
	
	void restartListener(){
		offListener();
		createListener();
	}
}