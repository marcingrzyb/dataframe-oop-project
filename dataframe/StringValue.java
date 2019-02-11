package dataframe;

import java.io.InvalidClassException;

public class StringValue extends Value implements Cloneable {

    private String value;

    private static StringValue stringValue;

    public StringValue(String x){
        value=x;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    public Value add(Value x) {
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public Value sub(Value x) {
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public Value mul(Value x) {
        throw new WrongOperationException("Operation not supported");
    }

    public Value sqrt() {
        return this;
    }

    @Override
    public Value div(Value x) {
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public Value pow(Value x) {
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public boolean eq(Value x) throws InvalidClassException {
        if(x instanceof StringValue)
            return value==x.toString();
        throw new  InvalidClassException("Comp error : "+x.toString());
    }

    @Override
    public boolean lte(Value x) throws InvalidClassException {
            throw new WrongOperationException("Operation not supported");
    }

    @Override
    public boolean gte(Value x) {
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public boolean neq(Value x) {
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public boolean equals(Object other) {
        return this.hashCode()==other.hashCode();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public static StringValue getInstance() {
        return stringValue;
    }

    @Override
    public StringValue create(String s) {
        return new StringValue(s);
    }


    @Override
    public int compareTo(Value o) {
        return value.compareTo(((StringValue)o).value);
    }
}
