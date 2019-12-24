package com.jzxiao.LifeSimulation;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import javax.swing.table.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList; 
import javax.swing.JTable;

// saves the data from a specific time in the simulation to an excel file 
public class ExcelWrite {
  
    public static void writeExcel(File file, ArrayList<Integer> pop1, ArrayList<Integer> pop2, ArrayList<Integer> pop3, ArrayList<Integer> pop4, JTable table, JTable table1, JTable table2,JTable table3,JTable table4,JTable table5, JTable table6 ) 
    {
        // pop1, pop2, pop3, and pop4 are arrayLists containing the number of each agent after each tick 
        // table, table1, table2, table3, table4, table5 and table6 are the Jtables in options that contain food, disease and agent information
        WritableWorkbook myFirstWbook = null;
        try {
            myFirstWbook = Workbook.createWorkbook(file);
            // create an Excel sheet
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

            // labels for the population data
            Label L = new Label(0, 0,"Population");
            excelSheet.addCell(L);
            
            Label L0 = new Label(0, 2,"Generation #");
            excelSheet.addCell(L0);
            
            Label L1 = new Label(0, 3, "Population of Agent 1");
            excelSheet.addCell(L1);
            
            Label L2 = new Label(0, 4, "Population of Agent 2");
            excelSheet.addCell(L2);
            
            Label L3 = new Label(0, 5, "Population of Agent 3");
            excelSheet.addCell(L3);
            
            Label L4 = new Label(0, 6, "Population of Agent 4");
            excelSheet.addCell(L4);
            
            // Labels for the agent data 
             Label m = new Label(0, 10, "Agent");
            excelSheet.addCell(m);
            
             Label L5 = new Label(0, 12, "Variables");
            excelSheet.addCell(L5);
            
            Label L6 = new Label(1, 12, "Agent 1");
            excelSheet.addCell(L6);
            
             Label L7 = new Label(2, 12, "Agent 2");
            excelSheet.addCell(L7);
            
             Label L8 = new Label(3, 12, "Agent 3");
            excelSheet.addCell(L8);
            
             Label L9 = new Label(4, 12, "Agent 4");
            excelSheet.addCell(L9);
            
            // Get all data relatig to agent from JTable in options 
            TableModel model = table.getModel();
         
            for (int i = 0; i < model.getRowCount(); i++) { // go through all rows of the JTable 
                for (int j = 0; j < model.getColumnCount(); j++) { // go through all columns 
                    Label row = new Label(j, i + 13, 
                            model.getValueAt(i, j).toString()); // for each piece of data make a label with the data on it  
                    excelSheet.addCell(row); // add this label 
                }
            }
            
             TableModel model1 = table1.getModel();
            for (int i = 0; i < model1.getRowCount(); i++) { // go through all rows of the JTable 
                for (int j = 0; j < model1.getColumnCount(); j++) { // go through all columns 
                    Label row = new Label(j, i + 21, 
                            model1.getValueAt(i, j).toString()); // for each piece of data make a label with the data on it  
                    excelSheet.addCell(row); // add this label 
                }
            }
        
             // Labels for the food data 
         
            Label m2 = new Label(0, 25, "Food");
            excelSheet.addCell(m2);
            
             Label m3 = new Label(0, 27, "Variables");
            excelSheet.addCell(m3);
            
            Label m4 = new Label(1, 27, "Food 1");
            excelSheet.addCell(m4);
            
             Label m5 = new Label(2, 27, "Food 2");
            excelSheet.addCell(m5);
            
             Label m6 = new Label(3, 27, "Food 3");
            excelSheet.addCell(m6);
            
             Label m7 = new Label(4, 27, "Food 4");
            excelSheet.addCell(m7);
        
            // Get all data relatig to food from JTable in options 
          
            TableModel model2 = table2.getModel();
            for (int i = 0; i < model2.getRowCount(); i++) { // go through all rows of the JTable 
                for (int j = 0; j < model2.getColumnCount(); j++) { // go through all columns 
                    Label row = new Label(j, i + 28, 
                            model2.getValueAt(i, j).toString()); // for each piece of data make a label with the data on it  
                    excelSheet.addCell(row); // add this label 
                }
            }
        
            // Labels for the disease data 
         
            Label n2 = new Label(0, 35, "Disease");
            excelSheet.addCell(n2);
            
            Label n3 = new Label(0, 37, "Variables");
            excelSheet.addCell(n3);
            
            Label n4 = new Label(1, 37, "Agent 1");
            excelSheet.addCell(n4);
            
             Label n5 = new Label(2, 37, "Agent 2");
            excelSheet.addCell(n5);
            
             Label n6 = new Label(3, 37, "Agent 3");
            excelSheet.addCell(n6);
            
             Label n7 = new Label(4, 37, "Agent 4");
            excelSheet.addCell(n7);
            
            // Get all data relatig to disease from JTable in options 
            
        
            TableModel model4 = table4.getModel();
            for (int i = 0; i < model4.getRowCount(); i++) { // go through all rows of the JTable 
                for (int j = 0; j < model4.getColumnCount(); j++) { // go through all columns 
                    Label row = new Label(j, i + 38, 
                            model4.getValueAt(i, j).toString()); // for each piece of data make a label with the data on it  
                    excelSheet.addCell(row); // add this label 
                }
            }
        
            TableModel model5 = table5.getModel();
            for (int i = 0; i < model5.getRowCount(); i++) { // go through all rows of the JTable 
                for (int j = 0; j < model5.getColumnCount(); j++) { // go through all columns 
                    Label row = new Label(j, i + 44, 
                            model5.getValueAt(i, j).toString()); // for each piece of data make a label with the data on it  
                    excelSheet.addCell(row); // add this label 
                }
            }
        
            Label s3 = new Label(0, 48, "Origin");
            excelSheet.addCell(s3);
            
            Label s4 = new Label(1, 48, "Food 1");
            excelSheet.addCell(s4);
            
             Label s5 = new Label(2, 48, "Food 2");
            excelSheet.addCell(s5);
            
             Label s6 = new Label(3, 48, "Food 3");
            excelSheet.addCell(s6);
            
             Label s7 = new Label(4, 48, "Food 4");
            excelSheet.addCell(s7);
            TableModel model3 = table3.getModel();
            for (int i = 0; i < model3.getRowCount(); i++) { // go through all rows of the JTable 
                for (int j = 0; j < model3.getColumnCount(); j++) { // go through all columns 
                    Label row = new Label(j, i +49, 
                            model3.getValueAt(i, j).toString()); // for each piece of data make a label with the data on it  
                    excelSheet.addCell(row); // add this label 
                }
            }
            // Labels for the food web data 
            Label a = new Label(0, 53, "Food Web");
            excelSheet.addCell(a);
            
            Label a2 = new Label(0, 55, "Predator");
            excelSheet.addCell(a2);
            
            Label a4 = new Label(1, 55, "Agent 1");
            excelSheet.addCell(a4);
            
             Label a5 = new Label(2, 55, "Agent 2");
            excelSheet.addCell(a5);
            
             Label a6 = new Label(3, 55, "Agent 3");
            excelSheet.addCell(a6);
            
             Label a7 = new Label(4, 55, "Agent 4");
            excelSheet.addCell(a7);
            
            Label a8 = new Label(5, 55, "Food 1");
            excelSheet.addCell(a8);
            
            Label a9 = new Label(6, 55, "Food 2");
            excelSheet.addCell(a9);
            
            Label a10 = new Label(7, 55, "Food 3");
            excelSheet.addCell(a10);
            
            Label a11= new Label(8, 55, "Food 4");
            excelSheet.addCell(a11);
            
            // Get all data relatig to food web from JTable in options            
            TableModel model6 = table6.getModel();
            for (int i = 0; i < model6.getRowCount(); i++) { // go through all rows of the JTable 
                for (int j = 0; j < model6.getColumnCount(); j++) { // go through all columns 
                    Label row = new Label(j, i + 56, 
                            model6.getValueAt(i, j).toString()); // for each piece of data make a label with the data on it  
                    excelSheet.addCell(row); // add this label 
                }
            }
       
           // Print the number of each generation 
            for (int x=0; x<pop1.size(); x++)// number of generations equals to the size of pop1, pop2, pop3, and pop4
            {
                Number number = new Number (x+1, 2, x+1); 
                excelSheet.addCell(number); // add the generation number 
            }
            
            // Print the population numbers for agent 1  
            for (int x=0; x<pop1.size(); x++) // go through array list 
            {
                Number number = new Number (x+1, 3, pop1.get(x)); // make a number equal to the population 
                excelSheet.addCell(number); // add this number 
            }
            
            // Print the population numbers for agent 2 
            for (int x=0; x<pop2.size(); x++) // go through array list 
            {
                Number number = new Number (x+1, 4, pop2.get(x)); // make a number equal to the population 
                excelSheet.addCell(number); // add this number  
            }
            
            // Print the population numbers for agent 3  
            for (int x=0; x<pop3.size(); x++) // go through array list 
            {
                Number number = new Number (x+1, 5, pop3.get(x)); // make a number equal to the population 
                excelSheet.addCell(number); // add this number  
            }
            
            // Print the population numbers for agent 4  
            for (int x=0; x<pop4.size(); x++) // go through array list 
            {
                Number number = new Number (x+1, 6, pop4.get(x)); // make a number equal to the population 
                excelSheet.addCell(number); // add this number 
            }

            myFirstWbook.write();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
