package dataframe;

public class IntValue extends NumericValue implements Cloneable {
    private int value;
    private static IntValue intValue;

    public IntValue(int x){
        value=x;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Value sqrt() {
        return null;
    }


    @Override
    public boolean equals(Object other) {
        return this.hashCode()==other.hashCode();
    }

    @Override
    public int hashCode() {
        return ((Integer)value).hashCode();
    }

    public static IntValue getInstance() {
        return intValue;
    }

    @Override
    public IntValue create(String s) {
        return new IntValue(Integer.parseInt(s));
    }


    @Override
    public int compareTo(Value o) {
        return ((Integer)getValue()).compareTo((Integer) (o.getValue()));

    }
}
