package ex1;

import java.sql.SQLException;

import com.newlecture.app.console.NoticeConsole;

public class Program5 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		NoticeConsole console = new NoticeConsole();//������ ������ NoticeConsole�� ������ �ִ°� �� ���� �� ����.
		//int page;
		
		EXIT:
			while(true) {
				console.printNoticeList(); //�ָܼ޴� ��� ���ִ� ��
				int menu = console.inputNoticeMenu(); // �޴��� �Է� �޴� �κ�
		
				switch(menu) {
					case 1: //����ȸ
						break;
					case 2: //����
						console.movePrevList();
						//page--;
						break;
					case 3: //����
						console.moveNextList();
						//dpage++;
						break;
					case 4: //�۾���
						System.out.println("4�� ����");
						break;
					case 5: //�˻�
						console.inputSearchWord();
						break;
					case 6: //����
						System.out.println("Bye~~");
						break EXIT; //�� �� Ȱ��
					default:
						System.out.println("<<�����>> �޴��� 1~4������ �Է��� �� �ֽ��ϴ�.");
						break;
			}
		}
	}

}
