package controller;
import java.util.ArrayList;
import java.util.List;

public class Folder implements DirectoryComponent {
	private String name;
	private DirectoryComponent parent;
	private List<DirectoryComponent> components;
	
	public Folder(String name) {
		this.name = name;
		this.parent = null;
		this.components = new ArrayList<>();
	}
	
	public void setParent(DirectoryComponent parent) {
		this.parent = parent;
	}
	
	public void addComponent(DirectoryComponent component) {
		components.add(component);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void list() {
		for (DirectoryComponent component : components) {
			System.out.println(component + " ");
		}
		System.out.println();
	}
	
	@Override
	public void listAll() {
		System.out.println(name + ":");
		for (DirectoryComponent component : components) {
			component.listAll();
		}
	}
	
	@Override
	public void chdir(String entry) {
		for (DirectoryComponent component : components) {
			if (component instanceof Folder && ((Folder) component).name.equals(entry)) {
				System.out.println(entry + ">");
				return;
			}
		}
		System.out.println("Directory does not exist");
	}
	
	@Override
	public void up() {
		System.out.println("up");
	}
	
	@Override
	public int count() {
		int fileCount = 0;
		for (DirectoryComponent component : components) {
			if (!(component instanceof Folder)) {
				fileCount++;
			}
		}
		return fileCount;
	}
	
	@Override
	public int countAll() {
		int count = count();
		for (DirectoryComponent component : components) {
			if (component instanceof Folder) {
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
