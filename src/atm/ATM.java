package atm;

//ATM.java
//Represents an automated teller machine

public class ATM {
  private boolean userAuthenticated; // whether user is authenticated
  private int currentAccountNumber; // current user's account number
  private BankDatabase bankDatabase; // account information database
  private int accountNumber;

  // constants corresponding to main menu options

	public static final int START = 0;
	public static final int PIN = 2;
	public static final int ACCOUNT = 3;
	public static final int TRANSACT = 4;
	public static final int ERROR = 7;
	public int state = 0;

  // no-argument ATM constructor initializes instance variables
  public ATM() {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      bankDatabase = new BankDatabase(); // create acct info database
  } // end no-argument ATM constructor


	public int getState() {
		return state;
	}
	
	
	public double getBalance() {
		return bankDatabase.getTotalBalance( currentAccountNumber );
	}
	
	public void setCustomerNumber(int value) {
			accountNumber = value;
			state = 2;
	}
	public void setState(int value) {
			state = value;
	}
	
	public void selectCustomer(int value) {
		int pin = value;
		// set userAuthenticated to boolean value returned by database
	 userAuthenticated = bankDatabase.authenticateUser( accountNumber, pin );
	
	 // check whether authentication succeeded
	 if ( userAuthenticated ) {
	    currentAccountNumber = accountNumber; // save user's account #
	    state = 4;
	 } // end if
	 else 
	 	state = ERROR;
	}
	
	
	
	public void withdraw(int value) {
		bankDatabase.debit( currentAccountNumber, value );
	}
	
	public void deposit(int value) {
		bankDatabase.credit( currentAccountNumber, value );
	}
	
	public void reset() {
		userAuthenticated = false;
		state = 0;
	}

} // end class ATM