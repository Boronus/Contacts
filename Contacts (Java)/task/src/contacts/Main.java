package contacts;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Book book = new Book();
        Action action = null;
        CurrentAction currentAction = CurrentAction.MENU;
        Scanner scanner = new Scanner(System.in);

        while (action != Action.EXIT) {
            if (currentAction == CurrentAction.MENU) {
                System.out.println();
                System.out.println("[menu] Enter action (add, list, search, count, exit):");
                action = getAction(scanner.nextLine());

                switch (action) {
                    case ADD:
                        book.add();
                        break;
                    case LIST:
                        book.list();
                        currentAction = CurrentAction.LIST;
                        break;
                    case SEARCH:
                        book.search();
                        currentAction = CurrentAction.SEARCH;
                        break;
                    case COUNT:
                        book.count();
                        break;
                }
            }

            if (currentAction == CurrentAction.SEARCH) {
                System.out.println();
                System.out.println("[search] Enter action ([number], back, again): ");
                String actionString = scanner.nextLine();
                try {
                    int index = Integer.parseInt(actionString);
                    book.selectedRecordNumber = index;
                    Contact infoContact = book.list.get(index -1);
                    infoContact.info();
                    currentAction = CurrentAction.RECORD;
                } catch (NumberFormatException nfe) {
                    action = getAction(actionString);

                    switch (action) {
                        case BACK:
                            currentAction = CurrentAction.MENU;
                            break;
                        case AGAIN:
                            book.search();
                            break;
                    }
                }
            }

            if (currentAction == CurrentAction.RECORD) {
                System.out.println();
                System.out.println("[record] Enter action (edit, delete, menu): ");
                String actionString = scanner.nextLine();
                try {
                    int index = Integer.parseInt(actionString);
                    book.selectedRecordNumber = index;
                    Contact infoContact = book.list.get(index -1);
                    infoContact.info();
                } catch (NumberFormatException nfe) {
                    action = getAction(actionString);

                    switch (action) {
                        case EDIT:
                            book.edit();
                            break;
                        case DELETE:
                            book.delete();
                            currentAction = CurrentAction.MENU;
                            break;
                        case MENU:
                            currentAction = CurrentAction.MENU;
                            break;
                    }
                }
            }

            if (currentAction == CurrentAction.LIST) {
                System.out.println();
                System.out.println("[list] Enter action ([number], back): ");
                String actionString = scanner.nextLine();
                try {
                    int index = Integer.parseInt(actionString);
                    book.selectedRecordNumber = index;
                    Contact infoContact = book.list.get(index -1);
                    infoContact.info();
                    currentAction = CurrentAction.RECORD;
                } catch (NumberFormatException nfe) {
                    action = getAction(actionString);

                    if (Objects.requireNonNull(action) == Action.BACK) {
                        currentAction = CurrentAction.MENU;
                    }
                }
            }
        }
    }

    public static Action getAction(String actionName) {
        return switch (actionName) {
            case "add" -> Action.ADD;
            case "remove" -> Action.REMOVE;
            case "edit" -> Action.EDIT;
            case "count" -> Action.COUNT;
            case "info" -> Action.INFO;
            case "search" -> Action.SEARCH;
            case "list" -> Action.LIST;
            case "back" -> Action.BACK;
            case "again" -> Action.AGAIN;
            case "menu" -> Action.MENU;
            case "delete" -> Action.DELETE;
            default -> Action.EXIT;
        };
    }

}
