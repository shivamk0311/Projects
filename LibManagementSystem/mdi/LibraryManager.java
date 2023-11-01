package mdi;

import java.util.ArrayList;
import java.util.Scanner;
import library.Library;
import library.Publication;
import library.Video;
import library.Patron;
import library.InvalidRuntimeException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Console;
import java.time.LocalDate;

public class LibraryManager {
    private Library library;
    private Scanner scanner;


    public LibraryManager() {
        this.library = new Library("MyLibrary");
        this.scanner = new Scanner(System.in);
    }

    public void mainMenu() {
        System.out.println("============");
        System.out.println(" Main Menu:");
        System.out.println("============\n\n");
        System.out.println("PUBLICATIONS\n");
        System.out.println("1. List all Publications and Videos");
        System.out.println("2. Add a new Publication");
        System.out.println("3. Check out");
        System.out.println("4. Check in\n");
        System.out.println("VIDEOS\n");
        System.out.println("5. Add a new Video\n");
        System.out.println("PATRONS\n");
        System.out.println("6. List all Patrons");
        System.out.println("7. Add a new Patron\n");
        System.out.println("FILES\n");
        System.out.println("8. Save Library Data");
        System.out.println("9. Open Library Data");
        System.out.println("0. Exit");
    }



    public void mdi() {
        int option;
        do {
            mainMenu();
            System.out.print("\nEnter your choice: ");
            option = scanner.nextInt();
            scanner.nextLine();


            switch (option) {
                case 1:
                    listPublicationsAndVideos();
                    break;
                case 2:
                    addNewPublication();
                    break;
                case 3:
                    checkOutPublicationOrVideo();
                    break;
                case 4:
                    checkInPublicationOrVideo();
                    break;
                case 5:
                    addNewVideo();
                    break;
                case 6:
                    library.patronMenu();
                    break;
                case 7:
                    addNewPatron();
                    break;
                case 8:
                    saveLibrary();
                    break;
                case 9:
                    openLibrary();
                    break;
                case 0:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (option != 0);
    }

    public void listPublicationsAndVideos() {
        System.out.println(library);
    }

    public void addNewPublication() {
        System.out.print("Enter publication title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter copyright year: ");
        int copyright = scanner.nextInt();
        scanner.nextLine();

        Publication publication = new Publication(title, author, copyright);
        library.addPublication(publication);
        System.out.println("Publication added: " + publication);
    }

    private Patron patron;

    public void listPatrons() {
        System.out.println(patron);
    }

    public void addNewPatron() {
        System.out.print("Enter patron name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patron email id: ");
        String email = scanner.nextLine();


        Patron patron = new Patron(name,email);
        library.addPatron(patron);
        System.out.println("Patron added: " + patron);
    }

    public void addNewVideo() {
        System.out.print("Enter video title: ");
        String title = scanner.nextLine();
        System.out.print("Enter director: ");
        String director = scanner.nextLine();
        System.out.print("Enter copyright year: ");
        int copyright = scanner.nextInt();
        System.out.print("Enter runtime (in minutes): ");
        int runtime = scanner.nextInt();
        scanner.nextLine();

        try {
            Video video = new Video(title, director, copyright, runtime);
            library.addPublication(video);
            System.out.println("Video added: " + video);
        } catch (InvalidRuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void checkOutPublicationOrVideo() {
        System.out.print("Index for publication/video to be checked out: ");
        int publicationIndex = scanner.nextInt();
        System.out.print("Enter the index of the patron: ");
        int patronIndex = scanner.nextInt();
        scanner.nextLine();

        try {
            library.checkOut(publicationIndex, patronIndex);
            System.out.println("Item checked out successfully.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Invalid Index, enter valid index.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void checkInPublicationOrVideo() {
        System.out.print("Index for publication or video to be checked in: ");
        int publicationIndex = scanner.nextInt();
        scanner.nextLine();

        try {
            library.checkIn(publicationIndex);
            System.out.println("Item checked in successfully.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Incorrect Index. Please enter a valid index");
        }
    }

    public void saveLibrary() {
        System.out.print("Enter the filename to save the library data: ");
        String filename = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            library.save(writer);
            System.out.println("Library data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error: Unable to save library data.");
        }
    }



    public void openLibrary() {
       System.out.print("Enter the filename to open: ");
       String filename = scanner.nextLine();
       //if(filemname.isEmpty()) return;
        if(filename.isEmpty()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            library = new Library(br);
        } catch(IOException e) {
            System.err.println("#### Error: Cannot read " + filename + "\n" + e.getMessage());
        }
    }






    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();
        manager.mdi();
    }
}
