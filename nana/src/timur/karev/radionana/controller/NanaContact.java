package timur.karev.radionana.controller;

public class NanaContact {
	private String mName;
	private String mNumber;

	public NanaContact(String str){
		mName = str;
		mNumber = str;
	}

	public NanaContact(String name, String number){
		mName = name;
		mNumber = number;
	}
	
	public String getName() {
		return mName;
	}

	public String getNumber() {
		return mNumber;
	}

	public void setContact(String name, String number) {
		mNumber = number;
		mName = name;
	}
	
	public String toString(){
		super.toString();

		if (mName != null && mNumber != null){
		return mName + " " + mNumber;
		}
		return "Nichego";
	}
	
	public boolean equal(NanaContact cont){
		if (mNumber.equals(cont.getNumber())){
			return true;
		}
		return false;
	}
	
}