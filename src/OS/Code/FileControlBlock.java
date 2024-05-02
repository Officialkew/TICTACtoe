package OS.Code;

/**
 * Created by Kaheem on 11/4/2017.
 */
public class FileControlBlock {

    private int fileID;
    private int processID;
    private String data;
    private int start;
    private int end;

    public FileControlBlock(){

        this.fileID=this.processID=this.end=this.start;
        this.data="";

    }

   public FileControlBlock( int fileID,int processID,String data,int start, int end)
   {
       this.fileID=fileID;
       this.processID=processID;
       this.data=data;
       this.start=start;
       this.end=end;

   }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }



}
