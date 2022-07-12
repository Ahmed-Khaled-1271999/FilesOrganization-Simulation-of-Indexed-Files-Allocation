/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IndexedAllocation;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/**
 *
 * @author Ahmed Khaled
 */

/////////-------->               VERY IMPORTANT: to get a clear Result Run the Program more than once                      <-------//////////////
/////////-------->                                                                                                         <-------//////////////
/////////-------->                                                                                                         <-------//////////////
/////////-------->                                                                                                         <-------//////////////
/////////-------->                                                                                                         <-------//////////////
/////////-------->                                                                                                         <-------//////////////
/////////-------->                                                                                                         <-------//////////////

public class IndexedAllocation 
{
    
    // Function to return the number of 
    // empty Blocks from given memory 
    public static int getEmptyBlocks (Block[] Drive)
    {
        int sum = 0 ;
        for (Block b : Drive )
        {
            if (b.IsEmpty()==true)
                sum+=1;
        }
        return sum ;
    }
    
    // This function will generate random indexes 
    // from empty memory slots to test indexing 
    public static int generateIndex (Block[] Drive)
    {
        //when it generate all 20 slots indexes it will return -1
        int index =-1; 
        // Check if memory is full 
        Random rand = new Random ();
        if (!(getEmptyBlocks(Drive)==0))
        {
            // Here it will generate index until 
            // the memory block at generated 
            // index is found to be empty 
            do
            {
                index = rand.nextInt(Drive.length); //will generate index between 0 throght Block numbers in drive -1
                index = abs(index);  
            }while( !Drive[index].IsEmpty());
        }
        return index;
    }
    // This function will return if the file 
    // exists in a given memory 
    public static boolean IsFileExist (Block[]Drive , String fileName)
    {
        boolean flag=false;
        for (Block b : Drive )
        {
            if (b.getFile()!=null)
            {
                if (b.getFileName().equals(fileName))
                     return true ; //exit from the method with true
            }
        }
       return flag;
    }
    // This function will set file in 
    // Drive and push indexes wwhere the file allocated in index block  
    public static void setIndexedMemory (Block[] Drive ,indexBlock indexBlock , File file  )
    {
        int index  ;

        if (getEmptyBlocks(Drive)>=file.size )
        {
            for (int i=0 ; i<file.size ; i++)
            {
                // Generate random empty index 
                index=generateIndex(Drive);
                // Push the file to memory 
                Drive[index].setFile(file);
                // Push the index to index Block 
                indexBlock.pushindexex(index+1);
            }
            System.out.println("File"+"   " +file.name+"  "+"has succesfully Allocated");
        }
        else
        {
            System.out.println("no enough space !");
            indexBlock.setEmpty(); 

        }
               
        
    }
    //this function will take care of the whole resposibillity of the Allocation 
    public static void indexedAllocation( Block[] Drive , File file ) //the file here to allocate must be a new file (not exist before)
    {
        if (IsFileExist(Drive , file.name))
            System.out.println("File is Already exists !");
        else
        {
             int index = generateIndex(Drive);
         
            //create and assign index block for a file 
            indexBlock IB = new indexBlock();
            //the block of indexes becomes relevant to this file
            IB.setFile(file);
            //let the file keeps its index block , and if index = -1 you shoulg free the IB 
            file.indexBlock=index; 
            if(index!=-1) // never call setIndexedMemory with -1 generated index 
            {
                //the index block occupied an existing block on the drive (overwrite it) 
                Drive[index]=IB; 
                //this function will Allocate the file into Blocks and save the file locations 
                setIndexedMemory(Drive,IB,file);  
            }
            else
            {
            System.out.println("no enough space !!!!!!"); //////////////////////////////////////////////need to think about again , and every function that recieve -1 index should modified ///////////////////////
            IB.setEmpty(); //makes this location in the Drive invisible it can reuse for a new block
            }
        }
    }
    //this method is for dlete files by its name 
    public static void deleteFile (Block [] Drive , String fileName)
    {
        File file = null;
           for(Block b : Drive )
           {
              file =  b.getFile();
              if( file.name== fileName)
               break;
           }
        //Allah يسترfrom casting exception
        //get the indexBlock among other Data Blocks on the Drive
        indexBlock IB =(indexBlock)Drive[file.indexBlock];
        //prepare an array to go throught
        ArrayList<Integer> theIndexes = IB.returnIndexex();
        //clean empty spaces 
        theIndexes.trimToSize();
        //loop through the index Block
        for(int i=0 ; i<theIndexes.size() ; i++ )
        {
            //take the Data Block indexes index by index
            int anIndex =theIndexes.get(i);
            //set the blocks free block by block
            Drive[anIndex].setEmpty();
        }
        //the space of the index block should go away
        IB.setEmpty();        
        System.out.println("File"+"   " +file.name+"  "+"has successfully Deleted ");
    }
    //show the Header on each call , i can not make it appears once
    public static void showHeader ()
    {
        System.out.println("+-------------+---------------+------------------------------------------------------------+");
        System.out.println("|   FileName  |  Index BlocK  |                          Data Blocks                       |");
        System.out.println("+-------------+---------------+------------------------------------------------------------+");
    }
    //show on One row the information abot One file Alloction
    public static void showFileAllocation(Block[] Drive , File file) 
    {
        // important to handle if index here is -1
        //the second format will printed if the file parameter recieves a file whose size bigger than empty blocks and that to not print to such a file an index block id <--- genuis 
        if(file.indexBlock!=-1 ) 
        {
        int index;   
        index = file.indexBlock;
        indexBlock IB = (indexBlock)Drive[index]; //the cast here is perfectly legal <---------------------------------------------- مهم 
        System.out.println("+-------------+---------------+------------------------------------------------------------+");
        System.out.println("|    "+file.name+"  |  "+(file.indexBlock+1)+"      |       "+IB.IndexestoString()+"  |  ");
        System.out.println("+-------------+---------------+------------------------------------------------------------+");
        }
        else
        {
           showNaNTable(file);
        }
    }
    // shortcut for usage in special case
    public static void showNaNTable(File file)
    {
            System.out.println("+-------------+---------------+------------------------------------------------------------+");
            System.out.println("|    "+file.name+"  |  "+Double.NaN+"      |       "+"          -           "+"  |  ");
            System.out.println("+-------------+---------------+------------------------------------------------------------+");
    }
    
    //show Header and the file Aloocation 
    public static void showAllocationTable(Block [] Drive , File file)
    {
        //showHeader();
        showFileAllocation(Drive,file);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // Driver Code 
        
        
        //********************************************** Interactive Mode ****************************************************//
        //Assume the size of the Drive 
        final int SIZE = 20 ;
        //establish the Drive
        Block[] Drive = new Block[SIZE]; 
        //fill the Drive with empty Blocks
        for(int i = 0 ; i<Drive.length ; i++)
            Drive[i] = new Block();
        //create a list of files to easily remove deleted files and traverse the files to print the Data Blocks Allocation table
        ArrayList<File> filesBuffer = new ArrayList<>(3);
        
        
        //Show a brief Describtion of the Implementation 
        System.out.println("Hello, This Program provide an Implementation to simulate one of the File Allocation methods \"Indexed Allocation\" ");
        System.out.println("and, this is the Interactive Mode of my implementation in which you are supposed to create a file by providing the program with its data required for the Allocation");
        System.out.println("you can create one file at a time. you should supply the File Name.extention like \"Book.pdf\" and the File size in Blocks like 5 Blocks.");
        System.out.println("Once you entered the file will store on the Drives Blocks and an Allocation table will apear to let you know where the file Blocks is scattered by printing the Blocks ids");
        System.out.println("and it is very important to know that in this method  \"Indexed Allocation\" the Data Blocks that the file occupies is stored in an independent Block on the Drive called Index Block.");
        System.out.println("That means if the file size is 5 Blocks it will store in 6 Blocks! five for the Data Blocks and one for the Index Block associated with that file. Do not be confused.");
        System.out.println("it is assumed that the Drive has just 20 Blocks ,as soon as you fill all blocks the program wont allocate any other files and print Nan Row");
        
        
        
        // flag to control the loop
        String flag;
        do
        {
            System.out.println("*********************************************************");
            //show the remaining memory block
            System.out.println("Remaining memory .. " + getEmptyBlocks(Drive)+ " Blocks");
            // Take The File Data (name and size ) From The User
            Scanner input = new Scanner (System.in);
            System.out.println(" Entre The File Name like \"Report.docx\" ");
            String name = input.nextLine();
            System.out.println("Entre the file size in Blocks");
            int size = input.nextInt();
            //create a file fits input data
            File file = new File(name,size);
            
            
            //the Logic of the show Allocation table is genuis <3 !
            if (file.size+1 <= getEmptyBlocks(Drive))
            {
                //Allocate the file on the Drive
                indexedAllocation(Drive,file);
                //show the Drive state again after allocation
                System.out.println("Remaining memory .. " + getEmptyBlocks(Drive)+ " Blocks");
                //Add the file into temporarily Buffer for printing functionallity
                filesBuffer.add(file);
                //show indexed Allocation table all at one command
                showHeader();
                for(File f : filesBuffer)
                    showAllocationTable(Drive , f); 
            }
            else
            {
                //Allocate the file on the Drive
                indexedAllocation(Drive,file);
                //show the Drive state again after allocation
                System.out.println("Remaining memory .. " + getEmptyBlocks(Drive)+ " Blocks");
                showHeader();
                for(File f : filesBuffer)
                    showAllocationTable(Drive , f); 
                showNaNTable(file);
            }
            //flag 
            Scanner in = new Scanner (System.in);
            System.out.println("Add another File ? (yes|no) ");
            flag = in.nextLine();
            }while( flag.equalsIgnoreCase("yes"));
        
        
        
        
        
        //********************************************************************* Explicitly Mode ********************************************************************///
        /*
        //Sizes Parameter fot testCases
        /*
        /////////////////////Test Case 1 //////////////////////
        final int size1 = 3 ;
        final int size2 = 2 ;
        final int size3 = 5 ;
        //final int size4=0 ;
        ///////////////////////////////////////////////////////
        */
        
        /*
        /////////////////////Test Case 2 //////////////////////
        final int size1 = 8 ;
        final int size2 = 1 ;
        final int size3 = 4 ;
        //final int size4=0;
        ///////////////////////////////////////////////////////
        */
        
        /*
        /////////////////////Test Case 3 //////////////////////
        final int size1 = 14;
        final int size2 = 4 ;
        final int size3 = 2 ;
        //final int size4=0;
        ///////////////////////////////////////////////////////
        */
        
        /*
        /////////////////////Test Case 4 //////////////////////
        final int size1 = 6 ;
        final int size2 = 6 ;
        final int size3 = 5 ;
        //final int size4=0;
        ///////////////////////////////////////////////////////
        */
        
        /*
        /////////////////////Test Case 2 //////////////////////
        final int size1 = 1 ;
        final int size2 = 2 ;
        final int size3 = 3 ;
        //final int size4=5 ;
        ///////////////////////////////////////////////////////
        */
        
        /*
        //Assume the size of the Drive 
        final int SIZE = 20 ;
        //establish the Drive
        Block[] Drive = new Block[SIZE]; 
        //fill the Drive with empty Blocks
        for(int i = 0 ; i<Drive.length ; i++)
            Drive[i] = new Block();
        //create a list of files to easily remove deleted files and traverse the files to print the Data Blocks Allocation table
        ArrayList<File> filesBuffer = new ArrayList<>(3);
        */
        
        /*
        File file1 = new File ("home.docx",size1); 
        File file2 = new File ("code.java",size2);
        File file3 = new File ("img.png"  ,size3); 
        */
        
        
        
        /*
        //Allocate the file "you should allocate one by one to test if it epmty"
        //then print the current space each time
        System.out.println("Remaining memory .. " + getEmptyBlocks(Drive)+ " Blocks");
        filesBuffer.add(file1);
        indexedAllocation(Drive,file1);
        System.out.println("Remaining memory .. " + getEmptyBlocks(Drive)+ " Blocks");
        */
        /*
        filesBuffer.add(file2);
        indexedAllocation(Drive,file2);
        System.out.println("Remaining memory .. " + getEmptyBlocks(Drive)+ " Blocks");
        */
        
        /*
        filesBuffer.add(file3);
        indexedAllocation(Drive,file3);
        System.out.println("Remaining memory .. " + getEmptyBlocks(Drive)+ " Blocks");
        */
        
        /*
        File file4 = new File();
        ////set Data of a second file
        file4.name="story.txt";
        file4.size=size4;
        //one more
        filesBuffer.add(file4);
        indexedAllocation(Drive,file4);
        System.out.println("Remaining memory .. " + getEmptyBlocks(Drive)+ " Blocks");
        */
        
        
        /*
        //show indexed Allocation table all at one command
        showHeader();
        for(File f : filesBuffer)
            showAllocationTable(Drive , f);
        */
        
        
        /*  
        //// Delete a file by name 
        deleteFile(Drive,"Story.txt");
        
        //remove the file from Files Buffer
        for (File f : filesBuffer)
        {
            if (f.name=="Story.txt")
                filesBuffer.remove(f);
        }
        System.out.println("Remaining memory .. " + getEmptyBlocks(Drive)+ " Blocks");
        
        
        //show indexed Allocation table all at one command
        for(File f : filesBuffer)
            showAllocationTable(Drive , f);
        */   
}
}
