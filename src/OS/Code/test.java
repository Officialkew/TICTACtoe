package OS.Code;
import java.io.*;

/**
 * Created by Kaheem on 11/4/2017.
 */
public class test {


        public static void main(String args[]) throws IOException
        {

            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            int nh,np;
            System.out.println("Enter the number of Holes:");
            nh=Integer.parseInt(br.readLine());
            System.out.println("Enter the number of Processes:");
            np=Integer.parseInt(br.readLine());
            int P[]=new int[np];
            int H[]=new int[nh];
            System.out.println("Enter the size of holes:");
            for(int i=0;i<nh;i++)
            {
                System.out.print("H["+i+"]=");
                H[i]=Integer.parseInt(br.readLine());
                System.out.println();
            }
            System.out.println("Enter the size of Processes:");
            for(int i=0;i<np;i++)
            {
                System.out.print("P["+i+"]=");
                P[i]=Integer.parseInt(br.readLine());
                System.out.println();
            }
            int h=nh;
            int j=0;
            while(h>0)
            {
                for(int i=0;i<nh;i++)
                {
                    if(H[i]>P[j])
                    {
                        System.out.println("Process "+j+"allocated in hole "+i);
                        H[i]-=P[j];
                        j++;
                        h--;
                    }
                }
            }
            System.out.println("_______________");
        }
    }

