package dataframe;

public class SparseDataFrame extends DataFrame {
    Value hide;

    public SparseDataFrame (String x[],Class<? extends Value>[] y,Value hide_){
        super(x,y);
        hide=hide_;
    }

    public SparseDataFrame (DataFrame df,Value hide_){
        super(df.getcolNames(),df.getcolTypes());
        hide =hide_;

        for (int i=0;i<df.getcolNames().length;i++){
            for (int j=0;j<df.getcols(i).size();j++){
                if(df.getElement(i,j).equals(hide)){
                    COOValue coo =new COOValue(j,df.getElement(i,j));
                    addElement(i,coo);
                }
            }
        }
    }

    public DataFrame toDense(){
        DataFrame x= new DataFrame(getcolNames(),getcolTypes());
        int max=0;


        for(int i=0;i<getcolTypes().length;i++){
            int k=0;
            for (int j=0;j<getcols(i).size();j++){
                if(j==0){
                    for(int z=0;z<((COOValue)getElement(i,j)).getIndex();z++){
                        x.addElement(i,hide);
                    }
                }
                if(((COOValue)getElement(i,j)).getIndex()!=j){
                    System.out.println(((COOValue)getElement(i,j)).getIndex());
                    for(k=x.getcols(i).size()-1;k<((COOValue)getElement(i,j)).getIndex()-1;k++){
                        x.addElement(i,hide);
                    }
                }
                x.addElement(i,((COOValue)getElement(i,j)).getA());
                if(((COOValue)getElement(i,j)).getIndex()>max)
                    max=((COOValue)getElement(i,j)).getIndex();
            }
        }
        for(int i=0;i<getcolTypes().length;i++){
            if(x.getcols(i).size()<max+1){
                for(int j=x.getcols(i).size();j<max+1;j++){
                    x.addElement(i,hide);
                }
            }
        }

        return x;
    }

}
