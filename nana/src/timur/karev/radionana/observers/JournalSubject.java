package timur.karev.radionana.observers;


public interface JournalSubject {
	public void registerObserver(FragmentObserver o);
	public void removeObserver();
	public void notifyObservers(int type);

}
