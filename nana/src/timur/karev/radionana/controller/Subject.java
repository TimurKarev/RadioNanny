package timur.karev.radionana.controller;

import timur.karev.radionana.fragments.Observer;

public interface Subject {
	
	public void registerObserver(Observer o);
	public void removeObserver();
	public void notifyObservers();
}
