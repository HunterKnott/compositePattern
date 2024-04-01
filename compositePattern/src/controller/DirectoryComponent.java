package controller;

public interface DirectoryComponent {
	void list();
	void listAll();
	void chdir(String entry);
	void up();
	int count();
	int countAll();
	int q();
}
