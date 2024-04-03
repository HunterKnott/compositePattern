// Hunter Knott, CS 3450, Utah Valley University

package controller;
import java.io.*;
import java.util.*;

public class Explorer {
	private static final String INDENT = "   ";
	private Folder current;
	
	public Explorer(Folder root) {
		this.current = root;
	}
	
	private static Folder buildFileTree(BufferedReader reader) throws IOException {
		Folder root = null;
		Folder current = null;
		String line;
		int depth = 0;
		
		while ((line = reader.readLine()) != null) {
			int currentDepth = countSpaces(line) / INDENT.length();
			
			if (currentDepth == 0) {
				root = new Folder(line, null);
				current = root;
				depth = 1;
			} else if (currentDepth == depth && !line.endsWith(":")) {
				current.addComponent(new FileComponent(line));
			} else if (currentDepth == depth && line.endsWith(":")) {
				Folder newFolder = new Folder(line, current);
				current.addComponent(newFolder);
				current = newFolder;
				depth = currentDepth;
			} else if (currentDepth > depth && !line.endsWith(":")) {
				current.addComponent(new FileComponent(line));
			} else if (currentDepth > depth && line.endsWith(":")) {
				Folder newFolder = new Folder(line, current);
				current = newFolder;
				depth = currentDepth;
			} else if (currentDepth < depth && !line.endsWith(":")) {
				for (int i = depth - currentDepth; i > 0; i--) {
					current = current.getParent();
				}
				current.addComponent(new FileComponent(line));
			} else if (currentDepth < depth && line.endsWith(":")) {
				for (int i = depth - currentDepth; i > 0; i--) {
					current = current.getParent();
				}
				Folder newFolder = new Folder(line, current);
				current = newFolder;
				depth = currentDepth;
			}
		}
		return root;
	}
	
	private static int countSpaces(String line) {
		  int count = 0;
		  for (char c : line.toCharArray()) {
		    if (c == ' ') {
		      count++;
		    } else {
		      break;
		    }
		  }
		  return count;
		}
	
	public void process(BufferedReader reader) throws IOException {
		while (true) {
			System.out.print(current.getName() + "> ");
			String line = reader.readLine().trim();
			String[] parts = line.split(" ");
			String command = parts[0];
			switch (command) {
				case "list":
					current.list();
					break;
				case "listall":
					current.listAll("");
					break;
				case "chdir":
					if (parts.length == 1) {
						System.out.println("You must give a directory to go to");
					}
					else {
						current = current.chdir(parts[1]);
						if (current == null) {
							System.out.println("Directory does not exist");
						}
					}
					break;
				case "up":
					current = current.getParent();
					break;
				case "count":
					System.out.println(current.count());
					break;
				case "countall":
					System.out.println(current.countAll());
					break;
				case "q":
					System.out.println("Quitting program...");
					current.q();
					break;
				default:
					System.out.println("Invalid command");
					break;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("src/controller/directory.dat"));
		Folder root = buildFileTree(reader);
		Explorer explorer = new Explorer(root);
		explorer.process(new BufferedReader(new InputStreamReader(System.in)));
//		explorer.process(new BufferedReader(new InputStreamReader(System.in)));
//		try {
//	        Scanner scanner = new Scanner(new File("src/controller/directory.dat"));
//	        Folder root = readDirectory(scanner, 0);
//	        Explorer explorer = new Explorer(root);
////			FileInputStream fileInputStream = new FileInputStream("src/controller/directory.dat");
////			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//			String line;
//			Folder current = null;
//			while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(line);
//				String[] parts = line.split("(?=:)");
//				if (parts.length > 0) {
//					String folderName = parts[0].trim();
//					if (!folderName.isEmpty()) {
//						current = new Folder(folderName);
//						root.addComponent(current);
//					}
//				}
//				if (parts.length > 1){
//					String fileName = parts[1].trim();
//					if (!fileName.isEmpty()) {
//						FileComponent fileComp = new FileComponent(fileName);
//						current.addComponent(fileComp);
//					}
//				}
//			}
//			
//			Explorer explorer = new Explorer(root);
//			explorer.process(new BufferedReader(new InputStreamReader(System.in)));
//			
////			FileInputStream fileInputStream = new FileInputStream("src/controller/directory.dat");
////			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
////			Folder root = buildFileSystem(bufferedReader);
////			Explorer explorer = new Explorer(root);
////			explorer.process(new BufferedReader(new InputStreamReader(System.in)));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
