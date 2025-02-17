package domain;

import java.util.Scanner;

public class RacingGame {
	public Scanner sc;
	public Car[] cars;
	public int rounds;

	public RacingGame() {
		sc = new Scanner(System.in);
	}

	public void start() {
		ready();		// 자동차들의 이름과 게임의 rounds 입력 및 자동차 생성
		System.out.println("\n실행 결과");
		while (rounds != 0) {
			play();			// 자동차 마다 랜덤 숫자 생성 후, 이동 및 현재 위치 출력
			System.out.println();
			rounds--;
		}
		printResult();		// 최종 우승 자동차 출력
	}

	public void ready() {
		String[] nameList = inputNames();
		cars = makeCars(nameList);
		rounds = inputRounds();
		if (sc != null) {
			sc.close();
		}
	}

	public void play() {
		for (int j = 0; j < cars.length; j++) {
			int energy = getRandomNumber();
			cars[j].tryToMove(energy);
			cars[j].printPosition();
		}
	}

	public void printResult() {
		System.out.println(getWinners() + "(이)가 최종 우승했습니다.");
	}

	public String[] inputNames() {
		System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,)기준으로 구분)\n예시) pobi, crong, honu");
		String names = sc.nextLine();
		String[] nameList = names.split(",", -1);

		while (!validate(nameList)) {
			System.out.println("이름 입력형식이 잘못되었습니다. 다시 입력해주세요.\n예시) pobi, crong, honux");
			names = sc.nextLine();
			nameList = names.split(",", -1);
		}
		return nameList;
	}

	public boolean validate(String[] nameList) {

		/* 배열에 담긴 모든 이름들이 빈 문자열이 아니고, 길이가 5이하인지 확인 */
		for (int i = 0; i < nameList.length; i++) {
			String name = nameList[i].trim();
			if ((name == null) || (name.length() == 0) || (name.length() > 5)) {
				return false;
			}
		}
		return true;
	}

	public Car[] makeCars(String[] nameList) {
		Car[] cars = new Car[nameList.length];

		for (int i = 0; i < nameList.length; i++) {
			cars[i] = new Car(nameList[i].trim());
		}
		return cars;
	}

	public int inputRounds() {
		System.out.println("\n시도할 회수는 몇 회인가요?");
		String rounds = sc.nextLine();

		while (!isDigit(rounds)) {
			System.out.println("라운드 입력 형식이 잘못되었습니다. 숫자를 다시 입력해주세요.");
			rounds = sc.nextLine();
		}
		return Integer.parseInt(rounds);
	}

	public boolean isDigit(String rounds) {
		try {
			Integer.parseInt(rounds);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public int getRandomNumber() {
		return (int) (Math.random() * 10);
	}

	public String getWinners() {
		int maxPosition = 0;
		String winners = "";

		for (Car car : cars) {
			int position = car.getPosition();
			if (position > maxPosition) {
				maxPosition = position;
				winners = car.getName();
				continue;
			}
			if (position == maxPosition) {
				winners += ", " + car.getName();
			}
		}
		return winners;
	}
}
