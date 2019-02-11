package dataframe;

public class DoubleValue extends NumericValue implements Cloneable {

    private static DoubleValue doubleValue;

    public DoubleValue(double x){
        value=x;
    }

    public DoubleValue(String x){
        value=Double.parseDouble(x);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }


    public DoubleValue sqrt() {
        return new DoubleValue(Math.sqrt((Double)value));
    }

    @Override
    public boolean equals(Object other) {
        return this.hashCode()==other.hashCode();
    }

    @Override
    public int hashCode() {

        return ((Double)value).hashCode();
    }

    public static DoubleValue getInstance() {
        return doubleValue;
    }

    @Override
    public Value create(String s) {
        return new DoubleValue(Double.parseDouble(s));
    }

    @Override
    public int compareTo(Value o) {
        return ((Double)getValue()).compareTo((Double)(o.getValue()));
    }
}
