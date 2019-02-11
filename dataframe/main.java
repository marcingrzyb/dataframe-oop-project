package dataframe;

public class main {
    public static void main(String[] args) throws Exception{

        DataFrame dfmain = null;
        try {
        dfmain = new DataFrame("/home/marcin/Pobrane/groupby.csv",new Class[]{StringValue.class, DataTimeValue.class,DoubleValue.class, DoubleValue.class},true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println(dfmain.groupby(new String[]{"id"}).apply(new Max()));
    }
}