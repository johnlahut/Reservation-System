/*
 * Author: John Lahut
 * Date: 11.12.2014
 * Project: Reservation System
 * Filename: Reservation.java
 * Purpose: Creates a new generic resevation that provides input validation, and holds basic information for all reservations 
 *          such as a name and payment method. Also contains many constants that are used throughout the reservation system
 *          Forces all future classes to implement the abstract option methods as well as the abstract GUI methods that have not 
 *          been implemented in Reservation.
 */
import java.text.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 

public abstract class Reservation extends JFrame implements ActionListener, WindowListener, FocusListener, ItemListener
{
    //Used for validation/formatting
    final String CURRENCY_FORMAT = "$.00";
    final String CC_NUM_VALIDATION = "[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}";
    final String CC_DATE_VALIDATION = "[0-1]{1}[0-9]{1}/[0-3]{1}[0-9]{1}";
    final String DATE_VALIDATION = CC_DATE_VALIDATION+"/[2]{1}[0-9]{3}";
    final String NAME_VALIDATION = "[a-zA-Z]{0,10}\\ [a-zA-Z]{0,15}";

    //GUI constnats
    static final String NAME_LABEL = "Name: ";
    static final String PAYMENT_LABEL = "Select Payment Type: ";
    static final String CC_NUM_LABEL = "Enter credit card number: ";
    static final String EXP_DATE_LABEL = "Enter expiration date: ";
    static final String GROUP_SIZE_LABEL = "Party size: ";
    static final String NAME_FORMAT = "John Smith";
    static final String DATE_FORMAT = "MM/DD/YYYY";
    static final String EXP_DATE_FORMAT = "MM/YY";
    static final String CC_FORMAT = "XXXX-XXXX-XXXX-XXXX";
    static final String INVALID_CCNUM_ERR = "Invalid Credit Card Number";
    static final String INVALID_CCDATE_ERR = "Invalid Credit Card Date";
    static final String INVALID_DATE_ERR = "Invalid Date";
    static final String INVALID_NAME_ERR = "Invalid Name";

    //Possible sizes in a given group. Needed to be an int wrapper class to initalize JComboBox with an array (cannot be primitive data type arrays)
    static final Integer[] GROUP_SIZES = {1,2,3,4,5,6,7,8,9};

    //Possible payment types for a given reservation
    String[] paymentTypes = {"Visa", "American Express", "Mastercard", "Discover"};

    //payment info struct
    static class PaymentInfo
    {
        public static String paymentType;
        public static String ccNum;
        public static String ccDate;
    }

    //used for formatting the payment 
    DecimalFormat currencyFormat;

    //PDMs for a given generic reservation
    String date;
    String name;
    int groupSize;
    double payment;

    //DC -- call JFrame's DC, initalize PDMS
    public Reservation()
    {
        super();
        this.Init();
    }

    //NDC -- call JFrame's NDC, initalize PDMS
    public Reservation(String s)
    {
        super(s);
        this.Init();
    }

    //pre: Reservation has been instantiated
    //post: Reservation's PDMS have been initalized 
    private void Init()
    {
        date = "**/**/****";
        name = "FIRST LAST";
        groupSize = 0;
        payment = 0.0;
        currencyFormat = new DecimalFormat(CURRENCY_FORMAT);

    }//end Init

    //Protected Methods - Do not want client modifing data, just setting options

    //pre: Reservation has been instantiated
    //post: The reservations date has been returned to the caller 
    protected String GetDate()
    {
        //Returns the date
        return date;

    }//end GetDate

    //pre: Reservation has been instantiated
    //post: The reservations date has been set 
    protected void SetDate(/*in*/String date)  //new date
    {
        this.date = date;

    }//end SetDate

    //pre: Reservation has been instantiated
    //post: The name on the current reservation has been returned to the caller
    protected String GetName()
    {
        return name;

    }//end GetName

    //pre: Reservation has been instantiated
    //post: The name of the current reservation has been set to name
    protected void SetName(/*in*/String name)  //name to set to
    {
        this.name = name;

    }//end SetName

    //pre: Reservation has been instantiated
    //post: The type of payment has been set for the current reservation
    protected void SetPaymentType(/*in*/String paymentType) //payment type to set
    {
        PaymentInfo.paymentType = paymentType;

    }//end SetPaymentType

    //pre: Reservation has been instantiated
    //post: returns the payment amount of the current reservation
    protected double GetPayment()
    {
        return payment;

    }//end GetPayment

    //pre: Reservation has been instantiated
    //post: returns a string representation of the current reservation (formats it to USD)
    protected String GetPaymentAsString()
    {
        return currencyFormat.format(GetPayment());

    }//end GetPaymentAsString

    //pre: Reservation has been instantiated
    //post: sets the current reservations payment as payment
    protected void SetPayment(/*in*/double payment)    //payment to be set
    {
        this.payment = payment;

    }//end SetPayment

    //pre: Reservation has been instantiated
    //post: payment is added to the current payment amout
    protected void AddToPayment(/*in*/double payment) //payment to be added
    {
        this.payment += payment;

    }//end AddToPayment

    //pre: Reservation has been instantiated
    //post: adds a new payment type to PaymentInfo struct
    protected void AddPaymentOption(/*in*/String newPayment)   //payment type to be added
    {
        //Expand array by one
        String[] temp = new String[paymentTypes.length+1];
        for(int i=0; i<paymentTypes.length; i++)
            temp[i] = paymentTypes[i];

        temp[paymentTypes.length] = newPayment;

        paymentTypes = temp;

    }//end AddPaymentOption

    //pre: Reservation has been instantiated
    //post: The payment types have been returned as a string array
    protected String[] GetPaymentTypes()
    {
        return paymentTypes;

    }//end GetPaymentTypes

    //pre: Reservation has been instantiated, ccNum has been validated
    //post: the credit card number has been set
    protected void SetCCNum(/*in*/String ccNum) //valid credit card num
    {
        PaymentInfo.ccNum = ccNum;

    }//end SetCCnum

    //pre: Reservation has been instantiated, ccDate has been validated
    //post: the exipiration date has been set to ccDate
    protected void SetCCDate(/*in*/String ccDate) //valid date
    {
        PaymentInfo.ccDate = ccDate;

    }//emd SetCCDate

    //pre: Reservation has been instantiated
    //post: returns this reservations class's payment info
    protected PaymentInfo GetPaymentInfo()
    {
        return new PaymentInfo();

    }//end GetPaymentInfo

    //pre: Reservation has been instantiated
    //post: sets this reservations groupSize
    protected void SetGroupSize(/*in*/int groupSize) //group size
    {
        this.groupSize = groupSize;

    }//end SetGroupSize

    //pre: Reservation has been instantiated
    //post: returns this reservations group size
    protected int GetGroupSize()
    {
        return groupSize;
    }//end GetGroupSize

    //pre: Reservation has been instantiated
    //post: returns true to the caller if ccNum is a valid credit card number, false otherwise
    protected boolean ValidateCCNum(String ccNum)
    {
        return (ccNum.matches(CC_NUM_VALIDATION));

    }//end ValidateCCNum

    //pre: Reservation has been instantiated
    //post: returns true to the caller if ccDate is a valid credit card exp date, false otherwise
    protected boolean ValidateCCDate(String ccDate)
    {
        return (ccDate.matches(CC_DATE_VALIDATION));

    }//end ValidateCCDate

    //pre: Reservation has been instantiated
    //post: returns true to thr caller if date is a valid date, false otherwise
    protected boolean ValidateDate(String date)
    {
        return (date.matches(DATE_VALIDATION));

    }//end ValidateDate

    //pre: Reservation has been instantiated
    //post: returns true to the caller if name is a valid name, false otherwise
    protected boolean ValidateName(String name)
    {
        return (name.matches(NAME_VALIDATION));

    }//end ValidName

    //must implement -- returns options of this class -- basic reservation does not have options
    public abstract ArrayList<String> GetOptions();

    //must implement -- adds an option to the list of options
    public abstract void AddOption(String option);

    //boliler plate code
    public void windowActivated( WindowEvent e ) 
    { 
    } 

    public void windowClosed( WindowEvent e ) 
    { 
    } 

    public void windowClosing( WindowEvent e ) 
    { 
        System.exit(0); 
    } 

    public void windowDeactivated( WindowEvent e ) 
    { 
    } 

    public void windowDeiconified( WindowEvent e ) 
    { 
    } 

    public void windowOpened( WindowEvent e ) 
    { 
    } 

    public void windowIconified( WindowEvent e ) 
    { 
    } 

    public void itemStateChanged(ItemEvent e)
    {
    }

}
