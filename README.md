# Team Assignments for SJSU's CS 153 (Compilers) course. Starring Jeff, Itaru, Ben, and Ki Sung.


## WHAT YOU SHOULD ALWAYS DO
Incremental, staged pull requests AND commits -- that is, never just blindly select all and commit.
Rather, if you have a lot of changes, do it in blocks (eg updated readme, improved parser field names, etc)

## How to contribute using Git from terminal
0. use git clone on the repo, using credentials and this url
1. use _git checkout_ with `your-new-feature-branch-name` from master to create branch
2. use git add to index the files, commit to update locally, and push to push the changes
3. then do a compare and pull request from the _GitHub website_
4. add the relevant code reviewers, and implement any of their requested changes
5. merge your branch with master - rebase and squash if necessary (test merging on a
   temporary branch off master if you're not comfortable with that sort of things).


## How to contribute using GitHub Desktop
0. clone the repo into a location into the default GitHub documents folder
1. create Branch
    1. go `toolbar>branch>new-branch` and then enter _your-new-feature-branch-name_
    2. click publish branch (upper middle of the screen)
2. every time you make changes to any of the contents in the _CS153Projects_ directory:
    1. fetch origin (this syncs the remote repositories changes with your local copy)
    2. uncheck all changes, and check only the logically related ones to add.
       IMPORTANT: DON'T LAZILY COMMIT ALL and say LATEST CHANGES, etc
    3. add a summary and press commit
3. when you've gotten to the point that you're ready to add your changes to master:
    1. test your changes, and make sure it compiles with the makefile
       (you should be doing this incrementally and often, anyways).
    2. go onto the GitHub website, go to your branch off of master, and then click compare and pull request
    3. give it a title of the change, and summary of why/how/what, and manual/compilation tests you've made,
       and add reviewers
    4. add reviewer to the PR (add everyone if unsure) - and then implement any of their changes
    5. once approved, merge with master and delete the feature branch (or someone else will do it for you)
