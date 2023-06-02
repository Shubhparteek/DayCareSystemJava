package DayCareSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DayCare {
    static Scanner input = new Scanner(System.in);
    static final String DATA_FILE = "src/DayCareSystem/childDataList.txt";
    //for staff data
    static final String DATA_FILE_STAFF = "src/DayCareSystem/staffDataList.txt";
    //group data
    static final String DATA_FILE_GROUP = "src/DayCareSystem/groupDataFile.txt";

    static ArrayList<Staff> staffDataList = new ArrayList<>();
    static ArrayList<Children> childDataList = new ArrayList<>();
    static ArrayList<Children> groupOneList = new ArrayList<>();
    static ArrayList<Children> groupTwoList = new ArrayList<>();
    static ArrayList<Children> groupThreeList = new ArrayList<>();

    public static void main(String[] args) {

        readFromFile();

        while (true) {
            try {
                System.out.println();
                System.out.println("***********DAYCARE DATA ENTRY SYSTEM**********");
                System.out.print("\nPlease choose an option to begin: \n0. Save and Exit \n1. List all children \n2. Delete a Child from Daycare " +
                        "\n3. Register a new child to the system." +
                        "\n4. Modify a child data  \n5. Arrange in age groups, view and print groups" +
                        "\n6. To add staff data \n7. To view,modify and delete staff \n");
                int userInput = input.nextInt();
                switch (userInput) {
                    case 0:
                        saveDataToFile();
                        return;
                    case 1:
                        listAllChildren();
                        break;
                    case 2:
                        deleteChild();
                        break;
                    case 3:
                        addChild();
                        break;
                    case 4:
                        modifyChild();
                        break;
                    case 5:
                        ageGroup();
                        break;
                    case 6:
                        addStaff();
                        break;
                    case 7:
                        viewModifyStaff();
                        break;
                }
            } catch (IllegalArgumentException | InputMismatchException ex) {
                System.out.println("Please enter a number between 0 and 5");
                input.next();
            }
        }
    }

    private static void viewModifyStaff() {
        if (staffDataList.isEmpty()) {
            System.out.println("There are no staff in the system please add a staff");
            return;
        }
        System.out.println("Please enter 1 to view staff, 2 to modify and 3 to delete ");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                input.nextLine();
                for (Staff staff : staffDataList) {
                    System.out.println(staff.toDataString());
                }
                break;
            case 2:
                System.out.println("Please enter staff ID to modify data");
                String inputID = input.next();
                int index;
                for (Staff staff : staffDataList) {
                    if (Objects.equals(staff.getStaffId(), inputID)) {
                        index = staffDataList.indexOf(staff);
                        Staff modifyStaff = staffDataList.get(index);
                        input.nextLine(); // discard
                        System.out.println("Please enter staff name");
                        String sName = input.nextLine();
                        modifyStaff.setName(sName);
                        System.out.println("Please enter the gender of staff");
                        Person.Gender genderS = null;
                        try {
                            String genderStaff = input.nextLine();
                            genderStaff = genderStaff.toUpperCase();
                            genderS = Person.Gender.valueOf(genderStaff);
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Please enter male,female or unspecified");
                        }
                        modifyStaff.setGender(genderS);
                        System.out.println("Please enter a skill");
                        String skillSet = input.nextLine();
                        modifyStaff.setSkillSet(skillSet);
                    }
                }
                break;
            case 3:
                System.out.println("Please enter staff ID to delete");
                String inputID2 = input.next();
                int index2;
                /*for (Staff staff : staffDataList) {
                    if (Objects.equals(staff.getStaffId(), inputID2)) {
                        //index2 = staffDataList.indexOf(staff);
                        staffDataList.remove(index2);
                    }*/
                for (int i = 0; i < staffDataList.size(); i++) {
                    if (Objects.equals(staffDataList.get(i).getStaffId(), inputID2)) {
                        index2 = staffDataList.indexOf(staffDataList.get(i));
                        staffDataList.remove(index2);
                    }
                }


        //System.out.println("3rd");
        break;
    }

}

    private static void addStaff() {
        try{
            input.nextLine(); // discard
            System.out.println("Please assign a 3 digit Staff ID");
            String idStaff = input.nextLine();

            System.out.println("Please enter staff name");
            String sName = input.nextLine();
            System.out.println("Please enter the gender of staff");
            Person.Gender genderS = null;
            try {
                String genderStaff = input.nextLine();
                genderStaff = genderStaff.toUpperCase();
                genderS = Person.Gender.valueOf(genderStaff);
            } catch (IllegalArgumentException ex) {
                System.out.println("Please enter male,female or unspecified");
            }
            System.out.println("Please enter a skill of the staff");
            String skillSet = input.nextLine();
            Staff staff= new Staff(sName,genderS,idStaff,skillSet);
            staffDataList.add(staff);
            System.out.println(staff.toDataString());
        }catch (InputMismatchException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid input");
        }
    }

    private static void ageGroup() {
        try {
            if (childDataList.isEmpty()) {
                System.out.println("There are no children in the system please add a child");
                return;
            }

            System.out.println("Please enter 1 to arrange in groups, 2 to view groups, 3 to print group to file");
            int choice= input.nextInt();

            switch (choice){
                case 1:
                    for (Children child : childDataList) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(child.getDateOfBirth());
                        double childYear = calendar.get(Calendar.YEAR);
                        double currentYear = Calendar.getInstance().get(Calendar.YEAR);

                        double age = currentYear - childYear;
                        // group1 2 years or younger;
                        // group2 2-4 years
                        // group3 4 years and older
                        if (age < 2) {
                            groupOneList.add(child);
                        }
                        else if (age > 2 && age < 4) {
                            groupTwoList.add(child);
                        }
                        else if (age>=4) {
                            groupThreeList.add(child);
                        }
                    }
                    break;
                case 2:
                    if (groupOneList.isEmpty()&&groupTwoList.isEmpty()&&groupThreeList.isEmpty()) {
                        System.out.println("Please assign children to groups");
                        return;
                    }
                    System.out.println("Group 1");
                    for (Children child : groupOneList) {
                        System.out.println(child.toDataString());
                    }
                    System.out.println("Group 2");
                    for (Children child : groupTwoList) {
                        System.out.println(child.toDataString());
                    }
                    System.out.println("Group 3");
                    for (Children child : groupThreeList) {
                        System.out.println(child.toDataString());
                    }
                    break;
                case 3:
                    if (groupOneList.isEmpty()&&groupTwoList.isEmpty()&&groupThreeList.isEmpty()) {
                        System.out.println("Please assign children to groups");
                        return;
                    }
                    try{
                        PrintWriter writer = new PrintWriter(new File(DATA_FILE_GROUP));
                        writer.print("");
                        writer.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("There is an error writing into the file");
                    }

                    if (!groupOneList.isEmpty()&&!groupTwoList.isEmpty()&&!groupThreeList.isEmpty()){
                        try (PrintWriter writer = new PrintWriter(new File(DATA_FILE_GROUP))) {
                            writer.println("Group 1 Data");
                            for (Children child : groupOneList) {
                                writer.println(child.toDataString());
                            }
                            writer.println("Group 2 Data");
                            for (Children child : groupTwoList) {
                                writer.println(child.toDataString());
                            }
                            writer.println("Group 3 Data");

                            for (Children child : groupThreeList) {
                                writer.println(child.toDataString());
                            }
                        } catch (IOException e) {
                            System.out.println("There is an error writing into the file");
                        }
                    }

                    break;
            }
            //TODO
        } catch (InputMismatchException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid input");
        }
    }


    private static void readFromFile() {
        try (Scanner fileReader = new Scanner(new File(DATA_FILE))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] dataLine = line.split(";");
                if (dataLine.length != 13) {
                    System.out.println("There has been an error in reading the file");
                    continue;
                }
                String childID = dataLine[0];
                String childName = dataLine[1];
                String childGender = dataLine[2];
                // formatting
                childGender = childGender.toUpperCase();
                Person.Gender genderC = Person.Gender.valueOf(childGender);
                String birthDate = dataLine[3];
                // Date formatting
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = dateFormat.parse(birthDate);

                String parentName = dataLine[4];
                String parentGender = dataLine[5];
                // formatting
                parentGender = parentGender.toUpperCase();
                Person.Gender genderP = Person.Gender.valueOf(parentGender);

                String relation = dataLine[6];
                // formatting
                relation = relation.toUpperCase();
                Parents.RelationShip relationShip = Parents.RelationShip.valueOf(relation);
                String phoneNum = dataLine[7];
                String streetNum = dataLine[8];
                String streetName = dataLine[9];
                String cityName = dataLine[10];
                String province = dataLine[11];
                String codePostal = dataLine[12];
                Person cPerson = new Person(childName, genderC);
                Parents parent = new Parents(parentName, genderP, phoneNum, relationShip);
                Address address = new Address(streetNum, streetName, cityName, province, codePostal);
                Children child = new Children(childID, cPerson, dateOfBirth, parent, address);
                childDataList.add(child);
            }
        } catch (IOException e) {
            System.out.println("There has been an exception in reading the child file");
        } catch (ParseException e) {
            System.out.println("Please check date format");
        }

        try (Scanner fileReader1 = new Scanner(new File(DATA_FILE_STAFF))) {
            while (fileReader1.hasNextLine()) {
                String lineStaff = fileReader1.nextLine();
                String[] dataLineStaff = lineStaff.split(";");
                if (dataLineStaff.length != 4) {
                    System.out.println("There has been an error in reading the staff file");
                    continue;
                }
                String staffID = dataLineStaff[0];
                String sName = dataLineStaff[1];
                String staffGender = dataLineStaff[2];
                // formatting
                staffGender = staffGender.toUpperCase();
                Person.Gender genderS = Person.Gender.valueOf(staffGender);
                String skillSet = dataLineStaff[3];
                Staff staff= new Staff(sName,genderS,staffID,skillSet);
                staffDataList.add(staff);
            }
        } catch (IOException e) {
            System.out.println("There has been an exception in reading the file");
        } catch (IllegalArgumentException ex){
            System.out.println("There is problem reading data");
        }
    }

    private static void saveDataToFile() {
        if (!childDataList.isEmpty()) {
            try (PrintWriter writer = new PrintWriter(new File(DATA_FILE))) {
                for (Children child : childDataList) {
                    writer.println(child.toDataString());
                }
            } catch (IOException e) {
                System.out.println("There is an error writing into the child file");
            }
        }
        if (!staffDataList.isEmpty()){
            try (PrintWriter writer1 = new PrintWriter(new File(DATA_FILE_STAFF))) {
                for (Staff staff : staffDataList) {
                    writer1.println(staff.toDataString());
                }
            } catch (IOException e) {
                System.out.println("There is an error writing into the staff file");
            }
        }
    }

    private static void listAllChildren() {
        if (childDataList.isEmpty()) {
            System.out.println("There are no children in the system please add a child");
            return;
        }
        for (Children child : childDataList) {
            System.out.println(child.toDataString());
        }
    }

    private static void deleteChild() {
        try {
            if (childDataList.isEmpty()) {
                System.out.println("There are no children in the system please add a child");
                return;
            }
            System.out.println("Please enter child ID to delete");
            String inputID = input.next();
            int index;
            /*for (Children child : childDataList) {
                if (Objects.equals(child.getChildId(), inputID)) {
                    index = childDataList.indexOf(child);
                    childDataList.remove(index);
                }*/
                for (int i = 0; i < childDataList.size(); i++) {
                    if (Objects.equals(childDataList.get(i).getChildId(), inputID)) {
                        index = childDataList.indexOf(childDataList.get(i));
                        childDataList.remove(index);
                    }
                }

        } catch (InputMismatchException e) {
            System.out.println("Please enter correct child ID");
        }
    }

    private static void modifyChild() {
        try {
            if (childDataList.isEmpty()) {
                System.out.println("There are no children in the system please add a child");
                return;
            }
            System.out.println("Please enter child ID to modify child data");
            String inputID = input.next();
            int index;
            for (Children child : childDataList) {
                if (Objects.equals(child.getChildId(), inputID)) {
                    index = childDataList.indexOf(child);
                    Children modifyChild = childDataList.get(index);
                    input.nextLine(); // discard
                    System.out.println("Please enter child name");
                    String sName = input.nextLine();

                    modifyChild.setName(sName);

                    System.out.println("Please enter the gender of child");
                    Person.Gender genderC = null;
                    try {
                        String genderChild = input.nextLine();
                        genderChild = genderChild.toUpperCase();
                        genderC = Person.Gender.valueOf(genderChild);
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Please enter male,female or unspecified");
                        return;
                    }

                    modifyChild.setGender(genderC);

                    System.out.println("Enter the date of birth");
                    String birthDate = input.next();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateOfBirth = dateFormat.parse(birthDate);

                    modifyChild.setDateOfBirth(dateOfBirth);

                    //Modify Parents info
                    System.out.println("Want need modify parent info\n1- Yes\n2- No");
                    int choiceP= input.nextInt();
                    if(choiceP==1){
                        input.nextLine();
                        System.out.println("Please enter parents name");
                        String pName = input.nextLine();
                        modifyChild.getParents().setName(pName);
                        System.out.println("Please enter the gender of parent");
                        Person.Gender genderP = null;
                        try {
                            String genderParent = input.nextLine();
                            genderParent = genderParent.toUpperCase();
                            genderP = Person.Gender.valueOf(genderParent);
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Please enter male,female or unspecified");
                            return;
                        }

                        modifyChild.getParents().setGender(genderP);

                        System.out.println("Please enter the relationship between parent and child");
                        String relation = input.nextLine();
                        relation = relation.toUpperCase();
                        Parents.RelationShip relationShip = Parents.RelationShip.valueOf(relation);
                        modifyChild.getParents().setRelationShip(relationShip);
                        System.out.println("Please enter contact number for parent");
                        String phoneNum = input.nextLine();

                        modifyChild.getParents().setPhoneNumber(phoneNum);
                    }

                    System.out.println("Want to update address info \n1- yes\n2- No");
                    int choiceA= input.nextInt();

                    if(choiceA==1){
                        input.nextLine(); // discard
                        System.out.println("Please enter the Address details" +
                                "\nPlease enter street number");
                        String streetNum = input.nextLine();
                        System.out.println("Please enter street name");
                        String streetName = input.nextLine();
                        System.out.println("Please enter city");
                        String cityName = input.nextLine();
                        System.out.println("Please enter province");
                        String province = input.nextLine();
                        System.out.println("Please enter postal code");
                        String postalCode = input.nextLine();
                        Address newAddress= new Address(streetNum,streetName,cityName,province,postalCode);
                        modifyChild.setAddress(newAddress);
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter correct info");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
        private static void addChild() {
            try {
                input.nextLine(); // discard
                System.out.println("Please assign a 3 digit student ID");
                String idChild = input.nextLine();

                System.out.println("Please enter child name");
                String sName = input.nextLine();
                System.out.println("Please enter the gender of child");
                Person.Gender genderC = null;
                try {
                    String genderChild = input.nextLine();
                    genderChild = genderChild.toUpperCase();
                    genderC = Person.Gender.valueOf(genderChild);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Please enter male,female or unspecified");
                    return;
                }
                System.out.println("Enter the date of birth");
                String birthDate = input.next();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = dateFormat.parse(birthDate);
                Person cPerson = new Person(sName, genderC);

                //Parents info
                input.nextLine();
                System.out.println("Please enter parents name");
                String pName = input.nextLine();
                System.out.println("Please enter the gender of parent");
                Person.Gender genderP = null;
                try {
                    String genderParent = input.nextLine();
                    genderParent = genderParent.toUpperCase();
                    genderP = Person.Gender.valueOf(genderParent);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Please enter male,female or unspecified");
                    return;
                }
                Person pPerson = new Person(pName, genderP);
                System.out.println("Please enter the relationship between parent and child");
                String relation = input.nextLine();
                relation = relation.toUpperCase();
                Parents.RelationShip relationShip = Parents.RelationShip.valueOf(relation);
                System.out.println("Please enter contact number for parent");
                String phoneNum = input.nextLine();
                Parents parent = new Parents(pPerson, relationShip, phoneNum);
                System.out.println("Please enter the Address details" +
                        "\nPlease enter street number");
                String streetNum = input.nextLine();
                System.out.println("Please enter street name");
                String streetName = input.nextLine();
                System.out.println("Please enter city");
                String cityName = input.nextLine();
                System.out.println("Please enter province");
                String province = input.nextLine();
                System.out.println("Please enter postal code");
                String postalCode = input.nextLine();
                Address address = new Address(streetNum, streetName, cityName, province, postalCode);
                Children child = new Children(idChild, cPerson, dateOfBirth, parent, address);
                childDataList.add(child);
                System.out.println(child.toDataString());
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid input");
            }
        }
    }

