package view;

import java.util.Scanner;

import controller.CustomerDAO;

public class DeliveryView {
	Scanner sc = new Scanner(System.in);
	int act;
	CustomerDAO cd = new CustomerDAO();
	String cid = null;

	public DeliveryView() {
		while (true) {
			System.out.println("1. 로그인   2. 회원가입   3. 종료");
			System.out.print("입력 : ");

			act = sc.nextInt();

			if (act == 3) {
				break;
			} else if (act == 1) {
				// 로그인
				System.out.println("로그인중 ...");
				System.out.print("아이디 : ");
				this.cid = sc.next();
				System.out.print("비밀번호 : ");
				int cpw = sc.nextInt();
				if (cd.signIn(this.cid, cpw)) {
					System.out.println("로그인 성공!");
					break;
				} else {
					System.out.println("로그인 실패! 아이디 비밀번호를 다시 확인하세요.");
				}
			} else {
				// 회원가입
				String cid = null;
				int cpw = 0;
				String cname = null;
				int cphone = 0;

				while (true) {
					System.out.println("회원가입중 ...");
					while (true) {
						System.out.print("아이디 : ");
						cid = sc.next();
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

					if (cd.signUp(cid, cpw, cname, cphone)) {
						System.out.println("회원가입 성공!");
						break;
					} else {
						System.out.println("회원가입 실패! 다시시도하세요.");
						continue;
					}
				}

			}

			if (act < 1 || act > 3) {
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
				continue;
			}
		}

		if (act == 1) {
			new MemberView(this.cid);
		}
	}
}
