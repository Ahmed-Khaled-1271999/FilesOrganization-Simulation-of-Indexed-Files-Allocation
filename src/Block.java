/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IndexedAllocation;

/**
 *
 * @author DELL
 */
public class Block 
{
    private boolean occupied ;
    private File file;
    
    
    public
        
        Block()
        {
            this.occupied=false;
            this.file=null;
        }

        void setFile(File file)
        {
            this.file = file;
            this.occupied = true; 
        }
        File getFile ()
        {
            return this.file;
        }
        String getFileName()
        {
            return this.file.name;
        }
        boolean IsEmpty()
        {
            if (this.occupied==false)
                return true;
            return false ;        
        }
        //make the block ready for overwriting
        void setEmpty()
        {
            this.occupied = false;
        }
}
