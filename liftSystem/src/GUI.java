import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.JLabel;

public class GUI extends javax.swing.JFrame{
    
    public GUI() {
        initComponents();
        // Set Background COLOR: WHITE
        getContentPane().setBackground(Color.orange);
        this.setBounds(0, 0, 1550, 1000);
        File file = new File("F:\\Studies\\Sem-3\\DSA Lab\\liftSystem\\src\\output.txt"); 
          
        if(file.delete()) 
        {      
        } 
        else
        { 
        } 

    } 
    public static ArrayList <Pair <Integer,Boolean> > listvy = new ArrayList <Pair <Integer,Boolean> > (); 
    public static ArrayList <Pair <Integer,Boolean> > list_final = new ArrayList <Pair <Integer,Boolean> > ();
    public static boolean exit = false;
    

    public static void execute() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        //System.out.print("Enter name of input file: ");
        //System.out.println(s+" hi ");
        String fileName="F:\\Studies\\Sem-3\\DSA Lab\\liftSystem\\src\\input2.txt";
        ArrayList<Passenger> people=getListOfPassenger(fileName);
        System.out.println("> SIMULATE WORKS!!");
        if(people!=null)
        {
            simulate(people);
        }
        else
        {
            System.out.println("Error occured while reading input file.");
        }
    }

    public static ArrayList<Passenger> getListOfPassenger(String fileName)
    {
        ArrayList<Passenger> arr=new ArrayList<>();
        try 
        {
            BufferedReader input=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            while(input.ready())
            {
                String data=input.readLine();
                 if(!data.startsWith("//")&&data.length()>1)
                 {
                    Scanner scr=new Scanner(data);
                    String name=scr.next();
                    name=name.trim();
                    int entry=scr.nextInt();
                    int exit=scr.nextInt();
                    arr.add(new Passenger(name, entry, exit));
                 }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return arr;
    }
    
    public static void appendStrToFile(String fileName, 
                                       String str) 
    { 
        try { 
  
            // Open given file in append mode. 
            BufferedWriter out = new BufferedWriter( 
                   new FileWriter(fileName, true)); 
            out.write(str); 
            out.close(); 
        } 
        catch (IOException e) { 
            System.out.println("exception occoured" + e); 
        } 
    } 

   public static void simulate(ArrayList<Passenger> list) throws InterruptedException
    {
        int numberOfPeopleRide=0;
        int numberOfPeopleDoNotRide=0;
        int emptyCount=0;
        int fullCount=0;
        
        
        
        boolean direction=true; //for up true for down false
        int targetFloor=-1; //target of lift
        int currentFloor=1;  //start the lift from here
        
        //stack for elevator
        Stack<Passenger> elevator=new Stack<Passenger>();
        // temporary stack
        Stack<Passenger> temp=new Stack<>();
        
        //keep track of number of people with exitfloor
        int target[]=new int[10];
        boolean f = false;
        
        while(!list.isEmpty()||!elevator.isEmpty())
        {
            f = false;
            if(elevator.isEmpty())
            {
               // System.out.println("Elevator is empty. hyf");
                emptyCount++;

            }
            //first check to see if there people who want to exit at current floor
            if(currentFloor==targetFloor)
            {
                listvy.add(new Pair <Integer,Boolean> (currentFloor, true));
                f = true;
                
                //if someone wants to exit
                //remove others in front of them
                while(target[currentFloor-1]!= 0)
                {
                    //remove from elevator
                    Passenger r = elevator.pop();
                    //check if it is the passengers exit
                    if(r.getExitFloor() == currentFloor)
                    {
                        target[currentFloor-1]--;
                        String s = r.getName()+" exit. Exit floor is -->"+r.getExitFloor()+ "\n";
                        System.out.println(r.getName()+" exit. His/her exit floor is--->"+r.getExitFloor()+" His/her total number of temporary exits----> "+r.getTemporaryExit());
                        appendStrToFile("F:\\Studies\\Sem-3\\DSA Lab\\liftSystem\\src\\output.txt", s);
                        instructionField2.append(s);
                    }
                    else
                    {
                        temp.push(r);
                        r.temporaryExit();
                    }
                }
                
                //load the elevator with temporary exit person
                while(!temp.isEmpty())
                {
                    elevator.push(temp.pop());
                }
            }
            
            //now come to those who were waiting for lift
            if(elevator.size()==5)
            {
                System.out.println("Elevator is full. ");
                fullCount++;
                //these people have to travel without elevator
                while(!list.isEmpty()&&list.get(0).getEnteryFloor()==currentFloor)
                {
                    System.out.println(list.get(0).getName()+" can't get into elevator so he/she take stairs. His/Her destination is floor: "+list.get(0).getExitFloor());
                    numberOfPeopleDoNotRide++;
                    list.remove(0);
                }
            }
            else
            {
                //fill until elevator become full or waiting people for this floor become zero
                while(elevator.size()<5&&!list.isEmpty()&&list.get(0).getEnteryFloor()==currentFloor)
                {
                    f = true;
                    listvy.add(new Pair <Integer,Boolean> (currentFloor, true));
                    Passenger p = list.remove(0);
                    elevator.push(p);
                    numberOfPeopleRide++;
                    target[p.getExitFloor()-1]++;
                }
                
                //if elevator become full and waiting people are left then they have to take stairs
                if(elevator.size()==5&&!list.isEmpty()&&list.get(0).getEnteryFloor()==currentFloor)
                {
                    fullCount++;
                    System.out.println("Elevator is full.");
                    //these people have to travel without elevator
                     while(!list.isEmpty()&&list.get(0).getEnteryFloor()==currentFloor)
                     {
                            System.out.println(list.get(0).getName()+" can't get into elevator so he/she takes stairs. His/Her destination is floor: "+list.get(0).getExitFloor());
                            numberOfPeopleDoNotRide++;
                            list.remove(0);
                    }
                }
            }
            //find target floor
            if(direction)
            {
                    if(currentFloor==9)
                    {
                        direction=false;
                        for(int i=9;i>=0;i--)
                        {
                             if(target[i]!=0)
                             {
                                 targetFloor=i+1;
                                 break;
                             }
                        }
                        if(f == false){
                           listvy.add(new Pair <Integer,Boolean> (currentFloor, false)); 
                         
                        }
                        currentFloor--;
                        
                        System.out.println("sleep 1");
                        
                        //animation here
                    }
                    else
                    {
                        for(int i=currentFloor-1;i<10;i++)
                        {
                             if(target[i]!=0)
                             {
                                 targetFloor=i+1;
                                 break;
                             }
                        }
                        if(f == false){
                           listvy.add(new Pair <Integer,Boolean> (currentFloor, false)); 
                         
                        }
                        currentFloor++;
                        
                        System.out.println("sleep 2");
                        
                        //animation here
                    }
                    
            }
            else
            {
                    if(currentFloor==0)
                    {
                        direction=true;
                        for(int i=0;i<10;i++)
                        {
                             if(target[i]!=0)
                             {
                                 targetFloor=i+1;
                                 break;
                             }
                        }
                        if(f == false){
                           listvy.add(new Pair <Integer,Boolean> (currentFloor, false)); 
                         
                        }
                        currentFloor++;
                        
                        
                        System.out.println("sleep 3");
                        
                        //aniatio here
                    }
                    else
                    {
                        for(int i=currentFloor-1;i>=0;i--)
                        {
                             if(target[i]!=0)
                             {
                                 targetFloor=i+1;
                                 break;
                             
                             }
                        }
                        if(f == false){
                           listvy.add(new Pair <Integer,Boolean> (currentFloor, false)); 
                         
                        }
                        currentFloor--;
                        
                        System.out.println("sleep 4");
                        
                        //animation here
                   }
           }
           
           //cnt++;
        }
        int arr[] = new int[listvy.size()];
        boolean arr2[] = new boolean[listvy.size()];
        
        int t=0;
        
        for(Pair<Integer, Boolean> p : listvy){
            arr[t] = p.getKey();
            System.out.println(p.getKey() + " hi " + p.getValue());
            arr2[t] = p.getValue();
            t++;
        }
        
        int x,y = 0;
        for(int i=0; i<arr.length; i++){
            if(arr2[i] == true){
                y = arr[i];
                for(int j=i+1; j<arr.length; j++){
                    if(arr2[j] == true){
                        x = arr[j];
                        for(int k = i+1; k<j; k++){
                            if(y<x){
                                if(arr[k] <= y){
                                    arr[k] = -1;
                                }
                            }
                            else if(y>x){
                                if(arr[k] >= y){
                                    arr[k] = -1;
                                }
                            }
                            
                        }
                        
                        break;
                    }
                }
            }
        }
        
        Pair <Integer, Boolean> pp = new Pair<Integer, Boolean>(1, false);
        for(int i=0; i<arr.length; i++){
            if(arr[i]!=-1)
                list_final.add(new Pair <Integer,Boolean> (arr[i], arr2[i])); 
        }

        
      
        
        Lift l = new Lift(LIFT3, list_final, exit, jTextArea1);
        l.start();
        
        System.out.println("\n\n");
        System.out.println("Total number of passenger who took elevator: "+numberOfPeopleRide);
        System.out.println("Total number of passenger who don't take elevator: "+numberOfPeopleDoNotRide);
        System.out.println("Number of time elevator become empty: "+emptyCount);
        System.out.println("Number of time elevator become full: "+fullCount);
        System.out.println("> LIFT LOCATION, X: "+LIFT3.getX()+", Y: "+LIFT3.getY());
        
    }
    
    
    //Do  not change this
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inLiftButtonsPanel = new javax.swing.JPanel();
        inL0button = new javax.swing.JLabel();
        inL8button = new javax.swing.JLabel();
        inL1button = new javax.swing.JLabel();
        inL2button = new javax.swing.JLabel();
        inL3button = new javax.swing.JLabel();
        inL4button = new javax.swing.JLabel();
        inL5button = new javax.swing.JLabel();
        inL6button = new javax.swing.JLabel();
        inL7button = new javax.swing.JLabel();
        inL9button = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        endButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        instructionField = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        instructionField2 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        LIFT3 = new javax.swing.JLabel();
        L23 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        L34 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        L35 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        L36 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        L37 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        L38 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        L53 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        L39 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        L54 = new javax.swing.JPanel();
        CallButtonL9 = new javax.swing.JLabel();
        L55 = new javax.swing.JPanel();
        CallButtonL8 = new javax.swing.JLabel();
        L56 = new javax.swing.JPanel();
        CallButtonL7 = new javax.swing.JLabel();
        L57 = new javax.swing.JPanel();
        CallButtonL6 = new javax.swing.JLabel();
        L58 = new javax.swing.JPanel();
        CallButtonL3 = new javax.swing.JLabel();
        L59 = new javax.swing.JPanel();
        CallButtonL2 = new javax.swing.JLabel();
        L60 = new javax.swing.JPanel();
        CallButtonL1 = new javax.swing.JLabel();
        L61 = new javax.swing.JPanel();
        CallButtonL55 = new javax.swing.JLabel();
        L62 = new javax.swing.JPanel();
        CallButtonL4 = new javax.swing.JLabel();
        L63 = new javax.swing.JPanel();
        CallButtonL0 = new javax.swing.JLabel();
        L40 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        L41 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1550, 835));
        setSize(new java.awt.Dimension(1550, 835));

        inLiftButtonsPanel.setBackground(new java.awt.Color(239, 84, 85));

        inL0button.setBackground(new java.awt.Color(43, 50, 82));
        inL0button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL0button.setForeground(new java.awt.Color(250, 215, 68));
        inL0button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL0button.setText("G");
        inL0button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL0button.setOpaque(true);
        inL0button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL0buttonMouseClicked(evt);
            }
        });

        inL8button.setBackground(new java.awt.Color(43, 50, 82));
        inL8button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL8button.setForeground(new java.awt.Color(250, 215, 68));
        inL8button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL8button.setText("L8");
        inL8button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL8button.setOpaque(true);
        inL8button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL8buttonMouseClicked(evt);
            }
        });

        inL1button.setBackground(new java.awt.Color(43, 50, 82));
        inL1button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL1button.setForeground(new java.awt.Color(250, 215, 68));
        inL1button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL1button.setText("L1");
        inL1button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL1button.setOpaque(true);
        inL1button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL1buttonMouseClicked(evt);
            }
        });

        inL2button.setBackground(new java.awt.Color(43, 50, 82));
        inL2button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL2button.setForeground(new java.awt.Color(250, 215, 68));
        inL2button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL2button.setText("L2");
        inL2button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL2button.setOpaque(true);
        inL2button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL2buttonMouseClicked(evt);
            }
        });

        inL3button.setBackground(new java.awt.Color(43, 50, 82));
        inL3button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL3button.setForeground(new java.awt.Color(250, 215, 68));
        inL3button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL3button.setText("L3");
        inL3button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL3button.setOpaque(true);
        inL3button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL3buttonMouseClicked(evt);
            }
        });

        inL4button.setBackground(new java.awt.Color(43, 50, 82));
        inL4button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL4button.setForeground(new java.awt.Color(250, 215, 68));
        inL4button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL4button.setText("L4");
        inL4button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL4button.setOpaque(true);
        inL4button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL4buttonMouseClicked(evt);
            }
        });

        inL5button.setBackground(new java.awt.Color(43, 50, 82));
        inL5button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL5button.setForeground(new java.awt.Color(250, 215, 68));
        inL5button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL5button.setText("L5");
        inL5button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL5button.setOpaque(true);
        inL5button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL5buttonMouseClicked(evt);
            }
        });

        inL6button.setBackground(new java.awt.Color(43, 50, 82));
        inL6button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL6button.setForeground(new java.awt.Color(250, 215, 68));
        inL6button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL6button.setText("L6");
        inL6button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL6button.setOpaque(true);
        inL6button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL6buttonMouseClicked(evt);
            }
        });

        inL7button.setBackground(new java.awt.Color(43, 50, 82));
        inL7button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL7button.setForeground(new java.awt.Color(250, 215, 68));
        inL7button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL7button.setText("L7");
        inL7button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL7button.setOpaque(true);
        inL7button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL7buttonMouseClicked(evt);
            }
        });

        inL9button.setBackground(new java.awt.Color(43, 50, 82));
        inL9button.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        inL9button.setForeground(new java.awt.Color(250, 215, 68));
        inL9button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inL9button.setText("L9");
        inL9button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inL9button.setOpaque(true);
        inL9button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inL9buttonMouseClicked(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(43, 50, 82));
        jButton1.setFont(new java.awt.Font("Bahnschrift", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(250, 215, 68));
        jButton1.setText("EMERGENCY STOP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setBackground(new java.awt.Color(239, 84, 85));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout inLiftButtonsPanelLayout = new javax.swing.GroupLayout(inLiftButtonsPanel);
        inLiftButtonsPanel.setLayout(inLiftButtonsPanelLayout);
        inLiftButtonsPanelLayout.setHorizontalGroup(
            inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inLiftButtonsPanelLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inLiftButtonsPanelLayout.createSequentialGroup()
                        .addGroup(inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inLiftButtonsPanelLayout.createSequentialGroup()
                                .addComponent(inL8button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(inL7button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(inLiftButtonsPanelLayout.createSequentialGroup()
                                .addComponent(inL6button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(inL5button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inLiftButtonsPanelLayout.createSequentialGroup()
                                .addComponent(inL4button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(inL3button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(inLiftButtonsPanelLayout.createSequentialGroup()
                                .addComponent(inL2button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(inL1button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inLiftButtonsPanelLayout.createSequentialGroup()
                        .addGroup(inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inLiftButtonsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inLiftButtonsPanelLayout.createSequentialGroup()
                        .addComponent(inL0button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inLiftButtonsPanelLayout.createSequentialGroup()
                        .addComponent(inL9button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))))
        );
        inLiftButtonsPanelLayout.setVerticalGroup(
            inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inLiftButtonsPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inL9button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inL7button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL8button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inL5button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL6button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inL3button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL4button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inLiftButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inL1button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inL2button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addComponent(inL0button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107))
        );

        endButton.setBackground(new java.awt.Color(244, 169, 80));
        endButton.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        endButton.setForeground(new java.awt.Color(47, 60, 126));
        endButton.setText("PROCESS");
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonActionPerformed(evt);
            }
        });

        instructionField.setBackground(new java.awt.Color(22, 27, 33));
        instructionField.setColumns(20);
        instructionField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        instructionField.setForeground(new java.awt.Color(244, 169, 80));
        instructionField.setRows(5);
        instructionField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setViewportView(instructionField);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(22, 27, 33));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("-   Input   -");

        jLabel2.setBackground(new java.awt.Color(22, 27, 33));
        jLabel2.setForeground(new java.awt.Color(47, 60, 126));
        jLabel2.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(22, 27, 33));
        jLabel5.setForeground(new java.awt.Color(47, 60, 126));
        jLabel5.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(22, 27, 33));
        jLabel6.setForeground(new java.awt.Color(47, 60, 126));
        jLabel6.setOpaque(true);

        jLabel7.setBackground(new java.awt.Color(22, 27, 33));
        jLabel7.setForeground(new java.awt.Color(47, 60, 126));
        jLabel7.setOpaque(true);

        instructionField2.setBackground(new java.awt.Color(22, 27, 33));
        instructionField2.setColumns(20);
        instructionField2.setFont(new java.awt.Font("MS UI Gothic", 3, 18)); // NOI18N
        instructionField2.setForeground(new java.awt.Color(244, 169, 80));
        instructionField2.setRows(5);
        instructionField2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane3.setViewportView(instructionField2);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(22, 27, 33));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("-   Output   -");

        jLabel9.setBackground(new java.awt.Color(22, 27, 33));
        jLabel9.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(22, 27, 33));
        jLabel11.setOpaque(true);

        jLabel12.setBackground(new java.awt.Color(22, 27, 33));
        jLabel12.setOpaque(true);

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(22, 27, 33));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Name");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(22, 27, 33));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Entry Floor");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(22, 27, 33));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Exit Floor");

        jLabel13.setBackground(new java.awt.Color(22, 27, 33));
        jLabel13.setOpaque(true);

        LIFT3.setBackground(new java.awt.Color(60, 26, 91));
        LIFT3.setForeground(new java.awt.Color(255, 255, 255));
        LIFT3.setOpaque(true);

        L23.setBackground(new java.awt.Color(29, 27, 27));

        jLabel44.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(236, 77, 55));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("L 6");

        javax.swing.GroupLayout L23Layout = new javax.swing.GroupLayout(L23);
        L23.setLayout(L23Layout);
        L23Layout.setHorizontalGroup(
            L23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L23Layout.createSequentialGroup()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L23Layout.setVerticalGroup(
            L23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L23Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        L34.setBackground(new java.awt.Color(236, 77, 55));
        L34.setForeground(new java.awt.Color(236, 77, 55));

        jLabel47.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(29, 27, 27));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("L 5");

        javax.swing.GroupLayout L34Layout = new javax.swing.GroupLayout(L34);
        L34.setLayout(L34Layout);
        L34Layout.setHorizontalGroup(
            L34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L34Layout.createSequentialGroup()
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L34Layout.setVerticalGroup(
            L34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L34Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        L35.setBackground(new java.awt.Color(29, 27, 27));

        jLabel48.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(236, 77, 55));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("L 8");

        javax.swing.GroupLayout L35Layout = new javax.swing.GroupLayout(L35);
        L35.setLayout(L35Layout);
        L35Layout.setHorizontalGroup(
            L35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L35Layout.createSequentialGroup()
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L35Layout.setVerticalGroup(
            L35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L35Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        L36.setBackground(new java.awt.Color(236, 77, 55));
        L36.setForeground(new java.awt.Color(236, 77, 55));

        jLabel49.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(29, 27, 27));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("L 3");

        javax.swing.GroupLayout L36Layout = new javax.swing.GroupLayout(L36);
        L36.setLayout(L36Layout);
        L36Layout.setHorizontalGroup(
            L36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L36Layout.createSequentialGroup()
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L36Layout.setVerticalGroup(
            L36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L36Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        L37.setBackground(new java.awt.Color(29, 27, 27));

        jLabel50.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(236, 77, 55));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("L 4");

        javax.swing.GroupLayout L37Layout = new javax.swing.GroupLayout(L37);
        L37.setLayout(L37Layout);
        L37Layout.setHorizontalGroup(
            L37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L37Layout.createSequentialGroup()
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L37Layout.setVerticalGroup(
            L37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L37Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        L38.setBackground(new java.awt.Color(29, 27, 27));

        jLabel51.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(236, 77, 55));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("L 2");

        javax.swing.GroupLayout L38Layout = new javax.swing.GroupLayout(L38);
        L38.setLayout(L38Layout);
        L38Layout.setHorizontalGroup(
            L38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L38Layout.createSequentialGroup()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L38Layout.setVerticalGroup(
            L38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L38Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        L53.setBackground(new java.awt.Color(236, 77, 55));
        L53.setForeground(new java.awt.Color(236, 77, 55));

        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(29, 27, 27));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("L 1");

        javax.swing.GroupLayout L53Layout = new javax.swing.GroupLayout(L53);
        L53.setLayout(L53Layout);
        L53Layout.setHorizontalGroup(
            L53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L53Layout.createSequentialGroup()
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L53Layout.setVerticalGroup(
            L53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L53Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        L39.setBackground(new java.awt.Color(236, 77, 55));
        L39.setForeground(new java.awt.Color(236, 77, 55));

        jLabel53.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(29, 27, 27));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("L 9");

        javax.swing.GroupLayout L39Layout = new javax.swing.GroupLayout(L39);
        L39.setLayout(L39Layout);
        L39Layout.setHorizontalGroup(
            L39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L39Layout.createSequentialGroup()
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L39Layout.setVerticalGroup(
            L39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L39Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        L54.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL9.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL9.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL9.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL9.setText("O");
        CallButtonL9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL9.setOpaque(true);
        CallButtonL9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L54Layout = new javax.swing.GroupLayout(L54);
        L54.setLayout(L54Layout);
        L54Layout.setHorizontalGroup(
            L54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L54Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L54Layout.setVerticalGroup(
            L54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L54Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L55.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL8.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL8.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL8.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL8.setText("O");
        CallButtonL8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL8.setOpaque(true);
        CallButtonL8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L55Layout = new javax.swing.GroupLayout(L55);
        L55.setLayout(L55Layout);
        L55Layout.setHorizontalGroup(
            L55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L55Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L55Layout.setVerticalGroup(
            L55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L55Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L56.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL7.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL7.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL7.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL7.setText("O");
        CallButtonL7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL7.setOpaque(true);
        CallButtonL7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L56Layout = new javax.swing.GroupLayout(L56);
        L56.setLayout(L56Layout);
        L56Layout.setHorizontalGroup(
            L56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L56Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L56Layout.setVerticalGroup(
            L56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L56Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L57.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL6.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL6.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL6.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL6.setText("O");
        CallButtonL6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL6.setOpaque(true);
        CallButtonL6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L57Layout = new javax.swing.GroupLayout(L57);
        L57.setLayout(L57Layout);
        L57Layout.setHorizontalGroup(
            L57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L57Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L57Layout.setVerticalGroup(
            L57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L57Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L58.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL3.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL3.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL3.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL3.setText("O");
        CallButtonL3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL3.setOpaque(true);
        CallButtonL3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L58Layout = new javax.swing.GroupLayout(L58);
        L58.setLayout(L58Layout);
        L58Layout.setHorizontalGroup(
            L58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L58Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L58Layout.setVerticalGroup(
            L58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L58Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L59.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL2.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL2.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL2.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL2.setText("O");
        CallButtonL2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL2.setOpaque(true);
        CallButtonL2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L59Layout = new javax.swing.GroupLayout(L59);
        L59.setLayout(L59Layout);
        L59Layout.setHorizontalGroup(
            L59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L59Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L59Layout.setVerticalGroup(
            L59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L59Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L60.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL1.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL1.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL1.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL1.setText("O");
        CallButtonL1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL1.setOpaque(true);
        CallButtonL1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L60Layout = new javax.swing.GroupLayout(L60);
        L60.setLayout(L60Layout);
        L60Layout.setHorizontalGroup(
            L60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L60Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L60Layout.setVerticalGroup(
            L60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L60Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L61.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL55.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL55.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL55.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL55.setText("O");
        CallButtonL55.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL55.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL55.setOpaque(true);
        CallButtonL55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL55MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L61Layout = new javax.swing.GroupLayout(L61);
        L61.setLayout(L61Layout);
        L61Layout.setHorizontalGroup(
            L61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L61Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL55, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L61Layout.setVerticalGroup(
            L61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L61Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL55, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L62.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL4.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL4.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL4.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL4.setText("O");
        CallButtonL4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL4.setOpaque(true);
        CallButtonL4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L62Layout = new javax.swing.GroupLayout(L62);
        L62.setLayout(L62Layout);
        L62Layout.setHorizontalGroup(
            L62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L62Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L62Layout.setVerticalGroup(
            L62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L62Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L63.setBackground(new java.awt.Color(41, 40, 38));

        CallButtonL0.setBackground(new java.awt.Color(249, 211, 66));
        CallButtonL0.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        CallButtonL0.setForeground(new java.awt.Color(255, 255, 255));
        CallButtonL0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CallButtonL0.setText("O");
        CallButtonL0.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        CallButtonL0.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CallButtonL0.setOpaque(true);
        CallButtonL0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CallButtonL0MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout L63Layout = new javax.swing.GroupLayout(L63);
        L63.setLayout(L63Layout);
        L63Layout.setHorizontalGroup(
            L63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L63Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(CallButtonL0, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        L63Layout.setVerticalGroup(
            L63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L63Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CallButtonL0, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        L40.setBackground(new java.awt.Color(29, 27, 27));
        L40.setForeground(new java.awt.Color(236, 77, 55));

        jLabel54.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(236, 77, 55));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("L 0");

        javax.swing.GroupLayout L40Layout = new javax.swing.GroupLayout(L40);
        L40.setLayout(L40Layout);
        L40Layout.setHorizontalGroup(
            L40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L40Layout.createSequentialGroup()
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L40Layout.setVerticalGroup(
            L40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L40Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        L41.setBackground(new java.awt.Color(236, 77, 55));
        L41.setForeground(new java.awt.Color(236, 77, 55));

        jLabel55.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(29, 27, 27));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("L 7");

        javax.swing.GroupLayout L41Layout = new javax.swing.GroupLayout(L41);
        L41.setLayout(L41Layout);
        L41Layout.setHorizontalGroup(
            L41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(L41Layout.createSequentialGroup()
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );
        L41Layout.setVerticalGroup(
            L41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, L41Layout.createSequentialGroup()
                .addGap(0, 31, Short.MAX_VALUE)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(204, 204, 204)
                                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(18, 18, 18)))
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(endButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(L39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(L35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(L41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(L23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(L34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LIFT3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(L37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(L36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(L38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(L53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(L40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(L54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(inLiftButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(L54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(L55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(L56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(L57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(L34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(81, 81, 81)
                                    .addComponent(L36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(L38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(L53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(L40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LIFT3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(L37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(L61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, 0)
                                            .addComponent(L62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 0, 0)
                                    .addComponent(L58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(L59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(L60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(L63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(L39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(L35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(L41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(L23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(inLiftButtonsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel22)
                                        .addComponent(jLabel23))
                                    .addGap(2, 2, 2)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(46, 46, 46)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(1, 1, 1)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(11, 11, 11)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(24, 24, 24)
                            .addComponent(endButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void inL9buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL9buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t9\n");
    }//GEN-LAST:event_inL9buttonMouseClicked

    private void inL8buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL8buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t8\n");
    }//GEN-LAST:event_inL8buttonMouseClicked

    private void inL7buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL7buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t7\n");
    }//GEN-LAST:event_inL7buttonMouseClicked

    private void inL6buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL6buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t6\n");
    }//GEN-LAST:event_inL6buttonMouseClicked

    private void inL5buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL5buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t5\n");
    }//GEN-LAST:event_inL5buttonMouseClicked

    private void inL4buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL4buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t4\n");
    }//GEN-LAST:event_inL4buttonMouseClicked

    private void inL3buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL3buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t3\n");
    }//GEN-LAST:event_inL3buttonMouseClicked

    private void inL2buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL2buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t2\n");
    }//GEN-LAST:event_inL2buttonMouseClicked

    private void inL1buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL1buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t1\n");
    }//GEN-LAST:event_inL1buttonMouseClicked

    private void inL0buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inL0buttonMouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t0\n");
    }//GEN-LAST:event_inL0buttonMouseClicked

    private void endButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endButtonActionPerformed
        // TODO add your handling code here:
        FileWriter pw = null;
        try {
            pw = new FileWriter ("F:\\Studies\\Sem-3\\DSA Lab\\liftSystem\\src\\input2.txt");
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            instructionField.write(pw);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            execute();
            //  ElevatorStimulation obj = new ElevatorStimulation("F:\\Studies\\Sem-3\\DSA Lab\\liftSystem\\src\\instruction.txt");
        } catch (InterruptedException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_endButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        GUI.exit = true;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CallButtonL9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL9MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t9");
    }//GEN-LAST:event_CallButtonL9MouseClicked

    private void CallButtonL8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL8MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t8");
    }//GEN-LAST:event_CallButtonL8MouseClicked

    private void CallButtonL7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL7MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t7");
    }//GEN-LAST:event_CallButtonL7MouseClicked

    private void CallButtonL6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL6MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t6");
    }//GEN-LAST:event_CallButtonL6MouseClicked

    private void CallButtonL3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL3MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t3");
    }//GEN-LAST:event_CallButtonL3MouseClicked

    private void CallButtonL2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL2MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t2");
    }//GEN-LAST:event_CallButtonL2MouseClicked

    private void CallButtonL1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL1MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t1");
    }//GEN-LAST:event_CallButtonL1MouseClicked

    private void CallButtonL55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL55MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t5");
    }//GEN-LAST:event_CallButtonL55MouseClicked

    private void CallButtonL4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL4MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t4");
    }//GEN-LAST:event_CallButtonL4MouseClicked

    private void CallButtonL0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CallButtonL0MouseClicked
        // TODO add your handling code here:
        instructionField.append("\t\t0");
    }//GEN-LAST:event_CallButtonL0MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CallButtonL0;
    private javax.swing.JLabel CallButtonL1;
    private javax.swing.JLabel CallButtonL10;
    private javax.swing.JLabel CallButtonL2;
    private javax.swing.JLabel CallButtonL3;
    private javax.swing.JLabel CallButtonL4;
    private javax.swing.JLabel CallButtonL5;
    private javax.swing.JLabel CallButtonL55;
    private javax.swing.JLabel CallButtonL6;
    private javax.swing.JLabel CallButtonL7;
    private javax.swing.JLabel CallButtonL8;
    private javax.swing.JLabel CallButtonL9;
    private javax.swing.JPanel L23;
    private javax.swing.JPanel L29;
    private javax.swing.JPanel L31;
    private javax.swing.JPanel L32;
    private javax.swing.JPanel L33;
    private javax.swing.JPanel L34;
    private javax.swing.JPanel L35;
    private javax.swing.JPanel L36;
    private javax.swing.JPanel L37;
    private javax.swing.JPanel L38;
    private javax.swing.JPanel L39;
    private javax.swing.JPanel L40;
    private javax.swing.JPanel L41;
    private javax.swing.JPanel L53;
    private javax.swing.JPanel L54;
    private javax.swing.JPanel L55;
    private javax.swing.JPanel L56;
    private javax.swing.JPanel L57;
    private javax.swing.JPanel L58;
    private javax.swing.JPanel L59;
    private javax.swing.JPanel L60;
    private javax.swing.JPanel L61;
    private javax.swing.JPanel L62;
    private javax.swing.JPanel L63;
    private static javax.swing.JLabel LIFT3;
    private javax.swing.JButton endButton;
    private javax.swing.JLabel inL0button;
    private javax.swing.JLabel inL1button;
    private javax.swing.JLabel inL2button;
    private javax.swing.JLabel inL3button;
    private javax.swing.JLabel inL4button;
    private javax.swing.JLabel inL5button;
    private javax.swing.JLabel inL6button;
    private javax.swing.JLabel inL7button;
    private javax.swing.JLabel inL8button;
    private javax.swing.JLabel inL9button;
    private javax.swing.JPanel inLiftButtonsPanel;
    private javax.swing.JTextArea instructionField;
    public static javax.swing.JTextArea instructionField2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    private void add(Rectangle box2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
