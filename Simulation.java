//yara adel hassan mohamed 19100683
//system modelling and simulation for a bank
package simulation;

import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;   //importing all java classes


public class Simulation {
    
   
    
    
    public static void newrandom(double averageService ,double AverageInterarrival ,double AverageForWait ,double probidle,double probwait,double[]timing , int num,double [] arrival,double [] interarr,double [] endservice,double [] beginservice,double [] wait,double [] system,double [] idle,double [] qlen,double [] service,double totalwait,double totalsys,double totalqlen,double servutil,double totalidle ,double waitting,double[] prop,double [] cdfarr,double [] cdfser){
    
    double a = 0;  
    //double b = Math.random(); 
    double[] r = new double[num];
//    System.out.println(cdfarr.length);
//    System.out.println(cdfser.length);
//    System.out.println(cdfarr.length);
      for(int i=0;i<num;i++){
         a = Math.random();
          r[i]=a%10;
//          System.out.println(a);
     
      }
       cdfarr[0]=prop[0];
      cdfser[0]=prop[0];
      
      for(int j=1;j<6;j++){
          cdfarr[j]=cdfarr[j-1]+prop[j];
          
      }
      for(int k=1;k<6;k++){
          cdfser[k]=cdfser[k-1]+prop[k];
          
      }
      
      for(int l=0;l<num;l++){
          for(int j=0;l<interarr.length;j++){
              if(r[l]<cdfarr[j]){
                  interarr[l]=timing[j];
                  break;
              }
          }
          
      }
      for(int h=0;h<num;h++){
          for(int j=0;h<service.length;j++){
              if(r[h]<cdfser[j]){
                  service[h]=timing[j];
                  break;
              }
          }
          
      }
//        for(int k=0;k<num;k++){
//          System.out.println(service[k]);
//          
//      }
          
      calculate( probidle,probwait,num,arrival,interarr,endservice, beginservice, wait,system,idle,qlen,service,totalwait,totalsys,totalqlen,servutil,totalidle ,waitting, averageService , AverageInterarrival , AverageForWait  ); 

      
    }
    
    public static void calculate(double probidle,double probwait,int num,double [] arrival,double [] interarr,double [] endservice,double [] beginservice,double [] wait,double [] system,double [] idle,double [] qlen,double [] service,double totalwait,double totalsys,double totalqlen,double servutil,double totalidle ,double waitting ,double averageService ,double AverageInterarrival ,double AverageForWait){
    
        arrival[0] = interarr[0] ; 
        beginservice[0] = arrival[0] ;
        endservice[0] = beginservice[0]+service[0];
        wait[0] = 0 ; 
        system[0]=endservice[0]-arrival[0];
        idle[0] =0 ; 
        qlen[0] =0 ; 
        // calculating arrival
       for(int i=1; i<num; i++){ 
         arrival[i]=interarr[i]+arrival[i-1];
         
         //calculating begin service
         if(arrival[i] >endservice[i-1]){
              beginservice[i]=arrival[i];
         
         }
         else{
             beginservice[i]=endservice[i-1]; 
         }
         
         //calculating wait time
         wait[i]=endservice[i-1]-arrival[i];
         if(endservice[i-1]-arrival[i]<0){
          wait[i]=0;   
         }
         
         //calculating end service 
         endservice[i]=beginservice[i]+service[i];
         
         //calculating system time
         system[i]=endservice[i]-arrival[i];
         
         //calculating idle
         idle[i]=beginservice[i]-endservice[i-1];
         
        
      
        }
       double queue =0  ; 
        for(int i=1 ;i<num ; i++){
           queue = 0 ; 
           if(wait[i]> 0 ){
             queue++ ; 
           }
           if(arrival[i-1]+wait[i-1] > arrival[i]){
             queue++ ; 
           }
           qlen[i] = queue ; 
           
        }
        
        double totalservice = 0 , totalinterarrival  =0 ; 
        for(int i=0 ;i<num ;i++){
          totalservice+=service[i]; 
          totalinterarrival += interarr[i] ; 
          totalwait+= wait[i];
          totalsys+=system[i];
          totalqlen+=qlen[i];
          totalidle+= idle[i] ; 
          
          if(wait[i]>0){
            
              waitting++;
          }
          
     
        
        }
        
        servutil = (endservice[num-1] - totalidle )  / endservice[num-1] ; 
        double averagewait=totalwait/num;//average  waitting time for those who wait
        double averagesys=totalsys/num;// average time customers spend in the system
        double averageqlen=totalqlen/num;
        double waittime=totalwait/waitting;
        averageService = totalservice / num ; 
        AverageInterarrival  = totalinterarrival / num ; 
        AverageForWait = totalwait / waitting ; 
         probwait=waitting/num;
           //System.out.println(waitting/num);
         probidle=totalidle/endservice[num-1];
    print( averageService , AverageInterarrival , AverageForWait , probidle,probwait,num,arrival,interarr,endservice, beginservice, wait,system,idle,qlen,service,averagewait,averagesys,averageqlen,waittime,totalwait,totalsys,totalqlen,servutil,totalidle ,waitting);
    }
       
    
     public static void print(double averageService ,double AverageInterarrival ,double AverageForWait ,double probidle,double probwait,int num,double [] arrival,double [] interarr,double [] endservice,double [] beginservice,double [] wait,double [] system,double [] idle,double [] qlen,double [] service,double averagewait,double averagesys,double averageqlen,double waittime,double totalwait,double totalsys,double totalqlen,double servutil,double totalidle ,double waitting){
        
        System.out.println("__________________________________________________________________________________________________________________");
        System.out.println("_________________________________system simulation_______________________________________________________________");

        System.out.println("int" + "\t" + "arr" + "\t" + "ser" + "\t" + "beg"+ "\t" +"wait" + "\t" + "end"+ "\t" +"sys"+"\t"+"idle"+"\t"+"qlen");

        for(int i=0; i<num; i++){ //printing
            System.out.println(interarr[i] + "\t" + arrival[i] + "\t" + service[i] + "\t" + beginservice[i]+ "\t" +wait[i]+ "\t" +endservice[i] + "\t" + system[i]+ "\t" +idle[i] +"\t"+qlen[i]);
       
      
       }
        System.out.println("__________________________________________________________________________________________________________________");
        System.out.println("_________________________________average calculation___________________________________");

           System.out.println("averagewait"+"=" +averagewait);
            System.out.println("averageqlen"+"=" +averageqlen);
            System.out.println("serveutil"+"=" +servutil);
            System.out.println("averagesystem"+"=" +averagesys);
            System.out.println("waittime"+"=" +waittime);
            System.out.println("propwaittime"+"=" +probwait);
            System.out.println("propidle server"+"=" +probidle);
            System.out.println("averageService"+"=" +averageService);
            System.out.println("AverageInterarrival"+"=" +AverageInterarrival);
            System.out.println("Average Waiting for who wait"+"=" +AverageForWait);
            System.out.println("______________________________thank you for using our program____________________________________________________");
            System.out.println("__________________________________________________________________________________________________________________");
        }
     
    
    public static void main(String[] args) throws FileNotFoundException {
        
        
        Scanner scan = new Scanner(System.in) ; 
        System.out.println("enter number of customers");
        int num= scan.nextInt();
        double[] interarr = new double[num]; // array holding the random arrival time of the customers
        double[] arrival = new double[num];  // array holding the time of the customer arrival on the service window
        double[] service = new double[num];  // array holding the random time for the service 
        double[] beginservice = new double[num];  // array holding the time when the service begins
        double[] wait = new double[num]; // array holding the waitting time for each customer
        double[] endservice = new double[num]; // array holding the time when the service ends
        double[] system = new double[num];  // array holding the time taken by the system
        double[] idle = new double[num];  // array holding the idle
        double[] qlen = new double[num];  // array holding the lenth of people waitting in the queue
        double[] prop = {0.0,0.2, 0.15, 0.25,0.35,0.05};
        double[] cdfarr = new double[6];
        double[] cdfser = new double[6];
        double[] timing={1,2,3,4,5,6};
        
        double totalwait=0;
        double totalsys=0;
        double totalqlen=0;
        double servutil=0;
        double totalidle=0; 
        double waitting=0;
        double averagewait;
        double averagesys;
        double averageqlen;
        double waittime;
        double probwait=0;
        double probidle=0;
        double averageService=0 ;
        double AverageInterarrival=0 ;
        double AverageForWait=0; 
        
        System.out.println("_______________________");
        System.out.println("enter choice number");
        System.out.println("_______________________");
        System.out.println("[1]random numbers input");
        System.out.println("[2]console input");
        //System.out.println("[3]read from file");
        System.out.println("[4]quit");
        System.out.println("_______________________");
      
        int x=scan.nextInt();
        switch(x) {
  case 1:
    newrandom(averageService , AverageInterarrival , AverageForWait ,probidle, probwait,timing,num,arrival,interarr,endservice, beginservice, wait,system,idle,qlen,service,totalwait,totalsys,totalqlen,servutil,totalidle ,waitting,prop,cdfarr,cdfser);
    
    
    break;
      ///////////////////////////////////////////////////////////
  case 2:
      for(int i=0 ; i<10 ;i++){
        System.out.println("Enter interarrival time");
        interarr[i] = scan.nextDouble() ; 
     
        
        }
        //entering interarrivals  and service 
        for(int i=0;i<10;i++){
           System.out.println("Enter service time");
           service[i] = scan.nextDouble() ; 
        
        }
        calculate(probidle, probwait,num,arrival,interarr,endservice, beginservice, wait,system,idle,qlen,service,totalwait,totalsys,totalqlen,servutil,totalidle ,waitting,  averageService , AverageInterarrival , AverageForWait );
        
    
    break;
      ///////////////////////////////////////////////////////////
      case 3:
    File myObj = new File("C:\\Users\\fast\\Downloads\\input.txt");
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                       
                double str1 = Double.parseDouble(data);
  
                
                //data = data.toUpperCase();//making sure all data are upper case
                
               data = data.trim();//removing any unnecessary spaces
               
               for(int i=0 ; i<num ;i++){
        
        interarr[i] = str1 ; 
      }
        //entering interarrivals  and service 
        for(int i=0;i<num;i++){
           
           service[i] = str1 ; 
        
        }
       
               
                
                
                
        
                        calculate(probidle,probwait,num,arrival,interarr,endservice, beginservice, wait,system,idle,qlen,service,totalwait,totalsys,totalqlen,servutil,totalidle ,waitting,  averageService , AverageInterarrival , AverageForWait );


    break;
    //////////////////////////////////////////////////////////////////////
   
  //default:
    // code block
}
        
        
  case 4:
   System.exit(0);
   break;  
        
    }
    }}
