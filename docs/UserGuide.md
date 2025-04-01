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

[//]: # (![Main Screen Tour]&#40;images/main-screen.png "Your new hiring dashboard"&#41;)

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
**Command**:  
`add n/NAME p/PHONE e/EMAIL j/JOB s/STATUS [t/TAG]...`

**Example**:
```bash
add n/Alex Yeoh p/91237654 e/alexy@example.com a/34, Chartwell Drive j/Data Analyst s/Interview Scheduled t/Recommended
```

💡 **Pro Tip**: Tags help you categorize candidates for easy searching later!

Command Input:\
<img title="addCommand" alt="Command Input" src="/images/addCommand_before.png"><br/><br/>
Result:\
<img title="addCommand" alt="Result" src="/images/addCommand_after.png"><br/><br/>

### ✏️ Editing Details
**Command**:  
`edit INDEX [n/NAME] [p/PHONE]...`

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
**Command**:  
`delete id/INDEX [--force]`

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

**Command**:  
`update id/INDEX s/STATUS`

**Common Stages**:
1. `Applied` 🆕
2. `Screening` 🔍
3. `Interview` 💬
4. `Offered` ✉️
5. `Hired` 🎉

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

**Command**:  
`rate id/INDEX r/RATING`

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

**Command**:  
`search [n/NAME] [e/EMAIL] [j/JOB] [s/STATUS]`

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

**Command**:  
`sort CRITERIA/`

**Options**:
- `n/` - Name
- `time/` - When added
- `s/` - Current status

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
Create CSV files for sharing:

**Command**:  
`export FILENAME.csv`

**Example**:
```bash
export april_candidates.csv
```

### 📊 Summary Reports
Get quick statistics:

**Command**:  
`summary [j/JOB] [s/STATUS]`

**Example Output**:
```
🌟 Candidate Summary 🌟
Total: 42 applicants
Top Jobs:
- Developer: 18
- Designer: 9
Current Status:
- Screening: 12
- Interview: 8
```

<img title="summary" alt="summary" src="/images/summary.png"><br/><br/>

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## 💾 Your Data is Safe With Us

🔒 **Automatic Saving**: Every change is saved instantly  
📂 **Easy Backups**: Just copy the `data/` folder  
🔄 **Recovery**: Previous versions are kept for safety

[//]: # (<box type="warning" seamless>)

[//]: # (⚠️ **Important**: While you can edit the data file directly, we recommend using the app interface to avoid accidents!)

[//]: # (</box>)

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

[//]: # ()
[//]: # (| Action | Command | Example |)

[//]: # (|--------|---------|---------|)

[//]: # (| Add | `add n/NAME p/PHONE e/EMAIL j/JOB` | `add n/Alex p/91234567 e/alex@ex.com j/Developer` |)

[//]: # (| Edit | `edit INDEX [FIELD/VALUE]` | `edit 1 p/98765432` |)

[//]: # (| Delete | `delete id/INDEX` | `delete id/3` |)

[//]: # (| Update | `update id/INDEX s/STATUS` | `update id/2 s/Interview` |)

[//]: # (| Rate | `rate id/INDEX r/1-5` | `rate id/4 r/5` |)

[//]: # (| Export | `export FILENAME.csv` | `export candidates.csv` |)

[🔝 Back to top](#-recruittrack-user-guide)

--------------------------------------------------------------------------------------------------------------------

## 💌 Final Thoughts

We hope you enjoy using RecruitTrack as much as we enjoyed making it! Remember:

✨ Happy recruiting = Happy hiring! ✨

Need help? Just type `help` in the app or reach out to our friendly support team.

[🔝 Back to top](#-recruittrack-user-guide)
