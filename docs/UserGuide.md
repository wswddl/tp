---
layout: default.md
title: "User Guide"
pageNav: 3
---

# 🌟 RecruitTrack User Guide

Welcome to **RecruitTrack**, your efficient companion for managing job applicants! ✨ RecruitTrack is a **desktop application designed for recruiters and hiring managers** who prefer a fast and streamlined workflow. It is optimized for users who can type quickly, leveraging a **Command Line Interface (CLI)** while still offering the benefits of a **Graphical User Interface (GUI)**.

With RecruitTrack, you can:  
✅ Quickly add, edit, and remove applicants from your records.  
✅ Track the progress of candidates throughout the hiring process.  
✅ Search, filter, and sort applicants based on various criteria.  
✅ Organize and manage candidate information efficiently—all without relying on a mouse.

RecruitTrack is lightweight, runs on **Java 17 or later**, and requires no internet connection. Whether you're an HR professional, a recruiter, or part of a hiring team, RecruitTrack simplifies applicant tracking so you can focus on finding the best talent.

## Who is this guide for?

This guide is designed for:
- **First-time users** who want to get started quickly.
- **Recruiters and hiring managers** looking for an efficient way to manage applicants.
- **Power users** who want to maximize productivity using keyboard commands.

## 🎯 Why You'll Love RecruitTrack

- 🚀 **Blazing fast** applicant management
- � **No more mouse dependency** – everything at your fingertips
- 📊 **Smart organization** to keep your pipeline flowing
- 💾 **Automatic saves** so you never lose data

Let’s dive in and explore how RecruitTrack can help you streamline your hiring process! 🚀

--------------------------------------------------------------------------------------------------------------------

## 🚀 Getting Started in 5 Minutes

Let's get you up and running quickly!

### 1️⃣ Install Java
First, ensure you have **Java 17 or later**.  
*Mac users:* Our [special guide](https://se-education.org/guides/tutorials/javaInstallationMac.html) has you covered!

### 2️⃣ Download the App
Grab the latest version from our [download page](https://github.com/AY2425S2-CS2103T-W09-1/tp/releases).

### 3️⃣ Set Up Your Workspace
Place the `.jar` file in your favorite folder – this will be your RecruitTrack home.

### 4️⃣ Launch and Explore
Double-click the file or run:
```bash
java -jar recruittrack.jar
```

You'll see our friendly interface welcoming you:

### 5️⃣ Try These Starter Commands
Type in the command box:
- `help` 📚 - Shows all commands
- `add n/Emma p/87654321 e/emma@tech.com j/Developer` ➕ - Adds Emma
- `list` 📋 - Shows everyone
- `exit` 🚪 - Leaves the party (saves automatically!)

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## ✨ Feature Highlights

### 👥 People Management
- `add` - Welcome new candidates
- `edit` - Update details
- `delete` - Remove applicants

### 🔍 Finding Talent
- `search` - Find needles in haystacks
- `sort` - Organize your view
- `list` - See everyone at once

### 📈 Tracking Progress
- `update` - Move candidates through stages
- `rate` - Give star ratings
- `summary` - Get the big picture

### 🛠️ Advanced Tools
- `export` - Take data elsewhere
- Profile pics - Add friendly faces

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## 🧑‍💻 Working With Applicants

### ➕ Adding New Candidates
**Command Format**: `add n/NAME p/PHONE e/EMAIL j/JOB s/STATUS [t/TAG]...`  

💡 **Pro Tip**: Tags help you categorize candidates for easy searching later!

**Example**:
```bash
add n/Alex Yeoh p/91237654 e/alexy@example.com a/34, Chartwell Drive j/Data Analyst s/Interview Scheduled t/Recommended
```

Command Input:\
<img title="addCommand" alt="Command Input" src="/images/addCommand_before.png"><br/><br/>
Result:\
<img title="addCommand" alt="Result" src="/images/addCommand_after.png"><br/><br/>

### ✏️ Editing Details
**Command Format**: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`
* Edits the applicant at the specified `INDEX`. The index refers to the index number shown in the **displayed** applicant list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the applicant will be removed i.e. adding of tags is **not cumulative**.
* You can remove all the applicant’s tags by typing `t/` without
  specifying any tags after it.

**Example**:
```bash
edit 4 j/Data Scientist p/91238765
```
Updates phone number and job position for candidate #4.

Command Input:\
<img title="editCommand" alt="Command Input" src="/images/editCommand_before.png"><br/><br/>
Result:\
<img title="editCommand" alt="Result" src="/images/editCommand_after.png"><br/><br/>

### 🗑️ Removing Applicants
**Command Format**: `delete IDENTIFIER_TYPE/CONTACT_IDENTIFIER [--force]`
* Deletes the applicant based on the specified `IDENTIFIER_TYPE` and `CONTACT_IDENTIFIER`.
* The `IDENTIFIER_TYPE` can be either `id/` – the ID in the last shown list
  or any combination of the following:
    * `n/` – Name
    * `e/` – Email
    * `p/` – Phone number
    * `bfr/` - Date added (before the specified date)
    * `aft/` - Date added (after the specified date).
    * `j/` - Job Position
    * `s/` - Status
* The `CONTACT_IDENTIFIER` must match the corresponding identifier type (e.g., a name for `n/`, an email for `e/`, etc.).
* The `--force` flag (optional) bypasses confirmation prompts and deletes the applicant immediately.

**Example**:
```bash
delete n/Alex Yeoh --force
```
💡 **Pro Tip**: Adding `--force` skips confirmation for quick removal.

Command Input:\
<img title="deleteCommand" alt="Command Input" src="/images/deleteCommand_before.png"><br/><br/>
Result:\
<img title="deleteCommand" alt="Result" src="/images/deleteCommand_after.png"><br/><br/>

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## 📊 Tracking Progress

### 🔄 Updating Status
Move candidates through your pipeline:

**Command Format**: `update IDENTIFIER_TYPE/CONTACT_IDENTIFIER s/STATUS`
* Identifies the applicant based on the specified `IDENTIFIER_TYPE` and `CONTACT_IDENTIFIER`, then updates their application status to the provided `STATUS`.
* The `IDENTIFIER_TYPE` can be one of the following:
    * `n/` – Name
    * `e/` – Email
    * `p/` – Phone number
    * `id/` – The index of the applicant in the last shown list
* The `CONTACT_IDENTIFIER` must match the corresponding identifier type (e.g., a name for `n/`, an email for `e/`, etc.).
* The `STATUS` should contain only **alphanumeric** characters and spaces.

**Common Statuses**:
1. `Applied` 🆕
2. `Screening` 🔍
3. `Interview Scheduled` 💬
4. `Offered` ✉️
5. `Failed` ❌
5. `Offer Accepted` 🎉
6. `Offer Rejected` ❎

**Example**:
```bash
update n/John Doe s/Job Offered
```

Command Input:\
<img title="updateCommand" alt="Command Input" src="/images/updateCommand_before.png"><br/><br/>
Result:\
<img title="updateCommand" alt="Result" src="/images/updateCommand_after.png"><br/><br/>

### ⭐ Rating Candidates
Give 1-5 star ratings:

**Command Format**: `rate IDENTIFIER_TYPE/CONTACT_IDENTIFIER r/RATING`
* Identifies the applicant based on the specified `IDENTIFIER_TYPE` and `CONTACT_IDENTIFIER`, then assigns the provided `RATING` to them.
* The `IDENTIFIER_TYPE` can be one of the following:
    * `n/` – Name
    * `e/` – Email
    * `p/` – Phone number
    * `id/` – The index of the applicant in the last shown list
* The `CONTACT_IDENTIFIER` must match the corresponding identifier type (e.g., a name for `n/`, an email for `e/`, etc.).
* The `RATING` should be an integer from **1 to 5**, decimal values are not accepted.

**Example**:
```bash
rate id/2 r/4
```
Now candidate #2 has a shiny 4-star rating!

Command Input:\
<img title="rateCommand" alt="Command Input" src="/images/rateCommand_before.png"><br/><br/>
Result:\
<img title="rateCommand" alt="Result" src="/images/rateCommand_after.png"><br/><br/>


[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## 🔍 Finding Your Perfect Hire

### 📋 Listing Everyone
Simple command to see all candidates:
```bash
list
```

### 🔎 Smart Searching
Find candidates by any detail:

**Command Format**: `search [n/NAME] [e/EMAIL] [j/JOB] [s/STATUS]`
* The search is **case-insensitive**. e.g. `hans` will match `Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* Only applicants that match all provided criteria are returned (i.e. logical `AND` search, applicant must match **all** specified field values to appear in the results).<br>
  e.g. `search n/John e/john@example.com` searches by name and email

**Example**:
```bash
search j/Frontend SWE
```
Shows all frontend developers.

Command Input:\
<img title="searchCommand" alt="Command Input" src="/images/searchCommand_before.png"><br/><br/>
Result:\
<img title="searchCommand" alt="Result" src="/images/searchCommand_after.png"><br/><br/>

### 🔄 Sorting Your View
Organize by what matters most:

**Command Format**: `sort CRITERIA/`
* Sort the applicant list by the sorting `CRITERIA/`. The supported `CRITERIA/` are:
    * `n/`: Applicant's name
    * `e/`: Applicant's email address
    * `time/`: The time the applicant was added to the list.
    * `j/`: Job position
    * `s/`: Hiring stage
* Only one sorting criterion can be provided at a time.
* The list will be sorted in lexicographical order with case sensitivity based on the chosen criterion.

**Example**:
```bash
sort n/
```
Shows applicants in alphabetical order.

Command Input:\
<img title="sortCommand" alt="Command Input" src="/images/sortCommand_before.png"><br/><br/>
Result:\
<img title="sortCommand" alt="Result" src="/images/sortCommand_after.png"><br/><br/>

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## 🛠️ Power User Tools

### 📤 Exporting Data
Export the **currently displayed** applicant data into a CSV (Comma-Separated Values) file for sharing:

**Command Format**: `export [FILE-NAME]`
* `FILE-NAME`: The name of the CSV file to be created. It can include a relative folder path (e.g., `data/export.csv`), but the folder must already exist.
* File extension `.csv` is recommended for proper formatting.

**Example**:
```bash
export candidates.csv
```
<br/>

### 📊 Summary Reports
Get quick statistics:

**Command Format**: `summary [n/NAME] [e/EMAIL] [j/JOB_POSITION] [s/STATUS]`
* Having no identifiers will summarize all applicants
* The filter is case-insensitive. e.g. `hans` will match `Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* Only applicants that match all provided identifiers are returned (i.e. `AND` search).<br>
  e.g. `summary s/Rejected j/Data Analyst` summarizes applicants applying for the role of Data Analyst with the status "Rejected".

**Example Output**:
```
Summarized 4 / 4 Applicants
Job Positions ->
[Data Scientist: 1, AI Engineer: 1, Frontend SWE: 1, Backend SWE: 1]
Statuses ->
[Interview Scheduled: 1, Pending Review: 2, Offer Rejected: 1]
```

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## 💾 Your Data is Safe With Us

🔒 **Automatic Saving**: Every change is saved instantly  
📂 **Easy Backups**: Just copy the `data/` folder  
🔄 **Recovery**: Previous versions are kept for safety

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## ❓ Frequently Asked Questions

### 🧐 How do I move my data to a new computer?
Just copy the `data/addressbook.json` file to the new computer - it's that easy!

### 😅 Can I undo a deletion?
Not directly, but if you have a backup of your data file, you can restore it.

### 🌈 Can I change the colors?
Not yet, but we're working on theme options for a future update!

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## 🎨 Cheat Sheet


| Action      | Format, Examples                                                                                                                                                                                                                      |
|-------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Help**    | `help`                                                                                                                                                                                                                                |
| **Add**     | `add n/NAME p/PHONE_NUMBER e/EMAIL j/JOB_POSITION s/STATUS a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com j/Frontend Engineer s/Online Assessment a/123, Clementi Rd, 1234665 t/friend t/SQLExpert` |
| **List**    | `list`                                                                                                                                                                                                                                |
| **Edit**    | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                                                           |
| **Export**  | `export [FILE-NAME]`<br> e.g., `export applicantData.csv`                                                                                                                                                                             |
| **Search**  | `search [n/NAME] [e/EMAIL] [j/JOB_POSITION] [s/STATUS]`<br> e.g., `search n/James Jake`                                                                                                                                               |
| **Delete**  | `delete IDENTIFIER_TYPE/CONTACT_IDENTIFIER [--force]`<br> e.g., `delete n/John Doe`<br> e.g., `delete id/3 --force`                                                                                                                   |
| **Update**  | `update IDENTIFIER_TYPE/CONTACT_IDENTIFIER s/STATUS` <br> e.g., `update e/johndoe@example.com s/Pending Review`                                                                                                                       |
| **Sort**    | `sort CRITERIA/`<br> e.g., `sort n/`                                                                                                                                                                                                  |
| **Summary** | `summary [n/NAME] [e/EMAIL] [j/JOB_POSITION] [s/STATUS]`<br> e.g., `summary j/Frontend Engineer`                                                                                                                                      |
| **Rate**    | `update IDENTIFIER_TYPE/CONTACT_IDENTIFIER r/RATING`<br> e.g., `rate n/Amy Lee r/5`                                                                                                                                                   |
| **Clear**   | `clear`                                                                                                                                                                                                                               |
| **Exit**    | `exit`                                                                                                                                                                                                                                |

<br/>

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## 💌 Final Thoughts

We hope you enjoy using RecruitTrack as much as we enjoyed making it! Remember:

✨ Happy recruiting = Happy hiring! ✨

Need help? Just type `help` in the app or reach out to our friendly support team.

[🔝 Back to top](#-recruittrack-user-guide)
