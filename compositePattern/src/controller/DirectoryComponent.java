package controller;

public interface DirectoryComponent {
	void list();
	void listAll(String indent);
	Folder chdir(String name);
	Folder getParent();
	Folder up();
	int count();
	int countAll();
	int q();
	String getName();
}
