package library;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Displays the list of patrons and publications
 */

public class Library{

     private String name;
     private ArrayList<Publication> publications;
	 private ArrayList<Patron> patrons;

     public Library(String name){
          this.name =name;
          this.publications= new ArrayList<>();
		  this.patrons = new ArrayList<>();
     }

       public void save(BufferedWriter bw) throws IOException {
        bw.write(name + '\n');


        bw.write("" + Integer.toString(publications.size()) + '\n');


        for (Publication publication : publications) {
            if (publication instanceof Video) {
                bw.write("video" + '\n');
                ((Video) publication).save(bw);
            } else {
                bw.write("publication" + '\n');
                publication.save(bw);
            }
        }

        bw.write(Integer.toString(patrons.size()) + '\n');


        for (Patron patron : patrons) {
            patron.save(bw);
        }
    }


    public Library(BufferedReader br) throws IOException {
        name = br.readLine();

        int numPublications = Integer.parseInt(br.readLine());
        publications = new ArrayList<>();
        for (int i = 0; i < numPublications; i++) {
            String publicationType = br.readLine();
            if ("video".equals(publicationType)) {
                publications.add(new Video(br));
            } else {
                publications.add(new Publication(br));
            }
        }
        int numPatrons = Integer.parseInt(br.readLine());
        patrons = new ArrayList<>();
        for (int i = 0; i < numPatrons; i++) {
            patrons.add(new Patron(br));
        }
    }

	 public void addPatron(Patron patron){
		  patrons.add(patron);
	 }

     public void addPublication(Publication publication){
          publications.add(publication);
     }

     public void patronMenu(){
          System.out.println("List of patrons in the library:");
            for(int i=0; i<patrons.size(); i++){
                System.out.println("Index "+i+":" + patrons.get(i).getName());
            }
     }

     public void checkOut(int publicationIndex, int patronIndex){
          if(publicationIndex >= 0 && publicationIndex <= publications.size()&& patronIndex<patrons.size()){
          Publication publication = publications.get(publicationIndex);
          Patron patron = patrons.get(patronIndex);
          publication.checkOut(patrons.get(patronIndex));
          }
          else
          {
          System.out.println("Invalid Publication Index.");
          }
     }

     public void checkIn(int publicationIndex){
          if(publicationIndex >= 0 && publicationIndex <= publications.size()){
                Publication publication = publications.get(publicationIndex);
                publication.checkIn();
          }
          else
          {
                System.out.println("Invalid Publication Index");
           }
     }


     @Override
     public String toString(){
          String DisplayInfo = "Library name: "+name+"\n\n";


          if(publications.isEmpty()){
               DisplayInfo += "Currently, there are no publications in this library.\n";
          }
          else
          {
               DisplayInfo += "Publications in the library: \n";
               for(int i=0; i<publications.size();i++){
                    DisplayInfo += "Index "+ i +":\n" +publications.get(i).toString()+"\n";
               }

          }
          return DisplayInfo;
     }
}
