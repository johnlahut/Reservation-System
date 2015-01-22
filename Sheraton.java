/*
 * Author: John Lahut
 * Date: 11.12.2014
 * Project: Reservation System
 * Filename: Sheraton.java
 * Purpose: Implements the Hotel reservation system.
 */
public class Sheraton
{
    Hotel sheraton = new Hotel("Sheraton Reservation System");

    //creates a new hotel for the Sheraton
    Sheraton()
    {
        
        sheraton.SetClientName("Sheraton Hotels");
        sheraton.AddOption("Suite");            //The sheraton offers suites
        sheraton.AddOption("Pets");             //The sheraton lets you have a pet
        sheraton.AddOption("Single bed");       //Single bed rooms
        sheraton.SetRoomPrice(200d);            //Sheraton is expensive
        sheraton.Setup();                       //Create the sheratons reservation system
    }

}
