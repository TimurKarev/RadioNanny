package timur.karev.radionana.controller;

import android.os.Environment;
import android.util.Log;

public class OutputFileNameManager {

    private final String TAG = "OutputFileNameManager";

    int mCount = 1;

	public String getNextFileName(){

		String string = "";
     //   string = Environment.getExternalStorageDirectory().getAbsolutePath();
        string += NanaController.getNana().getAppContext().getFilesDir();
        Log.d(TAG, "Dir = " + string);
        string += "audiorecordtest" + mCount + ".3gp";
        mCount ++;
		return string;
	}
	
	public static String getEmptyFileName(){
		return "/dev/null";
	}
}