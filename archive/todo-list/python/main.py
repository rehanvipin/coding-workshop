import json

todo_list = []

def view_list():
    if len(todo_list) == 0:
        print("Your list is empty.")
        return
    
    print("Your list currently looks like this:")
    print("Index", "Completed", "Item Name")
    for i, item in enumerate(todo_list):
        completed_icon = '[x]' if item['completed'] else '[ ]'
        print(f'{i+1}'.ljust(5), f'{completed_icon}'.ljust(9), item['name'])

def add_to_list():
    item_name = input("Please specify the entry to be added: ")
    if len(item_name) == 0:
        print("Not adding an empty entry")
        return
    todo_list.append({'name': item_name, 'completed': False})
    print("Entry added successfully.")

def remove_from_list():
    del_index = input("Please enter the index of the entry you would like to remove: ")
    try:
        del_index = int(del_index)
    except ValueError:
        print("Invalid number", del_index)
        return
    if del_index < 1 or del_index > len(todo_list):
        print("Entry with such an index does not exist.", del_index)
        return
    
    todo_list.pop(del_index-1)
    print("Entry removed successfully")


def mark_complete():
    complete_index = input("Please enter the index of the entry you would mark complete: ")
    try:
        complete_index = int(complete_index)
    except ValueError:
        print("Invalid number", complete_index)
        return
    if complete_index < 1 or complete_index > len(todo_list):
        print("Entry with such an index does not exist.", complete_index)
        return
    
    todo_list[complete_index-1]['completed'] = True
    print("Entry marked complete successfully")

def clear_list():
    confirm = input("Are you sure you want to clear the list? (Y/N): ")
    if confirm.lower().startswith('y'):
        todo_list.clear()
        print("List cleared successfully")
    else:
        print("Not clearing the list")

def save_list():
    with open('./todo-list-data.json', 'w') as wire:
        wire.write(json.dumps(todo_list))

def load_list():
    try:
        with open('./todo-list-data.json', 'r') as red:
            global todo_list
            todo_list = json.loads(red.read())
    except FileNotFoundError:
        todo_list = []

def capture_interaction():
    print("")
    view_list()
    print("""
What would you like to do?
    
1. Add item to the list
2. Remove things from the list
3. Mark things as completed
4. Clear the list
5. Exit the app
    """)

    choice = input("Enter your choice: ")
    match choice:
        case "1":
            add_to_list()
        case "2":
            remove_from_list()
        case "3":
            mark_complete()
        case "4":
            clear_list()
        case "5":
            return False
        case _:
            print("Unknown choice")
    save_list()
    return True

def main():
    load_list()
    print("To-Do List App")
    continue_interaction = True
    while continue_interaction:
        continue_interaction = capture_interaction()

main()