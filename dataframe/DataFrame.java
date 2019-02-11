package dataframe;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DataFrame {
    private String [] colNames;
    private Class<? extends Value>[] colTypes;
    private ArrayList<ArrayList<Value>> cols=new ArrayList<>();

    DataFrame(String [] x,Class<? extends Value>[] y) {
        if(x.length==y.length){
            colNames = x;
            colTypes = y;
            for(int i=0;i<x.length;i++){
                ArrayList<Value> single = new ArrayList<>();
                cols.add(single);
            }
        }
    }
    
    public DataFrame(String filepath, Class<? extends Value>[] colTypes_, boolean header ) throws NoSuchMethodException {
        colTypes=colTypes_;
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        List<Constructor<? extends Value>> constructors = new ArrayList<>(colTypes.length);
        for (int i = 0; i < colTypes.length; i++) {
            constructors.add(colTypes[i].getConstructor(String.class));
        }
        int i=0;
        try {
            initiate(header, br, constructors, i);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initiate(boolean header, BufferedReader br, List<Constructor<? extends Value>> constructors, int i) throws Exception, InstantiationException, IllegalAccessException {
        String strLine;
        while (true){
            if((strLine=br.readLine())==null ){break;}
            if((header==true) && i==0){
                colNames = strLine.split(",");
                i++;
                for(int j=0;j<colNames.length;j++){
                    ArrayList<Value> single = new ArrayList<>();
                    cols.add(single);
                }
            }else{
                String[] linia=strLine.split(",");
                for(int j=0;j<colTypes.length;j++){
                    try {
                        addElement(j,constructors.get(j).newInstance(linia[j]));
                    }
                    catch (InvocationTargetException e){
                        throw new IllegalArgumentException(String.valueOf(new String[]{this.colNames[j]}));
                    }
                }
            }
        }
    }


    public String[] getcolNames() {
        return colNames;
    }

    public Class<? extends Value>[] getcolTypes() {
        return colTypes;
    }

    public Integer size(){
        ArrayList a = cols.get(0);
        return a.size();
    }

    public ArrayList<Value> get(String name) {
        int i;
        for(i=0;i<colNames.length;i++){
            if(name==colNames[i]){
                break;
            }
        }
        return cols.get(i);
    }

    public void setCol(ArrayList <Value> x,int i){
        cols.set(i-1,x);
    }

    public ArrayList<Value> getcols(int i) {
        return cols.get(i);
    }

    public void addElement(int i,Value x){
        (cols.get(i)).add(x);
    }

    private void addDataFrame(DataFrame x){
        for (int i=0;i<colTypes.length;i++){
            addElement(i,x.getElement(i,0));
        }
    }


    public Value getElement(int i,int j){
        return (Value) ((ArrayList)cols.get(i)).get(j);
    }



    public DataFrame get(String [] columns, boolean copy) {
        DataFrame tmp;
        if(copy == false){
            tmp = new DataFrame(columns,colTypes);
            for(int i=0;i<colTypes.length;i++){
                tmp.setCol(getcols(i+1),i);
            }
        }
        else
        {
            tmp = new DataFrame(columns,colTypes);
            for(int i=0;i<colTypes.length;i++){
                for (int j=0;j<getcols(i).size();j++){
                    tmp.addElement(i,getElement(i,j));
                }
            }
        }
        return tmp;
    }

    public DataFrame iloc(int i){
        DataFrame tmp = new DataFrame(colNames,colTypes);

        for(int j=0;j<colNames.length;j++){
            tmp.addElement(j,getElement(j,i));
        }
        return tmp;
    }

    public DataFrame iloc(int from, int to){
        DataFrame tmp = new DataFrame(colNames,colTypes);
        for(int i=0;i<colNames.length;i++){
            for (int j=from;j<=to;j++){
                tmp.addElement(j,getElement(i,j));
            }
        }
        return tmp;
    }

    @Override
    public String toString() {
        StringBuffer x=new StringBuffer();;
        for (int j=0;j<getcols(0).size();j++){
            for (int i=0;i<colTypes.length;i++){
                x.append(getElement(i,j));
                x.append(" ");
            }
            x.append("\n");
        }
        return x.toString();
    }

    public Value[] getRecord(int index) {
        Value[] tab = new Value[this.cols.size()];
        int it = 0;
        for (int i=0;i<tab.length;i++) {
            tab[it++] = getElement(index,i);
        }
        return tab;
    }

    public void add(Value[] tab){
        for (int i=0; i<tab.length; i++){
        }
    }


    public GroupedDF groupby(String[] colnames) throws Exception {
        if (colnames.length > getcolNames().length) throw new Exception();
        LinkedList<DataFrame> dataFrameLinkedList = new LinkedList<>();
        dataFrameLinkedList.add(this);
        //int[] indexes = new int[colnames.length];
        ArrayList<Integer> indexes = new ArrayList<>();
        int index = 0;
        for (String str : colnames) {
            boolean found = false;
            for (int i = 0; i < getcolNames().length; ++i) {
                if (Objects.equals(str, getcolNames()[i])) {
                    indexes.add(index++, i);
                    found = true;
                }
            }
            if (!found) throw new Exception("Invalid column name");
        }
        for (int j : indexes) {
            LinkedList<DataFrame> result = new LinkedList<>();
            for (DataFrame dataFrame : dataFrameLinkedList) {
                Set<Value> UniqueValues = new HashSet<>(dataFrame.getcols(j));
                List<Value> values = new ArrayList<>(UniqueValues);
                Collections.sort(values);

                //for (Value value:listOfUniqueValues) System.out.println(value);
                for (Value value : values) {
                    DataFrame current = getDataFrameOfCertainValue(dataFrame, value, j);
//                    System.out.println(current);
                    if (current.size() != 0) result.add(current);
                }

                dataFrameLinkedList = new LinkedList<>(result);
            }
        }
        for (int i:indexes) System.out.println(i);
        return new GroupedDF(dataFrameLinkedList, indexes);

    }

    private DataFrame getDataFrameOfCertainValue(DataFrame df, Value v, int indexOfColumn) throws Exception {
        DataFrame dataFrame = new DataFrame(getcolNames(), getcolTypes());
        int indexOfRow = 0;
//        System.out.println(getcols(indexOfColumn+1));
        for (Value value : getcols(indexOfColumn)) {
//            System.out.println(value);
            if (value.equals(v)) {
//                System.out.println(df.iloc(indexOfRow));
                dataFrame.addDataFrame(df.iloc(indexOfRow));
            }
            indexOfRow++;
        }
        return dataFrame;
    }


    public void add(Value value, int col) {
        for ( int i=0;i<this.getcols(col).size();i++)
            try {
                cols.get(col).set(i,cols.get(col).get(i).add(value));
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
    }

    public void sub(Value value, int col) {
        for ( int i=0;i<this.getcols(col).size();i++)
            try {
                cols.get(col).set(i,cols.get(col).get(i).sub(value));
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
    }

    public void mul(Value value, int col) {
        for ( int i=0;i<this.getcols(col).size();i++)
            try {
                cols.get(col).set(i,cols.get(col).get(i).mul(value));
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
    }

    public void div(Value value, int col) {
        for ( int i=0;i<this.getcols(col).size();i++)
            try {
                cols.get(col).set(i,cols.get(col).get(i).div(value));
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
    }

    public void add2col(int col1, int col2) {
        if(this.getcols(col1).size()!=this.getcols(col2).size()) {
            try {
                throw new WrongColumnSizeException(getcolNames()[col1], getcolNames()[col2]);
            } catch (WrongColumnSizeException e) {
                e.printStackTrace();
            }
        }

        int len=0;
        if(getcols(col1).size()>getcols(col2).size()){
            len=getcols(col2).size();
        }
        else{
            len=getcols(col1).size();
        }

        for (int i=0;i<len;i++) {
            try {
                cols.get(col1).set(i, cols.get(col1).get(i).add(cols.get(col2).get(i)));
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    public void sub2col(int col1, int col2) {
        if(this.getcols(col1).size()!=this.getcols(col2).size()) {
            try {
                throw new WrongColumnSizeException(getcolNames()[col1], getcolNames()[col2]);
            } catch (WrongColumnSizeException e) {
                e.printStackTrace();
            }
        }

        int len=0;
        if(getcols(col1).size()>getcols(col2).size()){
            len=getcols(col2).size();
        }
        else{
            len=getcols(col1).size();
        }

        for (int i=0;i<len;i++) {
            try {
                cols.get(col1).set(i, cols.get(col1).get(i).sub(cols.get(col2).get(i)));
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    public void mul2col(int col1, int col2) {
        if(this.getcols(col1).size()!=this.getcols(col2).size()) {
            try {
                throw new WrongColumnSizeException(getcolNames()[col1], getcolNames()[col2]);
            } catch (WrongColumnSizeException e) {
                e.printStackTrace();
            }
        }

        int len=0;
        if(getcols(col1).size()>getcols(col2).size()){
            len=getcols(col2).size();
        }
        else{
            len=getcols(col1).size();
        }

        for (int i=0;i<len;i++) {
            try {
                cols.get(col1).set(i, cols.get(col1).get(i).mul(cols.get(col2).get(i)));
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    public void div2col(int col1, int col2) {
        if(this.getcols(col1).size()!=this.getcols(col2).size()) {
            try {
                throw new WrongColumnSizeException(getcolNames()[col1], getcolNames()[col2]);
            } catch (WrongColumnSizeException e) {
                e.printStackTrace();
            }
        }

        int len=0;
        if(getcols(col1).size()>getcols(col2).size()){
            len=getcols(col2).size();
        }
        else{
            len=getcols(col1).size();
        }

        for (int i=0;i<len;i++) {
            try {
                cols.get(col1).set(i, cols.get(col1).get(i).div(cols.get(col2).get(i)));
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }


}
