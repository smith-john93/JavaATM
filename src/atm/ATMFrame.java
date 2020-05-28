package atm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   A frame displaying the components of an ATM.
*/
public class ATMFrame extends JFrame
{  
   private static final long serialVersionUID = 1L;
   private JButton aButton;
   private JButton bButton;
   private JButton cButton;
   private JButton one;
   private JButton two;
   private JButton three;
   private JButton four;
   private JButton five;
   private JButton six;
   private JButton seven;
   private JButton eight;
   private JButton nine;
   private JButton zero;
   private JButton clear;
   private JButton abort;
   private JTextField textfield = new JTextField(20);
   private Keypad pad;
   private JTextArea display;
   StringBuilder numHold = new StringBuilder();
   JPanel buttonPanel = new JPanel();
   

   private ATM atm;
   private static final int FRAME_WIDTH = 300;
   private static final int FRAME_HEIGHT = 300;
   
   // Constructs the user interface of the ATM frame.
   public ATMFrame(ATM ATM)
   {  
      atm = ATM;
      textfield.setEditable(true);
      
      pad = new Keypad();
      buildDisplay();
      buildButtons();
          
      
      setLayout(new BorderLayout());
      add(display,BorderLayout.NORTH);
      add(buttonPanel,BorderLayout.SOUTH);
      add(textfield,BorderLayout.CENTER);
      showState();

      setSize(FRAME_WIDTH, FRAME_HEIGHT);
      setVisible(true);
   }

    //Updates display message.
   public void showState()
   {  
      int state = atm.getState();
      if (state == ATM.START) {
    	  display.setText("Enter customer number\nA = OK \n");    	  
      }
      else if (state == ATM.PIN) {
    	  display.setText("Enter PIN\nA = OK");    	  
      }
      else if (state == ATM.TRANSACT) {    	  
         display.setText("Balance = " 
               + atm.getBalance() 
               + "\nEnter amount and select transaction\n"
               + "A = Withdraw\nB = Deposit\nC = Cancel");
      }
      else if(state == ATM.ERROR) {
    	  display.setText("bad account or pin \n Press A to try again");
      }
   }
   
   public void buildDisplay() {
	   display = new JTextArea(4, 20);
	   display.setEditable(false);
   }
   
   public void buildButtons() {
	   buttonPanel.setLayout(new GridLayout(5,3));
	   aButton = new JButton("  A  ");
	   aButton.addActionListener(new AButtonListener());

	   bButton = new JButton("  B  ");
	   bButton.addActionListener(new BButtonListener());

	   cButton = new JButton("  C  ");
	   cButton.addActionListener(new CButtonListener());
	   
	   zero = new JButton("" + 0);
	   zero.addActionListener(new numListenerZero());
	   
	   one = new JButton(""+ 1);
	   one.addActionListener(new numListenerOne());
	   
	   two = new JButton(""+ 2);
	   two.addActionListener(new numListenerTwo());
	   
	   three = new JButton(""+ 3);
	   three.addActionListener(new numListenerThree());
	   
	   four = new JButton(""+ 4);
	   four.addActionListener(new numListenerFour());
	   
	   five = new JButton(""+ 5);
	   five.addActionListener(new numListenerFive());
	   
	   six = new JButton(""+ 6);
	   six.addActionListener(new numListenerSix());
	   
	   seven = new JButton(""+ 7);
	   seven.addActionListener(new numListenerSeven());
	   
	   eight=new JButton(""+ 8);
	   eight.addActionListener(new numListenerEight());
	   
	   nine =  new JButton(""+ 9); 
	   nine.addActionListener(new numListenerNine());
	   	   
	   clear = new JButton("Clear");
	   clear.addActionListener(new clearListener());
	   
	   abort = new JButton("Abort");
	   abort.addActionListener(new abortListener());
	   
	   // Add components 
	   buttonPanel.add(aButton);
	   buttonPanel.add(bButton);
	   buttonPanel.add(cButton);
	   buttonPanel.add(one);
	   buttonPanel.add(two);
	   buttonPanel.add(three);
	   buttonPanel.add(four);
	   buttonPanel.add(five);
	   buttonPanel.add(six);
	   buttonPanel.add(seven);
	   buttonPanel.add(eight);
	   buttonPanel.add(nine);
	   buttonPanel.add(clear);
	   buttonPanel.add(zero);
	   buttonPanel.add(abort);
   }
   
   private class AButtonListener implements ActionListener
   {  
      public void actionPerformed(ActionEvent event)
      {  
    	  
    	 pad.setInput(0);
         int state = atm.getState();
         if (state == ATM.START) {
        	setPad();
        	if (!(pad.getValue() == 0)) { 
        		atm.setCustomerNumber((int) pad.getValue());
        	}
         }
         else if (state == ATM.PIN) {
        	setPad();
            atm.selectCustomer((int) pad.getValue());
         }
         else if (state == ATM.TRANSACT) {
        	setPad();
        	int inputValue = pad.getValue();
        	if(inputValue == 0) {
        		JOptionPane.showMessageDialog(null, "Please enter an amount");
        	}
        	else if (pad.getValue() > atm.getBalance()) {
        		JOptionPane.showMessageDialog(null, "Error, insufficent funds");
        	}
        	else {
	            atm.withdraw(pad.getValue());
	            JOptionPane.showMessageDialog(null, "Take your money");
        	}
         }
         else if(state == ATM.ERROR) {
	          	display.setText("Bad ID or Pin please try again\nA = OK");
	          	atm.setCustomerNumber((int) pad.getValue());
	          	atm.setState(0);
          }
         showState();
	  
      }
   }
   
   private class BButtonListener implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	 pad.setInput(0);
         int state = atm.getState();
         if (state == ATM.TRANSACT) {
        	setPad();
        	int inputValue = pad.getValue();
        	if(inputValue == 0) {
        		JOptionPane.showMessageDialog(null, "Please enter an amount to insert");
        	}
        	else {
	            atm.deposit(pad.getValue());
	            JOptionPane.showMessageDialog(null, "Please Insert Money");
	            display.setText("$"+ atm.getBalance());
        	}
         }
         showState();
         
      }
   }

   private class CButtonListener implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  pad.setInput(0);
         int state = atm.getState();
         if (state == ATM.ACCOUNT) {
        	 atm.reset();        	 
         }
         else if (state == ATM.TRANSACT) {
        	 atm.reset();        	 
         }
         showState();
      }
   }
   
   private class numListenerOne implements ActionListener {  
      public void actionPerformed(ActionEvent event) {   
    	  numHold.append(""+1);
    	  textfield.setText(numHold.toString());
      }
   }
   
   private class numListenerTwo implements ActionListener {  
      public void actionPerformed(ActionEvent event) {     	  
    	  numHold.append(""+2);
    	  textfield.setText(numHold.toString());
      }
   }
   
   private class numListenerThree implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  numHold.append(""+3);
    	  textfield.setText(numHold.toString());
      }
   }
   
   private class numListenerFour implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  numHold.append(""+4);
    	  textfield.setText(numHold.toString());
      }
   } 
   
   private class numListenerFive implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  numHold.append(""+5);
    	  textfield.setText(numHold.toString());
	  }
   }
   
   private class numListenerSix implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  numHold.append(""+6);
    	  textfield.setText(numHold.toString());
      }
   }
   
   private class numListenerSeven implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  numHold.append(""+7);
    	  textfield.setText(numHold.toString());
      }
   }
   
   private class numListenerEight implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  numHold.append(""+8);
    	  textfield.setText(numHold.toString());
      }
   }
   
   private class numListenerNine implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  numHold.append(""+9);
    	  textfield.setText(numHold.toString());
      }
   }
   
   private class numListenerZero implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  numHold.append(""+0);
    	  textfield.setText(numHold.toString());
      }
   }
   
   private class clearListener implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    		  numHold =new StringBuilder();
    		  textfield.setText("");
      }
   }
   
   private class abortListener implements ActionListener {  
      public void actionPerformed(ActionEvent event) {  
    	  System.exit(0);
      }
   }
   
   public void setPad() {
	   try {
 		 pad.setInput(Integer.parseInt(textfield.getText()));
 		 numHold.setLength(0);
 		 textfield.setText("");
	   }
	   catch(NumberFormatException e) {
		  JOptionPane.showMessageDialog(null, "Error, please try again");
		  showState();
      }
   }
}
