package com.skuniv.mvc;

import java.nio.channels.AlreadyBoundException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.skuniv.mvc.AddBook;
import com.skuniv.mvc.BorrowBook;
import com.skuniv.mvc.FindBook;
import com.skuniv.mvc.FindMember;
import com.skuniv.mvc.ReserveBook;
import com.skuniv.mvc.ReturnBook;
import com.skuniv.mvc.AddMember;

@Controller
public class WebController {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath*:appCtx.xml");
	AddBook addBook = (AddBook) ctx.getBean("addBook");
	AddMember addMember = (AddMember) ctx.getBean("addMember");
	BorrowBook borrowBook = (BorrowBook) ctx.getBean("borrowBook");
	ReturnBook returnBook = (ReturnBook) ctx.getBean("returnBook");
	ReserveBook reserveBook = (ReserveBook) ctx.getBean("reserveBook");
	FindBook findBook = (FindBook) ctx.getBean("findBook");
	FindMember findMember = (FindMember) ctx.getBean("findMember");
	ShowMember showMember = (ShowMember) ctx.getBean("showMember");
	ShowBook showBook = (ShowBook) ctx.getBean("showBook");
	ServiceDAO serviceDAO = (ServiceDAO) ctx.getBean("serviceDAO");

	@GetMapping("/")
	public String list(Model model) {
		return "selectmode";
	}

	@RequestMapping(value = "/selectmode", method = RequestMethod.POST)
	public String handleselect(@RequestParam(value = "mode", required = false) String mode, Model model) {
		if (mode.equals("0"))
			return "addbook";
		else if (mode.equals("1"))
			return "addmember";
		else if (mode.equals("2"))
			return "borrowbook";
		else if (mode.equals("3"))
			return "reservebook";
		else if (mode.equals("4"))
			return "returnbook";
		else if (mode.equals("5"))
			return "findbook";
		else if (mode.equals("6"))
			return "findmember";
		else if (mode.equals("7")) {
			List<Book> bookList = showBook.showBook();
			model.addAttribute("books", bookList);
			return "showbookr";
		} else if (mode.equals("8")) {
			List<Member> memberList = showMember.showMember();
			model.addAttribute("members", memberList);
			return "showmemberr";
		}

		else
			return "selectmode";

	}

	@RequestMapping(value = "/addmemberr", method = RequestMethod.POST)
	public String handleaddmember(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "password", required = false) String password, Model model) {
		addMember.addMember(id, password);
		List<Member> memberList = showMember.showMember();
		model.addAttribute("members", memberList);
		return "addmemberr";
	}

	@RequestMapping(value = "/addbookr", method = RequestMethod.POST)
	public String handleaddbook(@RequestParam(value = "bname", required = false) String bname,
			@RequestParam(value = "publisher", required = false) String publisher,
			@RequestParam(value = "author", required = false) String author, Model model) {
		addBook.addBook(bname, publisher, author);
		List<Book> bookList = showBook.showBook();
		model.addAttribute("books", bookList);
		return "addbookr";

	}

	@RequestMapping(value = "/borrowbookr", method = RequestMethod.POST)
	public String handleborrowbook(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "bname", required = false) String bname, Model model) {
		borrowBook.borrowBook(id, password, bname);
		Member member = findMember.findMember(id);
		List<Book> booklist = serviceDAO.BselectByMnum2(member.getMnum());
		model.addAttribute("booklist", booklist);
		return "borrowbookr";
	}

	@RequestMapping(value = "/reservebookr", method = RequestMethod.POST)
	public String handlereservebook(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "bname", required = false) String bname, Model model) {
		reserveBook.reserveBook(id, bname);
		Member member = findMember.findMember(id);
		List<Book> booklist = serviceDAO.RselectByMnum2(member.getMnum());
		model.addAttribute("booklist", booklist);
		return "reservebookr";
	}

	@RequestMapping(value = "/returnbookr", method = RequestMethod.POST)
	public String handlereturnbook(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "bname", required = false) String bname, Model model) {
		returnBook.returnBook(id, bname);
		Member member = findMember.findMember(id);
		List<Book> booklist = serviceDAO.BselectByMnum2(member.getMnum());
		model.addAttribute("booklist", booklist);
		return "returnbookr";
	}

	@RequestMapping(value = "/findbookr", method = RequestMethod.POST)
	public String handlefindbook(@RequestParam(value = "bname", required = false) String bname, Model model) {
		Book book = findBook.findBook(bname);
		model.addAttribute("book", book);
		return "findbookr";
	}

	@RequestMapping(value = "/findmemberr", method = RequestMethod.POST)
	public String handlefindmember(@RequestParam(value = "id", required = false) String id, Model model) {
		Member member = findMember.findMember(id);
		model.addAttribute("member", member);
		List<Book> booklist = serviceDAO.BselectByMnum2(member.getMnum());
		model.addAttribute("booklist", booklist);
		List<Book> rbooklist = serviceDAO.RselectByMnum2(member.getMnum());
		model.addAttribute("rbooklist", rbooklist);
		return "findmemberr";
	}
	/*
	 * @RequestMapping(value = "/showbookr", method = RequestMethod.POST) public
	 * String handleshowbook(Model model) { List<Book> bookList =
	 * showBook.showBook(); model.addAttribute("books", bookList); return
	 * "showbookr"; }
	 * 
	 * @RequestMapping(value = "/showmemberr", method = RequestMethod.POST) public
	 * String handleshowmember(Model model) { List<Member> memberList =
	 * showMember.showMember(); model.addAttribute("members", memberList); return
	 * "showmemberr"; }
	 */

}
