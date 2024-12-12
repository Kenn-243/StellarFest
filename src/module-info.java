module stellarFest {
	opens main;
	opens models;
	opens views.user;
	opens views.eventOrganizer;
	requires javafx.graphics;
	requires javafx.controls;
	requires java.sql;
}