//Capstone Project II - Refactoring

//module imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class implements a project management system for a faux
 * structural engineering firm, "Poised"
 * @author Nhlanhla
 * @version 1.0
 */
public class Main {
  //global project counter
  public static int projectNumber;
  //initialize input scanner object
  public static Scanner sc = new Scanner(System.in);
  //initialize ArrayList for project objects
  static ArrayList<Project> projectList = new ArrayList<Project>();

  
  /**
   * Display main menu
   */
  public static void displayMenu() {
    //print menu options - create or view projects
    //use "project" if there is only one, else use "projects"
    System.out.println("\nMenu (Enter '-1' to exit)\n===================");
    if (projectNumber == 1) {
      System.out.println("(" + projectNumber + " Project available)");
    } else {
      System.out.println("(" + projectNumber + " Projects available)");
    }
    System.out.println("1. Create new project\n2. View projects");        
    
    //call getInteger method, which validates user input internally and returns a number option
    int user_option = getInteger();
    //call method depending on user option
    if (user_option == -1) {
      //save projects to file only if there are projects available
      if ((projectList.isEmpty()) == false) {
        saveProjects();
      }
      System.out.println("Goodbye");
      System.exit(0);
      
      //if create project selected - call create project method
    } else if (user_option == 1) {
      createProject();
      
      //if view project selected -  view current projects, if there are projects created
    } else if (user_option == 2) {
      if (projectNumber == 0) {
        System.out.println("No projects available to view");
        displayMenu();
      }
      viewAllProjects();

      //display error if user chooses a wrong number - display menu again
    } else {
      System.out.println("Please enter a valid number");
      displayMenu();
    }
  }
      
  /**
   * View all Projects
   *
   * Lists all projects in <code>projectList</code>,
   * giving options to search for particular projects as well as
   * listing incomplete / overdue projects.
   */
  public static void viewAllProjects() {
    //print all available projects
    String displayProjects = "\nAll Projects\nProject Number\tProject Name\n";
    for (Project project: projectList) {
      //show completed next to finalised projects
      if (project.getFinalised()) {
        displayProjects += project.getProjectNumber()+".(Completed)\t"+project.getProjectName()+"\n";
      } else {
      displayProjects += project.getProjectNumber()+".\t\t"+project.getProjectName()+"\n";
      }
    }
    System.out.println(displayProjects);

    //present options for finding specific project and seeing incomplete or overdue projects
    System.out.println("1. Select project using number \n2. Select project using name (Case & space sensitive)"); 
    System.out.println("3. List incomplete projects \n4. List overdue projects \n5. Main menu");

    //get and validate user option
    int user_option = getInteger();

    //find project using number
    if (user_option == 1) {
      System.out.println("Enter project number");
      int searchNumber = getInteger();
      findProjectNumber(searchNumber);
      //find project using name
    } else if (user_option == 2) {
      System.out.println("Enter project name");
      String searchName = sc.nextLine();
      findProjectName(searchName);
      //list incomplete projects 
    } else if (user_option == 3) {
      viewIncompleteProjects();
      //list overdue projects
    } else if (user_option == 4) {
      viewOverdueProjects();
      //go back to main menu
    } else if (user_option == 5) {
      displayMenu();
    }
    //view all projects again should methods above fail or user input incorrect
    viewAllProjects();
  }

  /**
   * Views the selected project
   * 
   * Prints out project's details, then provides
   * options to change the details of the project, 
   * the customer, the architect or the contractor,
   * or finalise the project.
   * 
   * @param project project being viewed and updated.
   */
  public static void viewSelectedProject(Project project) {
    System.out.println(project);
    //present user with options to change project details
    System.out.println("1. Update due date \n2. Update amount paid \n3. View/Update Customer");
    System.out.println("4. View/Update Architect \n5. View/Update Contractor \n6. Finalise project");
    System.out.println("7. Main menu");
    //get user option
    int user_option = getInteger();
    
    //from user input, call method to change necessary detail in project
    //if 1, change due date
    if (user_option == 1) {
      System.out.println("Old due date :" + project.getDueDate());
      LocalDate newDate = getDate();
      project.setDueDate(newDate);
      //return to view project
      viewSelectedProject(project);
      
      //if 2, change amount paid to date
    } else if (user_option == 2) {
      System.out.println("Old amount :" + project.getAmountPaid());
      project.setAmountPaid();
      //return to view project
      viewSelectedProject(project);
      
      //If 3, view customer details
    } else if (user_option == 3) { 
      viewPerson(project.getCustomer());
  
      //If 4, view architect details    
    } else if (user_option == 4) {
      viewPerson(project.getArchitect());

      //If 5, view contractor details   
    } else if (user_option == 5) {
      viewPerson(project.getContractor());
    
      //if 6, Finalise project
    } else if (user_option == 6) {
      finaliseProject(project);

      //if 7, go back to main menu
    } else if (user_option == 7) {
      displayMenu();
    
      //view project again if user choice is incorrect
    } else {
      System.out.println("Incorrect input. Please try again");
      viewSelectedProject(project);
    }
  }

  /**
   * Searches for a project number
   * 
   * Finds a matching project number and views that project, if found.
   *
   * @param searchNumber number used for finding a project
   */
  public static void findProjectNumber(int searchNumber) {
    //check every project, if search number is equal to project number, display that project
    for (Project project: projectList) {
      if (searchNumber == project.getProjectNumber()){
        System.out.println("Project Found!");
        viewSelectedProject(project); 
      }
    } //else view all projects
    System.out.println("Project not found. (Press Enter to go back)");
    sc.nextLine();
  }

  /**
   * Searches for a project name
   * 
   * Finds a matching project name and views that project, if found.
   *
   * @param searchName name used for finding a project
   */
  public static void findProjectName(String searchName) {
    //check every project, if search name is equal to project name, display that project
    for (Project project: projectList) {
      if (searchName.equals(project.getProjectName())){
        System.out.println("Project Found!");
        viewSelectedProject(project); 
      }
    } //else go back to view all projects
    System.out.println("Project not found. (Press Enter to go back)");
    sc.nextLine();
  }

  /**
   * Lists incomplete projects
   * 
   * Checks <code>finalised</code> value of project and lists it if false.
   */
  public static void viewIncompleteProjects() {
    String displayProjects = "\nIncomplete projects\nProject Number\tProject Name\n";
    //initialize incomplete project count
    int incompleteCount = 0;
    //check every project, if project finalised value is false, display that project, increment incomplete
    for (Project project: projectList) {
      if ((project.getFinalised()) == false) {
        displayProjects += project.getProjectNumber() + ".\t\t"+project.getProjectName();
        displayProjects += "\t(Complete: " + project.getFinalised() + ")\n";
        incompleteCount ++;
      }
    }
    System.out.println(displayProjects);
    //show count of incomplete projects
    if (incompleteCount == 1) {
      System.out.println("(" + incompleteCount + " project incomplete.)");
    } else {
      System.out.println("(" + incompleteCount + " projects incomplete.)");
    }
    //return to view projects
    System.out.println("Press enter to return to view projects and update them if needed.");
    sc.nextLine();
  }

  /**
   * Lists overdue projects, if they are also not completed.
   * 
   * Compares current date and due date of the project and lists in,
   * provided that <code>finalised</code> is also false.
   */
  public static void viewOverdueProjects() {
    String displayProjects = "\nOverdue projects\nProject Number\tProject Name\n";
    //initialize current date and number of overdue projects
    LocalDate currentDate = LocalDate.now();
    int overdueCount = 0;
    //check every project, if current date is past project date and project incomplete,
    //display that project
    for (Project project: projectList) {
      if ((currentDate.isAfter(project.getDueDate())) & (project.getFinalised() == false)) {
        displayProjects += project.getProjectNumber()+".\t\t"+project.getProjectName();
        //get and display overdue days
        long daysOverdue = ChronoUnit.DAYS.between(project.getDueDate(), currentDate);
        displayProjects += "\t(Days overdue: " + daysOverdue + ")\n";
        //increment overdue days
        overdueCount ++;
      }
    }
    System.out.println(displayProjects);
    //show count of overdue projects
    if (overdueCount == 1) {
      System.out.println("(" + overdueCount + " project overdue.)");
    } else {
      System.out.println("(" + overdueCount + " projects overdue.)");
    }
    //return to view projects
    System.out.println("Press enter to return to view projects and update them if needed.");
    sc.nextLine();
  }
  
  /**
   * Displays details of the person(customer, architect or contractor).
   * 
   * Also gives options to change person's details.
   * 
   * @param person person(customer, architect or contractor) being viewed.
   */
  public static void viewPerson(Person person) {
    System.out.println(person);
    //present options to user on which details to change
    System.out.println("1. Change name \n2. Change telephone number \n3. Change e-mail address");
    System.out.println("4. Change physical address \n5. View projects");
    
    //get user option
    int user_option = getInteger();
    
    //depending on user choice
    //If 1, change person's name
    if (user_option == 1) {
      person.setName();
      
      //If 2, change telephone number
    } else if (user_option == 2) {
      person.setTelNumber();
      
      //If 3, change email address
    } else if (user_option == 3) {
      person.setEmail();
      
      //If 4, change physical address
    } else if (user_option == 4) {
      person.setPhysicalAddress();
      
      //If 5, go back to view project
    } else if (user_option == 5) {
      viewAllProjects();
      //else view person again if input incorrect
    } else {
      System.out.println("Incorrect input. Please try again");
      viewPerson(person);
    }
    //view person again after changing details
    viewPerson(person);
  }

  /**
   * Marks project as finalised.
   * 
   * @param project project to be finalised
   */
  public static void finaliseProject(Project project) {
    //check if project isn't finalised already
    if (project.getFinalised() == false) {
      project.finaliseProject();
    } else {
      System.out.println("Project complete already.");
    }
    System.out.println("Press Enter to return to project");
    sc.nextLine();
    viewSelectedProject(project);
  }
  
  /**
   * Creates a project object.
   */
  public static void createProject() {
    //Create project object
    Project project = new Project();
    
    //increment project counter and assign it to project
    projectNumber += 1;
    project.setProjectNumber(projectNumber);

    //the set methods with no arguments have self containted logic for retrieving the required values
    //Get and assign project name - can be skipped, so the program can auto generate a name
    System.out.println("Project name: (This can be skipped)");
    String projectName = sc.nextLine();
    project.setProjectName(projectName);
    
    //assign building type and physical address
    project.setBuildingType();
    project.setAddress();

    //assign ERF Number - call get integer method
    //ERF Number cannot be 0 or too long
    System.out.println("Project ERF Number:");
    int eRFNum = getInteger();
    while (eRFNum <= 0) {
      System.out.println("ERF Number cannot be 0(or too long). Please retry");
      eRFNum = getInteger();
    }
    project.setERFNum(eRFNum);

    //assign amounts charged and paid
    project.setAmountCharged(); 
    project.setAmountPaid();
    
    //call get date method then assign due date
    LocalDate dueDate = getDate();
    project.setDueDate(dueDate);
    
    //create project customer, architect and contractor
    System.out.println("\nCreating Customer");
    project.setCustomer();
    System.out.println("Creating Architect");
    project.setArchitect();
    System.out.println("Creating Contractor");
    project.setContractor();

    //If project name was skipped, set project name to building type + customer Name
    if (projectName.isEmpty()) {
      projectName = project.getBuildingType() +" "+ project.getCustomer().getName();
      project.setProjectName(projectName);
    }
    
    //add project to list
    projectList.add(project);
    //return to menu
    displayMenu();
  }

  /**
   * Create a date object
   * 
   * Takes in input and parses it into a date object,
   * defaulting to the current date if date format provided is incorrect. 
   * 
   * @return the date object
   */
  public static LocalDate getDate() {
    //set default due date to current date
    LocalDate deadlineDate = LocalDate.now();
    String dateString;
    
    //try get due date from user, return that due date
    try {
      System.out.println("Project deadline: (yyyy-mm-dd)");
      dateString = sc.nextLine();  
      deadlineDate = LocalDate.parse(dateString);
      return deadlineDate;
    
    //inform user of incorrect format of date entered
    } catch (DateTimeParseException e) {
      System.out.println("Incorrect date format. Due date defaulted to current date. Please change it if this is not desired.");
    }
    //return default due date
    return deadlineDate;
  }

  /**
   * Validates input to ensure an integer is returned.
   * 
   * @return an integer.
   */
  public static Integer getInteger() {
    int integer = 0;
    //get and return number from user, clear the buffer.
    try {
      integer = sc.nextInt();
      sc.nextLine();
      return integer;
      //display error if user input is invalid
    } catch (InputMismatchException e) {
      sc.nextLine();
      System.out.println("Please enter a number e.g. '1'");
    }
    return integer;
  }

  /**
   * Creates projects from project data line
   * 
   * Splits data line into a string array used to create a project,
   * then add project onto the projectList.
   * 
   * @param line line of text containing project details
   */
  public static void createProjectFromFile(String line) {
    //increment project number
    projectNumber ++;
    //split project file line into a string array
    String[] fileContent = line.split(",");
    //pass array onto project constructor that will parse the data accordingly
    Project project = new Project(fileContent);
    //add project into project list
    projectList.add(project); 
  }

  /**
   * Writes existing projects to a file.
   */
  public static void saveProjects() {
    //create file so project objects can append to it
    try {
      FileWriter writer = new FileWriter("projectData.txt");
      for (Project project : projectList) {
        project.saveToFile();
      }
      writer.close();
      System.out.println("Projects saved");
    // display error if file not found
    } catch (IOException e) {
      System.out.println("Error. Unable to save - file unavailable");
    }
  }

  /**
   * Loads existing projects from file.
   * 
   * Reads each line in saved data file and passes it to the 
   * <code>createProjectFromFile</code> method. 
   */
  public static void loadProjects() {
    //create file object to load from
    try {
      File projectData = new File("projectData.txt");
      Scanner fileScan = new Scanner(projectData);
      //save file line into a variable and call create project from file method with that line
      while (fileScan.hasNextLine()) {
        String content = fileScan.nextLine();
        createProjectFromFile(content);
      }
      fileScan.close();
      System.out.println("Projects loaded\n");
      
    // display errors
    } catch (NullPointerException e) {
      System.out.println("Error: " + e.getMessage());
    } catch (FileNotFoundException e) {
      System.out.println("No existing projects found\n");
    }
  }


  /**
   * Main method
   * 
   * Loads projects, displays welcome screen, then calls display menu method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    //load existing projects from file
    loadProjects();

    //Welcome screen.
    System.out.println("Poised Engineering\n==============");
    
    //prompt user to press Enter to begin 
    System.out.println("Press enter");        
    sc.nextLine();
  
    //open main menu
    displayMenu();
  }
}