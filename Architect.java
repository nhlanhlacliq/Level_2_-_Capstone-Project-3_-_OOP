//Architect class inheriting from Person
/**
 * Inherits Person class
 */
public class Architect extends Person {
  //Constructor - use super constructor, print architect created
  public Architect() {
    super();
    System.out.println("Architect Created!\n");
  }

  //Constructor - creating from file - use super constructor,
  public Architect(String name, String telNumber, String emailAddress, String physicalAddress) {
    super(name, telNumber, emailAddress, physicalAddress);
  }

  //To string method
  public String toString(){
    String output = "\nARCHITECT:\n";
    output += "Name: \t\t\t" + super.getName() + "\n";
    output += "Telephone number: \t" + super.getTelNumber() + "\n";
    output += "E-mail address: \t" + super.getEmail() + "\n";
    output += "Physical address: \t" + super.getPhysicalAddress() + "\n";

    return output;
  }
}
