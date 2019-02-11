package dataframe;

public class FloatValue extends NumericValue {
    private float value;
    private static FloatValue floatValue;

    public FloatValue(float x){
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
    public int hashCode() {
        return this.hashCode();
    }

    public static FloatValue getInstance() {
        return floatValue;
    }

    @Override
    public Value create(String s) {
        return new FloatValue(Float.parseFloat(s));
    }

    @Override
    public int compareTo(Value o) {
        return ((Float)getValue()).compareTo((Float) (o.getValue()));

    }
}
