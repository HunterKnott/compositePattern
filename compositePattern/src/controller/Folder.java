package controller;
import java.util.ArrayList;
import java.util.List;

public class Folder implements DirectoryComponent {
	private String name;
	private Folder parent;
	private List<DirectoryComponent> components;
	
	public Folder(String name, Folder parent) {
		this.name = name;
		this.parent = parent;
		this.components = new ArrayList<>();
	}
	
	public void addComponent(DirectoryComponent component) {
		components.add(component);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void list() {
		components.forEach(component -> System.out.print(component.getName().trim().replace(":", "") + "   "));
		System.out.println();
	}
	
	@Override
    public void listAll(String currentIndent) {
		System.out.println(currentIndent + name);
		String newIndent = currentIndent; // Indent of 3
		components.forEach(component -> component.listAll(newIndent));
    }
	
	@Override
	public Folder chdir(String name) {
		for (DirectoryComponent component : components) {
			if (component instanceof Folder && component.getName().equals(name)) {
				return (Folder) component;
			}
		}
		System.out.println("Directory does not exist");
		return null;
	}
	
	@Override
	public Folder getParent() {
		return parent;
	}
	
    @Override
    public void up() {
        if (parent != null) {
            parent.chdir(name);
        } else {
            System.out.println("Already at the root directory");
        }
    }
    
	@Override
	public int count() {
		int fileCount = 0;
		for (DirectoryComponent component : components) {
			if (component instanceof FileComponent) {
				fileCount++;
			}
		}
		return fileCount;
	}
	
	@Override
	public int countAll() {
		int count = 0;
		for (DirectoryComponent component : components) {
			if (component instanceof Folder) {
				count ++;
			} else {
				count += ((Folder) component).countAll();
			}
		}
		return count;
	}

	@Override
	public int q() {
		System.exit(0);
		return 0;
	}
}
