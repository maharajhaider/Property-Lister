package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class ListingsViewer {
	private DatabaseConnectionHandler dbHandler;

	public ListingsViewer() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void start() {
		//
	}
    
    public void databaseSetup() {
		dbHandler.databaseSetup();
	}
    
	/**
	 * Main method called at launch time
	 */
	public static void main(String[] args) {
		ListingsViewer listingsViewer = new ListingsViewer();
		listingsViewer.start();
	}
}
