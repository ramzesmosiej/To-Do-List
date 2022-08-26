# This file is a summary of the chapters 1-10 from the Udemy Course [link to the course](https://www.udemy.com/course/git-and-github-bootcamp/)
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
$ git init
```
The main tool you use to determine which files are in which state is git status command
```
$ git status
```
In order to begin tracking a new file you use the command git add. It will than move to the Staging Area. git add . will add all the files present in the current repository.
```
$ git add filename
$ git add .
```
If the Staging Area is set up the way you want it is time to commit new changes.
```
$ git commit -m "commit message"
```
[docs](https://git-scm.com/book/en/v2/Git-Basics-Recording-Changes-to-the-Repository)
### Git log, VIM, commit messages
The command used to view the commit history is the git log command. It will list all the commits with the most recent commits showing at the top
```
$ git log
```
```
$ git log --oneline
```
The are many options when using this command: [link to official docs](https://git-scm.com/book/en/v2/Git-Basics-Viewing-the-Commit-History)
The default bash command line editor is VIM 
There is some exercise needed to learn how to use it: 
[vim docs](https://linuxhandbook.com/basic-vim-commands/#:~:text=Some%20of%20my%20favorite%20Vim%20movement%20commands%20are%3A,on%20the%20screen%20while%20typing%20the%20line%20numbers.)
### Working with branches
Branching means you diverge from the main line of development and continue to do work without messing that main line
In order to create new branch you use git branch command
```
$ git branch branchname
```
HEAD is a special pointer and points to the branch you are currently on.
Switching to the branch:
```
$ git switch branchname
```
That moves head to the chosen branch. If you add commits to the new branch the master branch still points to the commit you were when you switched.
In order to create branch and immediately switch to it use:
```
$ git switch -c branchname
```
```
// listing all current branches
$ git branch
```
```
// changing branch name
$ git branch --move bad-branch-name corrected-branch-name
```
Deleting branch
```
$ git branch -d branchname
```
### Merging Branches
When you switch branches, Git resets your working directory to look like it did the last time you committed on that branch
Let's say you are working on the main branch and have already few commits on it. At some point in time (commit) you decide to switch to the new branch to work on some new feature. If you are done with it you can merge it to the master branch. If there is no new commits on master branch the merge is called "fast- forward". Git simply puts the master pointer forward.
```
$ git switch master
$ git merge fastforwardbranch
Updating f42c576..3a0874c
Fast-forward
 index.html | 2 ++
 1 file changed, 2 insertions(+)
```
In second case, there is some work done on the master in the meantime. The commit on the branch you are on is not the direct ancestor of the branch you are merging into. This is reffered to as a merge commit and it has more that one parent.
```
$ git switch master
Switched to branch 'master'
$ git merge iss53
Merge made by the 'recursive' strategy.
index.html |    1 +
1 file changed, 1 insertion(+)
```
##### Merge Conficts
Merge Conficts occurs when the merged branches edit the same file line differently or when one branch modifies a file and another branch deletes it. You can resolve merge conficts in editors like Visual Studio or by using command line.
```
$ git merge iss53
Auto-merging index.html
CONFLICT (content): Merge conflict in index.html
Automatic merge failed; fix conflicts and then commit the result.
```
The process is paused and it is waiting for resolving the conflict. If you type git status:
```
$ git status
On branch master
You have unmerged paths.
  (fix conflicts and run "git commit")

Unmerged paths:
  (use "git add <file>..." to mark resolution)

    both modified:      index.html

no changes added to commit (use "git add" and/or "git commit -a")
```
You can resolve conflict in graphical tool or directly in the file. After that type git commit to finalize the merge commit.

### Git diff
Diff is used to show all the changes done after the last commit.
Git diff shows the exact lines added and removed. It is an extension to the git status command.
For example, to see what you have staged that will go into the next commit, use:
```
$ git diff --staged
```
```
diff --git a/README b/README
new file mode 100644
index 0000000..03902a1
--- /dev/null
+++ b/README
@@ -0,0 +1 @@
+My Project
```
It will show only changes that are still unstaged.
To see both staged and unstaged changes use
```
$ git diff HEAD
```
### Git Stashing
Often, when you are working on a project and you are not ready to commit you want to switch to another branch. In order not to lose your work you use git stash command. You can get back to this point later.
```
git stash
```
[docs](https://git-scm.com/book/en/v2/Git-Tools-Stashing-and-Cleaning)
### Git Reset & Restore & Checkout
There are very few reasons to checkout a commit (and not a branch). Maybe you want to experiment with a specific, old revision and therefore need to have that revision's files in your working copy folder.
```
$ git checkout 757c47d4
```
You are now in a state called detached head. The number of the commit can be found using 
```
$ git log --oneline
```
We can also reset our repository back to the specific commit using git reset command
git reset --soft this option moves HEAD back to the specified commit, undoes all the changes made between where HEAD is pointing and the specified commit
But all the changes are saved in the staged area.
git reset --hard
similar but it makes the commit non usable, it deletes all the changes in the working directory.
