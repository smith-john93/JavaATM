package atm;

//Keypad.java
//Represents the keypad of the ATM

public class Keypad 
{
	private int value;
	// no-argument constructor initializes the Scanner
	
	// return an integer value entered by user
	public int getInput()  
	{
	   return value;
	} // end method getInput
	
	public void  setInput(int value)
	{
		this.value =  value;
	}
	
	public int getValue()
	{
		return value;
	}
} // end class Keypad