package dataframe;

public interface Applyable {
    GroupedDF apply(GroupedDF df) throws WrongOperationException;
}
