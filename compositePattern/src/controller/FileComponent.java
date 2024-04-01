package controller;

public class FileComponent implements DirectoryComponent {
	private String name;
	
	public FileComponent(String name) {
		this.name = name;
	}
	
	@Override
	public void list() {
		System.out.println(name + " ");
	}
	
	@Override
	public void listAll() {
		System.out.println(name);
	}
	
	@Override
	public void chdir(String name) {
		System.out.println("Not in a directory");
	}
	
	@Override
	public void up() {
		System.out.println("up");
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
