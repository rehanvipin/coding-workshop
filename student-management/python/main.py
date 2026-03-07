# Student Management Application
# A simple CLI app to manage students in a class

students = []

def main():
    print("\nWelcome to Student Manager!\n")

    while True:
        show_menu()
        choice = input("Choose an option (1-5): ").strip()

        match choice:
            case "1": add_student()
            case "2": list_students()
            case "3": remove_student()
            case "4": assign_gpa()
            case "5":
                print("Goodbye!")
                break
            case _:
                print("Invalid option. Please choose 1 to 5.\n")

def show_menu():
    print("==========================")
    print("  Student Manager")
    print("==========================")
    print("1. Add a student")
    print("2. List all students")
    print("3. Remove a student")
    print("4. Assign GPA")
    print("5. Quit")
    print("--------------------------")

def add_student():
    name = input("Enter student name: ").strip()
    if not name:
        print("Name cannot be empty.")
        return
    students.append({"name": name, "gpa": None})
    print(f"'{name}' has been added to the class.")

def list_students():
    if not students:
        print("No students in the class yet.")
        return
    print(f"\nClass List ({len(students)} students):")
    for i, student in enumerate(students, start=1):
        gpa = student["gpa"] if student["gpa"] is not None else "N/A"
        print(f"  {i}. {student['name']}  |  GPA: {gpa}")
    gpas = [s["gpa"] for s in students if s["gpa"] is not None]
    if gpas:
        print(f"\n  Class Average GPA: {sum(gpas) / len(gpas):.2f}")
    else:
        print("\n  Class Average GPA: N/A")
    print()

def remove_student():
    name = input("Enter student name to remove: ").strip()
    if not name:
        print("Name cannot be empty.")
        return
    student = find_student(name)
    if student:
        students.remove(student)
        print(f"'{name}' has been removed from the class.")
    else:
        print(f"'{name}' was not found in the class.")

def assign_gpa():
    name = input("Enter student name: ").strip()
    if not name:
        print("Name cannot be empty.")
        return
    student = find_student(name)
    if not student:
        print(f"'{name}' was not found in the class.")
        return
    gpa_input = input("Enter GPA (0.0 - 10.0): ").strip()
    try:
        gpa = float(gpa_input)
    except ValueError:
        print("Invalid GPA. Please enter a number.")
        return
    if not 0 <= gpa <= 10:
        print("GPA must be between 0 and 10.")
        return
    student["gpa"] = gpa
    print(f"GPA for '{name}' set to {gpa}.")

def find_student(name):
    for student in students:
        if student["name"] == name:
            return student
    return None

main()