import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

class TodoApp {
    static List<ListItem> todoList = new ArrayList<>();
    private static final String storageFileName = "./todo-list-data.ser";

    private static class ListItem implements Serializable {
        private static final long serialVersionUID = 1l;
        String name;
        Boolean completed;
        ListItem(String name, Boolean completed) {
            this.name = name;
            this.completed = completed;
        }
    }

    public static void main(String args[]) {
        loadList();
        Boolean continueInteraction = true;
        while (continueInteraction) {
            continueInteraction = captureInteraction();
        }
    }

    private static Boolean captureInteraction() {
        System.out.println("");
        viewList();
        System.out.println("""
What would you like to do?
    
1. Add item to the list
2. Remove things from the list
3. Mark things as completed
4. Clear the list
5. Exit the app
                """);
        String input = System.console().readLine("Enter your choice: ");
        switch (input) {
            case "1":
                addToList();
                break;
            case "2":
                removeFromList();
                break;
            case "3":
                markComplete();
                break;
            case "4":
                clearList();
                break;
            case "5":
                return false;
            default:
                System.out.println("Unknown choice");
        }
        saveList();
        
        return true;
    }

    private static void viewList() {
        if (todoList.isEmpty()) {
            System.out.println("Your list is empty.");
            return;
        }

        System.out.println("Your list currently looks like this:");
        System.out.println("Index | Completed | Item Name");
        for (int i = 0; i < todoList.size(); i++) {
            ListItem item = todoList.get(i);
            String completedIcon = item.completed ? "[x]" : "[ ]";
            System.out.printf("%-7s %-11s %s%n", i+1, completedIcon, item.name);
        }
    }

    private static void addToList() {
        String itemName = System.console().readLine("Please specify the entry to be added: ");
        if (itemName.isBlank()) {
            System.out.println("Not adding an empty entry");
            return;
        }
        ListItem item = new ListItem(itemName, false);
        todoList.add(item);
        System.out.println("Entry added successfully");
    }

    private static void removeFromList() {
        String delIndexString = System.console().readLine("Please enter the index of the item you would like to remove: ");
        Integer delIndex;
        try {
            delIndex = Integer.parseInt(delIndexString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number " + delIndexString);
            return;
        }
        if (delIndex < 1 || delIndex > todoList.size()) {
            System.out.println("Index out of bounds of list");
            return;
        }
        todoList.remove(delIndex.intValue()-1);
        System.out.println("Entry removed successfully");
    }

    private static void markComplete() {
        String completeIndexString = System.console().readLine("Please enter the index of the item you would like to mark complete: ");
        Integer completeIndex;
        try {
            completeIndex = Integer.parseInt(completeIndexString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number " + completeIndexString);
            return;
        }
        if (completeIndex < 1 || completeIndex > todoList.size()) {
            System.out.println("Index out of bounds of list");
            return;
        }
        todoList.get(completeIndex.intValue()-1).completed = true;
        System.out.println("Entry marked complete successfully");
    }

    private static void clearList() {
        String confirm = System.console().readLine("Are you sure you want to clear the list? (Y/N): ");
        if (confirm.toLowerCase().startsWith("y")) {
            todoList.clear();
            System.out.println("List cleared successfully");
        } else {
            System.out.println("Not clearing the list");
        }
    }

    public static void saveList() {
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFileName))) {
            oos.writeObject(todoList);
        } catch (IOException e) {
            System.err.println("Error saving list: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFileName))) {
            todoList = (List<ListItem>) ois.readObject();
        } catch (FileNotFoundException e) {
            todoList = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading list: " + e.getMessage());
            todoList = new ArrayList<>();
        }
    }
}