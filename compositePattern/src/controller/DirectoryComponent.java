package controller;

import java.io.PrintStream;

public interface DirectoryComponent {
	void list();
	void listAll(String currentIndent);
	Folder chdir(String name);
	Folder getParent();
	void up();
	int count();
	int countAll();
	int q();
	String getName();
}
