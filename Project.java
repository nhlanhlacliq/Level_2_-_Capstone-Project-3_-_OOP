//Project class
//module inports
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Implements a project object.
 */
public class Project {
  // Attributes
  private int projectNumber;
  private String projectName;
  private String buildingType;
  private String physicalAddress;
  private int ERFNumber;
  private double amountCharged;
  private double amountPaid;
  private LocalDate projectDeadline;
  private boolean finalised = false;
  private String dateCompleted = " ";
  private Scanner sc = new Scanner(System.in);
  // Person objects belonging to project
  private Customer projectCustomer;
  private Architect projectArchitect;
  private Contractor projectContractor;

  /**
   * Constructs projects created from user input.
   */
  public Project() {
  }

  /**
   * Constructs projects created from file.
   * 
   * Parses data from string array.
   * 
   * @param file string array containing project details.
   */
  public Project(String[] file) {
    this.projectNumber = Integer.parseInt(file[0]);
    this.projectName = file[1];
    this.buildingType = file[2];
    this.physicalAddress = file[3];
    this.ERFNumber = Integer.parseInt(file[4]);
    this.amountCharged = Double.parseDouble(file[5]);
    this.amountPaid = Double.parseDouble(file[6]);
    this.projectDeadline = LocalDate.parse(file[7]);
    this.finalised = Boolean.parseBoolean(file[8]);
    this.dateCompleted = file[9];
    this.projectCustomer = new Customer(file[10], file[11], file[12], file[13]);
    this.projectArchitect = new Architect(file[14], file[15], file[16], file[17]);
    this.projectContractor = new Contractor(file[18], file[19], file[20], file[21]);
  }

  /**
   * Sets project number.
   * 
   * @param projectNumber number to be assign to this project.
   */
  public void setProjectNumber(int projectNumber) {
    this.projectNumber = projectNumber;
  }

  /**
   * Returns project number.
   * 
   * @return number assigned to this project.
   */
  public int getProjectNumber() {
    return projectNumber;
  }

  /**
   * Sets project name.
   * 
   * @param projectName name to be assign to this project.
   */
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  /**
   * Returns project name.
   * 
   * @return name assigned to this project.
   */
  public String getProjectName() {
    return projectName;
  }

  /**
   * Sets building type to be assigned to this project.
   * 
   * Gets user input, validates it and assigns the building type.
   */
  public void setBuildingType() {
    System.out.println("Building type:");
    String buildType = sc.nextLine();
    while (buildType.isEmpty()) {
      System.out.println("Building type cannot be empty. Please retry");
      buildType = sc.nextLine();
    }
    buildType.strip();
    this.buildingType = buildType;
  }

  /**
   * Returns building type.
   * 
   * @return building type assigned to this project.
   */
  public String getBuildingType() {
    return buildingType;
  }

  /**
   * Sets physical address of this project.
   * 
   * Gets user input, validates it and sets the address.
   */
  public void setAddress() {
    System.out.println("Project address:");
    String address = sc.nextLine();
    while (address.isEmpty()) {
      System.out.println("Address cannot be empty. Please retry");
      address = sc.nextLine();
    }
    this.physicalAddress = address;
  }

  /**
   * Sets ERF Number.
   * 
   * @param ERFNumber number to be assign to this project.
   */
  public void setERFNum(int ERFNumber) {
    this.ERFNumber = ERFNumber;
  }

  /**
   * Sets amount charged for project.
   * 
   * Gets user input, validates it and sets amount charged.
   */
  public void setAmountCharged() {
    // call get amount method
    System.out.println("Amount Charged:");
    Double amountCharged = getAmount();
    // ensure there is an amount charged value entered
    while (amountCharged <= 1) {
      System.out.println("Nothing is that cheap unfortunately. Please retry");
      amountCharged = getAmount();
    }
    this.amountCharged = amountCharged;
  }

  /** 
   * Sets amount paid for project.
   * 
   * Gets user input, validates it and sets amount paid.
   */
  public void setAmountPaid() {
    // call get amount method
    // compare amount charged and paid
    System.out.println("Amount Paid:");
    Double amountPaid = getAmount();
    //while amount paid is more than charged, or is less than zero, ask user to retry
    while ((amountPaid > (amountCharged)) || (amountPaid < 0)) {
      //if amount paid is less than zero, show suitable error
      if (amountPaid < 0) {
        System.out.println("Amount cannot be less than 0. Please retry");
        amountPaid = getAmount();
        //else amount paid is more than charged. show suitable error
      } else {
      System.out.println("Corrupt much?");
      System.out.println("Amount paid cannot be more than what has been charged. Please retry");
      amountPaid = getAmount();
      }
    } 
    this.amountPaid = amountPaid;
  }

  /**
   * Gets amount paid to date for project
   * 
   * @return the amount paid.
   */
  public double getAmountPaid() {
    return amountPaid;
  }

  /**
   * Sets project due date.
   * 
   * @param newDate new due date provided to be assign to this project.
   */
  public void setDueDate(LocalDate newDate) {
    projectDeadline = newDate;
  }

  /**
   * Gets project due date.
   * 
   * @return due date assigned to this project.
   */
  public LocalDate getDueDate() {
    return projectDeadline;
  }

  /**
   * Gets finalised status of this project.
   * 
   * @return true or false.
   */
  public Boolean getFinalised() {
    return finalised;
  }

  /**
   * Sets <code>finalised</code> to true and sets date completed to current date.
   * 
   * If the customer still owes an amount, generate an invoice for the customer.
   * Add project to a completed project file.
   */
  public void finaliseProject() {
    finalised = true;
    dateCompleted = "" + LocalDate.now();
    System.out.println("Project: " + projectName + " completed!");

    // generate invoice - if customer still owes an amount
    Double amountOutstanding = amountCharged - amountPaid;
    if (amountOutstanding > 0) {
      // create invoice file
      String invoiceName = "invoice_" + projectCustomer.getName() + ".txt";
      try {
        FileWriter writer = new FileWriter(invoiceName);
        //create invoice content
        String content = "\t\tINVOICE\n\t\t=======\n";
        content += "\t  Poised Engineering\n\n";
        content += "Date: " + dateCompleted;
        content += "\n\nBill To:\n";
        content += projectCustomer.invoiceFormat() + "\n";
        content += "Description\t\tAmount\n==================================\n";
        content += " Service\t\t" + amountCharged + "\n";
        content += " Amount Paid\t       -" + amountPaid + "\n==================================\n";
        content += "\tTOTAL\t" + amountOutstanding + "\n\n";
        content += "Thank you for your business!";
        //write invoice content into file
        writer.write(content);
        writer.close();
        //show invoice generated message
        System.out.println("Invoice generated");
        //display error if file not found
      } catch (IOException e) {
        System.out.println("File not found\nUnable to generate invoice.");
      }
    }
    // add project to completed projects file
    try {
      //create file in append mode if it already exists
      FileWriter writer = new FileWriter("CompletedProjects.txt", true);
      //create file contents consisting of project, customer, architect and contractor details 
      String content = toString();
      content += projectCustomer;
      content += projectArchitect;
      content += projectContractor;
      content += "=============================================\n";
      //write contents onto file
      writer.write(content);
      writer.close();
      //show completion message
      System.out.println("Project added to Completed file");
      //display error if file not found
    } catch (Exception e) {
      System.out.println("File not found\nUnable to add project to file.");
    } 
  }
  
  /**
   * Validates and returns input on amounts.
   * 
   * @return validated amount.
   */
  public Double getAmount() {
    //initializing a negative value to be returned as default,
    //which code calling this method will handle appropriately
    double amount = -1;
    //get and return amount from user, clear the buffer.
    try {
      amount = sc.nextDouble();
      sc.nextLine();
      return amount;
      //display error if input amount is invalid
    } catch (InputMismatchException e) {
      sc.nextLine();
      System.out.println("Please enter a number only. e.g. not R100, but just 100");
    }
    return amount;
  }

  /** 
   * Creates this project's customer.
   */
  public void setCustomer() {
    this.projectCustomer = new Customer();
  }

  /**
   * Returns this project's customer.
   * 
   * @return project customer.
   */
  public Customer getCustomer() {
    return projectCustomer;
  } 

  /**
   * Creates this project's architect.
   */
  public void setArchitect() {
    this.projectArchitect = new Architect();
  }

  /**
   * Returns this project's architect.
   * 
   * @return project architect.
   */
  public Architect getArchitect() {
    return projectArchitect;
  }

  /**
   * Creates this project's contractor.
   */
  public void setContractor() {
    this.projectContractor = new Contractor();
  }

  /**Returns this project's contractor.
   * 
   * @return project contractor.
   */
  public Contractor getContractor() {
    return projectContractor;
  }

  /**
   * Appends this project the data file.
   * 
   * Called when program exits.
   */
  public void saveToFile() {
    try {
      //create file in append mode
      FileWriter writer = new FileWriter("projectData.txt", true);
      //create file contents consisting of project details 
      String content = projectNumber + "," + projectName + "," + buildingType + ",";
      content += physicalAddress + "," + ERFNumber + "," + amountCharged + ",";
      content += amountPaid + "," + projectDeadline + "," + finalised + "," + dateCompleted + "," ;
      //adding customer
      content += projectCustomer.getName() + "," + projectCustomer.getTelNumber() + ",";
      content += projectCustomer.getEmail() + "," + projectCustomer.getPhysicalAddress() + ",";
      //adding architect
      content += projectArchitect.getName() + "," + projectArchitect.getTelNumber() + ",";
      content += projectArchitect.getEmail() + "," + projectArchitect.getPhysicalAddress() + ",";
      //adding contractor
      content += projectContractor.getName() + "," + projectContractor.getTelNumber() + ",";
      content += projectContractor.getEmail() + "," + projectContractor.getPhysicalAddress() + "\n";
    
      //write contents onto file
      writer.write(content);
      writer.close();
      //display error if file not found
    } catch (IOException e) {
      System.out.println("Error. Unable to save - File unavailable");
    }
  }


  /**
   * Displays this project's details.
   */
  public String toString() {
    String output = "\nProject number: \t" + projectNumber + "\n";
    output += "Project name: \t\t" + projectName + "\n";
    output += "Building type: \t\t" + buildingType + "\n";
    output += "Physical address: \t" + physicalAddress + "\n";
    output += "ERF number: \t\t" + ERFNumber + "\n";
    output += "Amount charged: \t" + amountCharged + "\n";
    output += "Amount paid: \t\t" + amountPaid + "\n";
    output += "Amount owing: \t\t" + (amountCharged - amountPaid) + "\n";
    output += "Project deadline: \t" + projectDeadline + "\n";
    //display finalised and date completed if project has been completed
    if (finalised) {
      output += "Project finalised: \t" + finalised + "\n";
      output += "Date completed: \t" + dateCompleted + "\n";
    }

    return output;
  }
}