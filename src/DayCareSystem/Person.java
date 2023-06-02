package DayCareSystem;

import java.util.InputMismatchException;

public class Person {
    private String name;

   // private String gender;

    public Gender getGender() {
        return gender;
    }
    private  Gender gender;

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    enum Gender {
        MALE,
        FEMALE,
        UNSPECIFIED
    }

    private String phoneNumber;

    public Person(String name, Gender gender) throws InputMismatchException {
        setName(name);
        this.gender = gender;
      }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InputMismatchException {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                throw new InputMismatchException("Please enter valid name.");
            }
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender=" + gender + '\'' +
                '}';
    }
    public String toDataString(){
        return String.format("%s;%s",this.name,this.gender);
    }
}
