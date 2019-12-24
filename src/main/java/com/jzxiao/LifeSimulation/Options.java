package com.jzxiao.LifeSimulation;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.table.*;

class Options extends JPanel
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTabbedPane pane = new JTabbedPane();
    private JPanel agentPanel = new JPanel(); 
    private JPanel foodPanel = new JPanel(); 
    private JPanel diseasePanel = new JPanel(); 
    private JPanel foodWebPanel = new JPanel(); 
    private JTable diseaseVar, foodWeb, agentVar,agentVar1, foodVar, diseaseVar1, diseaseVar2; 
    private DefaultTableModel table, table1, table2, table3;  

    public Options()
    {
        super(new GridLayout(1,0));

        //Agent Variable Table
        final String[] columnNames = {"Variables   ", 
                "Agent 1", 
                "Agent 2", 
                "Agent 3", 
                "Agent 4"}; 
        final Object [][] data = {
                {"Initial Count", Integer.valueOf(20), Integer.valueOf(20), Integer.valueOf(20), Integer.valueOf(20)}, 
                {"Initial Energy", Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100)}, 
                {"Initial Energy", Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(100)}, 
                {"Breeding Energy Threshold", Integer.valueOf(80), Integer.valueOf(80), Integer.valueOf(80), Integer.valueOf(80)},
                {"Breed Energy", Integer.valueOf(60), Integer.valueOf(60), Integer.valueOf(60), Integer.valueOf(60)}, 
                {"Step Energy", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1)}, 
                {"Aging Rate", Integer.valueOf(10), Integer.valueOf(10), Integer.valueOf(10), Integer.valueOf(10)}, 
                {"Age Limit", Integer.valueOf(300), Integer.valueOf(300), Integer.valueOf(300), Integer.valueOf(300)},
                {"Fighting Energy", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1)}, 
            }; 
        final Object[][] choice = {{"Sexual Reproduction", false, false, false, false}}; 

        agentVar = new JTable(data, columnNames); 
        agentVar.setPreferredScrollableViewportSize(new 
            Dimension(675, 150));
        agentVar.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(agentVar);
        agentPanel.setLayout(new BoxLayout(agentPanel,BoxLayout.PAGE_AXIS)); 
        //other agent table 
        table = new DefaultTableModel(choice, columnNames);
        agentVar1= new 
        JTable(table) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                    return String.class;
                    default:
                    return Boolean.class;
                }
            }
        };
        agentVar1.setPreferredScrollableViewportSize(new Dimension(675, 135));
        agentVar1.setFillsViewportHeight(true);
        JScrollPane scrollPane1 = new JScrollPane(agentVar1);

        agentPanel.add(scrollPane1); 
        agentPanel.add(scrollPane); 
        // Food Variable Table
        final String[] columnNames1 = {"Variables", 
                "Food 1", 
                "Food 2", 
                "Food 3", 
                "Food 4"}; 
        final Object [][] data1 = {
                {"Initial amount", Integer.valueOf(20), Integer.valueOf(20), Integer.valueOf(20), Integer.valueOf(20)},
                {"Growth Rate", Double.valueOf(0.03), Double.valueOf(0.03), Double.valueOf(0.03), Double.valueOf(0.03)}, 
                {"Depletion Rate", Double.valueOf(0.3), Double.valueOf(0.3), Double.valueOf(0.3), Double.valueOf(0.3)},
                {"Age Limit", Integer.valueOf(300), Integer.valueOf(300), Integer.valueOf(300), Integer.valueOf(300)},
            }; 
        foodVar = new JTable(data1, columnNames1); 
        foodVar.setPreferredScrollableViewportSize(new 
            Dimension(675, 135));
        foodVar.setFillsViewportHeight(true);
        JScrollPane scrollPane2 = new JScrollPane(foodVar);
        foodPanel.setLayout(new BoxLayout(foodPanel,BoxLayout.PAGE_AXIS)); 
        foodPanel.add(scrollPane2); 

        //First Disease Table 
        final String[] columnNames2 = {"Origin", 
                "Food 1", 
                "Food 2", 
                "Food 3", 
                "Food 4", 
            }; 

        final Object[][] data2 = {
                {"Originates from", false, false, false, false}
            }; 

        table1 = new DefaultTableModel(data2, columnNames2);
        diseaseVar= new 
        JTable(table1) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                    return String.class;
                    default:
                    return Boolean.class;
                }
            }
        };
        diseaseVar.setPreferredScrollableViewportSize(new Dimension(675, 135));
        diseaseVar.setFillsViewportHeight(true);
        JScrollPane scrollPane3 = new JScrollPane(diseaseVar);

        final String[] columnNames3 = {"Transmission Web", 
                "Agent 1", 
                "Agent 2", 
                "Agent 3", 
                "Agent 4", 
            }; 
        final Object[][] data3 = {
                {"Vacinnator", false, false, false, false}, 
                {"Healer", false, false, false, false},
                {"Transmit to 1",false, false, false, false},
                {"Transmit to 2", false, false, false, false},
                {"Transmit to 3", false, false, false, false},
                {"Transmit to 4", false, false, false, false}
            }; 
        table2 = new DefaultTableModel(data3, columnNames3);
        diseaseVar1= new 
        JTable(table2) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                    return String.class;
                    default:
                    return Boolean.class;
                }
            }
        };
        diseaseVar1.setPreferredScrollableViewportSize(new Dimension(675, 135));
        diseaseVar1.setFillsViewportHeight(true);
        JScrollPane scrollPane4 = new JScrollPane(diseaseVar1);

        //Second Disease Table 
        final Object[][] data4 = {
                {"Contact Transmission Rate", Double.valueOf(0.5), Double.valueOf(0.5), Double.valueOf(0.5), Double.valueOf(0.5)},
                {"Child Transmission Rate", Double.valueOf(0.9), Double.valueOf(0.9), Double.valueOf(0.9), Double.valueOf(0.9)},
                {"Recovery Time", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)}, 
            }; 
        diseaseVar2 = new JTable(data4, columnNames);
        diseaseVar2.setPreferredScrollableViewportSize(new Dimension(675, 135));
        diseaseVar2.setFillsViewportHeight(true);
        JScrollPane scrollPane5 = new JScrollPane(diseaseVar2);

        diseasePanel.setLayout(new BoxLayout(diseasePanel,BoxLayout.PAGE_AXIS)); 
        diseasePanel.add(scrollPane3); 
        diseasePanel.add(scrollPane4); 
        diseasePanel.add(scrollPane5); 
        // Food Web Table 
        final String[] columnNames4 = {"Predator", 
                "Agent 1", 
                "Agent 2", 
                "Agent 3",
                "Agent 4",
                "Food 1", 
                "Food 2", 
                "Food 3", 
                "Food 4",
            }; 
        final Object[][] data5 = {
                {"Agent 1",false, false, false, false, true, true, true, true},
                {"Agent 2", false, false, false, false, true, true, true, true},
                {"Agent 3", false, false, false, false, true, true, true, true},
                {"Agent 4", false, false, false, false, true, true, true, true}
            }; 
        table3 = new DefaultTableModel(data5, columnNames4);
        foodWeb= new JTable(table3) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                    return String.class;
                    default:
                    return Boolean.class;
                }
            }
        };

        foodWeb.setPreferredScrollableViewportSize(new Dimension(675, 135));
        foodWeb.setFillsViewportHeight(true);
        JScrollPane scrollPane6 = new JScrollPane(foodWeb);

        foodWebPanel.setLayout(new BoxLayout(foodWebPanel,BoxLayout.PAGE_AXIS)); 
        foodWebPanel.add(scrollPane6); 

        //adding tabs 
        pane.addTab("Agent", agentPanel); 
        pane.addTab("Food", foodPanel); 
        pane.addTab("Disease", diseasePanel); 
        pane.addTab("Food Web", foodWebPanel); 
        add(pane); 

        // 4... Set this window's attributes.
        setPreferredSize(new Dimension(785, 270)); 
    }

    public boolean getTransmitData(int row, int col) //access method to get transmission data from diseaseVar1
    {
        return (boolean)diseaseVar1.getValueAt(row,col); 
    }

    public double getDiseaseData(int row, int col) //acess method to get rate data from diseaseVar1
    {
        double temp =0, temp1 = 0; 
        String value; 
        if (row == 0 || row == 1)
        {
            if(diseaseVar2.getValueAt(row,col).getClass().getName().equals("java.lang.String"))
            {
                value = (String)(diseaseVar2.getValueAt(row,col)); 
                for (int i = 0; i < value.length(); i ++) // check every character in string for non-letter characters 
                {
                    if (value.charAt(i) < 48 || value.charAt(i) > 57) // if non number characters are found, will automatically return 0 
                    {   
                        temp1 = -1; 
                    }
                }

                if (temp1 == 0) //if no strange characters are found 
                {
                    try  //return number 
                    {
                        temp = Double.parseDouble((String)(diseaseVar2.getValueAt(row,col))); 
                    }
                    catch (Exception e) 
                    {
                    }
                }
                else 
                    temp = 0; 
            }
            else 
                temp = (double)diseaseVar2.getValueAt(row,col); 
        }
        else 
        {
            if(diseaseVar2.getValueAt(row,col).getClass().getName().equals("java.lang.String"))
            {
                value = (String)(diseaseVar2.getValueAt(row,col)); 
                for (int i = 0; i < value.length(); i ++) // check every character in string for non-letter characters 
                {
                    if (value.charAt(i) < 48 || value.charAt(i) > 57) // if non number characters are found, will automatically return 0 
                    {   
                        temp1 = -1; 
                    }
                }

                if (temp1 == 0) //if no strange characters are found, make 
                {
                    try 
                    {
                        temp = (Integer.parseInt(value))*1.0; 
                    }
                    catch (Exception e) 
                    {

                    }
                }
                else 
                    temp = 0;
            }
            else 
                temp = (int)diseaseVar2.getValueAt(row,col)*1.0; 
        }

        return temp; 
    }

    public boolean getAgent1Data(int row, int col) //gets checkbox data from agentVar1 
    {
        return (boolean)agentVar1.getValueAt(row,col);
    }

    public int getAgentData(int row, int col) //get numerical data from agentVar 
    {
        int temp = 0, temp1 = 0; 
        String value; 
        if(agentVar.getValueAt(row,col).getClass().getName().equals("java.lang.String")) //if the value is a string object
        {
            value = (String)(agentVar.getValueAt(row,col)); 
            for (int i = 0; i < value.length(); i ++) // check every character in string for non-letter characters 
            {
                if (value.charAt(i) < 48 || value.charAt(i) > 57 || value.length() == 0) // if non number characters are found, will automatically return 0 
                {   
                    temp1 = -1; 
                }
            }

            if (temp1 == 0) //if no strange characters are found 
            {
                try  //make it a integer 
                {
                    temp = Integer.parseInt(value); 
                }
                catch (Exception e) //do nothing if exception happens 
                {
                    
                }
            }
            else 
                temp = 0; 
        }
        else 
            temp = (int)agentVar.getValueAt(row,col); 

        return temp; 
    }

    public boolean getOrigin(int col) //get checkbox data from diseaseVar
    {
        return (boolean)diseaseVar.getValueAt(0,col); 
    }

    public double getFoodData (int row, int col) //get numerical data from foodVar 
    {
        double temp = 0, temp1 = 0; 
        String value; 
        if (row == 1 || row == 2) //if row is row1 or row2, returns double directly 
        {
            if(foodVar.getValueAt(row,col).getClass().getName().equals("java.lang.String"))
            {
                value = (String)(foodVar.getValueAt(row,col)); 
                for (int i = 0; i < value.length(); i ++) // check every character in string for non-letter characters 
                {
                    if (value.charAt(i) < 48 || value.charAt(i) > 57 || value.length()==0) // if non number characters are found, will automatically return 0 
                    {   
                        temp1 = -1; 
                    }
                }

                if (temp1 == 0)
                {
                    try 
                    {
                        temp = Double.parseDouble(value); 
                    }
                    catch (Exception e) 
                    {
                    }
                }
                else 
                    temp = 0; 
            }
            else 
                temp = (double)foodVar.getValueAt(row,col); 
        }
        else //must turn into int and then double 
        {
            if(foodVar.getValueAt(row,col).getClass().getName().equals("java.lang.String"))
            {
                value = (String)(foodVar.getValueAt(row,col)); 
                for (int i = 0; i < value.length(); i ++) // check every character in string for non-letter characters 
                {
                    if (value.charAt(i) < 48 || value.charAt(i) > 57 || value.length() == 0) // if non number characters are found, will automatically return 0 
                    {   
                        temp1 = -1; 
                    }
                }

                if (temp1 == 0) //if no strange characters are found, make into a number 
                {
                    try 
                    {
                        temp = (Integer.parseInt(value))*1.0; 
                    }
                    catch (Exception e) 
                    {
                    }
                }
                else 
                    temp = 0; 
            }
            else 
                temp = ((int)foodVar.getValueAt(row,col))*1.0; 
        }

        return temp; 
    }

    public boolean[] AgentPrey(int num) //gets foodWeb data depending on agent/food type 
    {
        boolean [] prey = new boolean [8];
        for (int i = 1;i<=8;i++) //going through all columns 
        {
            if ((boolean)foodWeb.getValueAt(num-1,i)) //if the box is checked, make the array 
            {
                prey[i-1] = true; 
            }
        }
        return prey; 
    }

    // the methods that return the JTables
    public JTable getdiseaseVar()
    {
        return diseaseVar; 
    }

    public JTable getfoodWeb()
    {
        return foodWeb; 
    }

    public JTable getdiseaseVar2()
    {
        return diseaseVar2; 
    }

    public JTable getagentVar()
    {
        return agentVar; 
    }

    public JTable getagentVar1()
    {
        return agentVar1; 
    }

    public JTable getfoodVar()
    {
        return foodVar; 
    }

    public JTable getdiseaseVar1()
    {
        return diseaseVar1; 
    }
}