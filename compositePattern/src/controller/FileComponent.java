package controller;

public class FileComponent implements DirectoryComponent {
	private String name;
	
	public FileComponent(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void list() {
		System.out.println(getName());
	}
	
	@Override
	public void listAll(String currentIndent) {
		System.out.println(currentIndent + name);
	}
	
	@Override
	public Folder chdir(String name) {
		System.out.println("Not in a directory");
		return null;
	}
	
	@Override
	public Folder getParent() {
		throw new UnsupportedOperationException("File does not have a parent directory");
	}
	
	@Override
	public Folder up() {
		return this.getParent();
	}
	
	@Override
	public int count() {
		return 1;
	}
	
	public int countAll() {
		return 1;
	}

	@Override
	public int q() {
		System.exit(0);
		return 0;
	}
}
