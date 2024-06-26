// Hunter Knott, CS 3450, Utah Valley University

package controller;
import java.io.*;

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
				depth++;
			} else if (currentDepth > depth && !line.endsWith(":")) {
				current.addComponent(new FileComponent(line));
			} else if (currentDepth > depth && line.endsWith(":")) {
				Folder newFolder = new Folder(line, current);
				current.addComponent(newFolder);
				current = newFolder;
				depth++;
			} else if (currentDepth < depth && !line.endsWith(":")) {
				for (int i = depth - currentDepth; i > 0; i--) {
					current = current.getParent();
				}
				current.addComponent(new FileComponent(line));
				depth--;
			} else if (currentDepth < depth && line.endsWith(":")) {
				for (int i = depth - currentDepth; i > 0; i--) {
					current = current.getParent();
				}
				Folder newFolder = new Folder(line, current);
				current = newFolder;
				depth--;
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
		String path = current.getName().trim().replace(":", "");
		while (true) {
			System.out.print(path + "> ");
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
						if (!path.substring(path.lastIndexOf("/") + 1).equals(current.getName().trim().replace(":", ""))) {
							path += "/" + current.getName().trim().replace(":", "");
						}
					}
					break;
				case "up":
					current = current.up();
					if (path.contains("/")) {
						path = path.substring(0, path.lastIndexOf("/"));
					}
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
		String fileName = "directory.dat";
		if (args.length > 0) {
			fileName = args[0];
		}
		
		InputStream inputStream = Explorer.class.getResourceAsStream(fileName);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		Folder root = buildFileTree(reader);
		Explorer explorer = new Explorer(root);
		explorer.process(new BufferedReader(new InputStreamReader(System.in)));
	}
}

