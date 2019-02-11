package dataframe;

import java.io.InvalidClassException;

public abstract class NumericValue extends Value {

    @Override
    public Object getValue(){
        return value;
    }
    @Override
    public boolean eq(Value v) throws InvalidClassException {
        if(!(v instanceof NumericValue))
            throw new  InvalidClassException("Comp error: "+v.toString());
        else
            return value==((NumericValue) v).getValue();
    }

    @Override
    public boolean neq(Value v) throws InvalidClassException {
        return !eq(v);
    }

    @Override
    public boolean equals(Object v) {
        if (!(v instanceof NumericValue))
            return false;
        else
            return getValue().equals(((NumericValue) v).getValue());
    }

    @Override
    public boolean lte(Value v) {
        if (!(v instanceof NumericValue))
            throw new UnsupportedOperationException();
        else
            return (Double)getValue() <=  (Double)v.getValue();
    }

    @Override
    public boolean gte(Value v) {
        if (!(v instanceof NumericValue))
            throw new UnsupportedOperationException();

        return (Double)getValue() >=  (Double)v.getValue();
    }

    @Override
    public DoubleValue add(Value v) throws UnsupportedOperationException{
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();
        //System.out.println(value);
        return new DoubleValue((Double)value+ (Double)v.getValue() );
    }
    @Override
    public DoubleValue sub(Value v) throws UnsupportedOperationException{
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();

        return new DoubleValue((Double)value - (Double)v.getValue());
    }
    @Override
    public DoubleValue mul(Value v) throws UnsupportedOperationException{
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();

        return new DoubleValue((Double)value * (Double)v.getValue());
    }
    @Override
    public DoubleValue div(Value v) throws UnsupportedOperationException{
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();
        return new DoubleValue((Double)value / Double.parseDouble(v.toString()));
    }
    @Override
    public DoubleValue pow(Value v) throws UnsupportedOperationException{
        if(! (v instanceof NumericValue))
            throw new UnsupportedOperationException();

        return new DoubleValue(Math.pow((Double)value,Double.parseDouble(v.toString())));
    }


}
