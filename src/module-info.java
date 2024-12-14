module stellarFest {
	opens main;
	opens models;
	opens views.user;
	opens views.eventOrganizer;
	opens views.guestVendor;
	opens controller;
	requires javafx.graphics;
	requires javafx.controls;
	requires java.sql;
}