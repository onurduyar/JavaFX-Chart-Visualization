package sample;

import java.util.ArrayList;

public class Data {


    private int success = 0;
    public String title;
    public String xAxisLabel;
    public ArrayList<Integer> recordsNumber = new ArrayList<Integer>();
    public ArrayList<Record> records = new ArrayList<Record>();

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public void printRecords(){
        for (Record record : records){
            System.out.println(record.getYear() + record.getName() + record.getCountry() + record.getValue() + record.getCategory());
        }
        System.out.println(recordsNumber.size());
    }
}
