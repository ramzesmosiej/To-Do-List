# This file is a summary of the chapters 1-10 from the Udemy Course [link to course](https://www.udemy.com/course/git-and-github-bootcamp/)
## Contents: 
- [Git: Adding & Commiting](Git-Adding-Commiting)
- [Git log, VIM, commit messages](Git-log-VIM-commit-messages)
- [Working with branches](Working-with-branches)
- [Merging Branches](Merging-Branches)
- [Git diff](Git-diff)
- [Git Stashing](Git-Stashing)
- [Git Reset & Restore & Checkout](Git-Reset-&-Restore-&-Checkout)

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
The are many options when using this command: [link to officail docs](https://git-scm.com/book/en/v2/Git-Basics-Viewing-the-Commit-History)

