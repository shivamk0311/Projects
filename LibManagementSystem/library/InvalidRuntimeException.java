package library;

/**
* Custom Exception
*/

public class InvalidRuntimeException extends ArithmeticException{

     private String title;
     private int runtime;
     
     
     public InvalidRuntimeException(){
          super();
     }
     
     public InvalidRuntimeException(String message){
          super(message);
     }
     
     public InvalidRuntimeException(String title, int runtime){
          super(title + " has invalid runtime : " +runtime);
          this.title = title;
          this.runtime = runtime;
     }
     
     public String getTitle(){
          return title;
     }
     
     public int getRuntime(){
          return runtime;
     }
     
     
}
