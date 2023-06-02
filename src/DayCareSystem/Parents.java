package DayCareSystem;

import java.util.InputMismatchException;

public class Parents extends Person{
    private  RelationShip relationShip;

    public void setRelationShip(RelationShip relationShip) {
        this.relationShip = relationShip;
    }

    enum RelationShip {
        FATHER,
        MOTHER,
        OTHER
    }

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws InputMismatchException {
        char[] chars = phoneNumber.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c) || chars.length != 10) {
                throw new InputMismatchException("Please enter a 10 digits phone number.");
            }
        }
        this.phoneNumber = phoneNumber;
    }

    public RelationShip getRelationShip() {
        return relationShip;
    }



    public Parents(String name, Gender gender, String phoneNumber, RelationShip relationShip) throws InputMismatchException {
        super(name, gender);
        this.relationShip = relationShip;
        setPhoneNumber(phoneNumber);
    }

    public Parents(Person person, RelationShip relationShip,String phoneNumber) throws InputMismatchException {
        super(person.getName(),person.getGender());
        this.relationShip = relationShip;
        setPhoneNumber(phoneNumber);
    }

    @Override
    public String toString() {
        return "Parents{" +
                "relationShip=" + relationShip +
                '}';
    }
    public String toDataString(){
        return String.format("%s;%s;%s",super.toDataString(),this.relationShip,this.phoneNumber);
    }
}
