package library;

import java.time.Duration;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
* Information about videos in library publication
*/
public class Video extends Publication{

     private int runtime;

     public Video(String title, String author, int copyright, int runtime) throws InvalidRuntimeException{
          super(title, author, copyright);
          if(runtime <= 0)
               throw new InvalidRuntimeException(title, runtime);
          this.runtime = runtime;
     }

     public void save(BufferedWriter bw) throws IOException {
        super.save(bw);
        bw.write(Integer.toString(runtime) + '\n');


    }



     public Video(BufferedReader br) throws IOException {
        super(br);
        this.runtime = Integer.parseInt(br.readLine());
    }


     @Override

     public String toString(){
          return super.toString() + "Runtime: " + Duration.ofMinutes(runtime).toString() +"\n";
     }
}
