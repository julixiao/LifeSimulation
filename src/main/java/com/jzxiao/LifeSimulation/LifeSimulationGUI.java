package com.jzxiao.LifeSimulation;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import javax.swing.event.*;  // Needed for ActionListener
import java.io.*;
import java.util.Random;
import java.util.ArrayList;

class LifeSimulationGUI extends JFrame implements ActionListener, ChangeListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static Options options = new Options();
    static Colony colony = new Colony (options);
    static JSlider speedSldr = new JSlider ();
    static Timer t;
    static JFrame frame = new JFrame("Modify Simulation"); 
    private JButton simulateBtn, stepBtn; 
    static JLabel tickcounter = new JLabel("0");
    private static LifeSimulationGUI window; //the JFrame that contains all the components
    //======================================================== constructor
    public LifeSimulationGUI ()
    {
        // 1... Create/initialize components
        JButton optionsBtn = new JButton ("Options");
        optionsBtn.addActionListener(this);
        simulateBtn = new JButton ("Simulate");
        simulateBtn.addActionListener (this);
        JLabel tickheader = new JLabel ("Tick: ");
        JPanel tickdisplay = new JPanel();
        tickdisplay.add(tickheader);
        tickdisplay.add(tickcounter);
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener (this);
        speedSldr.addChangeListener (this);
        stepBtn = new JButton("Step");
        stepBtn.addActionListener(this); 
        // 2... Create content pane, set layout
        JPanel content = new JPanel ();        // Create a content pane
        content.setLayout (new BorderLayout ()); // Use BorderLayout for panel
        JPanel north = new JPanel ();
        north.setLayout (new FlowLayout ()); // Use FlowLayout for input area

        DrawArea board = new DrawArea (603, 603);
        JPanel pane = new JPanel ();
        pane.add(board);
        pane.setAlignmentX((float)0.5);

        // 3... Add the components to the input area.
        north.add (simulateBtn);
        north.add (stepBtn); 
        north.add (optionsBtn);
        north.add (tickdisplay);
        north.add (speedSldr);
        north.add (saveBtn);

        content.add (north, "North"); // Input area
        content.add (pane, "South"); // Output area

        // 4... Set this window's attributes.
        setContentPane (content);
        pack ();
        setTitle ("Life Simulation Demo");
        setPreferredSize(new Dimension(6*100+3, 6*100+3));
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null);           // Center window.
    }

    public void updateCounter() // method that updates the tick counter  
    {
        int counter = 0;
        try
        {
            counter = Integer.parseInt(tickcounter.getText());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        counter++;
        tickcounter.setText(Integer.toString(counter));
    }

    public void stateChanged (ChangeEvent e)// method that speeds up or slows down simulation according to speed slider
    {
        if (t != null)
            t.setDelay (400 - 4 * speedSldr.getValue()); // 0 to 400 ms
    }

    public void actionPerformed (ActionEvent e) // actionPerformed describing what each button does 
    {
        if (e.getActionCommand ().equals ("Simulate")) // if simulate button is pressed 
        {
            ((JButton)e.getSource()).setEnabled(true);
            Movement moveColony = new Movement (colony, this); // ActionListener
            t = new Timer (400 - 4 * speedSldr.getValue(), moveColony); // set up timer
            t.start (); // start simulation
            simulateBtn.setText("Stop"); //Make simulate button a stope button 
        }
        else if (e.getActionCommand().equals("Stop")) //if stop button is pressed 
        {
            t.stop(); //stops the simulation
            simulateBtn.setText("Simulate"); //allows the user to simulate again
        }
        else if (e.getActionCommand().equals("Step")) //if step button is pressed, moves simulation step by step
        {
            this.updateCounter();
            colony.spawnFood();
            colony.spawnAgent(); 
            colony.iterate();
            colony.popcalc();
            colony.advance();
            if (colony.overCheck())
            {
                t.stop(); 
                JOptionPane.showMessageDialog(window, "Overpopulation!", "Simulation stopped", 0);
            }
        }
        else if(e.getActionCommand().equals("Save")) //if save button is pressed 
        {
            //Ask user where to save file 
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            File selectedFile; 
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                //Save data to excel file
                ExcelWrite.writeExcel(selectedFile, colony.getpop1(), colony.getpop2(), colony.getpop3(), colony.getpop4(), options.getagentVar(), options.getagentVar1(), options.getfoodVar(), options.getdiseaseVar(),options.getdiseaseVar1(),options.getdiseaseVar2(), options.getfoodWeb()  );
            }
        }
        else if(e.getActionCommand().equals("Options"))//if options button is pressed 
        {
            if(t!=null &&t.isRunning())
                t.stop();
            frame.setVisible(true); 
            simulateBtn.setText("Update"); //Make simulate button a stope button 
        }
        else if (e.getActionCommand().equals("Update"))
        {
            colony = new Colony(options);
            ((JButton)e.getSource()).setEnabled(true);
            Movement moveColony = new Movement (colony, this); // ActionListener
            t = new Timer (400 - 4 * speedSldr.getValue(), moveColony); // set up timer
            t.start (); // start simulation
            simulateBtn.setText("Stop"); //Make simulate button a stope button '
        }
        repaint ();            // refresh display of colony
    }

    class DrawArea extends JPanel //Class that draws the life simulation 
    {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public DrawArea(int width, int height)
        {
            this.setPreferredSize (new Dimension (width, height)); // size
        }

        public void paintComponent (Graphics g) //shows the colony and draws grid lines 
        {
            colony.show (g);
            g.setColor(new Color(160,160,160)); 

            for (int row = 0; row <= 100; row++) {
                int y = (int)(row*6);
                g.drawLine(2,y+2,getWidth(),y+2);
            }
            for (int col = 0; col <= 100; col++) {
                int x = (int)(col*6);
                g.drawLine(x+2,2,x+2,getHeight());
            }

        }
    }

    class Movement implements ActionListener //class that describes the colony's movements 
    {
        private Colony colony;
        private LifeSimulationGUI display;

        public Movement (Colony col, LifeSimulationGUI display)
        {
            colony = col;
            this.display = display;
        }

        public void actionPerformed (ActionEvent event) //basically what to do when simulate button is pressed 
        {
            display.updateCounter();
            colony.spawnFood();
            colony.spawnAgent(); 
            colony.iterate();
            colony.popcalc();
            colony.advance();
            if (colony.overCheck())
            {
                t.stop(); 
                JOptionPane.showMessageDialog(window, "Overpopulation!", "Simulation stopped", 0);
            }
            repaint ();
        }
    }

    private static void createAndShowOptionsGUI() //for the Options panel 
    {
        frame.add(options, BorderLayout.EAST); 
        options.setOpaque(true); //content panes must be opaque
        frame.setContentPane(options);

        frame.pack();
        frame.setVisible(false);
    }

    //======================================================== method main
    public static void main (String[] args)
    {
        LifeSimulationGUI window = new LifeSimulationGUI ();
        window.setVisible (true);
        SwingUtilities.invokeLater(new Runnable() 
            {
                public void run() {
                    //Turn off metal's use of bold fonts
                    UIManager.put("swing.boldMetal", Boolean.FALSE);
                    createAndShowOptionsGUI();
                }
            });
    }
}

class Colony
{
    private Agent agentgrid[][];
    private Display envgrid[][];
    private Options options;
    private ArrayList<Integer> pop1 = new ArrayList<Integer>();
    private ArrayList<Integer> pop2 = new ArrayList<Integer>();
    private ArrayList<Integer> pop3 = new ArrayList<Integer>();
    private ArrayList<Integer> pop4 = new ArrayList<Integer>();
    private int prevmove[][] = new int[100][100];

    public Colony (Options options) //Colony constructor 
    {
        this.options = options;
        Random r = new Random();
        agentgrid = new Agent [100] [100];
        envgrid = new Display [100] [100]; //MUST BE SAME SIZE
        int row, col;

        //populating the grid with the correct number of food 
        for (int x = 0; x < (int)options.getFoodData(0,1); x++)
        {
            do
            {
                row = r.nextInt(envgrid.length);
                col = r.nextInt(envgrid[0].length);
            }
            while(agentgrid [row] [col] != null);
            if(r.nextInt((int)(options.getDiseaseData(0,1)*100))==0)
            {
                envgrid [row] [col] = new Food(5,options.getFoodData(1,1),options.getFoodData(2,1),(int)options.getFoodData(3,1), new Disease(10, options.getDiseaseData(0,1),options.getDiseaseData(1,1), (int)options.getDiseaseData(2,1), new boolean[]{options.getTransmitData(2,1),options.getTransmitData(3,1),options.getTransmitData(4,1),options.getTransmitData(5,1)}));
            }
            else
            {
                envgrid [row] [col] = new Food(5,options.getFoodData(1,1),options.getFoodData(2,1),(int)options.getFoodData(3,1));
            }
            do
            {
                row = r.nextInt(envgrid.length);
                col = r.nextInt(envgrid[0].length);
            }
            while(envgrid [row] [col] != null);
            if(r.nextInt((int)(options.getDiseaseData(0,1)*100))==0)
            {
                envgrid [row] [col] = new Food(6,options.getFoodData(1,2),options.getFoodData(2,2),(int)options.getFoodData(3,2), new Disease(10, options.getDiseaseData(0,2),options.getDiseaseData(1,2), (int)options.getDiseaseData(2,2), new boolean[]{options.getTransmitData(2,2),options.getTransmitData(3,2),options.getTransmitData(4,2),options.getTransmitData(5,2)}));
            }
            else
            {
                envgrid [row] [col] = new Food(6,options.getFoodData(1,2),options.getFoodData(2,2),(int)options.getFoodData(3,2));
            }
            do
            {
                row = r.nextInt(envgrid.length);
                col = r.nextInt(envgrid[0].length);
            }
            while(envgrid [row] [col] != null);
            if(r.nextInt((int)(options.getDiseaseData(0,1)*100))==0)
            {
                envgrid [row] [col] = new Food(7,options.getFoodData(1,3),options.getFoodData(2,3),(int)options.getFoodData(3,3), new Disease(10, options.getDiseaseData(0,3),options.getDiseaseData(1,3), (int)options.getDiseaseData(2,3), new boolean[]{options.getTransmitData(2,3),options.getTransmitData(3,3),options.getTransmitData(4,3),options.getTransmitData(5,3)}));
            }
            else
            {
                envgrid [row] [col] = new Food(7,options.getFoodData(1,3),options.getFoodData(2,3),(int)options.getFoodData(3,3));
            }
            do
            {
                row = r.nextInt(envgrid.length);
                col = r.nextInt(envgrid[0].length);
            }
            while(envgrid [row] [col] != null);
            if(r.nextInt((int)(options.getDiseaseData(0,1)*100))==0)
            {
                envgrid [row] [col] = new Food(8,options.getFoodData(1,4),options.getFoodData(2,4),(int)options.getFoodData(3,4), new Disease(10, options.getDiseaseData(0,4),options.getDiseaseData(1,4), (int)options.getDiseaseData(2,4), new boolean[]{options.getTransmitData(2,4),options.getTransmitData(3,4),options.getTransmitData(4,4),options.getTransmitData(5,4)}));
            }
            else
            {
                envgrid [row] [col] = new Food(8,options.getFoodData(1,4),options.getFoodData(2,4),(int)options.getFoodData(3,4));
            }
        }
        // populating the grid with the correct number of agents 
        for (int y = 0; y <options.getAgentData(0,1); y ++)
        {
            row = r.nextInt(agentgrid.length);
            col = r.nextInt(agentgrid[0].length);
            do
            {
                row = r.nextInt(agentgrid.length);
                col = r.nextInt(agentgrid[0].length);
            }
            while(agentgrid [row] [col] != null);
            agentgrid [row] [col] = new Agent(1,options.getAgentData(1,1),options.getAgentData(4,1),options.getAgentData(5,1),options.getAgentData(6,1), options.getTransmitData(0,1), options.getTransmitData(1,1));
        }

        for (int x = 0; x<options.getAgentData(0,2); x++)
        {
            do
            {
                row = r.nextInt(agentgrid.length);
                col = r.nextInt(agentgrid[0].length);
            }
            while(agentgrid [row] [col] != null);

            agentgrid [row] [col] = new Agent(2,options.getAgentData(1,2),options.getAgentData(4,2),options.getAgentData(5,2),options.getAgentData(6,2), options.getTransmitData(0,2), options.getTransmitData(1,2));
        }

        for(int z = 0; z<options.getAgentData(0,3); z++)
        {
            do
            {
                row = r.nextInt(agentgrid.length);
                col = r.nextInt(agentgrid[0].length);
            }
            while(agentgrid [row] [col] != null);
            agentgrid [row] [col] = new Agent(3,options.getAgentData(1,3),options.getAgentData(4,3),options.getAgentData(5,3),options.getAgentData(6,3), options.getTransmitData(0,3), options.getTransmitData(1,3));
        }

        for (int w = 0; w<options.getAgentData(0,4);w++)
        {
            do
            {
                row = r.nextInt(agentgrid.length);
                col = r.nextInt(agentgrid[0].length);
            }
            while(agentgrid [row] [col] != null);
            agentgrid [row] [col] = new Agent(4,options.getAgentData(1,4),options.getAgentData(4,4),options.getAgentData(5,4),options.getAgentData(6,4), options.getTransmitData(0,4), options.getTransmitData(1,4));
        }
    }

    public void show (Graphics g) //method that displays the agents and food 
    {
        for (int row = 0 ; row < agentgrid.length ; row++)
        {
            for (int col = 0 ; col < agentgrid [0].length ; col++)
            {
                if(envgrid[row][col] != null)
                {
                    switch(envgrid[row][col].getType()) //coloring squares based on food type 
                    {
                        case 5:
                        g.setColor (Color.yellow);
                        g.fillRect (col * 6 + 2, row * 6 + 2, 6, 6);
                        break;
                        case 6:
                        g.setColor (Color.cyan);
                        g.fillRect (col * 6 + 2, row * 6 + 2, 6, 6);
                        break;
                        case 7:
                        g.setColor (Color.red);
                        g.fillRect (col * 6 + 2, row * 6 + 2, 6, 6);
                        break;
                        case 8:
                        g.setColor (Color.green);
                        g.fillRect (col * 6 + 2, row * 6 + 2, 6, 6);
                        break;
                        case 9:
                        g.setColor (new Color(64,79,36));
                        g.fillRect (col * 6 + 2, row * 6 + 2, 6, 6);
                        break;
                        case 10:
                        g.setColor (Color.gray);
                        g.fillRect (col * 6 + 2, row * 6 + 2, 6, 6);
                        break;
                    }

                }
                else
                {
                    g.setColor (Color.white);
                    g.fillRect (col * 6 + 2, row * 6 + 2, 6, 6);
                }
                if(agentgrid[row][col] != null)
                {
                    int dir= agentgrid[row][col].getDirection(); // get the direction in which the agent will turn 
                    switch(agentgrid[row][col].getType())
                    {
                        case 1: // if it's a yellow agent 
                        g.setColor (Color.yellow);  
                        if (dir==1)// agent turns right 
                        {
                            g.fillPolygon (new int[] {col * 6 +2 , col * 6 + 8, col * 6 +2 }, new int[] {row * 6 +2 , row * 6 + 5, row * 6 + 8}, 3);  
                        }
                        else if(dir==2)// agent turns down
                        {
                            g.fillPolygon (new int[] {col * 6 +2, col * 6 + 8, col * 6 + 5}, new int[] {row * 6 +2 , row * 6 +2 , row * 6 + 8}, 3);
                        }
                        else if(dir==3)// agent turns left
                        {
                            g.fillPolygon (new int[] {col * 6 + 8, col * 6 + 8, col * 6 +2}, new int[] {row * 6 +2, row * 6 + 8, row * 6 + 5}, 3);
                        }
                        else // agent turns up
                        {
                            g.fillPolygon (new int[] {col * 6 + 5, col * 6 + 8, col * 6 +2}, new int[] {row * 6 +2 , row * 6 + 8, row * 6 + 8}, 3);
                        }
                        break;
                        case 2: // if it's a cyan agent
                        g.setColor (Color.cyan);
                        if (dir==1)// agent turns right 
                        {
                            g.fillPolygon (new int[] {col * 6 +2 , col * 6 + 8, col * 6 +2 }, new int[] {row * 6 +2 , row * 6 + 5, row * 6 + 8}, 3);  
                        }
                        else if(dir==2)// agent turns down
                        {
                            g.fillPolygon (new int[] {col * 6 +2, col * 6 + 8, col * 6 + 5}, new int[] {row * 6 +2 , row * 6 +2 , row * 6 + 8}, 3);
                        }
                        else if(dir==3)// agent turns left
                        {
                            g.fillPolygon (new int[] {col * 6 + 8, col * 6 + 8, col * 6 +2}, new int[] {row * 6 +2, row * 6 + 8, row * 6 + 5}, 3);
                        }
                        else // agent turns up
                        {
                            g.fillPolygon (new int[] {col * 6 + 5, col * 6 + 8, col * 6 +2}, new int[] {row * 6 +2 , row * 6 + 8, row * 6 + 8}, 3);
                        }
                        break;
                        case 3: // if it's a red agent
                        g.setColor (Color.red);
                        if (dir==1)// agent turns right 
                        {
                            g.fillPolygon (new int[] {col * 6 +2 , col * 6 + 8, col * 6 +2 }, new int[] {row * 6 +2 , row * 6 + 5, row * 6 + 8}, 3);  
                        }
                        else if(dir==2)// agent turns down
                        {
                            g.fillPolygon (new int[] {col * 6 +2, col * 6 + 8, col * 6 + 5}, new int[] {row * 6 +2 , row * 6 +2 , row * 6 + 8}, 3);
                        }
                        else if(dir==3)// agent turns left
                        {
                            g.fillPolygon (new int[] {col * 6 + 8, col * 6 + 8, col * 6 +2}, new int[] {row * 6 +2, row * 6 + 8, row * 6 + 5}, 3);
                        }
                        else // agent turns up
                        {
                            g.fillPolygon (new int[] {col * 6 + 5, col * 6 + 8, col * 6 +2}, new int[] {row * 6 +2 , row * 6 + 8, row * 6 + 8}, 3);
                        }
                        break;
                        case 4: // if it's a green agent
                        g.setColor (Color.green);
                        if (dir==1)// agent turns right 
                        {
                            g.fillPolygon (new int[] {col * 6 +2 , col * 6 + 8, col * 6 +2 }, new int[] {row * 6 +2 , row * 6 + 5, row * 6 + 8}, 3);  
                        }
                        else if(dir==2)// agent turns down
                        {
                            g.fillPolygon (new int[] {col * 6 +2, col * 6 + 8, col * 6 + 5}, new int[] {row * 6 +2 , row * 6 +2 , row * 6 + 8}, 3);
                        }
                        else if(dir==3)// agent turns left
                        {
                            g.fillPolygon (new int[] {col * 6 + 8, col * 6 + 8, col * 6 +2}, new int[] {row * 6 +2, row * 6 + 8, row * 6 + 5}, 3);
                        }
                        else // agent turns up
                        {
                            g.fillPolygon (new int[] {col * 6 + 5, col * 6 + 8, col * 6 +2}, new int[] {row * 6 +2 , row * 6 + 8, row * 6 + 8}, 3);
                        }
                        break;
                    }
                }
            }
        }
    }

    public void spawnFood() //method that is responsible for food spawning 
    {
        Random r = new Random(); 
        int row, col;
        double growth; 
        int amount; 
        double total; 

        for (int i = 5; i <= 8; i ++) //going through all types of food 
        {
            growth = (double)(options.getFoodData(1, (i-4)));
            amount = searchEnv(i); 
            total = amount*(1+ growth); 
            for (int t = amount; t < total; t ++) //number of new foods produced 
            {
                row = r.nextInt(envgrid.length);
                col = r.nextInt(envgrid.length);
                for(int x = 0; envgrid[row][col]!=null && x<10000; x++) // make sure 
                {
                    row = r.nextInt(envgrid.length);
                    col = r.nextInt(envgrid.length);
                }
                if(r.nextInt((int)(options.getDiseaseData(0,1)*100))==0) //diseased food spawns diseased food
                {
                    envgrid [row] [col] = new Food(i,options.getFoodData(1,(i-4)),options.getFoodData(2,(i-4)),(int)options.getFoodData(3,(i-4)), new Disease(10, options.getDiseaseData(0,(i-4)),options.getDiseaseData(1,(i-4)), (int)options.getDiseaseData(2,(i-4)), new boolean[]{options.getTransmitData(2,(i-4)),options.getTransmitData(3,(i-4)),options.getTransmitData(4,(i-4)),options.getTransmitData(5,(i-4))}));
                }
                else
                {
                    envgrid [row] [col] = new Food(i,options.getFoodData(1,(i-4)),options.getFoodData(2,(i-4)),(int)options.getFoodData(3,(i-4)));
                }
            }
        }
    }

    public void spawnAgent() //method that is responsible for agents spawning 
    {
        Random rng = new Random(); 
        int energy, threshold, breed; 
        boolean choice; 
        int row,col,row1,col1,counter = 0; 

        for (int i = 1; i <= 4; i ++)//goes through every agent type 
        {
            choice = options.getAgent1Data(0,i); 
            threshold = options.getAgentData(2,i); 
            breed = options.getAgentData(3, i); 
            if (choice == false) //if agents can spawn by themselves 
            {
                for (int r = 0; r < agentgrid.length; r ++) //goes through whole agentgrid 
                {
                    for (int c = 0; c < agentgrid[0].length; c++)
                    {
                        if (agentgrid[r][c] != null && i == agentgrid[r][c].getType()) //if the agent type is correct and the square is not null
                        {                          
                            energy = agentgrid[r][c].getEnergy(); 
                            if (energy>threshold && rng.nextInt(10) == 0) // if energy is more than threshold; 10% of reproduction 
                            {
                                agentgrid[r][c].loseEnergy(breed);
                                row = rng.nextInt(agentgrid.length);
                                col = rng.nextInt(agentgrid.length);
                                for(int x = 0; agentgrid[row][col]!=null && x<10000; x++) //new agent is produced 
                                {
                                    row = rng.nextInt(agentgrid.length);
                                    col = rng.nextInt(agentgrid.length);
                                }
                                agentgrid[row][col] = new Agent(i, options.getAgentData(1, i), options.getAgentData(4, i), options.getAgentData(5, i), options.getAgentData(6, i), options.getTransmitData(0,i), options.getTransmitData(1,i));
                            }
                        }
                    }
                }

            }
            else //if two agents are needed 
            {
                breed = options.getAgentData(3, i); 
                for (int r = 0; r < agentgrid.length; r ++) //going through all agentgrid 
                {
                    for (int c = 0; c < agentgrid[0].length; c++)
                    {
                        if (agentgrid[r][c]!= null && i==agentgrid[r][c].getType() && agentgrid[r][c].getEnergy()>threshold) // if threshold is more than energy amount and type matches 
                        {
                            //sets up correct points for checking number of agents around agent 
                            if (r > 2)
                                row = r - 3; 
                            else 
                                row = 0 ; 

                            if (r < agentgrid.length-2)
                                row1 = r + 3; 
                            else 
                                row1 = agentgrid.length; 

                            if (c > 2)
                                col = c - 3;
                            else 
                                col = 0; 

                            if (c < agentgrid[0].length-2)
                                col1 = c + 3; 
                            else 
                                col1 = agentgrid[0].length; 

                            for (int h = row; h <= row1; h++) //checking squares around grid[row][col] for life
                            {
                                for (int k = col; k <= col1; k++)
                                {
                                    if ((h != row || k != col) && agentgrid[h][k].getType() == i) // if life found, add to counter 
                                        counter++;
                                }
                            }

                            if (counter >1) //if there is an agent around, new agent produced 
                            {
                                agentgrid[row1][col1].loseEnergy(breed); 
                                row = rng.nextInt(agentgrid.length);
                                col = rng.nextInt(agentgrid.length);
                                for(int x = 0; agentgrid[row][col]!=null && x<10000; x++)
                                {
                                    row = rng.nextInt(agentgrid.length);
                                    col = rng.nextInt(agentgrid.length);
                                }
                                agentgrid[row][col] = new Agent(i, options.getAgentData(1, i), options.getAgentData(4, i), options.getAgentData(5, i), options.getAgentData(6, i), options.getTransmitData(0,i), options.getTransmitData(1,i));
                            }
                        }
                    }
                }
            }
        }
    }

    public int searchEnv (int type) //method that searches for number of certain type of food in envgrid
    {
        int total = 0; 
        for (int row = 0; row < envgrid.length; row ++)//go through entire envgrid 
        {
            for (int col = 0; col < envgrid[0].length; col++)
            {
                if (envgrid[row][col] != null && type == envgrid[row][col].getType()) //add to counter if found
                    total ++; 
            }
        }
        return total; 
    }

    public void eat() //Method for agents to eat one another
    {
        Random r = new Random(); //Create Random object
        for (int row = 0 ; row < agentgrid.length ; row++)
        {
            for (int col = 0 ; col < agentgrid[0].length ; col++) //loop through grid
            {
                if(agentgrid[row][col]!=null) //if agent detected, initiate eating
                {
                    if(col+1<agentgrid[0].length && agentgrid[row][col+1]!=null) //if agent found right of agent
                    {
                        if(agentgrid[row][col+1].getEnergy()<agentgrid[row][col].getEnergy()) //if current agent is stronger
                        {
                            if(options.AgentPrey(agentgrid[row][col].getType())[agentgrid[row][col+1].getType()]) //if current agent can eat victim (is predator)
                            {
                                if(agentgrid[row][col+1].getDisease()!=null && r.nextInt((int)(options.getDiseaseData(0,agentgrid[row][col].getType())*100))==0)
                                {
                                    agentgrid[row][col].gainDisease(agentgrid[row][col+1].getDisease());
                                }
                                agentgrid[row][col+1] = null;
                            }
                        }
                        else
                        {
                            if(options.AgentPrey(agentgrid[row][col+1].getType())[agentgrid[row][col].getType()])
                            {
                                if(agentgrid[row][col].getDisease()!=null && r.nextInt((int)(options.getDiseaseData(0,agentgrid[row][col+1].getType())*100))==0)
                                {
                                    agentgrid[row][col+1].gainDisease(agentgrid[row][col].getDisease());
                                }
                                agentgrid[row][col] = null;
                            }
                        }
                    }
                    else if(row+1<agentgrid[0].length && agentgrid[row+1][col]!=null) //if agent found down of agent
                    {
                        if(agentgrid[row+1][col].getEnergy()<agentgrid[row][col].getEnergy())
                        {
                            if(options.AgentPrey(agentgrid[row][col].getType())[agentgrid[row+1][col].getType()])
                            {
                                if(agentgrid[row+1][col].getDisease()!=null && r.nextInt((int)(options.getDiseaseData(0,agentgrid[row][col].getType())*100))==0)
                                {
                                    agentgrid[row][col].gainDisease(agentgrid[row+1][col].getDisease());
                                }
                                agentgrid[row+1][col] = null;
                            }
                        }
                        else
                        {
                            if(options.AgentPrey(agentgrid[row+1][col].getType())[agentgrid[row][col].getType()])
                            {
                                if(agentgrid[row][col].getDisease()!=null && r.nextInt((int)(options.getDiseaseData(0,agentgrid[row+1][col].getType())*100))==0)
                                {
                                    agentgrid[row+1][col].gainDisease(agentgrid[row][col].getDisease());
                                }
                                agentgrid[row][col] = null;
                            }
                        }
                    }
                    else if(col-1>0 && agentgrid[row][col-1]!=null) //if agent found left of agent
                    {
                        if(agentgrid[row][col-1].getEnergy()<agentgrid[row][col].getEnergy())
                        {
                            if(options.AgentPrey(agentgrid[row][col].getType())[agentgrid[row][col-1].getType()])
                            {
                                if(agentgrid[row][col-1].getDisease()!=null && r.nextInt((int)(options.getDiseaseData(0,agentgrid[row][col].getType())*100))==0)
                                {
                                    agentgrid[row][col].gainDisease(agentgrid[row][col-1].getDisease());
                                }
                                agentgrid[row][col-1] = null;
                            }
                        }
                        else
                        {
                            if(options.AgentPrey(agentgrid[row][col-1].getType())[agentgrid[row][col].getType()])
                            {
                                if(agentgrid[row][col].getDisease()!=null && r.nextInt((int)(options.getDiseaseData(0,agentgrid[row][col-1].getType())*100))==0)
                                {
                                    agentgrid[row][col-1].gainDisease(agentgrid[row][col].getDisease());
                                }
                                agentgrid[row][col] = null;
                            }
                        }
                    }
                    else if(row-1>0 && agentgrid[row-1][col]!=null) //if agent found up of agent
                    {
                        if(agentgrid[row-1][col].getEnergy()<agentgrid[row][col].getEnergy())
                        {
                            if(options.AgentPrey(agentgrid[row][col].getType())[agentgrid[row-1][col].getType()])
                            {
                                if(agentgrid[row-1][col].getDisease()!=null && r.nextInt((int)(options.getDiseaseData(0,agentgrid[row][col].getType())*100))==0)
                                {
                                    agentgrid[row][col].gainDisease(agentgrid[row-1][col].getDisease());
                                }
                                agentgrid[row-1][col] = null;
                            }
                        }
                        else
                        {
                            if(options.AgentPrey(agentgrid[row-1][col].getType())[agentgrid[row][col].getType()])
                            {
                                if(agentgrid[row][col].getDisease()!=null && r.nextInt((int)(options.getDiseaseData(0,agentgrid[row-1][col].getType())*100))==0)
                                {
                                    agentgrid[row-1][col].gainDisease(agentgrid[row][col].getDisease());
                                }
                                agentgrid[row][col] = null;
                            }
                        }
                    }
                }
            }
        }
    }

    public void iterate () //Method for updating objects per tick
    {
        for (int row = 0 ; row < agentgrid.length ; row++) //loop through elements of array
        {
            for (int col = 0 ; col < agentgrid [0].length ; col++)
            {
                if(agentgrid [row][col] != null)
                {
                    agentgrid [row][col].iterate(); // iterate every element
                }
                if(envgrid [row][col] != null)
                {
                    envgrid [row][col].iterate(); // iterate every element
                }
            }
        }
        for (int row = 0 ; row < envgrid.length ; row++) //loop through elements of array
        {
            for (int col = 0 ; col < envgrid[0].length ; col++)
            {
                if(envgrid[row][col] != null && envgrid[row][col].getType() == 0) //If object is dead
                {
                    envgrid[row][col] = null; //Delete object
                }
                if(agentgrid[row][col] != null && agentgrid[row][col].getType() == 0) //If object is dead
                {
                    agentgrid[row][col] = null;//Delete object
                }
            }
        }
    }

    public void advance () //method to move agents
    {
        Agent nextagent[][] = new Agent [agentgrid.length] [agentgrid[0].length]; // create next generation of life forms
        for (int row = 0 ; row < agentgrid.length ; row++)
        {
            for (int col = 0 ; col < agentgrid[0].length ; col++)
            {
                Display newtile = null;  //Environment tile that agent advances to
                int direction = 0; //Direction that agent moves
                if(agentgrid[row][col] != null)
                {
                    agentgrid [row][col].setDirection(0);
                    switch(agentgrid [row][col].move(prevmove [row][col])) //Generate random movement for agent with preference given to previous movement
                    {
                        case 1: //if next tile has no possibility of another agent arriving (to avoid overlap of agents) 
                        if((col+1 >= agentgrid[0].length || agentgrid[row][col+1] == null) && (col+2 >= agentgrid[0].length || agentgrid[row][col+2] == null) && (col+1 >= agentgrid[0].length || row+1 >= agentgrid.length || agentgrid[row+1][col+1] == null) && (col+1 >= agentgrid[0].length || row-1 < 0 || agentgrid[row-1][col+1] == null))
                        {
                            if (col+1 < envgrid[0].length)
                            {
                                newtile = envgrid[row][col+1];//Move right
                                direction = 1;
                                agentgrid [row][col].setDirection(1);
                                break;
                            }
                        }
                        case 2:
                        if((row+1 >= agentgrid.length || agentgrid[row+1][col] == null) && (row+2 >= agentgrid.length || agentgrid[row+2][col] == null) && (col+1 >= agentgrid[0].length || row+1 >= agentgrid.length || agentgrid[row+1][col+1] == null) && (col-1 < 0 || row+1 >= agentgrid.length || agentgrid[row+1][col-1] == null))
                        {
                            if (row+1 < agentgrid.length)
                            {
                                newtile = envgrid[row+1][col];//Move down
                                direction = 2;
                                agentgrid [row][col].setDirection(2);
                                break;
                            }
                        }
                        case 3:
                        if((col-1 < 0 || agentgrid[row][col-1] == null) && (col-2 < 0 || agentgrid[row][col-2] == null) && (col-1 < 0 || row+1 >= agentgrid.length || agentgrid[row+1][col-1] == null) && (col-1 < 0 || row-1 < 0 || agentgrid[row-1][col-1] == null))
                        {
                            if (col-1 >= 0)
                            {
                                newtile = envgrid[row][col-1];//Move left
                                direction = 3;
                                agentgrid [row][col].setDirection(3);
                                break;
                            }
                        }
                        case 4:
                        if((row-1 < 0 || agentgrid[row-1][col] == null) && (row-2 < 0 || agentgrid[row-2][col] == null) && (col+1 >= agentgrid[0].length || row-1 < 0 || agentgrid[row-1][col+1] == null) && (row-1 < 0 || col-1 < 0 || agentgrid[row-1][col-1] == null))
                        {
                            if(row-1 >= 0)
                            {
                                newtile = envgrid[row-1][col];//Move up
                                direction = 4;
                                agentgrid [row][col].setDirection(4);
                                break;
                            }
                        }
                    }

                    if(newtile != null) //If there is a food or environment object
                    {
                        if(newtile.getType() == 9 || newtile.getType() == 10) //If there is a physical obstruction
                        {
                            nextagent[row][col] = agentgrid[row][col]; //agent does not move
                        }
                        else //If there is food
                        {
                            if(options.AgentPrey(agentgrid[row][col].getType())[newtile.getType()-1]) //if current agent can eat victim (is predator)
                            {
                                Food f = (Food)newtile; //Food is eaten by agent
                                Agent a = agentgrid[row][col];
                                a.gainEnergy(f.getNutrition()); //Transfer of energy
                                f.loseEnergy();
                                a.gainDisease(f.isDiseased()? f.getDisease():null); //Transfer of diseases
                            }
                            switch (direction) //Agent carries out movement
                            {
                                case 0: //Agent does not move
                                nextagent[row][col] = agentgrid[row][col];
                                break;
                                case 1: //Agent moves right
                                nextagent[row][col+1] = agentgrid[row][col];
                                prevmove[row][col] = 1; //Store movement for next iteration
                                agentgrid[row][col].step(); //Agent loses movement energy
                                break;
                                case 2: //Agent moves down
                                nextagent[row+1][col] = agentgrid[row][col];
                                prevmove[row][col] = 2;
                                agentgrid[row][col].step();
                                break;
                                case 3: //Agent moves left
                                nextagent[row][col-1] = agentgrid[row][col];
                                prevmove[row][col] = 3;
                                agentgrid[row][col].step();
                                break;
                                case 4: //Agent moves up
                                nextagent[row-1][col] = agentgrid[row][col];
                                prevmove[row][col] = 4;
                                agentgrid[row][col].step();
                                break;
                            }
                        }
                    }
                    else
                    {
                        switch (direction) //Agent carries out movement
                        {
                            case 0://Agent does not move
                            nextagent[row][col] = agentgrid[row][col];
                            break;
                            case 1: //Agent moves right
                            nextagent[row][col+1] = agentgrid[row][col];
                            prevmove[row][col] = 1;
                            agentgrid[row][col].step();
                            break;
                            case 2://Agent moves down
                            nextagent[row+1][col] = agentgrid[row][col];
                            prevmove[row][col] = 2;
                            agentgrid[row][col].step();
                            break;
                            case 3: //Agent moves left
                            nextagent[row][col-1] = agentgrid[row][col];
                            prevmove[row][col] = 3;
                            agentgrid[row][col].step();
                            break;
                            case 4://Agent moves up
                            nextagent[row-1][col] = agentgrid[row][col];
                            prevmove[row][col] = 4;
                            agentgrid[row][col].step();
                            break;
                        }
                    }
                }
            }
        }
        agentgrid = nextagent; // update life forms
        eat(); //Have agents eat one another
    }

    //The method that counts the number of each agent in the grid 
    public void popcalc ()
    {
        int counter1 = 0; // number of agent1
        int counter2 = 0; // number of agent2
        int counter3 = 0; // number of agent3
        int counter4 = 0; // number of agent4
        for (int row = 0 ; row < agentgrid.length ; row++)// go through each row 
        {
            for (int col = 0 ; col < agentgrid[0].length ; col++)// go through columns 
            {
                if (agentgrid[row][col] != null)// if cell is not empty
                {
                    switch(agentgrid[row][col].getType())// get type of agent
                    {
                        case 1: // if it's agent1
                        counter1++; // update counter
                        break; 
                        case 2:// if it's agent2
                        counter2++;// update counter
                        break;
                        case 3:// if it's agent3
                        counter3++;// update counter
                        break;
                        case 4:// if it's gent4
                        counter4++;// update counter
                        break;
                    }
                }
            }
        }
        // add these populations at this generation to their corresponding arraylist
        pop1.add(counter1);
        pop2.add(counter1);
        pop3.add(counter1);
        pop4.add(counter1);
    }

    // returns pop1, pop2, pop3, and pop4
    public ArrayList<Integer> getpop1 ()
    {
        return pop1; 
    }

    public ArrayList<Integer> getpop2 ()
    {
        return pop2; 
    }

    public ArrayList<Integer> getpop3 ()
    {
        return pop3; 
    }

    public ArrayList<Integer> getpop4 ()
    {
        return pop4; 
    }

    public boolean  overCheck() // method that checks for overpopulation 
    {
        int counter = 0, counter1 = 0; 

        for (int i = 0;i<envgrid.length;i++) //goes through envgrid to see how many spots are filled 
        {
            for (int j = 0;j<envgrid[0].length;j++)
            {
                if (envgrid[i][j] != null)
                    counter ++; 
            }
        }

        for (int r = 0;r<agentgrid.length;r++)//goes through agentgrid to see how many spots are filled 
        {
            for (int c = 0;c<agentgrid[0].length;c++)
            {
                if (agentgrid[r][c] != null)
                    counter1++; 
            }

        }

        if (counter > (envgrid.length*envgrid[0].length*0.94) || counter1>(agentgrid.length*agentgrid[0].length*0.94))//if they are more than 94% full, it is overpopulated 
            return true; 
        else 
            return false; 
    }
}