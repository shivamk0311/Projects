package library;

import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Updates and displays publications in the library
 */

public class Publication{
     private String title;
     private String author;
     private int copyright;
     private Patron loanedTo;
     private LocalDate dueDate;

     public Publication(String title, String author, int copyright){
          this.title = title;
          this.author = author;
          this.copyright = copyright;
     }

        public void save(BufferedWriter bw) throws IOException {
        bw.write(title + '\n');
        bw.write(author + '\n');
        bw.write("" + Integer.toString(copyright) + '\n');


        if (loanedTo == null) {
            bw.write("checked in" + '\n');
        } else {
            bw.write("checked out" + '\n');
            loanedTo.save(bw);
            bw.write("" + dueDate.toString() + '\n');
        }
}






     public Publication(BufferedReader br) throws IOException{
            this.title = br.readLine();
            this.author = br.readLine();
            this.copyright = Integer.parseInt(br.readLine());
            String loanStatus = br.readLine();
         if ("checked in".equals(loanStatus)) {
           loanedTo = null;
           dueDate = null;
         } else {
           loanedTo = new Patron(br);
           String dueDateString = br.readLine();
           if ("checked in".equals(dueDateString)) {
               dueDate = null;
           } else {
               dueDate = LocalDate.parse(dueDateString);
           }
        }
     }



     public void checkOut(Patron patron){
          if(loanedTo==null){
              loanedTo = patron;
              dueDate = LocalDate.now().plusDays(15);
              System.out.println(title+ " is checked out to "+loanedTo.getName()+ " and is due on "+dueDate);
              }
           else
              System.out.println(title+" is already checked out to "+loanedTo.getName());
     }

     public void checkIn(){
           if(loanedTo!=null){
               loanedTo = null;
               dueDate = null;
               System.out.println(title+" is checked in.");
               }
            else
                System.out.println(title+" is not on loan");
    }



    @Override
    public String toString(){
         String output = "Title: " +title+ "\n";
         output += "Author: "+author+ "\n";
         output += "Copyright: "+copyright+ "\n";
         if(loanedTo != null){
              output += "Checked out to : "+loanedTo.getName()+ "\n";
              output += "Due on: "+dueDate+ "\n";
         }
         else
              output += "Status: Available\n";
    return output;
    }
}
