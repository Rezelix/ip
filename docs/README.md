# Clovis User Guide

// Product screenshot goes here
```
  _____ _            _
 / ____| |          (_)
| |    | | _____   ___ ___
| |    | |/ _ \ \ / / / __|
| |____| | (_) \ V /| \__ \
 \_____|_|\___/ \_/ |_|___/
 What do you want from me this time?
__________________________________________________________
```
---

## Adding a todo : `todo`

Adds a task without any date or time.

**Format:**

```
todo DESCRIPTION
```

**Examples:**

```
todo read book
```

**Expected output:**

```
added: [T][ ] read book
You currently have 2 tasks in your list
```

---

## Adding a deadline : `deadline`

Adds a task with a due date.

**Format:**

```
deadline DESCRIPTION /by dd-mm-yyyy
```

**Example:**

```
deadline submit assignment /by 15-10-2025
```

**Expected output:**

```
added: [D][ ] submit assignment (by: 15-10-2025)
You currently have 2 tasks in your list
```

---

## Adding an event : `event`

Adds a task that happens at a specific date/time.

**Format:**

```
event DESCRIPTION /from START_DATE_OR_TIME /to END_DATE_OR_TIME
```

**Example:**

```
event project meeting /from 20-10-2025 14:00 to: 21-10-2025 14:00
```

**Expected output:**

```
added: [E][ ] project meeting (from: 20-10-2025 14:00 to: 21-10-2025 14:00)
You currently have 3 tasks in your list
```

---

## Listing all tasks : `list`

Displays all tasks currently stored.

**Format:**

```
list
```

**Expected output:**

```
1.[T][ ] read book
2.[D][ ] submit assignment (by: 15-10-2025)
3.[E][ ] project meeting (from: 20-10-2025 14:00 to: 21-10-2025 14:00)
```

---

## Marking a task : `mark`

Marks a task as done.

**Format:**

```
mark INDEX
```

**Example:**

```
mark 1
```

**Expected output:**

```
Marked Task 1 successfully!
[T][X] read book
```

---

## Unmarking a task : `unmark`

Marks a completed task as not done.

**Format:**

```
unmark INDEX
```

**Example:**

```
unmark 1
```

**Expected output:**

```
Unmarked Task 1 successfully!
[T][ ] read book
```

---

## Deleting a task : `delete`

Removes a task permanently.

**Format:**

```
delete INDEX
```

**Example:**
```
delete 2
```

**Expected output:**

```
Deleted the task: 2.[D][ ] submit assignment (by: 15-10-2025)
You currently have 2 tasks in your list
```

---

## Finding tasks : `find`

Searches for tasks containing the given keyword(s).

**Format:**

```
find KEYWORD
```

**Examples:**

```
find project
```

**Expected output:**

```
1.[E][ ] project meeting (from: 20-10-2025 14:00 to: 21-10-2025 14:00)
```

---

## Clearing all tasks : `deleteAll`

Removes all tasks.

**Format:**

```
deleteAll
```

---

## Exiting the program : `bye`

Exits Clovis.

**Format:**

```
bye
```

**Expected output:**

```
Bye. Don't come again!
```

---

## Saving the data : `save`

Saves all tasks into a text file.

**Format:**

```
save
```

**Expected output:**

```
Saving tasks to file...
Successfully saved all Tasks
```
* Data is stored in a file under the `/data` folder (e.g., `data/tasks.txt`).
* If the file is corrupted or deleted, Clovis will start with an empty task list.

---

## Command Summary

| Action           | Format / Example                                                                                                                               |
|------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Todo**     | `todo DESCRIPTION`<br>e.g., `todo read book`                                                                                                   |
| **Add Deadline** | `deadline DESCRIPTION /by dd-mm-yyyy`<br>e.g., `deadline submit assignment /by 15-10-2025`                                                     |
| **Add Event**    | `event DESCRIPTION /from START_DATE_OR_TIME /to END_DATE_OR_TIME`<br>e.g., `event project meeting /from 20-10-2025 14:00 /to 21-10-2025 14:00` |
| **List**         | `list`                                                                                                                                         |
| **Mark**         | `mark INDEX`                                                                                                                                   |
| **Unmark**       | `unmark INDEX`                                                                                                                                 |
| **Delete**       | `delete INDEX`<br>e.g., `delete 2`                                                                                                             |
| **Find**         | `find KEYWORD`<br>e.g., `find project`                                                                                                         |
| **Delete All**   | `deleteAll`                                                                                                                                    |
| **Help**         | `help`                                                                                                                                         |
| **Exit**         | `bye`                                                                                                                                          |
| **Save**         | `save`                                                                                                                                         |
---
