package com.newlecture.app.console;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.newlecture.app.entity.Notice;
import com.newlecture.app.service.NoticeService;

public class NoticeConsole {

	private NoticeService service;
	//페이지 정보를 나타내는 상태 변수 page 추가
	private int page;
	
	public NoticeConsole() {
		service = new NoticeService();
		page = 1; //페이지 기본 값은 1
	}
	
	public void printNoticeList() throws ClassNotFoundException, SQLException {
		List<Notice> list = service.getList(page);
		
		System.out.println("────────────────────────────────────");
		System.out.printf("<공지사항> 총 %d 게시글\n", 12);
		System.out.println("────────────────────────────────────");
		//System.out.printf("12. 안녕하세요 / newlec / 2015-07-12, args); 이렇게 되어야 하므로
		for (Notice n : list) {
			System.out.printf("%d. %s / %s / %s\n", n.getId(), n.getTitle(), n.getWriterId(), n.getRegDate());
		}
		System.out.println("────────────────────────────────────");
		System.out.printf("          %d/%d pages\n", 1, 2);
	}

	public int inputNoticeMenu() {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.printf("1.상세조회/ 2.이전/ 3.다음/ 4.글쓰기/ 5.종료 >");
		String menu_ = scan.nextLine(); //꼭 숫자가 아니더라도 문자가 입력될 수 있으므로 String 값으로 받는다.
		int menu = Integer.parseInt(menu_);
		
		return menu;
		
	}

	public void movePrevList() {
		if(page == 1) {
			System.out.println("이전 페이지가 없습니다.");
			return ;
		}
		page--;
		
	}

	public void moveNextList() {
		page++;
		
	}

}
