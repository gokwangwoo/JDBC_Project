package com.newlecture.app.console;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.newlecture.app.entity.Notice;
import com.newlecture.app.service.NoticeService;

public class NoticeConsole {

	private NoticeService service;
	private int page;
	private String searchWord;
	private String searchField;
	
	
	
	public NoticeConsole() {
		service = new NoticeService();
		page = 1; //페이지 기본 값은 1
		searchWord = "";
		searchField = "";
		
	}
	
	public void printNoticeList() throws ClassNotFoundException, SQLException {
		List<Notice> list = service.getList(page);
		int count = service.getCount(); // 페이지 값은 매번 달라져야 하므로 지역변수가 되어야 한다.
		int lastPage = count/10; //
		lastPage = count%10 > 0?lastPage+1:lastPage;
		
		System.out.println("────────────────────────────────────");
		System.out.printf("<공지사항> 총 %d 게시글\n", count);
		System.out.println("────────────────────────────────────");
		//System.out.printf("12. 안녕하세요 / newlec / 2015-07-12, args); 이렇게 되어야 하므로
		for (Notice n : list) {
			System.out.printf("%d. %s / %s / %s\n", n.getId(), n.getTitle(), n.getWriterId(), n.getRegDate());
		}
		System.out.println("────────────────────────────────────");
		System.out.printf("          %d/%d pages\n", page, lastPage);
	}

	public int inputNoticeMenu() {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.printf("1.상세조회/ 2.이전/ 3.다음/ 4.글쓰기/ 5.검색/  6.종료 >");
		String menu_ = scan.nextLine(); //꼭 숫자가 아니더라도 문자가 입력될 수 있으므로 String 값으로 받는다.
		int menu = Integer.parseInt(menu_);
		
		return menu;
		
	}

	public void movePrevList() throws ClassNotFoundException, SQLException {
		int count = service.getCount(); //지역 변수로 쓰기 위해서 이것들을 가져온다.
		int lastPage = count/10; //
		lastPage = count%10 > 0?lastPage+1:lastPage;
		
		if(page == 1) {
			System.out.println("=================");
			System.out.println("이전 페이지가 없습니다.");
			System.out.println("=================");
			return;
		}
		page--;
		
	}

	public void moveNextList() throws ClassNotFoundException, SQLException {
		int count = service.getCount(); //지역 변수로 쓰기 위해서 이것들을 가져온다.
		int lastPage = count/10; //
		lastPage = count%10 > 0?lastPage+1:lastPage;
		
		if(page == lastPage) {
			System.out.println("=================");
			System.out.println("이후 페이지가 없습니다.");
			System.out.println("=================");
			return;
		}
		page++;
		
	}

	public void inputSearchWord() {
		Scanner scan = new Scanner(System.in);
		System.out.println("검색 범주(title/content/writerId)중에 하나를 입력하세요.");
		System.out.print(" > ");
		searchField = scan.nextLine();//검색 범주(제목, 작성자 등)을 받는 입력값
		//검색할 값을 받는 searchWord는 공유하는 변수로 만드는게 좋다.
		System.out.print("검색어 > ");
		searchWord = scan.nextLine();
		
	}

}
