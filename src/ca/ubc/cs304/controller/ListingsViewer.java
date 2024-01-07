package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.entity.Listing;
import ca.ubc.cs304.ui.SearchScreen;

import java.io.IOException;
import java.util.Arrays;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class ListingsViewer {
	private DatabaseConnectionHandler dbHandler;

	public ListingsViewer() {
		dbHandler = new DatabaseConnectionHandler();
	}

	private void start() throws IOException {
		dbHandler.login("", "");
		dbHandler.databaseSetup();
		new SearchScreen(dbHandler);
	}
    
    public void databaseSetup() {
		dbHandler.databaseSetup();
	}

	/**
	 * Main method called at launch time
	 */
	public static void main(String[] args) throws IOException {
		ListingsViewer listingsViewer = new ListingsViewer();
		listingsViewer.start();
	}
}
