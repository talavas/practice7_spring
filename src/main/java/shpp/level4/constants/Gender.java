package shpp.level4.constants;
public enum Gender {
    MALE( "M"),
    FEMALE( "F");

    private final String code;

    Gender(String code) {
        this.code = code;
    }

    public static Gender fromCode(String code) {
        for (Gender gender : Gender.values()) {
            if (gender.code.equals(code.toUpperCase()) ||
            code.equals(gender.toString())) {
                return gender;
            }
        }

       throw new IllegalArgumentException("Can't convert code= " + code + " into Gender. Legal symbol M/F/m/f");
    }

    public static Gender fromTaxDigit(int code){
        if(code % 2 == 0){
            return FEMALE;
        }else{
            return MALE;
        }
    }

    public String getCode() {
        return code;
    }
}
