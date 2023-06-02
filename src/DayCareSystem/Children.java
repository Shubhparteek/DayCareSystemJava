package DayCareSystem;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;

public class Children extends Person{
    private String childId;
    private Date dateOfBirth;

    private Parents parents;

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Children(String name, Gender gender, String childId, Date dateOfBirth, Parents parents, Address address) throws InputMismatchException {
        super(name, gender);
        setChildId(childId);
        setDateOfBirth(dateOfBirth);
        setParents(parents);
        setAddress(address);
    }

    public Parents getParents() {
        return parents;
    }

    public void setParents(Parents parents) {
        this.parents = parents;
    }

    public Children(String childID, Person person, Date dateOfBirth, Parents parents,Address address) throws IllegalArgumentException{
        super(person.getName(),person.getGender());
           setChildId(childID);
           setDateOfBirth(dateOfBirth);
           setParents(parents);
           setAddress(address);
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) throws InputMismatchException {
        char[] chars = childId.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c) || chars.length != 3) {
                throw new InputMismatchException("Please enter a valid ID");
            }
        }
            this.childId = childId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) throws IllegalArgumentException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfBirth);
        int year = calendar.get(Calendar.YEAR);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(year<2015||year>currentYear){
            throw new IllegalArgumentException("Please enter valid date of birth.");
        }
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Children{" +
                "childId=" + childId +
                ", dateOfBirth=" + dateOfBirth +
                ", parents=" + parents +
                '}';
    }
    public String toDataString(){
        return String.format("%s;%s;%tY-%tm-%td;%s;%s",this.childId,super.toDataString(),this.dateOfBirth,this.dateOfBirth,this.dateOfBirth,this.parents.toDataString(),this.address.toDataString());
    }
}

