
package os.phase1;
import java.io.*;

public class OSPhase1  {
    char[][] m= new char[100][4];
    int i,j,k,cnt=0,block=0,IC,SI;
    String s,cd,IR,line="",R="";
    Boolean c;
    File fn,fn1;
    BufferedReader br;
    FileReader fr;
    //FileWriter fw;
    BufferedWriter bw;
    RandomAccessFile fw;
    public void LOAD()
    {
       String s="",cd="";
       
       for(int i=0;i<=99;i++)
        {
            for(int j=0;j<4;j++)
            {
                m[i][j]=' ';
            }
        }
       try
       {
       fn=new File("C:\\Users\\Rutuja\\Desktop\\input.txt");
       fr=new FileReader(fn);
       br= new BufferedReader(fr);
       fw=new RandomAccessFile("C:\\Users\\Rutuja\\Desktop\\output.txt","rw");
       //fw.writeChars("Hello");
       while((s=br.readLine())!=null)
       {
           if(s.charAt(0)=='$')
           {
                cd=s.substring(0,4);
                //System.out.println(cd);
                CONTROLCARD(s);
           }
           else if(cd.equals("$AMJ"))
           {
               cnt++;
               PROGRAMCARD(s);
           }
       }
       }
       catch(IOException e){}
    }   
       
    public void CONTROLCARD(String c)
    {
       cd=" ";
       cd=c.substring(1,4);
       //System.out.println(cd);
       switch(cd)
       {
           case "AMJ": break;
           case "DTA": START();break;
           case "END": break;
       }
    }
    
    public void START()
    {
        IC=0;
        //System.out.println(cnt);
        for(i=0;i<10*cnt;i++)
        {
            for(j=0;j<4;j++)
            {
                line=line+m[i][j];
            }
            //System.out.println(line);
        }
        i=0;
        int max=0;
        while(max!=10)
        {
            IR="";
            for(j=0;j<4;j++)
            {
                IR=new String(IR+""+m[IC][j]);
            }    
                //System.out.println(IR);
                IC++;
                String ins=IR.substring(0,2);
                //System.out.println(ins);
                if(ins.equals("H "))
                {
                    //System.out.println("last");
                    SI=3;
                    MOS(SI,00);
                    break;
                }
                
                String op=IR.substring(2,4);
                int operand=Integer.parseInt(op);
                //System.out.println(operand);
                
                switch(ins)
                {
                    case "GD": SI=1;MOS(SI,operand);break;
                    case "PD": SI=2;MOS(SI,operand);break;
                    case "LR": R=""+m[operand][0]+m[operand][1]+m[operand][2]+m[operand][3];break;
                    case "SR": m[operand][0]=R.charAt(0);
                               m[operand][1]=R.charAt(1);
                               m[operand][2]=R.charAt(2);
                               m[operand][3]=R.charAt(3);
                               break;
                    case "CR": if(R.equals((""+m[operand][0]+m[operand][1]+m[operand][2]+m[operand][3])))
                               {
                                   c=true;
                                   //System.out.println(c);
                               }
                               else
                               {
                                   c=false;
                                   System.out.println(c);
                               }
                               break;
                    case "BT": if(c==true)
                               {
                                   IC=operand;
                               }
                               break;
                }
            //max++;
        }
    }
    
    public void MOS(int SI,int operand)
    {
        switch(SI)
        {
            case 1: READ(operand);break;
            case 2: WRITE(operand);break;
            case 3: TERMINATE();break;
        }
    }
    
    public void READ(int o)
    {
        try
        {
            int l=0;
            o=o-(o%10);
            i=0;
            j=0;
            
            line=br.readLine();
            //System.out.println(line);
            for(k=0;k<line.length()&&line.charAt(k)!='\n';k++)
            {
                //System.out.println(line.charAt(k));
                m[o+i][j]=(line.charAt(k));
                j++;
                if(j==4)
                {    
                    i++;
                    j=0;
                }
                
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void WRITE(int o)
    {
        String print="";
        o=o-(o%10);
        i=0;j=0;
        
        try
        {
        for(i=0;i<10;i++)
        {
            for(j=0;j<4;j++)
            {
                print=print+m[o+i][j];
            }            
        }
        //fw.writeChars("\r");
        fw.writeChars(print);
        
        fw.writeChars("\n");
        System.out.println(print);
        }
        catch(Exception e)
        {}
    }
    
    public void TERMINATE()
    {
        //System.out.println("Terminated");
        for(i=0;i<99;i++)
        {
            for(j=0;j<4;j++)
            {
                m[i][j]=' ';
            }
        }
        i=0;j=0;block=0;cnt=0;k=0;
        try
        {
        System.out.println();
        System.out.println();
        fw.writeBytes(System.getProperty("line.seperator"));
        fw.writeBytes(System.getProperty("line.seperator"));
        //fw.writeChars("\n");
        }
        catch(Exception e){}
    }
    
    public void PROGRAMCARD(String pc)
    {
        i=block*10;
        j=0;
        //System.out.println(pc.length());
        if(m[99][3]!=' ')
        {
            System.out.println("Memory is full");
        }
        else
        {
            for(k=0;k!=pc.length()&&pc.charAt(k)!='\n';k++)
            {
                if(pc.charAt(k)=='H')
                {
                    m[i][j]=(pc.charAt(k));
                    j=0;
                    i++;
                    break;
                }
                m[i][j]=(pc.charAt(k));
                j++;
                if(j==4)
                {
                    i++;
                    j=0;
                }
                
            }
            block++;
        }
    }
            
    public static void main(String[] args) {
        
        OSPhase1 o1=new OSPhase1();
        o1.LOAD();
    }
    
}
