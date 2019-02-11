package dataframe;

import java.util.LinkedList;

public class AvgThread implements Applyable {
    @Override
    public GroupedDF apply(GroupedDF x) {
        LinkedList<Thread> t = new LinkedList<>();
        LinkedList<DataFrame> done = new LinkedList<>();
        int numberOfCol = x.get(0).getcolNames().length;

        for (int i = 0; i < x.size(); i++) {
            int finalI = i;

            t.add(i,new Thread(new Runnable(){
                @Override
                public void run() {
                DataFrame a1 = new DataFrame(x.get(0).getcolNames(), x.get(0).getcolTypes());


                for (int j = 0; j < numberOfCol; j++) {
                    if (x.isColin(j)) {
                    a1.addElement(j, x.get(finalI).getElement(j, 0));
                } else if (a1.getcolTypes()[j] == StringValue.class || a1.getcolTypes()[j] == DataTimeValue.class) {
                    a1.addElement(j, null);
                } else {
                    DoubleValue tmp = new DoubleValue(0);

                    for (int k = 0; k < x.get(finalI).getcols(j).size(); k++) {

                        tmp = (DoubleValue) tmp.add(x.get(finalI).getElement(j, k));
                    }
                    a1.addElement(j, tmp.div(new IntValue(x.get(finalI).getcols(j).size())));
                }
            }
                    done.add(a1);

                }
            }));
            t.get(i).start();
        }

        for (int i = 0; i < x.size(); i++) {
            try {
                t.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return new GroupedDF(done, x.getCol());
    }
}