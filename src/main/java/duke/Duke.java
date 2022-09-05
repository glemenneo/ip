package duke;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import duke.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

public class Duke {

    private TaskList taskList;
    private String path;

    public Duke(String path) {
        this.path = path;
        this.taskList = new TaskList();
    }

    public void runDuke() {
        Ui.greeting();
        try {
            Storage.loadData(this.taskList, this.path);
            System.out.println("\tYour previous tasks have been loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("\tWelcome new user!");
        }

        boolean quit = false;
        Scanner scanner = new Scanner(System.in);
        while (!quit) {
            System.out.println("Enter a command below:");
            String input = scanner.nextLine();
            try {
                quit = Parser.parseInput(input, this.taskList);
            } catch (DukeException e) {
                Ui.showExceptionMessage(e);
                quit = false;
            }
        }

        try {
            Storage.writeData(this.taskList, this.path);
        } catch (IOException e) {
            Ui.showExceptionMessage(e);
            System.out.println("\t☹ OOPS!!! I'm sorry, saving of tasks has failed :-(");
        }
        scanner.close();
        Ui.goodbye();
    }
}

