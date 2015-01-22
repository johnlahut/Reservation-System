import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 
/**
 * Write a description of class SummaryFrame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SummaryFrame extends JFrame
{   
    
    JTextArea summary = new JTextArea(20,30);
    
    public SummaryFrame(String name)
    {
        super(name);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        setBounds(100,100,400,400);
        summary = new JTextArea(20,30);
        summary.setLineWrap(true); summary.setWrapStyleWord(true);
        summary.setEditable(false);
        summary.setFont(new Font("Calibri", Font.BOLD, 14));
        c.add(summary);
        setVisible(true);
    }
    
    public void AddText(String newLine)
    {
        summary.append("\n"+newLine);
    }
}
