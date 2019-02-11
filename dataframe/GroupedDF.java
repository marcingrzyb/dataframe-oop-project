package dataframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GroupedDF {
    LinkedList<DataFrame> groupByDataFrames;
    ArrayList<Integer> col = new ArrayList<>();

    public GroupedDF(){
        groupByDataFrames = new LinkedList<>();
    }

    public GroupedDF(LinkedList<DataFrame> tmp, ArrayList<Integer> col_){
        groupByDataFrames = new LinkedList<>();
        groupByDataFrames = tmp;
        col=col_;
    }

    public GroupedDF(HashMap<Integer,DataFrame> tmp, ArrayList<Integer> col_){
        groupByDataFrames = new LinkedList<>();
        for (Map.Entry<Integer, DataFrame> entry : tmp.entrySet()) {
            groupByDataFrames.add(tmp.get(entry.getKey()));
        }
        col=col_;
    }

    public void add(DataFrame x){
        groupByDataFrames.add(x);
    }

    public DataFrame get(int j){
        return groupByDataFrames.get(j);
    }

    public ArrayList<Integer> getCol() {
        return col;
    }

    public int size(){
        return groupByDataFrames.size();
    }


    public GroupedDF apply(Applyable x) {
        return x.apply(this);
    }

    @Override
    public String toString() {   //randomly crashes toFIX
        StringBuffer tmp=new StringBuffer();
        for( DataFrame x:groupByDataFrames){
            tmp.append(x.toString()+"\n");
        }
        return tmp.toString();
    }
    public boolean isColin(Integer i){
        for (Integer x:col){
            if(x==i)
                return true;
        }
        return false;
    }
}
