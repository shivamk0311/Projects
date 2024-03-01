#define _GNU_SOURCE

#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <fcntl.h>

#define MAX_COMMAND_SIZE 255

int main( int argc, char * argv[] )
{
  char * command_string = (char*) malloc( MAX_COMMAND_SIZE );
  size_t len = 0;
  char error_message[30] = "An error has occurred\n";

  char *path[] = {"/bin", "/usr/bin", "/usr/local/bin", "./",NULL};
     

  if(argc == 1){// Code for interactive mode
     while( 1 )
    {
      printf ("msh> ");
      ssize_t cmd_str = getline(&command_string,&len,stdin);
 

        
        if (command_string[cmd_str-1]== '\n') {
            command_string[cmd_str-1] = '\0';
        }

        char *myargs[10];
        int arg_count =0;
        char *token = strtok(command_string, " ");
        while(token != NULL){
          myargs[arg_count] = token;
          arg_count++;
          token = strtok(NULL, " ");
        }
        myargs[arg_count] = NULL;
        if(myargs[0] == NULL) continue;
        if(strcmp(myargs[0],"exit")==0){
            if(myargs[1] != NULL) {
              write(STDERR_FILENO, error_message, strlen(error_message));
              continue;
            }
            exit(0);        
        } else if(strcmp(myargs[0], "cd") ==0){
            if(myargs[1] == NULL){
              write(STDERR_FILENO, error_message, strlen(error_message));
              continue;
            } else if(myargs[2] != NULL){
              write(STDERR_FILENO, error_message, strlen(error_message));
              continue;
            }
            if(chdir(myargs[1]) == -1 ){
              write(STDERR_FILENO, error_message, strlen(error_message));
              
            }
            continue;
        }else if(strcmp(myargs[0], "path") == 0){
              write(STDERR_FILENO, error_message, strlen(error_message));
              continue;
        }
        
        
        
        pid_t pid = fork();

          if (pid < 0) { 
            write(STDERR_FILENO, error_message, strlen(error_message));

          } else if (pid == 0) { 

            for (int i = 0; path[i] != NULL; i++) {

                    char full_path[MAX_COMMAND_SIZE];
                    snprintf(full_path, sizeof(full_path), "%s/%s", path[i], myargs[0]);
                    if (access(full_path, X_OK) == 0) {
                        
                            execv(full_path, myargs);
                            write(STDERR_FILENO, error_message, strlen(error_message));  
                            exit(1);
             
                    }
                }
             
          
                write(STDERR_FILENO, error_message, strlen(error_message));
                exit(1);
               
          } else { 

              wait(NULL);
          }   
    } 
  
  }//end  interactive mode
  else if(argc == 2){//  batch mode

      FILE *file = fopen(argv[1], "r");

      if(file == NULL){
        write(STDERR_FILENO, error_message, strlen(error_message));
        exit(1);
      }

      while(getline(&command_string,&len,file) != -1){
        int len2 = strlen(command_string);

        if(len2>0 && command_string[len2-1]=='\n'){
          command_string[len2-1] = '\0';
        }

        char *myargs[10];

        int arg_count =0;
        char *token = strtok(command_string, " ");
        while(token != NULL){
          myargs[arg_count] = token;
          arg_count++;
          token = strtok(NULL, " ");
        }
        myargs[arg_count] = NULL;
        if(myargs[0] == NULL) continue;
        if(strcmp(myargs[0],"exit")==0){
            if(myargs[1] != NULL) {
              write(STDERR_FILENO, error_message, strlen(error_message));
              continue;
            }
            exit(0);        
        } else if(strcmp(myargs[0], "cd") ==0){
            if(myargs[1] == NULL){
              write(STDERR_FILENO, error_message, strlen(error_message));
              continue;
            } else if(myargs[2] != NULL){
              write(STDERR_FILENO, error_message, strlen(error_message));
              continue;
            }
            if(chdir(myargs[1]) == -1 ){
              write(STDERR_FILENO, error_message, strlen(error_message));
            }
            continue;
        } else if(strcmp(myargs[0], "path") == 0){
              write(STDERR_FILENO, error_message, strlen(error_message));
              continue;
        }         
        
        pid_t pid = fork();
          if (pid < 0) { 
            write(STDERR_FILENO, error_message, strlen(error_message));
          } else if (pid == 0) { 
            for (int i = 0; path[i] != NULL; i++) {
                    char full_path[MAX_COMMAND_SIZE];
                    snprintf(full_path, sizeof(full_path), "%s/%s", path[i], myargs[0]);
                    if (access(full_path, X_OK) == 0) {
                            int pos = -1;
                            for( int i=1; myargs[i] !=NULL; i++ )
                            {
                              if( strcmp( myargs[i], ">" ) == 0 )
                              {
                                  if (myargs[i + 1] != NULL && myargs[i + 2] == NULL) {
                                  pos = i;
                                  break;
                                } else {
                                  write(STDERR_FILENO, error_message, strlen(error_message));
                                  exit(1);
                                }
                              }
                            }
                            if(pos != -1){ 
                                int fd =  open( myargs[ pos+1], O_RDWR | O_CREAT, S_IRUSR | S_IWUSR );
                                  if( fd < 0 )
                                  {
                                    write(STDERR_FILENO, error_message, strlen(error_message));
                                    exit( 0 );                    
                                  }
                                dup2( fd,  1 );
                                close( fd );
                                myargs[pos] = NULL;
                                myargs[pos + 1] = NULL;                               
                            }                        
                            execv(full_path, myargs);
                            write(STDERR_FILENO, error_message, strlen(error_message));  
                            exit(1);                            
                    }
                }          
                write(STDERR_FILENO, error_message, strlen(error_message));
                exit(1);               
          } else { 
                wait(NULL);
          }  
      }       
      fclose(file);
      exit(0);
  }//end of batch mode
  else{
      write(STDERR_FILENO, error_message, strlen(error_message));
      return 1;
  }
  free(command_string);    
  return 0;
}