package DayCareSystem;


import java.util.InputMismatchException;

public class Staff extends Person{
    private String staffId;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) throws InputMismatchException {
        char[] chars = staffId.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c) || chars.length != 3) {
                throw new InputMismatchException("Please enter a valid ID");
            }
        }
        this.staffId = staffId;
    }

    public String getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(String skillSet) throws InputMismatchException {
        char[] chars = skillSet.toCharArray();
        for (char c : chars){
            if (Character.isDigit(c)) {
                throw new InputMismatchException("Please enter valid skill");
            }
        }
        this.skillSet = skillSet;
    }

    private String skillSet;

    public Staff(String name, Gender gender, String staffId, String skillSet) throws InputMismatchException {
        super(name, gender);
        this.staffId = staffId;
        this.skillSet = skillSet;
    }
    public  Staff(Person person, String staffId, String skillSet){
        super(person.getName(),person.getGender());
        this.staffId = staffId;
        this.skillSet = skillSet;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId='" + staffId + '\'' +
                ", skillSet='" + skillSet + '\'' +
                '}';
    }
    public String toDataString(){
        return String.format("%s;%s;%s",this.staffId,super.toDataString(),this.skillSet);
    }
}

