// Hunter Knott, CS 3450, Utah Valley University

package controller;
import java.io.*;
import java.util.*;

public class Explorer {
	private Folder root;
	
	public Explorer(Folder root) {
		this.root = root;
	}
	
	public void process(BufferedReader reader) throws IOException {
		Folder currentFolder = root;
		while (true) {
			System.out.print(currentFolder.getName() + "> ");
			String line = reader.readLine().trim();
			String[] parts = line.split(" ");
			String command = parts[0];
			switch (command) {
			case "list":
				currentFolder.list();
				break;
			case "listall":
				currentFolder.listAll();
				break;
			case "chdir":
				if (parts.length > 1) {
					currentFolder.chdir(parts[1]);
				} else {
					System.out.println("Please provide a directory name");
				}
				break;
			case "up":
				currentFolder.up();
				break;
			case "count":
				System.out.println(currentFolder.count());
				break;
			case "countall":
				System.out.println(currentFolder.countAll());
				break;
			case "q":
				System.out.println("Quitting program...");
				currentFolder.q();
				break;
			default:
				System.out.println("Invalid command");
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("Current working directory: " + System.getProperty("user.dir"));
			FileInputStream fileInputStream = new FileInputStream("src/controller/directory.dat");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//			Folder root = buildFileSystem(bufferedReader);
//			Explorer explorer = new Explorer(root);
//			explorer.process(new BufferedReader(new InputStreamReader(System.in)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
