---
layout: page
title: User Guide
---

GrAB3 is an **all in one planner application** that provides a simple way for university students to stay ahead of their
busy schedules. The student interacts with the application using a command-line interface (CLI).

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `addressbook.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `setup n/NAME`, `NAME` is a parameter which can be used as `setup n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Initialize User and Semester Information : `setup`

Setup User and Semester data, including username, semester term and fiscal year.

Format: `setup n/USERNAME sd/STARTDURATION ed/ENDDURATION fy/FISCALYEAR`

- The `STARTDURATION` has to be **before** `ENDDURATION`

Examples:
* `setup n/John Doe sd/22-09-2020 ed/29-11-2020 fy/AY20/21 Sem 1`
* `setup fy/AY20/21 Sem 1 ed/29-11-2020 n/Betsy Crowe sd/22-09-2020`

### Edit Setup Details : `editsetup`

Edit Setup details for GrAB3, including username, semester term and fiscal year.

Format: `editsetup [n/USERNAME] [sd/STARTDURATION] [ed/ENDDURATION] [fy/FISCALYEAR]`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Edit the duration of the semester with either start duration or end duration or both
</div>

Examples:
*  `editsetup n/Jane Doe sd/23-02-2020 ed/29-06-2020 fy/AY19/20 Sem 2`
*  `editsetup n/Jenny Doe sd/23-02-2020 fy/AY19/20 Sem 2`

### Delete Setup Details: `deletesetup`

Delete all setup details in GrAB3.

Format: `deletesetup` 

### Add a Task to Task Schedule: `addtask`

Add a task to the task schedule in GrAB3.

Format: `addtask n/NAME sd/DATE d/DURATION [t/TAG]`

- Addition of a task to a specific day will be **rejected** if the total tasks for that day exceed 24 hours.

Examples:
* `addtask n/John Doe sd/20-09-2020 d/2`
* `addtask n/John Doe sd/20-09-2020 d/2 t/CS2101`

### Edit Task Details: `edittask`

Edit task details for GrAB3, including task name, date, duration and tag.

Format: `edittask i/INDEX n/TASKNAME sd/DATE d/DURATION`

- The index must be a **positive integer** and must be **valid**
- `DURATION` must be in hours

Example:
* `edittask i/2 n/Complete 2103 Project sd/20-09-2020 d/3`
* `edittask i/2 sd/20-09-2020`
* `edittask i/2 n/Complete 2103 Project d/4`

### Listing All Persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a Person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Archiving data files `[coming in v2.0]`

_{explain the feature here}_

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
