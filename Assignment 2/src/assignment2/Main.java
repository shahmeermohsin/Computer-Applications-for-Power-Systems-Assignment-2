package assignment2;

//Importing the Libraries
import javax.swing.JOptionPane;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList; // import the ArrayList class
import java.util.Arrays;
import java.util.Collections;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;  


//Main Class:
public class Main 
{
	
	//GUI prompt-> allows you to give a name to the tables housing the Kmeans and KNN output:
	 static String kmean=JOptionPane.showInputDialog("Give a name to the Kmeans output table!");
	 static String knn=JOptionPane.showInputDialog("Give a name to the knn output table!");
	 static Double k=Double.parseDouble(JOptionPane.showInputDialog("Select a value of K for KNN!"));

    //Main Function:
	public static void main(String[] args) throws SQLException 
	{
		 try 
		 {
			 //Make a DB named pow:
			 Database_SQL.create_DB("pow");
			 
			 //Making Statement and Connection Objects:
	         Statement stmt = null;
	    	 Connection conn = null;
	         conn =       DriverManager.getConnection("jdbc:mysql://localhost/pow" ,"root","password");
             stmt = conn.createStatement();
         	    
	         //Cleaning up the data in the KNN and Kmean tables;
       	     stmt.execute("DELETE FROM analogvalues") ;
       	     stmt.execute("DELETE FROM measurementstable") ;
       	     stmt.execute("DELETE FROM "+knn) ;
       	     stmt.execute("DELETE FROM "+kmean) ;

       	}
		 catch (Exception ex) 
		 {
            // handle the error
	    }
 	    
		//CreateTable makes two new refined versions of the provided measurements and analog_values tables:
		CreateTable ();
		//Fill up the kmean table with the result of the Kmeans clustering:
	    kmeans();
		//Fill up the knn table with the result of the KNN classification:
		knn();

	    }
	
    // Function to create new refined tables (Preprocessing the data):
    public static void CreateTable ()
    {
       Connection conn = null;
       Statement stmt,stmt1,stmt2  = null;
     
       try 
       {
         Class.forName("com.mysql.jdbc.Driver");
         
         //Initializing Statement and Connection objects:
    	 conn =   DriverManager.getConnection("jdbc:mysql://localhost/pow" ,"root","password");
         stmt1  = conn.createStatement();
         Statement stmt3  = conn.createStatement();
         stmt  = conn.createStatement();
         stmt2  = conn.createStatement();
         ResultSet rs2=null;
         
         //Creating the required tables:
         stmt1.executeUpdate("CREATE TABLE if not exists measurementstable( V1 double,Ang1 double,  V2 double,Ang2 double, V3 double,Ang3 double, V4 double,Ang4 double, V5 double,Ang5 double, V6 double,Ang6 double, V7 double,Ang7 double, V8 double,Ang8 double, V9 double,Ang9 double, Label double)");
         stmt1.executeUpdate("CREATE TABLE if not exists "+kmean+"( V1 double,Ang1 double,  V2 double,Ang2 double, V3 double,Ang3 double, V4 double,Ang4 double, V5 double,Ang5 double, V6 double,Ang6 double, V7 double,Ang7 double, V8 double,Ang8 double, V9 double,Ang9 double, Label double)");
         stmt1.executeUpdate("CREATE TABLE if not exists analogvalues( V1 double,Ang1 double,  V2 double,Ang2 double, V3 int,Ang3 double, V4 double,Ang4 double, V5 double,Ang5 double, V6 double,Ang6 double, V7 int,Ang7 double, V8 double,Ang8 double, V9 double,Ang9 double, Label double)");
         stmt1.executeUpdate("CREATE TABLE if not exists "+knn+"( V1 double,Ang1 double,  V2 double,Ang2 double, V3 double,Ang3 double, V4 double,Ang4 double, V5 double,Ang5 double, V6 double,Ang6 double, V7 double,Ang7 double, V8 double,Ang8 double, V9 double,Ang9 double, Label double)");
         
         
         //Making table analogvalues which is a refined version of the table named analog_values:
                  
         for (int i=0;i<=20;i++) 
            {
         	ArrayList <Double>arraylisttemp =new ArrayList<Double>();
         	   rs2=stmt2.executeQuery("SELECT * FROM analog_values WHERE time='"+i+"'") ;
               while (rs2.next()) 
                 {
         	     arraylisttemp.add(Double.parseDouble((rs2.getString(4))));
                 //System.out.println(rs2.getString(1) + ", " + rs2.getString(2) + ", "
                 //  + rs2.getString(3)+", "+rs2.getString(4)+", "+rs2.getString(5));
                 }
            if(i!=0) 
                 {
         	     BigDecimal e= new BigDecimal(3.222 );
         	     stmt3.executeUpdate("INSERT INTO analogvalues VALUES('"+arraylisttemp.get(0)+"','"+arraylisttemp.get(1)+"','"+arraylisttemp.get(2)+"','"+arraylisttemp.get(3)+"','"+arraylisttemp.get(4)+"','"+arraylisttemp.get(5)+"','"+arraylisttemp.get(6)+"','"+arraylisttemp.get(7)+"','"+arraylisttemp.get(8)+"','"+arraylisttemp.get(9)+"','"+
                 arraylisttemp.get(10)+"','"+arraylisttemp.get(11)+"','"+arraylisttemp.get(12)+"','"+arraylisttemp.get(13)+"','"+arraylisttemp.get(14)+"','"+arraylisttemp.get(15)+"','"+arraylisttemp.get(16)+"','"+arraylisttemp.get(17)+"','"+e+"')");
                 }
            }
         
         //Making table measurementstable which is a refined version of the table named measurements:

         for (int i=0;i<=200;i++) 
            {
      	    ArrayList <Double>arraylisttemp =new ArrayList<Double>();
      	    rs2=stmt2.executeQuery("SELECT * FROM measurements WHERE time='"+i+"'") ;
            while (rs2.next()) 
                {
      	        arraylisttemp.add(Double.parseDouble((rs2.getString(4))));
                //System.out.println(rs2.getString(1) + ", " + rs2.getString(2) + ", "
                //  + rs2.getString(3)+", "+rs2.getString(4)+", "+rs2.getString(5));
                }
             if(i!=0) 
                {
      	        BigDecimal e= new BigDecimal(3.222 );
      	        stmt3.executeUpdate("INSERT INTO measurementstable VALUES('"+arraylisttemp.get(0)+"','"+arraylisttemp.get(1)+"','"+arraylisttemp.get(2)+"','"+arraylisttemp.get(3)+"','"+arraylisttemp.get(4)+"','"+arraylisttemp.get(5)+"','"+arraylisttemp.get(6)+"','"+arraylisttemp.get(7)+"','"+arraylisttemp.get(8)+"','"+arraylisttemp.get(9)+"','"+
                arraylisttemp.get(10)+"','"+arraylisttemp.get(11)+"','"+arraylisttemp.get(12)+"','"+arraylisttemp.get(13)+"','"+arraylisttemp.get(14)+"','"+arraylisttemp.get(15)+"','"+arraylisttemp.get(16)+"','"+arraylisttemp.get(17)+"','"+e+"')");
                }
             }
  
       } 
       
       catch (Exception ex) 
         {
         // handle any errors
         }
  
  }
 
  
  
  
  
  
  
    //Function for Kmeans
    public static  void kmeans() throws SQLException 
    {
   	
   	 Connection conn = null;
        Statement stmt,stmt1,stmt2  = null;

         try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            // handle the error
        }
         int i=0;


       //Kmeans:

       //Step1: Making Random Centroids:
         
       double centroid1[]= {1,1,1,20,2,24,1,-5,1,-9,	1.0139,16,1,16,1.010438,11,1,-8};
       double centroid2[]= {3,1,4,2,1,-14,1,-3,0,-13,	1,-11,1,-9,1,-2,1,-10};
       double centroid3[]={1,1,1,20,2,24,1,-5,0.991391,-9,	1,16,1,16,1,11,0.9346810242379857,-8};
       double centroid4[]={3,2,1,-23,2,-25,1,-15,1,-29,	1,-28,1,-32,1,-27,1,-33};
         
      


   while (i<=10)
         { 
  

         try 
            { 
           conn =DriverManager.getConnection("jdbc:mysql://localhost/pow" ,"root","password");
         

           stmt1  = conn.createStatement();
           ResultSet rs2=stmt1.executeQuery("SELECT * FROM measurementstable");
           Statement stmt6  = conn.createStatement();




   	       double minimum;	
		   double []euclideandistancetemp1=new double[18];
		   double euclideandistance1;
		   double []euclideandistancetemp2=new double[18];
		   double euclideandistance2;
		   double []euclideandistancetemp3=new double[18];
		   double euclideandistance3;
		   double []euclideandistancetemp4=new double[18];
		   double euclideandistance4;
   	       double label=0;
   	       
           while (rs2.next()) 
                {
		        double sample[]= {Double.parseDouble(rs2.getString(1)),Double.parseDouble(rs2.getString(2)),Double.parseDouble(rs2.getString(3)),Double.parseDouble(rs2.getString(4))
		   		,Double.parseDouble(rs2.getString(5)),Double.parseDouble(rs2.getString(6)),Double.parseDouble(rs2.getString(7)),Double.parseDouble(rs2.getString(8))
		   		,Double.parseDouble(rs2.getString(9)),Double.parseDouble(rs2.getString(10)),Double.parseDouble(rs2.getString(11)),Double.parseDouble(rs2.getString(12))
		   		,Double.parseDouble(rs2.getString(13)),Double.parseDouble(rs2.getString(14)),Double.parseDouble(rs2.getString(15)),Double.parseDouble(rs2.getString(16)),
		   		Double.parseDouble(rs2.getString(17)),Double.parseDouble(rs2.getString(18))};
   
		        //Step2: Finding all euclidean distances between the data on which k-means clustering is applied
		        //and the 4 centroids:

                for(int j =0; j<= 17; j++)
                   {
			   	   euclideandistancetemp1[j] =  Math.pow((sample[j] - centroid1[j]),2);
			   	   euclideandistancetemp2[j] =  Math.pow((sample[j] - centroid2[j]),2);
			   	   euclideandistancetemp3[j] =  Math.pow((sample[j] - centroid3[j]),2);
			   	   euclideandistancetemp4[j] =  Math.pow((sample[j] - centroid4[j]),2);
                   }
                
		   euclideandistance1= Math.pow((Arrays.stream(euclideandistancetemp1).sum()),(0.5))	;
		   System.out.println(euclideandistance1);	
		   euclideandistance2= Math.pow((Arrays.stream(euclideandistancetemp2).sum()),(0.5))	;
		   System.out.println(euclideandistance2);	
		   euclideandistance3= Math.pow((Arrays.stream(euclideandistancetemp3).sum()),(0.5))	;
		   System.out.println(euclideandistance3);	
		   euclideandistance4= Math.pow((Arrays.stream(euclideandistancetemp4).sum()),(0.5))	;
		   System.out.println(euclideandistance4);	
		   System.out.println("");
		
		   //Step3 Finding minimum euclidean distance:
		   minimum=euclideandistance1;
		   if(minimum>euclideandistance2)
		    {
		   	minimum=euclideandistance2;
		   	}
		   if(minimum>euclideandistance3)
		    {
		   	minimum=euclideandistance3;
		   	}
		   if(minimum>euclideandistance4)
		    {
		   	minimum=euclideandistance4;
		   	}
		   //System.out.println(minimum);
		
		   //Step4: Assigning label to the sample corresponding to minimum distance centroid:
		   if(minimum==euclideandistance1)
		    {
		   	label=1;
		   	}
		   if(minimum==euclideandistance2)
		    {
		   	label=2;
		   	}
		
		   if(minimum==euclideandistance3)
		    {
		   	label=3;
		   	}
		   if(minimum==euclideandistance4)
		    {
		   	label=4;
		   	}
		
		
		   //Step5: Finding mean of the clusters for new centroid positions:
		
		   stmt6.executeUpdate("INSERT INTO  "+kmean+" VALUES('"+rs2.getString(1)+"','"+rs2.getString(2)+"','"+rs2.getString(3)+"','"+rs2.getString(4)+"','"+rs2.getString(5)+"','"+rs2.getString(6)+"','"+rs2.getString(7)+"','"+rs2.getString(8)+"','"+rs2.getString(9)+"','"+rs2.getString(10)+"','"+
		   		rs2.getString(11)+"','"+rs2.getString(12)+"','"+rs2.getString(13)+"','"+rs2.getString(14)+"','"+rs2.getString(15)+"','"+rs2.getString(16)+"','"+rs2.getString(17)+"','"+rs2.getString(18)+"','"+label+"')");
		   
		   System.out.println("label"+label);
		
		
		
		
		   }
		
		
		   ResultSet rs4=stmt6.executeQuery("SELECT AVG(V1)" + 
		   		"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt7 = conn.createStatement();
		   ResultSet rs5=stmt7.executeQuery("SELECT AVG(ANG1)" + 
		   		"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt8 = conn.createStatement();
		   ResultSet rs6=stmt8.executeQuery("SELECT AVG(V2)" + 
		   		"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt9 = conn.createStatement();
		   ResultSet rs7=stmt9.executeQuery("SELECT AVG(ANG2)" + 
		   		"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt10 = conn.createStatement();
		   ResultSet rs8=stmt10.executeQuery("SELECT AVG(V3)" + 
		   		"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt11 = conn.createStatement();
		   ResultSet rs9=stmt11.executeQuery("SELECT AVG(ANG3)" + 
		   "FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt12 = conn.createStatement();
		   ResultSet rs10=stmt12.executeQuery("SELECT AVG(V4)" + 
		   "FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt13 = conn.createStatement();
		   ResultSet rs11=stmt13.executeQuery("SELECT AVG(ANG4)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt14 = conn.createStatement();
		   ResultSet rs12=stmt14.executeQuery("SELECT AVG(V5)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt15 = conn.createStatement();
		   ResultSet rs13=stmt15.executeQuery("SELECT AVG(ANG5)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt16 = conn.createStatement();
		   ResultSet rs14=stmt16.executeQuery("SELECT AVG(V6)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt17 = conn.createStatement();
		   ResultSet rs15=stmt17.executeQuery("SELECT AVG(ANG6)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt18 = conn.createStatement();
		   ResultSet rs16=stmt18.executeQuery("SELECT AVG(V7)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt19 = conn.createStatement();
		   ResultSet rs17=stmt19.executeQuery("SELECT AVG(ANG7)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt20 = conn.createStatement();
		   ResultSet rs18=stmt20.executeQuery("SELECT AVG(V8)" + 
		   		"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt21 = conn.createStatement();
		   ResultSet rs19=stmt21.executeQuery("SELECT AVG(ANG8)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt22 = conn.createStatement();
		   ResultSet rs20=stmt22.executeQuery("SELECT AVG(V9)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		   Statement stmt23 = conn.createStatement();
		   ResultSet rs21=stmt23.executeQuery("SELECT AVG(ANG9)" + 
		   	"FROM  "+kmean+" WHERE label=1") ;
		
		
		   while(rs4.next()) {
		   	//System.out.println(rs4.getString(1) );
		   		centroid1[0]= Double.parseDouble(rs4.getString(1));
		   }
		   while(rs5.next()) {
		   	//System.out.println(rs5.getString(1) );
		   		centroid1[1]= Double.parseDouble(rs5.getString(1));
		   }
		   while(rs6.next()) {
		   	//System.out.println(rs6.getString(1) );
		   		centroid1[2]= Double.parseDouble(rs6.getString(1));
		   }
		   while(rs7.next()) {
		   	//System.out.println(rs7.getString(1) );
		   centroid1[3]= Double.parseDouble(rs7.getString(1));
		   }
		   	while(rs8.next()) {
		   		//System.out.println(rs8.getString(1) );
		   centroid1[4]= Double.parseDouble(rs8.getString(1));
		   }
		   	while(rs9.next()) {
		   		//System.out.println(rs9.getString(1) );
		   centroid1[5]= Double.parseDouble(rs9.getString(1));
		   }
		   	while(rs10.next()) {
		   		//System.out.println(rs10.getString(1) );
		   centroid1[6]= Double.parseDouble(rs10.getString(1));
		   }
		   	while(rs11.next()) {
		   		//System.out.println(rs11.getString(1) );
		   centroid1[7]= Double.parseDouble(rs11.getString(1));
		   }
		   	while(rs12.next()) {
		   		//System.out.println(rs12.getString(1) );
		   centroid1[8]= Double.parseDouble(rs12.getString(1));
		   }
		   	while(rs13.next()) {
		   		//System.out.println(rs13.getString(1) );
		   centroid1[9]= Double.parseDouble(rs13.getString(1));
		   }
		   	while(rs14.next()) {
		   		//System.out.println(rs14.getString(1) );
		   centroid1[10]= Double.parseDouble(rs14.getString(1));
		   }
		   	while(rs15.next()) {
		   		//System.out.println(rs15.getString(1) );
		   centroid1[11]=Double.parseDouble(rs15.getString(1));
		   }
		   	while(rs16.next()) {
		   		//System.out.println(rs16.getString(1) );
		   centroid1[12]= Double.parseDouble(rs16.getString(1));
		   }
		   	while(rs17.next()) {
		   		//System.out.println(rs17.getString(1) );
		   centroid1[13]= Double.parseDouble(rs17.getString(1));
		   }
		   	while(rs18.next()) {
		   		//System.out.println(rs18.getString(1) );
		   centroid1[14]= Double.parseDouble(rs18.getString(1));
		   }
		   	while(rs19.next()) {
		   		//System.out.println(rs19.getString(1) );
		   centroid1[15]= Double.parseDouble(rs19.getString(1));
		   }
		   	while(rs20.next()) {
		   		//System.out.println(rs20.getString(1) );
		   centroid1[16]=Double.parseDouble(rs20.getString(1));
		   }
		   	while(rs21.next()) {
		   		//System.out.println(rs21.getString(1) );
		   centroid1[17]= Double.parseDouble(rs21.getString(1));
		   }
		
		
		   ResultSet rs30=stmt6.executeQuery("SELECT AVG(V1)" + 
		   		"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt31 = conn.createStatement();
		   ResultSet rs31=stmt31.executeQuery("SELECT AVG(ANG1)" + 
		   		"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt32 = conn.createStatement();
		   ResultSet rs32=stmt32.executeQuery("SELECT AVG(V2)" + 
		   		"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt33 = conn.createStatement();
		   ResultSet rs33=stmt33.executeQuery("SELECT AVG(ANG2)" + 
		   		"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt34 = conn.createStatement();
		   ResultSet rs34=stmt34.executeQuery("SELECT AVG(V3)" + 
		   		"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt35 = conn.createStatement();
		   ResultSet rs35=stmt35.executeQuery("SELECT AVG(ANG3)" + 
		   "FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt36 = conn.createStatement();
		   ResultSet rs36=stmt36.executeQuery("SELECT AVG(V4)" + 
		   "FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt37 = conn.createStatement();
		   ResultSet rs37=stmt37.executeQuery("SELECT AVG(ANG4)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt38 = conn.createStatement();
		   ResultSet rs38=stmt38.executeQuery("SELECT AVG(V5)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt39 = conn.createStatement();
		   ResultSet rs39=stmt39.executeQuery("SELECT AVG(ANG5)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt40 = conn.createStatement();
		   ResultSet rs40=stmt40.executeQuery("SELECT AVG(V6)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt41 = conn.createStatement();
		   ResultSet rs41=stmt41.executeQuery("SELECT AVG(ANG6)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt42 = conn.createStatement();
		   ResultSet rs42=stmt42.executeQuery("SELECT AVG(V7)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt43 = conn.createStatement();
		   ResultSet rs43=stmt43.executeQuery("SELECT AVG(ANG7)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt44 = conn.createStatement();
		   ResultSet rs44=stmt44.executeQuery("SELECT AVG(V8)" + 
		   		"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt45 = conn.createStatement();
		   ResultSet rs45=stmt45.executeQuery("SELECT AVG(ANG8)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt46 = conn.createStatement();
		   ResultSet rs46=stmt46.executeQuery("SELECT AVG(V9)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		   Statement stmt47 = conn.createStatement();
		   ResultSet rs47=stmt47.executeQuery("SELECT AVG(ANG9)" + 
		   	"FROM  "+kmean+" WHERE label=2") ;
		
		
		   while(rs30.next()) {
		   	//System.out.println(rs4.getString(1) );
		   		centroid2[0]= Double.parseDouble(rs30.getString(1));
		   }
		   while(rs31.next()) {
		   	//System.out.println(rs5.getString(1) );
		   		centroid2[1]= Double.parseDouble(rs31.getString(1));
		   }
		   while(rs32.next()) {
		   	//System.out.println(rs6.getString(1) );
		   		centroid2[2]= Double.parseDouble(rs32.getString(1));
		   }
		   while(rs33.next()) {
		   	//System.out.println(rs7.getString(1) );
		   centroid2[3]= Double.parseDouble(rs33.getString(1));
		   }
		   	while(rs34.next()) {
		   		//System.out.println(rs8.getString(1) );
		   centroid2[4]= Double.parseDouble(rs34.getString(1));
		   }
		   	while(rs35.next()) {
		   		//System.out.println(rs9.getString(1) );
		   centroid2[5]= Double.parseDouble(rs35.getString(1));
		   }
		   	while(rs36.next()) {
		   		//System.out.println(rs10.getString(1) );
		   centroid2[6]= Double.parseDouble(rs36.getString(1));
		   }
		   	while(rs37.next()) {
		   		//System.out.println(rs11.getString(1) );
		   centroid2[7]= Double.parseDouble(rs37.getString(1));
		   }
		   	while(rs38.next()) {
		   		//System.out.println(rs12.getString(1) );
		   centroid2[8]= Double.parseDouble(rs38.getString(1));
		   }
		   	while(rs39.next()) {
		   		//System.out.println(rs13.getString(1) );
		   centroid2[9]= Double.parseDouble(rs39.getString(1));
		   }
		   	while(rs40.next()) {
		   		//System.out.println(rs14.getString(1) );
		   centroid2[10]= Double.parseDouble(rs40.getString(1));
		   }
		   	while(rs41.next()) {
		   		//System.out.println(rs15.getString(1) );
		   centroid2[11]=Double.parseDouble(rs41.getString(1));
		   }
		   	while(rs42.next()) {
		   		//System.out.println(rs16.getString(1) );
		   centroid2[12]= Double.parseDouble(rs42.getString(1));
		   }
		   	while(rs43.next()) {
		   		//System.out.println(rs17.getString(1) );
		   centroid2[13]= Double.parseDouble(rs43.getString(1));
		   }
		   	while(rs44.next()) {
		   		//System.out.println(rs18.getString(1) );
		   centroid2[14]= Double.parseDouble(rs44.getString(1));
		   }
		   	while(rs45.next()) {
		   		//System.out.println(rs19.getString(1) );
		   centroid2[15]= Double.parseDouble(rs45.getString(1));
		   }
		   	while(rs46.next()) {
		   		//System.out.println(rs20.getString(1) );
		   centroid2[16]=Double.parseDouble(rs46.getString(1));
		   }
		   	while(rs47.next()) {
		   		//System.out.println(rs21.getString(1) );
		   centroid2[17]= Double.parseDouble(rs47.getString(1));
		   }
		
		
		   	 ResultSet rs50=stmt6.executeQuery("SELECT AVG(V1)" + 
		   	    		"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt51 = conn.createStatement();
		   	    ResultSet rs51=stmt51.executeQuery("SELECT AVG(ANG1)" + 
		   	    		"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt52 = conn.createStatement();
		   	    ResultSet rs52=stmt52.executeQuery("SELECT AVG(V2)" + 
		   	    		"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt53 = conn.createStatement();
		   	    ResultSet rs53=stmt53.executeQuery("SELECT AVG(ANG2)" + 
		   	    		"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt54 = conn.createStatement();
		   	    ResultSet rs54=stmt54.executeQuery("SELECT AVG(V3)" + 
		   	    		"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt55 = conn.createStatement();
		   	    ResultSet rs55=stmt55.executeQuery("SELECT AVG(ANG3)" + 
		   	    "FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt56 = conn.createStatement();
		   	    ResultSet rs56=stmt56.executeQuery("SELECT AVG(V4)" + 
		   	    "FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt57 = conn.createStatement();
		   	    ResultSet rs57=stmt57.executeQuery("SELECT AVG(ANG4)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt58 = conn.createStatement();
		   	    ResultSet rs58=stmt58.executeQuery("SELECT AVG(V5)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt59 = conn.createStatement();
		   	    ResultSet rs59=stmt59.executeQuery("SELECT AVG(ANG5)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt60 = conn.createStatement();
		   	    ResultSet rs60=stmt60.executeQuery("SELECT AVG(V6)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt61 = conn.createStatement();
		   	    ResultSet rs61=stmt61.executeQuery("SELECT AVG(ANG6)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt62 = conn.createStatement();
		   	    ResultSet rs62=stmt62.executeQuery("SELECT AVG(V7)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt63 = conn.createStatement();
		   	    ResultSet rs63=stmt63.executeQuery("SELECT AVG(ANG7)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt64 = conn.createStatement();
		   	    ResultSet rs64=stmt64.executeQuery("SELECT AVG(V8)" + 
		   	    		"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt65 = conn.createStatement();
		   	    ResultSet rs65=stmt65.executeQuery("SELECT AVG(ANG8)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt66 = conn.createStatement();
		   	    ResultSet rs66=stmt66.executeQuery("SELECT AVG(V9)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		   	    Statement stmt67 = conn.createStatement();
		   	    ResultSet rs67=stmt67.executeQuery("SELECT AVG(ANG9)" + 
		   	    	"FROM  "+kmean+" WHERE label=3") ;
		
		
		   	    while(rs50.next()) {
		   	    	//System.out.println(rs4.getString(1) );
		   	    		centroid3[0]= Double.parseDouble(rs50.getString(1));
		   	    }
		   	    while(rs51.next()) {
		   	    	//System.out.println(rs5.getString(1) );
		   	    		centroid3[1]= Double.parseDouble(rs51.getString(1));
		   	    }
		   	    while(rs52.next()) {
		   	    	//System.out.println(rs6.getString(1) );
		   	    		centroid3[2]= Double.parseDouble(rs52.getString(1));
		   	    }
		   	    while(rs53.next()) {
		   	    	//System.out.println(rs7.getString(1) );
		   	    centroid3[3]= Double.parseDouble(rs53.getString(1));
		   	    }
		   	    	while(rs54.next()) {
		   	    		//System.out.println(rs8.getString(1) );
		   	    centroid3[4]= Double.parseDouble(rs54.getString(1));
		   	    }
		   	    	while(rs55.next()) {
		   	    		//System.out.println(rs9.getString(1) );
		   	    centroid3[5]= Double.parseDouble(rs55.getString(1));
		   	    }
		   	    	while(rs56.next()) {
		   	    		//System.out.println(rs10.getString(1) );
		   	    centroid3[6]= Double.parseDouble(rs56.getString(1));
		   	    }
		   	    	while(rs57.next()) {
		   	    		//System.out.println(rs11.getString(1) );
		   	    centroid3[7]= Double.parseDouble(rs57.getString(1));
		   	    }
		   	    	while(rs58.next()) {
		   	    		//System.out.println(rs12.getString(1) );
		   	    centroid3[8]= Double.parseDouble(rs58.getString(1));
		   	    }
		   	    	while(rs59.next()) {
		   	    		//System.out.println(rs13.getString(1) );
		   	    centroid3[9]= Double.parseDouble(rs59.getString(1));
		   	    }
		   	    	while(rs60.next()) {
		   	    		//System.out.println(rs14.getString(1) );
		   	    centroid3[10]= Double.parseDouble(rs60.getString(1));
		   	    }
		   	    	while(rs61.next()) {
		   	    		//System.out.println(rs15.getString(1) );
		   	    centroid3[11]=Double.parseDouble(rs61.getString(1));
		   	    }
		   	    	while(rs62.next()) {
		   	    		//System.out.println(rs16.getString(1) );
		   	    centroid3[12]= Double.parseDouble(rs62.getString(1));
		   	    }
		   	    	while(rs63.next()) {
		   	    		//System.out.println(rs17.getString(1) );
		   	    centroid3[13]= Double.parseDouble(rs63.getString(1));
		   	    }
		   	    	while(rs64.next()) {
		   	    		//System.out.println(rs18.getString(1) );
		   	    centroid3[14]= Double.parseDouble(rs64.getString(1));
		   	    }
		   	    	while(rs65.next()) {
		   	    		//System.out.println(rs19.getString(1) );
		   	    centroid3[15]= Double.parseDouble(rs65.getString(1));
		   	    }
		   	    	while(rs66.next()) {
		   	    		//System.out.println(rs20.getString(1) );
		   	    centroid3[16]=Double.parseDouble(rs66.getString(1));
		   	    }
		   	    	while(rs67.next()) {
		   	    		//System.out.println(rs21.getString(1) );
		   	    centroid3[17]= Double.parseDouble(rs67.getString(1));
		   	    }   	
		   	
		   	
		   	
		   

   	


    }
         catch(Exception ex) 
            {
   	        // handle the error
   	        System.out.println("SQLException: " + ex.getMessage());


    }
        
         i++;
         if(i<10) {
         	Statement stmttemp = conn.createStatement();
         	stmttemp.execute("DELETE FROM  "+kmean+"") ;
         	}
    }
         
    }
    

    
    
    
    
    
    
    
    
    //KNN Function:
    public static  void knn() throws SQLException 
       {
      	
   	//Setting things up:
      	Connection conn = null;
       Statement stmt,stmt1,stmt2  = null;
       try 
          {
          Class.forName("com.mysql.jdbc.Driver");
          conn =       DriverManager.getConnection("jdbc:mysql://localhost/pow" ,"root","password");
         
          stmt1  = conn.createStatement();
          //Select the training data table:
          ResultSet rs2=stmt1.executeQuery("SELECT * FROM "+kmean);
          Statement stmt3  = conn.createStatement();
          //Select the test data table:
          ResultSet rs3=stmt3.executeQuery("SELECT * FROM analogvalues");
          Statement stmt6  = conn.createStatement();


          double max=0;
          double []euclideandistancetemp1=new double[18];
      	  double euclideandistance1;
      	     
      	
          while (rs3.next()) 
             {
      	      ArrayList<Double> t=new ArrayList<Double>();  
              ArrayList<Double> euclideantemp=new ArrayList<Double>();
      	      ArrayList<Double> euclideantemp1=new ArrayList<Double>();
      	      ArrayList<Double> semieuclideantemp=new ArrayList<Double>();
          	  ArrayList<Double> labeltemp=new ArrayList<Double>();  
          	  HashMap<Double,Double> hashmap=new HashMap<Double ,Double>();    
              TreeMap<Double, Double> sortedHashMap = new TreeMap<>(); 

      	      double testsample[]= {Double.parseDouble(rs3.getString(1)),Double.parseDouble(rs3.getString(2)),Double.parseDouble(rs3.getString(3)),Double.parseDouble(rs3.getString(4))
      		  ,Double.parseDouble(rs3.getString(5)),Double.parseDouble(rs3.getString(6)),Double.parseDouble(rs3.getString(7)),Double.parseDouble(rs3.getString(8))
      		  ,Double.parseDouble(rs3.getString(9)),Double.parseDouble(rs3.getString(10)),Double.parseDouble(rs3.getString(11)),Double.parseDouble(rs3.getString(12))
      		  ,Double.parseDouble(rs3.getString(13)),Double.parseDouble(rs3.getString(14)),Double.parseDouble(rs3.getString(15)),Double.parseDouble(rs3.getString(16)),
      		  Double.parseDouble(rs3.getString(17)),Double.parseDouble(rs3.getString(18))};
                  while (rs2.next()) 
                     {
                     double trainingsample[]= {Double.parseDouble(rs2.getString(1)),Double.parseDouble(rs2.getString(2)),Double.parseDouble(rs2.getString(3)),Double.parseDouble(rs2.getString(4))
      		          ,Double.parseDouble(rs2.getString(5)),Double.parseDouble(rs2.getString(6)),Double.parseDouble(rs2.getString(7)),Double.parseDouble(rs2.getString(8))
      		          ,Double.parseDouble(rs2.getString(9)),Double.parseDouble(rs2.getString(10)),Double.parseDouble(rs2.getString(11)),Double.parseDouble(rs2.getString(12))
      		          ,Double.parseDouble(rs2.getString(13)),Double.parseDouble(rs2.getString(14)),Double.parseDouble(rs2.getString(15)),Double.parseDouble(rs2.getString(16)),
      		          Double.parseDouble(rs2.getString(17)),Double.parseDouble(rs2.getString(18))};

                     //Step1 Finding all euclidean distances between the test sample and all the training samples:
                     
                     for(int j =0; j<= 17; j++)
                                {
      	                          euclideandistancetemp1[j] =  Math.pow((testsample[j] - trainingsample[j]),2);
      	 
                                }
             euclideandistance1= Math.pow((Arrays.stream(euclideandistancetemp1).sum()),(0.5))	;
             euclideantemp.add(euclideandistance1);
             euclideantemp1.add(euclideandistance1);
             hashmap.put(euclideandistance1,Double.parseDouble(rs2.getString(19)));
             Collections.sort(euclideantemp1);   
             
             //Step 2: Sorting the euclidean distances to find which distances are the least:
             
             sortedHashMap.putAll(hashmap);              
             labeltemp.add(Double.parseDouble(rs2.getString(19)));
                       }
		
            
            //Step 3: Only the first k hashmap entries are stored in the new hashmap, "semieuclideantemp":

            int temp=0;
            for (Map.Entry<Double, Double> entry : sortedHashMap.entrySet())  
              {
              if (temp<k)
                {
                System.out.println("Key = " + entry.getKey() +  
                ", Value = " + entry.getValue()); 
                semieuclideantemp.add(entry.getValue());
                temp++;
                }
              }
            
            System.out.println(semieuclideantemp);
           
            //Step 4: Finding out which label (1-4) is most dominant/frequent among the K nearest 
            //neighbours of the test sample.
            double freq1 = Collections.frequency(semieuclideantemp, 1.0); 
            double freq2 = Collections.frequency(semieuclideantemp, 2.0); 
            double freq3 = Collections.frequency(semieuclideantemp, 3.0); 
            double freq4 = Collections.frequency(semieuclideantemp, 4.0); 

       
            if(freq1>freq2&&freq1>freq3&&freq1>freq4)
               {
      	       max=1.0; 
               System.out.println("f= "+max);
               }
            if(freq2>freq1&&freq2>freq3&&freq2>freq4)
               {
      	       max=2.0; 
               System.out.println("f= "+max);
               }
           if(freq3>freq1&&freq3>freq2&&freq3>freq4)
               {
      	       max=3.0;
               System.out.println("f= "+max);
               }
           if(freq4>freq1&&freq4>freq2&&freq4>freq3)
               {
      	       max=4.0;
               System.out.println("f= "+max);
               }
           
           
           //Step 5: Updating the knn Table with the label determined after the classification:

           stmt6.executeUpdate("INSERT INTO  "+knn+" VALUES('"+rs3.getString(1)+"','"+rs3.getString(2)+"','"+rs3.getString(3)+"','"+rs3.getString(4)+"','"+rs3.getString(5)+"','"+rs3.getString(6)+"','"+rs3.getString(7)+"','"+rs3.getString(8)+"','"+rs3.getString(9)+"','"+rs3.getString(10)+"','"+
      	    rs3.getString(11)+"','"+rs3.getString(12)+"','"+rs3.getString(13)+"','"+rs3.getString(14)+"','"+rs3.getString(15)+"','"+rs3.getString(16)+"','"+rs3.getString(17)+"','"+rs3.getString(18)+"','"+max+"')");
           stmt1  = conn.createStatement();
           rs2=stmt1.executeQuery("SELECT * FROM  "+kmean);
         }

       }
       
       
       catch(Exception ex) 
       {
      	  System.out.println("SQLException: " + ex.getMessage());

       }
           

     }         
    

   //The program ends here!    
    
 
 
 
 
 
 
 
 //KNN Function:
 public static  void knn1() throws SQLException 
    {
   	
   	Connection conn = null;
    Statement stmt,stmt1,stmt2  = null;
    int i=0;


    //Knn:
    try 
       {
       Class.forName("com.mysql.jdbc.Driver");
       conn =       DriverManager.getConnection("jdbc:mysql://localhost/pow" ,"root","password");
      
       stmt1  = conn.createStatement();
       ResultSet rs2=stmt1.executeQuery("SELECT * FROM "+kmean);
       Statement stmt3  = conn.createStatement();
       ResultSet rs3=stmt3.executeQuery("SELECT * FROM analogvalues");
    Statement stmt6  = conn.createStatement();



   double max=0;
   	double []euclideandistancetemp1=new double[18];
   	double euclideandistance1;
   	double label=0;
   int q=0;
   int w=0;
   	int k=3;
   	
   while (rs3.next()) {
   	ArrayList<Double> t=new ArrayList<Double>();//Creating arraylist  
   	ArrayList<Double> euclideantemp=new ArrayList<Double>();//Creating arraylist  
   	ArrayList<Double> euclideantemp1=new ArrayList<Double>();//Creating arraylist  
   	ArrayList<Double> labeltemp=new ArrayList<Double>();//Creating arraylist    
   	double testsample[]= {Double.parseDouble(rs3.getString(1)),Double.parseDouble(rs3.getString(2)),Double.parseDouble(rs3.getString(3)),Double.parseDouble(rs3.getString(4))
   			,Double.parseDouble(rs3.getString(5)),Double.parseDouble(rs3.getString(6)),Double.parseDouble(rs3.getString(7)),Double.parseDouble(rs3.getString(8))
   			,Double.parseDouble(rs3.getString(9)),Double.parseDouble(rs3.getString(10)),Double.parseDouble(rs3.getString(11)),Double.parseDouble(rs3.getString(12))
   			,Double.parseDouble(rs3.getString(13)),Double.parseDouble(rs3.getString(14)),Double.parseDouble(rs3.getString(15)),Double.parseDouble(rs3.getString(16)),
   			Double.parseDouble(rs3.getString(17)),Double.parseDouble(rs3.getString(18))};
    while (rs2.next()) {
    double trainingsample[]= {Double.parseDouble(rs2.getString(1)),Double.parseDouble(rs2.getString(2)),Double.parseDouble(rs2.getString(3)),Double.parseDouble(rs2.getString(4))
   		,Double.parseDouble(rs2.getString(5)),Double.parseDouble(rs2.getString(6)),Double.parseDouble(rs2.getString(7)),Double.parseDouble(rs2.getString(8))
   		,Double.parseDouble(rs2.getString(9)),Double.parseDouble(rs2.getString(10)),Double.parseDouble(rs2.getString(11)),Double.parseDouble(rs2.getString(12))
   		,Double.parseDouble(rs2.getString(13)),Double.parseDouble(rs2.getString(14)),Double.parseDouble(rs2.getString(15)),Double.parseDouble(rs2.getString(16)),
   		Double.parseDouble(rs2.getString(17)),Double.parseDouble(rs2.getString(18))};

    //Step1 Finding all euclidean distances between training sample and all traing samples:
      for(int j =0; j<= 17; j++)
      {
   	   euclideandistancetemp1[j] =  Math.pow((testsample[j] - trainingsample[j]),2);
   	 
      }
   euclideandistance1= Math.pow((Arrays.stream(euclideandistancetemp1).sum()),(0.5))	;
   euclideantemp.add(euclideandistance1);
   euclideantemp1.add(euclideandistance1);
   Collections.sort(euclideantemp1);
   labeltemp.add(Double.parseDouble(rs2.getString(19)));
   //Step3 Finding k nearest neighbours:

   //System.out.println(minimum);

   //Step4: Assigning label to the sample corresponding to minimum distance centroid:

   //Step5: Finding mean of the clusters for new centroid positions:


   //System.out.println(rs2.getString(1) + ", " + rs2.getString(2) + ", "
   //+ rs2.getString(3)+", "+rs2.getString(4)+", "+rs2.getString(5)+", "+rs2.getString(6)+", "
//   		+rs2.getString(7)+", "+rs2.getString(8)+", "+rs2.getString(9)+", "+rs2.getString(10)+", "+rs2.getString(11)+", "+rs2.getString(12)
//   	+", "+rs2.getString(13)+", "+rs2.getString(14)+", "+rs2.getString(15)+", "+rs2.getString(16)+", "+rs2.getString(17)+", "+rs2.getString(18)
//   	+", "+rs2.getString(19));

    }


    for (int p=0;p<euclideantemp.size();p++) 
    {
   	// System.out.println(euclideantemp.get(p)+"  "+p);
   	 if(euclideantemp.get(p).equals(euclideantemp1.get(1))||euclideantemp.get(p).equals(euclideantemp1.get(2))||euclideantemp.get(p).equals(euclideantemp1.get(3))) {
   		 t.add(labeltemp.get(p));
   		 //System.out.println(labeltemp.get(p));

   	 }
    }
    for (int r=0;r<t.size();r++){System.out.println(t.get(r));}
   // for (int r=0;r<euclideantemp.size();r++){System.out.println(labeltemp.get(r));}
System.out.println(" ");
    double freq1 = Collections.frequency(t, 1.0); 
    double freq2 = Collections.frequency(t, 2.0); 
    double freq3 = Collections.frequency(t, 3.0); 
    double freq4 = Collections.frequency(t, 4.0); 
String a;
    if(freq1>freq2&&freq1>freq3&&freq1>freq4)
    {
   	max=1.0; 
       System.out.println("f= "+max);
    }
    if(freq2>freq1&&freq2>freq3&&freq2>freq4)
    {
   	max=2.0; 
       System.out.println("f= "+max);

    }
    if(freq3>freq1&&freq3>freq2&&freq3>freq4)
    {
   	max=3.0;
       System.out.println("f= "+max);
    }
    if(freq4>freq1&&freq4>freq2&&freq4>freq1)
    {
   	max=4.0;
       System.out.println("f= "+max);
    }
    
    stmt6.executeUpdate("INSERT INTO  "+knn+" VALUES('"+rs3.getString(1)+"','"+rs3.getString(2)+"','"+rs3.getString(3)+"','"+rs3.getString(4)+"','"+rs3.getString(5)+"','"+rs3.getString(6)+"','"+rs3.getString(7)+"','"+rs3.getString(8)+"','"+rs3.getString(9)+"','"+rs3.getString(10)+"','"+
   			rs3.getString(11)+"','"+rs3.getString(12)+"','"+rs3.getString(13)+"','"+rs3.getString(14)+"','"+rs3.getString(15)+"','"+rs3.getString(16)+"','"+rs3.getString(17)+"','"+rs3.getString(18)+"','"+max+"')");

   	
   //while(rs3.next()) {
   	//System.out.println(rs3.getString(1) + ", " + rs3.getString(2) + ", "
   		//	+ rs3.getString(3)+", "+rs3.getString(4)+", "+rs3.getString(5)+", "+rs3.getString(6)+", "
   			//		+rs3.getString(7)+", "+rs3.getString(8)+", "+rs3.getString(9)+", "+rs3.getString(10)+", "+rs3.getString(11)+", "+rs3.getString(12)
   				//+", "+rs3.getString(13)+", "+rs3.getString(14)+", "+rs3.getString(15)+", "+rs3.getString(16)+", "+rs3.getString(17)+", "+rs3.getString(18)
   				//+", "+rs3.getString(19));
   	
   //}
    stmt1  = conn.createStatement();
    rs2=stmt1.executeQuery("SELECT * FROM  "+kmean);
   	
   }

    }catch(Exception ex) {
   	    // handle the error
   	  System.out.println("SQLException: " + ex.getMessage());


    }
        

    }
 
 
 
 
 
            
 


}