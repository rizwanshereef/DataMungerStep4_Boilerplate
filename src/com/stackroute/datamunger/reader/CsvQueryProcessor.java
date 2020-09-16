package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {
    private String fileName;
    /*
     * Parameterized constructor to initialize filename. As you are trying to
     * perform file reading, hence you need to be ready to handle the IO Exceptions.
     */

    public CsvQueryProcessor(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
    }

    /*
     * Implementation of getHeader() method. We will have to extract the headers
     * from the first line of the file.
     */

    @Override
    public Header getHeader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String inputFirstLine = reader.readLine();
        String[] header = inputFirstLine.split(",");
        Header headerOne = new Header();
        headerOne.setHeaders(header);
        reader.close();
        return headerOne;
    }

    /**
     * This method will be used in the upcoming assignments
     */
    @Override
    public void getDataRow() {

    }

    /*
     * Implementation of getColumnType() method. To find out the data types, we will
     * read the first line from the file and extract the field values from it. In
     * the previous assignment, we have tried to convert a specific field value to
     * Integer or Double. However, in this assignment, we are going to use Regular
     * Expression to find the appropriate data type of a field. Integers: should
     * contain only digits without decimal point Double: should contain digits as
     * well as decimal point Date: Dates can be written in many formats in the CSV
     * file. However, in this assignment,we will test for the following date
     * formats('dd/mm/yyyy',
     * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm
     * -dd')
     */

    @Override
    public DataTypeDefinitions getColumnType() throws IOException {

        // checking for Integer

        // checking for floating point numbers

        // checking for date format dd/mm/yyyy

        // checking for date format mm/dd/yyyy

        // checking for date format dd-mon-yy

        // checking for date format dd-mon-yyyy

        // checking for date format dd-month-yy

        // checking for date format dd-month-yyyy

        // checking for date format yyyy-mm-dd
        String[] data = null;
        int index = 0;
        DataTypeDefinitions dataType = new DataTypeDefinitions();
        FileReader file = null;
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException ex) {
            file = new FileReader("data/ipl.csv");
        }
        BufferedReader reader = new BufferedReader(file);
        /*Skipping the 1st line and selecting 2nd line*/
        reader.readLine();
        String firstRow = reader.readLine();
        /*split the 1st row to string array*/
        data = firstRow.split(",", -1);
        String[] arrOut = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            if (data[i].trim().isEmpty()) {
                arrOut[index] = "java.lang.Object";
                index++;
            } else if (data[i].matches("[0-9]+")) {
                arrOut[index] = "java.lang.Integer";
                index++;
            } else if (data[i].matches("[0-9]+.[0-9]")) {
                arrOut[index] = "java.lang.Double";
                index++;
            } else if (data[i].matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}$") || data[i].matches("^[0-9]{2}-[a-z]{3}-[0-9]{2}$")
                    || data[i].matches("^[0-9]{2}-[a-z]{3}-[0-9]{4}$") || data[i].matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{2}$")
                    || data[i].matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{4}$") || data[i].matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
                arrOut[index] = "java.util.Date";
                index++;
            } else {
                arrOut[index] = "java.lang.String";
                index++;
            }
        }
        dataType.setDataTypes(arrOut);
        file.close();
        reader.close();
        return dataType;
    }

}
