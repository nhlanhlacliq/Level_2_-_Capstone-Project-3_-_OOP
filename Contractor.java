//Contractor class inheriting from Person class
/**
 * Inherits Person class
 */
public class Contractor extends Person {
  //Constructor - use super constructor, print contractor created
  public Contractor() {
    super();
    System.out.println("Contractor Created!\n");
  }

  //Constructor - creating from file - use super constructor,
  public Contractor(String name, String telNumber, String emailAddress, String physicalAddress) {
    super(name, telNumber, emailAddress, physicalAddress);
  }

  //To string method
  public String toString(){
    String output = "\nCONTRACTOR:\n";
    output += "Name: \t\t\t" + super.getName() + "\n";
    output += "Telephone number: \t" + super.getTelNumber() + "\n";
    output += "E-mail address: \t" + super.getEmail() + "\n";
    output += "Physical address: \t" + super.getPhysicalAddress() + "\n";

    return output;
  }
}