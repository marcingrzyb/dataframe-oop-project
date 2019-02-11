package dataframe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataTimeValue extends Value implements Cloneable {
    private Date value;

    public DataTimeValue(Date x){
        value=x;
    }

    public DataTimeValue(String x){
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            value= dateParser.parse(x);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public DataTimeValue(Long x){
        value= new Date(x);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return dateParser.format(value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    public Long toLong() {
        return value.getTime();
    }

    @Override
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

    @Override
    public Value div(Value x) {
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public Value pow(Value x) {
        throw new WrongOperationException("Operation not supported");
    }

    public Value sqrt() {
        return this;

    }

    @Override
    public boolean eq(Value x) {
        if(x instanceof DataTimeValue)
            return toLong()==((DataTimeValue)x).toLong();
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public boolean lte(Value x) {
        if(x instanceof DataTimeValue)
            return toLong()<((DataTimeValue)x).toLong();
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public boolean gte(Value x) {
        if(x instanceof DataTimeValue)
            return toLong()>((DataTimeValue)x).toLong();
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public boolean neq(Value x) {
        if(x instanceof DataTimeValue)
            return toLong()!=((DataTimeValue)x).toLong();
        throw new WrongOperationException("Operation not supported");
    }

    @Override
    public boolean equals(Object other) {
        return value.hashCode()==other.hashCode();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public Value create(String s) {
        return new DataTimeValue(Long.parseLong(s));
    }

    @Override
    public int compareTo(Value o) {
        return value.compareTo(((DataTimeValue)o).value);
    }
}
