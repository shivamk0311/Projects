package library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
/**
 * Shows name and email of the registered patrons
 */

public class Patron{

	private String name;
	private String email;

	public Patron(String name, String email){
		this.name = name;
		this.email = email;
	}


    public void save(BufferedWriter bw) throws IOException {
        bw.write(name);
        bw.newLine();
        bw.write(email);
        bw.newLine();
    }


    public Patron(BufferedReader br) throws IOException {
        name = br.readLine();
        email = br.readLine();
    }


	public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nEmail: " + email;
    }

}
