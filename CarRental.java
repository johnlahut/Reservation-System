
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 

/**
 * Author: John Lahut
 * Date: 11.12.2014
 * Project: Reservation System
 * Filename: CarRental.java
 * Purpose: Creates a new generic car rental class. All dervied classes will be very simple. They just have to add the options they would like
 */
public class CarRental extends Reservation
{

    //Strings used for output
    public static final String SUMMARY_WINDOW = "Rental Summary";
    public static final String THANK_YOU_MSG = "Thank you ";
    public static final String SELECTED_OPTIONS = "\nYou have selected the following options for your rental: ";
    public static final String THANK_YOU_RENTAL = "Thank you for using ";
    public static final String DEFAULT_NAME = "Basic Car Rental";
    public static final String BOOK_CAR = "Book car";

    //Default tix price
    public static final double DEFAULT_CAR_PRICE = 60.0;

    //Default options for every car rental class
    public static final String[] DEFAULT_OPTIONS = {
            "Insurance"
        };

    //PDMS

    //car price
    double carPrice = DEFAULT_CAR_PRICE;

    //components of the GUI stored in an arraylist -- this was sort of an experiment..
    ArrayList<JComponent> components = new ArrayList<JComponent>();
    ArrayList<JComponent> results = new ArrayList<JComponent>();
    ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
    JButton bookRoom;

    String arrvDate;
    String deptDate;
    int numDays;
    String clientName = DEFAULT_NAME;

    ArrayList<String> options = new ArrayList<String>();
    ArrayList<String> selectedOptions = new ArrayList<String>();

    //DC, creates a new GUI with a placeholder name
    CarRental()
    {
        this(DEFAULT_NAME);
    }

    //Sets up a new GUI with a given name
    CarRental(/*in*/String s)     //Name of client
    {
        super(s);
        clientName = s;
        //Default car rental rentaloptions
        for(int i=0; i<DEFAULT_OPTIONS.length; i++)
        {
            options.add(DEFAULT_OPTIONS[i]);

        }//end for

    }

    //pre: car rental has been instantiated, all options have been added by client prior to calling the function
    //post: car rental has been properly set-up and created. All added options by client are represented on the GUI
    /**
     * 
     */
    public void Setup()
    {
        Container c = getContentPane();
        c.setLayout(null);
        JPanel input = new JPanel(new GridLayout(9,2));                             //Panel to hold all input for a car rental
        JPanel optionBoxes = new JPanel(new GridLayout(GetOptions().size(),1));     //Panel to hold option checkboxes

        /*0*/components.add(new JLabel(Reservation.NAME_LABEL));
        /*1*/components.add(new JTextField(Reservation.NAME_FORMAT));
        /*2*/components.add(new JLabel("Rental date: "));
        /*3*/components.add(new JTextField(Reservation.DATE_FORMAT));
        /*4*/components.add(new JLabel());
        /*5*/components.add(new JLabel());
        /*6*/components.add(new JLabel(Reservation.PAYMENT_LABEL));
        /*7*/components.add(new JComboBox(GetPaymentTypes()));
        /*8*/components.add(new JLabel(Reservation.CC_NUM_LABEL));
        /*9*/components.add(new JTextField(Reservation.CC_FORMAT));
        /*10*/components.add(new JLabel(Reservation.EXP_DATE_LABEL));
        /*11*/components.add(new JTextField(Reservation.EXP_DATE_FORMAT));
        /*12*/components.add(new JLabel("Number of cars to rent"));
        /*13*/components.add(new JComboBox(Reservation.GROUP_SIZES));
        /*14*/components.add(new JLabel("Number of days to rent: "));
        /*15*/components.add(new JTextField());

        //Get the options for the current car rental 
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

        JLabel thankYou = new JLabel(THANK_YOU_RENTAL + GetClientName());
        thankYou.setForeground(Color.BLUE);

        bookRoom = new JButton(BOOK_CAR);

        JComboBox payments = (JComboBox)components.get(7);
        payments.addItemListener(this);
        components.set(7, payments);

        numDays = 0;
        //Sets the GUI up
        input.setBounds(10,10,400,225);
        optionBoxes.setBounds(10,250,175,100);
        thankYou.setBounds(190,220,250,60);
        bookRoom.setBounds(10,370, 100, 25);
        c.add(input);
        c.add(optionBoxes);
        c.add(thankYou);
        c.add(bookRoom);
        bookRoom.addActionListener(this);
        setBounds(200,200,500,500);
        setVisible(true);

    }//end Setup()

    //pre: car rental has been instantiated 
    //post: ticketPrice has been set to cost
    public void SetCarPrice(/*in*/double cost) //new ticket price
    {
        carPrice = cost;

    }//end SetCarPrice

    //pre: car rental has been instantiated 
    //post: returns the ticketPrice to the caller
    public /*out*/double GetCarPrice()       //current ticket price
    {
        return carPrice;

    }//end GetCarPrice

    //pre: car rental has been instantiated 
    //post: the current clients name has been returned to the caller
    public /*out*/String GetClientName()    //name of the current client
    {
        return clientName;

    }//end GetClientName

    //pre: car rental has been instantiated 
    //post: this clientName has been set to the incoming clientName
    public void SetClientName(/*in*/String clientName) //new client name
    {
        this.clientName = clientName;

    }//end SetClientName

    //pre: car rental has been instantiated 
    //post: Adds an option to the current list options
    public void AddOption(/*in*/String option) //option to be added
    {
        options.add(option);

    }//end AddOption

    //pre: car rental has been instantiated 
    //post: the car rental options has been returned to the caller
    public /*out*/ArrayList<String> GetOptions()    //current options
    {
        return options;

    }//end GetOptions

    protected int GetNumOfNights()
    {
        return numDays;
    }

    protected void SetNumOfNights(/*in*/int numNights)
    {
        this.numDays = numNights;
    }

    protected double CalculateCost()
    {
        //Sets the cost which is the number of cars * the rate * number of days rented
        return (GetCarPrice()*GetNumOfNights()*GetGroupSize()); 
    }

    //pre: car rental has been instantiated 
    //post: if an option has been selected by the GUI, adds it to the list
    protected void AddSelectedOption(/*in*/String selection) //selected option
    {
        selectedOptions.add(selection);
    }

    //pre: car rental has been instantiated 
    //post: returns the list of selected options to the caller
    protected /*out*/ArrayList<String> GetSelectedOptions() //list of selected options
    {
        return selectedOptions;

    }//end GetSelectedOptions

    //pre: car rental has been instantiated 
    //post: input has been validated, a summary of the car rental is shown to the user as a new window
    protected void ShowSummaryFrame()
    {
        Reservation.PaymentInfo paymentInfo = GetPaymentInfo();
        //Create a new summary window
        SummaryFrame summary = new SummaryFrame(SUMMARY_WINDOW);
        summary.AddText(THANK_YOU_MSG + GetName());
        summary.AddText("You will be picking your car up on " + GetDate());
        summary.AddText("Please return the car in " + GetNumOfNights() + " days.");
        summary.AddText("\nYour " + paymentInfo.paymentType + " will be charged " + GetPaymentAsString() + " dollars.");

        ArrayList<String> selectedOptions = GetSelectedOptions();
        //display options
        if(!selectedOptions.isEmpty())
        {
            summary.AddText(SELECTED_OPTIONS);
            for(int i=0; i<selectedOptions.size(); i++)
            {
                summary.AddText(selectedOptions.get(i));
            }
        }

        summary.AddText("\n" + THANK_YOU_RENTAL+GetClientName());
        selectedOptions.clear();
        SetPayment(0);
        numDays = 0;
    }

    public void actionPerformed(ActionEvent e) 
    {
        //If the book car rental button is pressed, check each field, if they are valid entries,
        //store them in the reservation
        if(e.getSource() == bookRoom)
        {
            boolean allValid = true; //check to see if all fields are valid
            //Name
            JTextField name = (JTextField)components.get(1);
            if(!ValidateName(name.getText()))
            {
                name.setText(INVALID_NAME_ERR);
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
            JTextField arrvDate = (JTextField)components.get(3);
            if(!ValidateDate(arrvDate.getText()))
            {
                arrvDate.setText(INVALID_DATE_ERR);
                arrvDate.setForeground(Color.RED);
                components.set(3, arrvDate);
                validDates = false;
                allValid = false;
            }

            if(validDates)
            {
                SetDate(arrvDate.getText());
            }

            //Credit card number
            JTextField ccNum = (JTextField)components.get(9);
            if(!ValidateCCNum(ccNum.getText()))
            {
                ccNum.setText(INVALID_CCNUM_ERR);
                ccNum.setForeground(Color.RED);
                components.set(9, ccNum);
                allValid = false;
            }
            else
            {
                SetCCNum(ccNum.getText());
            }

            //Credit card date
            JTextField ccDate = (JTextField)components.get(11);
            if(!ValidateCCDate(ccDate.getText()))
            {
                ccDate.setText(INVALID_CCDATE_ERR);
                ccDate.setForeground(Color.RED);
                components.set(11, ccDate);
                allValid = false;
            }
            else
            {
                SetCCDate(ccDate.getText());
            }

            JTextField days = (JTextField)components.get(15);
            try
            {
                numDays = Integer.parseInt(days.getText());
            }
            catch(Exception i)
            {
                allValid = false;
                days.setText("Invalid Number");
                days.setForeground(Color.RED);
                components.set(15, days);
            }

            //If the options are all valid, create a new summary frame of the car rental
            if(allValid)
            {
                JComboBox paymentType = (JComboBox)components.get(7);
                JComboBox partySize = (JComboBox)components.get(13);
                SetGroupSize(((Integer)partySize.getSelectedItem()));                     //Set group size
                SetPaymentType(paymentType.getSelectedItem().toString());                   //Set the payment type
                SetPayment(CalculateCost());      

                for(int i=0; i<checkBoxes.size(); i++)
                {
                    if(checkBoxes.get(i).isSelected())
                    {
                        AddSelectedOption(GetOptions().get(i));
                    }
                }
                ShowSummaryFrame();
            }

        }//end bookCar btn

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

}//end car rental
