package view;

import java.util.Scanner;

import controller.CustomerDAO;

// 프로그램 실행 시 처음 나오는 화면
public class DeliveryView {
	Scanner sc = new Scanner(System.in);
	int act; // 사용자 입력을 받기 위한 변수
	CustomerDAO cd = new CustomerDAO(); // 고객 테이블과 데이터 주고받기 위함
	
	String cid; // 현재 접속중인 사용자 아이디를 저장하기 위한 변수
	boolean isAdmin; // 관리자로 로그인 했을 때 관리자임을 확인하기 위한 변수

	public DeliveryView() {
		while (true) {
			System.out.println("===delivery-service===");
			System.out.println("1. 로그인   2. 회원가입   3. 종료");
			System.out.print("입력 : ");

			act = sc.nextInt();

			if (act == 3) {
				break;
			} else if (act == 1) {
				// 로그인
				// 로그인 시에는 사용자 아이디 정보를 가지고 다른 페이지로 넘어가기 위해
				// 클래스 변수 cid를 사용
				System.out.println("로그인중 ...");
				System.out.print("아이디 : ");
				this.cid = sc.next();
				System.out.print("비밀번호 : ");
				int cpw = sc.nextInt();
				if (cd.signIn(this.cid, cpw)) { // CustomerDAO signIn() 메소드 호출
					if (cd.isAdmin(this.cid, cpw)) { // 관리자 id, pw로 로그인 시
						System.out.println("관리자 로그인 성공!");
						isAdmin = true; // 관리자임을 확인하는 flag 변수 변경
					} else {
						System.out.println("사용자 로그인 성공!");
					}
					break;
				} else {
					System.out.println("로그인 실패! 아이디 비밀번호를 다시 확인하세요.");
				}
			} else {
				// 회원가입
				String cid = null; // 클래스 변수가 아닌 지역변수 선언
				int cpw = 0;
				String cname = null;
				int cphone = 0;

				while (true) {
					System.out.println("회원가입중 ...");
					while (true) {
						System.out.print("아이디 : ");
						cid = sc.next();
						
						// 회원가입시 입력받은 아이디를 인자로 CustomerDAO의 checkCID() 메소드 호출
						// 중복되는 아이디가 있다면 true, 아니면 false 반환
						if (cd.checkCID(cid)) { 
							System.out.println("이미 존재하는 아이디 입니다. 다시 시도하세요.");
							continue;
						} else {
							System.out.print("비밀번호 : ");
							cpw = sc.nextInt();
							System.out.print("이름 : ");
							cname = sc.next();
							System.out.print("핸드폰 번호 : ");
							cphone = sc.nextInt();
							break;
						}
					}

					// 입력받은 정보를 인자로 signUP() 메소드 호출 
					// 회원가입 성공시 true, 실패시 false 반환
					if (cd.signUp(cid, cpw, cname, cphone)) {
						System.out.println("회원가입 성공!");
						break;
					} else {
						System.out.println("회원가입 실패! 다시시도하세요.");
						continue;
					}
				}

			}
			
			// 사용자 입력 유효성 검사
			if (act < 1 || act > 3) {
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
				continue;
			}
		}

		// 로그인을 선택했을 시
		// 관리자로 접근시 AdminView() 생성자 호출
		// 사용자로 접근시 MemberView() 생성자 호출
		// 이 때 로그인한 사용자가 누구인지 알기 위해 클래스 변수 cid 사용
		if (act == 1) {
			if(!isAdmin) {
				new MemberView(this.cid);
			} else {
				new AdminView();
			}
		}
	}
}
