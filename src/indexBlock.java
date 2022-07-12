package IndexedAllocation;


import IndexedAllocation.Block;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class indexBlock extends Block 
{
    
    private ArrayList<Integer> indexes ; //to set index blocks for a given file
    
    public indexBlock ()
    {
        this.indexes = new ArrayList<>(10); //(10) to minimize the time complexity of shrinking the size
    }
    public void pushindexex (int index)
    {
        this.indexes.add(index);
    }
    public ArrayList<Integer> returnIndexex ()
    {
        return this.indexes ;
    }
    public String IndexestoString ()
    {
       return this.indexes.toString();
    }    
}
