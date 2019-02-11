package dataframe;

public class WrongColumnSizeException extends RuntimeException {

    public WrongColumnSizeException(String col1, String col2) {
        super("cols: "+col1+" "+col2+" Have diffrent Size");
    }
}
