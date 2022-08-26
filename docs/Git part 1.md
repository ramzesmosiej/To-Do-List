# This file is a summary of the chapters 1-10 from the Udemy Course [link to course](https://www.udemy.com/course/git-and-github-bootcamp/)
## Contents: 
- [Git: Adding & Commiting](#Git-Adding-Commiting)
- [Git log, VIM, commit messages](#Git-log-VIM-commit-messages)
- [Working with branches](#Working-with-branches)
- [Merging Branches](#Merging-Branches)
- [Git diff](#Git-diff)
- [Git Stashing](#Git-Stashing)
- [Git Reset & Restore & Checkout](#Git-Reset-&-Restore-&-Checkout)

### Git: Adding & Commiting
In order to create git repository in a project directory that is currently under version control you have to execute git init command in that directory
```
git init
```
The main tool you use to determine which files are in which state is git status command
```
git status
```
In order to begin tracking a new file you use the command git add. It will than move to the Staging Area. git add . will add all the files present in the current repository.
```
git add filename
git add .
```
If the Staging Area is set up the way you want it is time to commit new changes.
```
git commit -m "commit message"
```
[docs](https://git-scm.com/book/en/v2/Git-Basics-Recording-Changes-to-the-Repository)
### Git log, VIM, commit messages
The command used to view the commit history is the git log command. It will list all the commits with the most recent commits showing at the top
```
git log
```
```
git log --oneline
```
The are many options when using this command: [link to official docs](https://git-scm.com/book/en/v2/Git-Basics-Viewing-the-Commit-History)
The default bash command line editor is VIM 
There is some exercise needed to learn how to use it: 
[vim docs](https://linuxhandbook.com/basic-vim-commands/#:~:text=Some%20of%20my%20favorite%20Vim%20movement%20commands%20are%3A,on%20the%20screen%20while%20typing%20the%20line%20numbers.)
### Working with branches
Branching means you diverge from the main line of development and continue to do work without messing that main line
In order to create new branch you use git branch command
```
git branch branchname
```
HEAD is a special pointer and points to the branch you are currently on.
Switching to the branch:
```
git switch branchname
```
That moves head to the chosen branch. If you add commits to the new branch the master branch still points to the commit you were when you switched.
In order to create branch and immidiately switch to it use:
```
git switch -c branchname
```
```
# listing all current branches
git branch
```
