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

```bash
todo DESCRIPTION
```

**Examples:**

```bash
todo read book
todo buy groceries
```

**Expected output:**

```text
Got it. I've added this task:
  [T][ ] read book
Now you have 1 task in the list.
```

---

## Adding a deadline : `deadline`

Adds a task with a due date.

**Format:**

```bash
deadline DESCRIPTION /by dd-mm-yyyy
```

**Example:**

```bash
deadline submit assignment /by 15-10-2025
```

**Expected output:**

```text
Got it. I've added this task:
  [D][ ] submit assignment (by: 15-10-2025)
Now you have 2 tasks in the list.
```

---

## Adding an event : `event`

Adds a task that happens at a specific date/time.

**Format:**

```bash
event DESCRIPTION /from START_DATE_OR_TIME /to END_DATE_OR_TIME
```

**Example:**

```bash
event project meeting /from 20-10-2025 14:00 to: 21-10-2025 14:00
```

**Expected output:**

```text
Got it. I've added this task:
  [E][ ] project meeting (from: 20-10-2025 14:00 to: 21-10-2025 14:00)
Now you have 3 tasks in the list.
```

---

## Listing all tasks : `list`

Displays all tasks currently stored.

**Format:**

```bash
list
```

**Expected output:**

```text
Here are the tasks in your list:
1. [T][ ] read book
2. [D][ ] submit assignment (by: 15-10-2025)
3. [E][ ] project meeting (from: 20-10-2025 14:00 to: 21-10-2025 14:00)
```

---

## Marking a task : `mark`

Marks a task as done.

**Format:**

```bash
mark INDEX
```

**Example:**

```bash
mark 1
```

**Expected output:**

```text
Nice! I've marked this task as done:
  [T][X] read book
```

---

## Unmarking a task : `unmark`

Marks a completed task as not done.

**Format:**

```bash
unmark INDEX
```

**Example:**

```bash
unmark 1
```

**Expected output:**

```text
OK, I've marked this task as not done yet:
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
Noted. I've removed this task:
  [D][ ] submit assignment (by: 15-10-2025)
Now you have 2 tasks in the list.
```

---

## Finding tasks : `find`

Searches for tasks containing the given keyword(s).

**Format:**

```
find KEYWORD [MORE_KEYWORDS]
```

**Examples:**

```
find project
find assignment submit
```

**Expected output:**

```
Here are the matching tasks in your list:
1. [E][ ] project meeting (from: 20-10-2025 14:00 to: 21-10-2025 14:00)
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

## Saving the data

Clovis saves automatically after every command that modifies tasks.
No manual save command is required.

* Data is stored in a file under the `/data` folder (e.g., `data/tasks.txt`).
* If the file is corrupted or deleted, Clovis will start with an empty task list.

**Caution:** Always back up the file before editing it manually.

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

---
