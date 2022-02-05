package ex1;

import java.sql.SQLException;

import com.newlecture.app.console.NoticeConsole;

public class Program5 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		
		NoticeConsole console = new NoticeConsole();
		
		EXIT:
			while(true) {
				console.printNoticeList(); //콘솔메뉴 출력 해주는 것
				int menu = console.inputNoticeMenu(); // 메뉴를 입력 받는 부분
		
				switch(menu) {
					case 1: //상세조회
						break;
					case 2: //이전
						break;
					case 3: //다음
						break;
					case 4: //글쓰기
						System.out.println("4번 선택");
						break;
					case 5: //종료
						System.out.println("Bye~~");
						break EXIT; //라벨 문 활용
					default:
						System.out.println("<<사용방법>> 메뉴는 1~4까지만 입력할 수 있습니다.");
						break;
			}
		}
	}

}
