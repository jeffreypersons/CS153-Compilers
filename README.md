**Project assignments for SJSU's CS 153 (Compilers) course.
Starring Jeff, Itaru, Ki Sung, and Ben.**


## How to contribute using GitHub Desktop
0. clone the repo into a location into the default GitHub documents folder
1. create Branch
    1. go `toolbar>branch>new-branch` and then enter _your-new-feature-branch-name_
    2. click publish branch (upper middle of the screen)
2. every time you make changes to any of the contents in the _CS153Projects_ directory:
    1. click fetch origin (this pulls any changes from the remote repository)
    2. go to the left side of the github desktop, and uncheck all, then check ONLY the
       ones that are logically related
    3. add a summary (and optional commit description if needed)
    4. click commit to _your-new-feature-branch-name_.
    5. click fetch origin again to sync your branch with the remote repository
    6. repeat these steps if there are more to add. DO NOT SELECT ALL AND SIMPLY SAY
       LATEST CHANGES. Rather, if you have a lot of changes, do it in blocks (eg updated
       readme, improved database field names, etc)
3. when you've gotten to the point that you're ready to add your changes to master:
    1. test your changes, and make sure it compiles with the makefile
       (you should be doing this incrementally and often, anyways).
    2. go onto the GitHub website, go to your branch off of master, and then click compare and pull request
    3. give the PR a title of what change your making, and then add a summary of the changes you're making,
       what/how you tested it, and if necessary, why (necessary to add if it's a bug fix or refactoring).
    4. on the right of the page there's a _reviewers_ button, and add the names of those to review (if unsure,
       then just add everyone's names).
    5. after receiving lgtm tags from reviewers, and at least one approval, then go ahead and merge.
       If not enough people looked at it, let them know.
    6. IF changes were requested, then you'll be blocked from adding your changes to master.
       In that case, add some more changes to your branch addressing the requested changes.
    7. Finally, merge the pull request with master, or have one of us do it for you.
4. delete the branch after merging, switch back to master, and rinse and repeat!


## How to contribute using Git from terminal
0. use git clone on the repo, using credentials and this url
1. use _git checkout_ with `your-new-feature-branch-name` from master to create branch
2. use git add to index the files, commit to update locally, and push to push the changes
3. then do a compare and pull request from the _GitHub website_
4. add the relevant code reviewers, and implement any of their requested changes
5. merge your branch with master - rebase and squash if necessary (test merging on a
   temporary branch off master if you're not comfortable with that sort of things).
 
