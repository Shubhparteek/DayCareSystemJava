package DayCareSystem;

import java.util.InputMismatchException;
public class Address {
    private String streetNumber;
    private String streetName;
    private String cityName;
    private String province;
    private String codePostal;

       public Address(String streetNumber, String streetName, String cityName, String province, String codePostal) throws InputMismatchException {
        setStreetName(streetName);
        setStreetNumber(streetNumber);
        setCityName(cityName);
        setProvince(province);
        setCodePostal(codePostal);
    }

    public void setStreetNumber(String streetNumber) throws InputMismatchException{
        char[] chars = streetNumber.toCharArray();
        for (char c : chars){
            if (!Character.isDigit(c)) {
                throw new InputMismatchException("Please enter valid street Number");
            }
        }
        this.streetNumber = streetNumber;
    }

    public void setStreetName(String streetName) throws InputMismatchException{
        char[] chars = streetName.toCharArray();
         if (chars.length < 2) {
                throw new InputMismatchException("Please enter a valid street name with a minimum of 2 characters.");
            }
         this.streetName = streetName;

    }
    private void setCodePostal(String codePostal) throws InputMismatchException{
        char[] chars = codePostal.toCharArray();

            if (chars.length != 6) {
                throw new InputMismatchException("Please enter a valid canadian postal code eg: H3H2P1.");
            }
            // checks is 2 character is a not a digit.
            if (!Character.isLetter(chars[0])||!Character.isLetter(chars[2])||!Character.isLetter(chars[4])){
                throw new InputMismatchException("Please enter a valid canadian postal code eg: H3H2P1.");
             }

            if (!Character.isDigit(chars[1])||!Character.isDigit(chars[3])||!Character.isDigit(chars[5])){
                throw new InputMismatchException("Please enter a valid canadian postal code eg: H3H2P1.");
            }
            this.codePostal = codePostal;
    }
    private void setProvince(String province) throws InputMismatchException {
        char[] chars = province.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                throw new InputMismatchException("Please enter valid province.");
            }
        }
        this.province=province;
    }

    private void setCityName(String cityName) throws InputMismatchException {
        char[] chars = cityName.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                throw new InputMismatchException("Please enter valid city.");
            }
        }
        this.cityName=cityName;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetNumber='" + streetNumber + '\'' +
                ", streetName='" + streetName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", province='" + province + '\'' +
                ", codePostal='" + codePostal + '\'' +
                '}';
    }
    public String toDataString(){
           return String.format("%s;%s;%s;%s;%s",this.streetNumber,this.streetName,this.cityName,this.province,this.codePostal);
    }
}
