package dataframe;

import java.util.LinkedList;

public class Min implements Applyable {
    @Override
    public GroupedDF apply(GroupedDF x) {

        LinkedList<DataFrame> done = new LinkedList<>();
        int numberOfCol = x.get(0).getcolNames().length;
        for (int i = 0; i < x.size(); i++) {
            int size = x.get(i).getcols(0).size() - 1;
            DataFrame a1 = new DataFrame(x.get(0).getcolNames(), x.get(0).getcolTypes());
            for (int j = 0; j < numberOfCol; j++) {
                if(x.isColin(j)){
                    a1.addElement(j,x.get(i).getElement(j,0));
                }
                else if(a1.getcolTypes()[j]== StringValue.class || a1.getcolTypes()[j]== DataTimeValue.class){
                    a1.addElement(j,null);
                }else {
                    DoubleValue tmp= (DoubleValue) x.get(i).getElement(j,0);

                    for (int k=0;k<x.get(i).getcols(j).size();k++){
                        if(tmp.gte( x.get(i).getElement(j,k)))
                            tmp= (DoubleValue)x.get(i).getElement(j,k);
                    }
                    a1.addElement(j,tmp);

                }
            }
            done.add(a1);
        }

        return new GroupedDF(done, x.getCol());
    }
}
