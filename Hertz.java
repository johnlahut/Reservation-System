/*
 * Author: John Lahut
 * Date: 11.12.2014
 * Project: Reservation System
 * Filename: Hertz.java
 * Purpose: Implements the CarRental reservation system.
 */
public class Hertz
{
    CarRental hertz = new CarRental("Hertz Reservation System");

    //creates a new hotel for the Sheraton
    Hertz()
    {
        
        hertz.SetClientName("Hertz Rent-a-car");
        hertz.AddOption("Hybrid");               //Hertz offers hybrids
        hertz.AddOption("SUV");                  //They also offer SUVs
        hertz.AddOption("Fill up the tank!");    //Tank full on pick up?
        hertz.SetCarPrice(80d);                 //80$ per day for a Hertz car
        hertz.Setup();                       //Create Hertz reservation system
    }

}