# This file is a summary of the chapters 1-10 from the Udemy Course [link to the course](https://www.udemy.com/course/git-and-github-bootcamp/)
- [Git: Adding & Commiting](#Git-Adding-Commiting)
- [Git log, VIM, commit messages](#Git-log-VIM-commit-messages)
- [Working with branches](#Working-with-branches)
- [Merging Branches](#Merging-Branches)
- [Git diff](#Git-diff)
- [Git Stashing](#Git-Stashing)
- [Git Reset & Restore & Checkout](#Git-Reset-&-Restore-&-Checkout)
$ git init
$ git status
$ git add filename
$ git add .
$ git commit -m "commit message"
$ git log
$ git log --oneline
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