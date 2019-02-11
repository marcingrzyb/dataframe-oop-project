package dataframe;

import java.io.InvalidClassException;

public abstract class Value implements Comparable<Value>{

    Object value;

    public abstract String toString();
    public abstract Object getValue();
    public abstract Value add(Value x) throws InvalidClassException;
    public abstract Value sub(Value x) throws InvalidClassException;
    public abstract Value mul(Value x) throws InvalidClassException;
    public abstract Value div(Value x) throws InvalidClassException;
    public abstract Value pow(Value x) throws InvalidClassException;
    public abstract Value sqrt();

    public abstract boolean eq(Value x) throws InvalidClassException;
    public abstract boolean lte(Value x) throws InvalidClassException;
    public abstract boolean gte(Value x) throws InvalidClassException;
    public abstract boolean neq(Value x) throws InvalidClassException;
    public abstract boolean equals(Object other);
    public abstract int hashCode();
    public abstract <T> T create(String s);
/*
    public boolean checkNumeric(Value x){
        if(x instanceof IntValue || x instanceof DoubleValue || x instanceof FloatValue ){
            return true;
        }
        else return false;
    }
    public static ValueBuilder builder(Class<? extends Value> c) {
        return new ValueBuilder(c);
    }
    public static class ValueBuilder {
        Class<? extends Value> typ;

        ValueBuilder(Class<? extends Value> c) {
            typ = c;
        }

        public Value build(String data) {
            try {
                return (Value) typ.getMethod("create", String.class).invoke(typ.newInstance(), data);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }
    }
*/
}
