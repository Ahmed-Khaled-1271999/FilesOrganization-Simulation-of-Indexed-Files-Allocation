/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IndexedAllocation;

/**
 *
 * @author Ahmed Khaled
 */
public class File 
{
    public
        String name;
        int size; //takes the size of the file an integral blocks not in KB.
        int indexBlock= -1 ; //each file keeps his index block
        
        public File()
        {
            
        }
        public File (String name , int size)
        {
            this.name=name;
            this.size=size;
        }
}
