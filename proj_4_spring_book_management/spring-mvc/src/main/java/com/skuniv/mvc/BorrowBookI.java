package com.skuniv.mvc;

import java.util.List;

public interface BorrowBookI{
	List<Book> borrowBook(String ID,String Password, String Bname);

}
