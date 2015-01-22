/*
 * Author: John Lahut
 * Date: 11.12.2014
 * Project: Reservation System
 * Filename: DeltaAirlines.java
 * Purpose: Implements the Airline reservation system.
 */

public class DeltaAirlines 
{
    Airline delta = new Airline("Delta Booking System");
    
    //Creates a new airline for Delta airlines
    DeltaAirlines()
    {
        //delta.AddPaymentOption("Paypal");       //You can use PayPal to pay for your Delta ticket!
        delta.SetClientName("Delta Airlines!"); //Sets the client name
        delta.AddOption("Extra check bag");     //You can have an extra check bag with Delta
        delta.AddOption("Pet travel");          //You can have a pet travel with you on Delta
        delta.AddOption("Extra carry on");      //You can have an extra carry on with Delta
        delta.AddOption("Extra pillow");        //Delta is nice enough to offer you an extra pillow
        delta.AddAirport("CHI");                //Delta also flies to Chicago airport
        delta.AddAirport("LHR");                //And is an international airline, so it can fly to London as well.
        delta.SetTicketPrice(400d);             //Delta is really expensive, but all their tickets cost the same
        delta.Setup();                          //Make a new Delta Airline GUI with the above parameters
    }
}
