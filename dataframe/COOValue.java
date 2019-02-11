package dataframe;

public class COOValue extends Value {
    private final int index;
    private final Value a;

    public COOValue(int index_,Value a_){
        index=index_;
        a=a_;
    }

    public int getIndex() {
        return index;
    }

    public Value getA() {
        return a;
    }

    @Override
    public String toString() {
        return "index "+index+" value "+a;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Value add(Value x) {
        return null;
    }

    @Override
    public Value sub(Value x) {
        return null;
    }

    @Override
    public Value mul(Value x) {
        return null;
    }

    @Override
    public Value div(Value x) {
        return null;
    }

    @Override
    public Value pow(Value x) {
        return null;
    }

    public Value sqrt() { return this; }

    @Override
    public boolean eq(Value x) {
        return false;
    }

    @Override
    public boolean lte(Value x) {
        return false;
    }

    @Override
    public boolean gte(Value x) {
        return false;
    }

    @Override
    public boolean neq(Value x) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public Value create(String s) {
        return null;
    }

    @Override
    public int compareTo(Value o) {
        return 0;    }
}
