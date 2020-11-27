// Person class
import java.util.Scanner;

/**
 * Implements a Person super class for the Customer, Architect and Contractor. 
 */
public class Person {
  //instance variables
  private String name;
  private String telNumber;
  private String emailAddress;
  private String physicalAddress;
  private Scanner sc = new Scanner(System.in);

  /**
   * Constructors a person with user input.
   * 
   * Calls setter methods with internal logic for acquiring data.
   */
  public Person() {
    setName();
    setTelNumber();
    setEmail();
    setPhysicalAddress();
  }

  /**
   * Constructs a person from the data file.
   * @param name this person's name.
   * @param telNumber this person's number.
   * @param emailAddress this person's e-mail.
   * @param physicalAddress this person's address.
   */
  public Person(String name, String telNumber, String emailAddress, String physicalAddress) {
    this.name = name;
    this.telNumber = telNumber;
    this.emailAddress = emailAddress;
    this.physicalAddress = physicalAddress;
  }

  /**
   * Sets this person's name.
   * 
   * Gets user input, validates it and assigns the name.
   */
  public void setName() {
    //get input from user, making sure name isn't empty
    System.out.println("Enter name");
    String name = sc.nextLine();
    while (name.isEmpty()) {
      System.out.println("Name can't be blank. Please retry");
      name = sc.nextLine();
    }
    name.strip();
    this.name = name;
  }
  
  /**
   * Gets this person's name.
   * 
   * @return person's name.
   */
  public String getName() {
    return name;
  } 

  /**
   * Sets this person's telephone.
   * 
   * Gets user input, validates it and assigns the number.
   */
  public void setTelNumber() {
    //get input from user, making sure number has a length of atleast 10 and starts with 0
    System.out.println("Enter telephone number");
    String telNumber = sc.nextLine();
    while (((telNumber.startsWith("0")) & (telNumber.length() >= 10) & (telNumber.length() < 13)) == false) {
      System.out.println("Enter full telephone number(10-12 digits & Starts with 0)");
      telNumber = sc.nextLine();
    }
    this.telNumber = telNumber;
  }

  /**
   * Gets this person's telephone number.
   * 
   * @return person's number.
   */
  public String getTelNumber() {
    return telNumber;
  }

  /**
   * Sets this person's e-mail.
   * 
   * Gets user input, validates it and assigns the email.
   */
  public void setEmail() {
    //get input form user, making sure email has "@" and "." characters
    System.out.println("Enter e-mail address");
    String email = sc.nextLine();
    while (((email.contains("@") & (email.contains("."))) == false)) {
      System.out.println("Enter valid e-mail");
      email = sc.nextLine();
    }
    this.emailAddress = email;
  }
  
  /**
   * Gets this person's e-mail.
   * 
   * @return person's e-mail.
   */
  public String getEmail() {
    return emailAddress;
  }

  /**
   * Sets this person's address.
   * 
   * Gets user input, validates it and assigns the address.
   */
  public void setPhysicalAddress() {
    //get input form user, making sure address isn't empty
    System.out.println("Enter physical address");
    String physicalAddress = sc.nextLine();
    while (physicalAddress.isEmpty()) {
      System.out.println("Address can't be empty");
      physicalAddress = sc.nextLine();
    }
    this.physicalAddress = physicalAddress;
  }
  
  /**
   * Gets this person's address.
   * 
   * @return person's address.
   */
  public String getPhysicalAddress() {
    return physicalAddress;
  }
  
  /**
   * Returns this person's details in format for an invoice.
   * 
   * @return the person's detail string.
   */
  public String invoiceFormat() {
    String output = "Name:\t\t\t" + name + "\n";
    output += "Telephone number:\t" + telNumber + "\n";
    output += "E-mail address:\t\t" + emailAddress + "\n";
    output += "Physical address:\t" + physicalAddress + "\n";

    return output;
  }
}
