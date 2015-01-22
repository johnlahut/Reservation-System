/*
 * Author: John Lahut
 * Date: 11.12.2014
 * Project: Reservation System
 * Filename: Airline.java
 * Purpose: Creates a new generic airline class. All dervied classes will be very simple. They just have to add the options they would like
 *          additional airports if desired, set their clients name, ticket price, and the program is ready to run
 */

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 
public class Airline extends Reservation
{
    
    //Length of itinerary array
    final int ITINERARY_SLOTS = 3;
    
    //Strings used for output
    public static final String SUMMARY_WINDOW = "Flight Summary";
    public static final String THANK_YOU_MSG = "Thank you ";
    public static final String FLIGHT_DEPT_MSG = "Your flight will depart on: ";
    public static final String FLIGHT_ARRV_MSG = "Your flight will arrive on: ";
    public static final String SELECTED_OPTIONS = "\nYou have selected the following options for your flight: ";
    public static final String THANK_YOU_AIRLINE = "Thank you for traveling with ";
    public static final String DEFAULT_NAME = "Basic Airline";
    public static final String BOOK_FLIGHT = "Book flight";
    
    //Default tix price
    public static final double DEFAULT_TICKET_PRICE = 100.0;
    
    //Default options for every flight class
    public static final String[] DEFAULT_OPTIONS = {
        "First class"
    };
    
    //Airports that every flight class can fly to
    public static final String[] DEFAULT_AIRPORTS = {
        "LAX",
        "JFK",
        "ATL",
        "DEN",
    };
    
    public static final String DEFAULT_FLIGHT_ID = "XXXX";
    
    //PDMS
    
    //tix price
    double ticketPrice = DEFAULT_TICKET_PRICE;
    
    //components of the GUI stored in an arraylist -- this was sort of an experiment..
    ArrayList<JComponent> components = new ArrayList<JComponent>();
    ArrayList<JComponent> results = new ArrayList<JComponent>();
    ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
    JButton bookFlight;

    
    String arrvDate;
    String deptDate;
    String flightID;
    String clientName;

    ArrayList<String> options = new ArrayList<String>();
    ArrayList<String> airports = new ArrayList<String>();
    ArrayList<String> selectedOptions = new ArrayList<String>();

    String[] itinerary = new String[ITINERARY_SLOTS];
    // where
    // 
    // itinerary[0] = Departure airport
    // itinerary[1] = Arrival airport
    // itinerary[2] = Flight ID 

    //DC, creates a new GUI with a placeholder name
    Airline()
    {
        this(DEFAULT_NAME);
        flightID = DEFAULT_FLIGHT_ID;
        clientName = DEFAULT_NAME;
    }

    //Sets up a new GUI with a given name
    Airline(/*in*/String s)     //Name of client
    {
        super(s);
        clientName = s;
        //Default airline options
        for(int i=0; i<DEFAULT_OPTIONS.length; i++)
        {
            options.add(DEFAULT_OPTIONS[i]);
            
        }//end for

        //Add the default airports
        for(int i=0; i<DEFAULT_AIRPORTS.length; i++)
        {
            airports.add(DEFAULT_AIRPORTS[i]);
        }
        
        flightID = DEFAULT_FLIGHT_ID;
        clientName = DEFAULT_NAME;
    }
    
    
    //pre: Airline has been instantiated, all options have been added by client prior to calling the function
    //post: Airline has been properly set-up and created. All added options by client are represented on the GUI
    public void Setup()
    {
        Container c = getContentPane();
        c.setLayout(null);
        JPanel input = new JPanel(new GridLayout(9,2));                             //Panel to hold all input for an airline
        JPanel optionBoxes = new JPanel(new GridLayout(GetOptions().size(),1));     //Panel to hold option checkboxes
        
        //Add a new component to the components array. these are accessed by the documented index. 
        //Originally made it easy to add to the form, however I realized that I need to do a cast every time I take
        //an element out. When i realized this it was too much work to revert back to the old way. Still not sure which way is more efficient..
        
        /*0*/components.add(new JLabel(Reservation.NAME_LABEL));
        /*1*/components.add(new JTextField(Reservation.NAME_FORMAT));
        /*2*/components.add(new JLabel("Departure date: "));
        /*3*/components.add(new JTextField(Reservation.DATE_FORMAT));
        /*4*/components.add(new JLabel("Arrival date: "));
        /*5*/components.add(new JTextField(Reservation.DATE_FORMAT));
        /*6*/components.add(new JLabel("Select departing airport: "));
        /*7*/components.add(new JComboBox(GetAirports().toArray()));
        /*8*/components.add(new JLabel("Select desired desination: "));
        /*9*/components.add(new JComboBox(GetAirports().toArray()));
        /*10*/components.add(new JLabel(Reservation.PAYMENT_LABEL));
        /*11*/components.add(new JComboBox(GetPaymentTypes()));
        /*12*/components.add(new JLabel(Reservation.CC_NUM_LABEL));
        /*13*/components.add(new JTextField(Reservation.CC_FORMAT));
        /*14*/components.add(new JLabel(Reservation.EXP_DATE_LABEL));
        /*15*/components.add(new JTextField(Reservation.EXP_DATE_FORMAT));
        /*16*/components.add(new JLabel(Reservation.GROUP_SIZE_LABEL));
        /*17*/components.add(new JComboBox(Reservation.GROUP_SIZES));

        //Get the options for the current airline 
        ArrayList<String> options = GetOptions();
        
        //Add a checkbox for each option
        for(int i=0; i<GetOptions().size(); i++)
        {
            checkBoxes.add(new JCheckBox(options.get(i)));
            optionBoxes.add(checkBoxes.get(i));
        }
        
        //Set the default formatting text in each text box to color gray, and add a FocusListener
        //FocusListener is to detect if the user has selected that text box
        for(int i=0; i<components.size(); i++)
        {
            input.add(components.get(i));
            if(components.get(i) instanceof JTextField)
            {
                components.get(i).setForeground(Color.GRAY);
                components.get(i).addFocusListener(this);
            }
        }

        JLabel thankYou = new JLabel(Airline.THANK_YOU_AIRLINE + GetClientName());
        thankYou.setForeground(Color.BLUE);

        bookFlight = new JButton(BOOK_FLIGHT);

        //Sets the GUI up
        input.setBounds(10,10,400,225);
        optionBoxes.setBounds(10,250,175,100);
        thankYou.setBounds(190,220,250,60);
        bookFlight.setBounds(10,370, 100, 25);
        c.add(input);
        c.add(optionBoxes);
        c.add(thankYou);
        c.add(bookFlight);
        bookFlight.addActionListener(this);
        setBounds(100,100,500,500);
        setVisible(true);
        
    }//end Setup()
    
    //pre: Airline has been instantiated 
    //post: ticketPrice has been set to cost
    public void SetTicketPrice(/*in*/double cost) //new ticket price
    {
        ticketPrice = cost;
        
    }//end SetTicketPrice
    
    //pre: Airline has been instantiated 
    //post: returns the ticketPrice to the caller
    public /*out*/double GetTicketPrice()       //current ticket price
    {
        return ticketPrice;
    
    }//end GetTicketPrice
    
    //pre: Airline has been instantiated 
    //post: newAirport has been added to the list of airports
    public void AddAirport(/*in*/String newAirport) //newAirport to be added to the list
    {
        airports.add(newAirport);
        
    }//end AddAirport

    //pre: Airline has been instantiated 
    //post: The airports have been returned to the caller as an ArrayList
    public /*out*/ArrayList<String> GetAirports()   //current list of airports
    {
        return airports;
        
    }//end GetAirprots()
    
    //pre: Airline has been instantiated 
    //post: the current clients name has been returned to the caller
    public /*out*/String GetClientName()    //name of the current client
    {
        return clientName;
        
    }//end GetClientName
    
    //pre: Airline has been instantiated 
    //post: this clientName has been set to the incoming clientName
    public void SetClientName(/*in*/String clientName) //new client name
    {
        this.clientName = clientName;
    
    }//end SetClientName

    //pre: Airline has been instantiated 
    //post: Adds an option to the current list options
    public void AddOption(/*in*/String option) //option to be added
    {
        options.add(option);
    
    }//end AddOption
    
    //pre: Airline has been instantiated 
    //post: the airlines options has been returned to the caller
    public /*out*/ArrayList<String> GetOptions()    //current options
    {
        return options;
    
    }//end GetOptions
    
    //pre: Airline has been instantiated 
    //post: overrides Reservation's GetDate and returns the date as both the arrival and departure date to the caller
    public /*out*/String GetDate() //Date to be returned
    {
        return arrvDate + " " + deptDate;
    
    }//end GetDate

    //pre: Airline has been instantiated 
    //post: if an option has been selected by the GUI, adds it to the list
    protected void AddSelectedOption(/*in*/String selection) //selected option
    {
        selectedOptions.add(selection);
    }

    //pre: Airline has been instantiated 
    //post: returns the list of selected options to the caller
    protected /*out*/ArrayList<String> GetSelectedOptions() //list of selected options
    {
        return selectedOptions;
    
    }//end GetSelectedOptions

    //pre: Airline has been instantiated 
    //post: the flights itnerarry has been set
    protected void SetFlightInfo(/*in*/String depart,   //Departing airprot
                                /*in*/String arrival,   //Arriving airport
                                /*in*/String flightID)  //flight's ID
    {
        itinerary[0] = depart;
        itinerary[1] = arrival;
        itinerary[2] = flightID;
        
    }//end SetFlightInfo()

    //pre: Airline has been instantiated 
    //post: the flights Itinerary has been returned to the caller
    protected /*out*/String[] GetItinerary() //Flights itinerary
    {
        return itinerary;
        
    }//end GetItinerary
    
    //pre: Airline has been instantiated 
    //post: sets the date for the current airline
    protected void SetDate(/*in*/String arrvDate,   //Arriving date 
                           /*in*/String deptDate)   //Departing date
    {
        this.arrvDate = arrvDate;
        this.deptDate = deptDate;
        
    }//end SetDate

    //pre: Airline has been instantiated 
    //post: input has been validated, a summary of the flight is shown to the user as a new window
    protected void ShowSummaryFrame()
    {
        String[] flightInfo = GetItinerary();
        Reservation.PaymentInfo paymentInfo = GetPaymentInfo();
        //Create a new summary window
        SummaryFrame summary = new SummaryFrame(SUMMARY_WINDOW);
        summary.AddText(Airline.THANK_YOU_MSG + GetName());
        summary.AddText("\nYou are departing from " + flightInfo[0] + " airport on flight ID " + flightInfo[2] + ". You will be arriving at " + flightInfo[1] + " airport.");
        summary.AddText("\nYour " + paymentInfo.paymentType + " will be charged " + GetPaymentAsString() + " dollars.");
        
        ArrayList<String> selectedOptions = GetSelectedOptions();
        //display options
        if(!selectedOptions.isEmpty())
        {
            summary.AddText(Airline.SELECTED_OPTIONS);
            for(int i=0; i<selectedOptions.size(); i++)
            {
                summary.AddText(selectedOptions.get(i));
            }
        }
        
        String[] dates = GetDate().split(" ");
        summary.AddText("\n" + "You will be departing from " + flightInfo[0] + " on " + dates[1] + " and");
        summary.AddText(" returning on " + dates[0] + ". You will be flying home out of " + flightInfo[1] + " airport.");
        
        summary.AddText("\n" + Airline.THANK_YOU_AIRLINE+GetClientName());
        
        
        selectedOptions.clear();
        SetPayment(0);
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        //If the book flight button is pressed, check each field, if they are valid entries,
        //store them in the reservation
        if(e.getSource() == bookFlight)
        {
            boolean allValid = true; //check to see if all fields are valid
            //Name
            JTextField name = (JTextField)components.get(1);
            if(!ValidateName(name.getText()))
            {
                name.setText(Airline.INVALID_NAME_ERR);
                name.setForeground(Color.RED);
                components.set(1, name);
                allValid = false;
            }
            else
            {
                SetName(name.getText());
            }

            //Dates
            boolean validDates = true;
            JTextField arrvDate = (JTextField)components.get(5);
            JTextField deptDate = (JTextField)components.get(3);
            if(!ValidateDate(arrvDate.getText()))
            {
                arrvDate.setText(Airline.INVALID_DATE_ERR);
                arrvDate.setForeground(Color.RED);
                components.set(3, arrvDate);
                validDates = false;
                allValid = false;
            }

            if(!ValidateDate(deptDate.getText()))
            {
                deptDate.setText(Airline.INVALID_DATE_ERR);
                deptDate.setForeground(Color.RED);
                components.set(5, deptDate);
                validDates=false;
                allValid = false;
            }
            
            if(validDates)
            {
                SetDate(arrvDate.getText(), deptDate.getText());
            }
            
            

            //Credit card number
            JTextField ccNum = (JTextField)components.get(13);
            if(!ValidateCCNum(ccNum.getText()))
            {
                ccNum.setText(Airline.INVALID_CCNUM_ERR);
                ccNum.setForeground(Color.RED);
                components.set(13, ccNum);
                allValid = false;
            }
            else
            {
                SetCCNum(ccNum.getText());
            }
            

            //Credit card date
            JTextField ccDate = (JTextField)components.get(15);
            if(!ValidateCCDate(ccDate.getText()))
            {
                ccDate.setText(Airline.INVALID_CCDATE_ERR);
                ccDate.setForeground(Color.RED);
                components.set(15, ccDate);
                allValid = false;
            }
            else
            {
                SetCCDate(ccDate.getText());
            }
            
            //If the options are all valid, create a new summary frame of the flight
            if(allValid)
            {
                JComboBox dept = (JComboBox)components.get(7);
                JComboBox arrv = (JComboBox)components.get(9);
                JComboBox paymentType = (JComboBox)components.get(11);
                JComboBox partySize = (JComboBox)components.get(17);
                SetGroupSize(((Integer)partySize.getSelectedItem()-1));                     //Set group size
                SetFlightInfo(dept.getSelectedItem().toString(), arrv.getSelectedItem().toString(), "401C");    //Set interary
                SetPaymentType(paymentType.getSelectedItem().toString());                   //Set the payment type
                SetPayment(GetTicketPrice()*GetGroupSize());                                //Set the cost
                
                for(int i=0; i<checkBoxes.size(); i++)
                {
                    if(checkBoxes.get(i).isSelected())
                    {
                        AddSelectedOption(GetOptions().get(i));
                    }
                }
                
                AddToPayment(GetTicketPrice());
                ShowSummaryFrame();
            }
            
        }//end bookFlight btn
        
    }//end actionperformed
    
    //If the textfield is not selected, the text is gray
    public void focusLost(FocusEvent e)
    {
        if(e.getSource() instanceof JTextField)
        {
            e.getComponent().setForeground(Color.GRAY);
        }
    }
    
    //If the user selected the textfield, turn the text black
    public void focusGained(FocusEvent e)
    {
        if(e.getSource() instanceof JTextField)
        {
            e.getComponent().setForeground(Color.BLACK);
        }
    }


}//end airline
