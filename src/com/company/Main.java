package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Utils.SignIn(scanner);

			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

			System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();

				if ("users".equals(text)){
					Utils.getUsers();
					continue;
				}
				if ("status".equals(text)){
					System.out.println("Enter your status: ");
					String status = scanner.nextLine();
					Utils.setStatus(status);
					continue;
				}
				if ("room".equals(text)){
					System.out.println("Enter room name");
					String room = scanner.nextLine();
					Utils.setRoom(room);
					continue;
				}
				if("help".equals(text)){
					Utils.help();
					continue;
				}
				if (text.equals("pm")) {

					System.out.println("Send to the user: ");
					String to = scanner.nextLine();
					System.out.println("Enter your message: ");
					text = scanner.nextLine();

					Message m = new Message(Utils.getLogin(), to, text, " private message to " + to);

					int res = m.send(Utils.getURL() + "/add");
					if (res != 200) {
						System.out.println("Error: " + res);
						return;
					}
					continue;
				}
				if (text.equals("quit")){
					break;
				}

				Message m = new Message(Utils.getLogin(), "all", text, Utils.getRoom());
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) {
					System.out.println("Error: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
