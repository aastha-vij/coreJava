# Git & CI/CD — Complete Reference Guide

---

## Table of Contents
1. [Git Fundamentals](#1-git-fundamentals)
2. [Core Git Commands](#2-core-git-commands)
3. [Branching & Merging](#3-branching--merging)
4. [Remote Operations](#4-remote-operations)
5. [Undoing Changes](#5-undoing-changes)
6. [Stash & Tags](#6-stash--tags)
7. [Git Workflows & Branching Strategies](#7-git-workflows--branching-strategies)
8. [GitHub Actions](#8-github-actions)
9. [Jenkins](#9-jenkins)
10. [Maven Build Tool](#10-maven-build-tool)
11. [Interview Q&A](#11-interview-qa)

---

## 1. Git Fundamentals

### What is Git?
Distributed version control system. Every developer has a full copy of the repository history (unlike centralized SVN).

### Key Concepts

| Concept | Description |
|---------|-------------|
| **Repository** | Project folder tracked by Git |
| **Working Directory** | Files you're currently editing |
| **Staging Area (Index)** | Files prepared for the next commit |
| **Commit** | Snapshot of staged changes with a message |
| **Branch** | Lightweight pointer to a commit |
| **HEAD** | Pointer to the current commit/branch |
| **Remote** | Copy of the repository on a server (GitHub, GitLab, Bitbucket) |
| **Clone** | Copy of a remote repo to local |
| **Fork** | Your personal copy of someone else's repo (GitHub concept) |

### The Three States

```
Working Directory  →  git add  →  Staging Area  →  git commit  →  Local Repo
                                                                        ↓
                                                              git push → Remote Repo
```

---

## 2. Core Git Commands

### Setup

```bash
# Configure identity (first-time setup)
git config --global user.name  "Your Name"
git config --global user.email "you@example.com"
git config --global core.editor "code --wait"    # VS Code as editor
git config --list                                  # see all config
git config user.name                               # see specific value

# Initialize
git init                       # create new repo in current folder
git clone <url>                # clone remote repo
git clone <url> my-folder      # clone into specific folder
git clone --depth 1 <url>      # shallow clone (only latest commit)
```

### Basic Workflow

```bash
# Check status
git status                     # show modified/staged/untracked files
git status -s                  # short format: M = modified, ?? = untracked

# Add to staging
git add file.java              # stage specific file
git add src/                   # stage entire folder
git add *.java                 # stage by pattern
git add .                      # stage ALL changes in current directory
git add -p                     # interactive staging (patch mode — select hunks)

# Commit
git commit -m "Add login page POM class"
git commit -am "Fix login locator"    # stage ALL tracked + commit in one step
git commit --amend                     # modify last commit (message or add files)
git commit --amend -m "New message"   # amend just the message

# View history
git log                        # full log
git log --oneline              # compact — one line per commit
git log --oneline --graph      # ASCII graph of branches
git log --oneline -10          # last 10 commits
git log --author="name"        # commits by author
git log --since="2 weeks ago"
git log --grep="login"         # commits with 'login' in message
git log -- src/LoginPage.java  # commits that changed a file

# View changes
git diff                       # working dir vs staging
git diff --staged              # staging vs last commit
git diff HEAD~1                # working dir vs 1 commit ago
git diff main..feature-branch  # diff between branches
git show abc1234               # show a specific commit's diff
```

---

## 3. Branching & Merging

### Branches

```bash
# List branches
git branch                     # local branches
git branch -r                  # remote branches
git branch -a                  # all (local + remote)
git branch -v                  # with last commit info

# Create branch
git branch feature/login-tests         # create (stay on current)
git checkout -b feature/login-tests    # create + switch
git switch -c feature/login-tests      # modern syntax (Git 2.23+)

# Switch branch
git checkout main
git switch main                        # modern syntax

# Rename branch
git branch -m old-name new-name
git branch -m new-name                 # rename current branch

# Delete branch
git branch -d feature/login-tests      # safe delete (merged only)
git branch -D feature/login-tests      # force delete (even if unmerged)
git push origin --delete feature/login-tests  # delete remote branch
```

### Merge

```bash
# Merge feature branch into main
git checkout main
git merge feature/login-tests          # creates merge commit
git merge --ff-only feature/branch     # fast-forward only (fails if not possible)
git merge --no-ff feature/branch       # always create merge commit (preserves history)
git merge --squash feature/branch      # squash all commits into one, then commit manually

# Abort merge on conflict
git merge --abort
```

### Rebase

```bash
# Rebase feature onto main (replay commits on top of main)
git checkout feature/login-tests
git rebase main                        # move feature commits on top of latest main

# Interactive rebase — rewrite history
git rebase -i HEAD~3                   # last 3 commits
# Options in interactive mode:
# pick   — keep commit as-is
# reword — keep changes, edit message
# squash — combine with previous commit
# fixup  — squash, discard commit message
# drop   — remove commit entirely

git rebase --abort                     # abort rebase
git rebase --continue                  # continue after resolving conflict
```

### Resolving Merge Conflicts

```
<<<<<<< HEAD
WebDriver driver = new ChromeDriver();    ← your change
=======
WebDriver driver = new FirefoxDriver();  ← incoming change
>>>>>>> feature/browser-switch
```

1. Open conflicted file
2. Keep the correct version (or combine both)
3. Remove `<<<<<<<`, `=======`, `>>>>>>>` markers
4. `git add <file>`
5. `git commit` (for merge) or `git rebase --continue` (for rebase)

---

## 4. Remote Operations

```bash
# View remotes
git remote -v                  # list remotes with URLs
git remote show origin         # detailed info about remote

# Add/change remote
git remote add origin https://github.com/user/repo.git
git remote set-url origin <new-url>
git remote rename origin upstream
git remote remove origin

# Fetch — download changes, don't merge
git fetch origin               # fetch all branches
git fetch origin main          # fetch specific branch

# Pull — fetch + merge (or fetch + rebase)
git pull                       # fetch + merge current branch
git pull origin main           # fetch + merge specific branch
git pull --rebase              # fetch + rebase (cleaner history)

# Push
git push                       # push current branch to its upstream
git push origin main           # push to specific remote branch
git push -u origin feature/login-tests  # push + set upstream tracking
git push --force-with-lease    # safe force push (fails if remote changed)
git push --tags                # push all tags
git push origin :feature/old   # delete remote branch (old syntax)
git push origin --delete feature/old   # delete remote branch
```

---

## 5. Undoing Changes

```bash
# Discard uncommitted working directory changes
git checkout -- file.java      # restore file from last commit
git restore file.java          # modern syntax
git restore .                  # restore all files

# Unstage (keep changes in working dir)
git reset HEAD file.java       # unstage specific file
git restore --staged file.java # modern syntax

# Undo commits (local — not pushed)
git reset --soft HEAD~1        # undo last commit, keep changes staged
git reset --mixed HEAD~1       # undo last commit, keep changes unstaged (default)
git reset --hard HEAD~1        # undo last commit, DISCARD changes (dangerous!)
git reset --hard abc1234       # reset to specific commit

# Undo commits (safe — for pushed commits)
git revert HEAD                # create new commit that undoes last commit
git revert abc1234             # create new commit that undoes specific commit
git revert HEAD~3..HEAD        # revert last 3 commits

# Recover deleted branch or lost commit
git reflog                     # log of all HEAD movements
git checkout -b recovered abc1234  # recreate branch from reflog entry
```

### Reset vs Revert

| | `git reset` | `git revert` |
|--|------------|-------------|
| History | Rewrites history | Preserves history |
| Safe for pushed? | ❌ (rewrites shared history) | ✅ |
| Creates new commit? | No | Yes |
| Use case | Local cleanup | Undoing pushed commits |

---

## 6. Stash & Tags

### Stash — Save work temporarily

```bash
git stash                      # stash all changes (tracked files)
git stash push -u              # stash including untracked files
git stash push -m "WIP: login fix"   # stash with message

git stash list                 # see all stashes
git stash show                 # show files changed in latest stash
git stash show -p              # show diff of latest stash

git stash pop                  # apply latest stash + remove from stash list
git stash apply                # apply latest stash, KEEP in stash list
git stash apply stash@{2}      # apply specific stash

git stash drop stash@{0}       # delete specific stash
git stash clear                # delete ALL stashes
```

### Tags — Mark release points

```bash
git tag                        # list tags
git tag v1.0.0                 # lightweight tag
git tag -a v1.0.0 -m "Release 1.0.0"  # annotated tag (recommended)
git tag -a v0.9.0 abc1234      # tag a specific commit

git show v1.0.0                # show tag details
git push origin v1.0.0         # push specific tag
git push origin --tags         # push all tags
git tag -d v0.9.0              # delete local tag
git push origin --delete v0.9.0  # delete remote tag

git checkout v1.0.0            # detached HEAD at tag
```

---

## 7. Git Workflows & Branching Strategies

### GitFlow

```
main          ─────●──────────────────────●───────────── (production)
                   │                      │
develop       ─────●──────●──────●──────●─────────────── (integration)
                   │      │      │      │
feature/x     ─────●──────●      │      │                (feature branches)
feature/y                  ───●──●      │
release/1.0                            ─●──●             (release prep)
hotfix/bug                                  ─●──●         (emergency fix)
```

| Branch | Purpose | Merges from | Merges into |
|--------|---------|-------------|-------------|
| `main` | Production | `release`, `hotfix` | — |
| `develop` | Integration | `feature/*` | `release` |
| `feature/*` | New feature | `develop` | `develop` |
| `release/*` | Release prep | `develop` | `main` + `develop` |
| `hotfix/*` | Emergency fix | `main` | `main` + `develop` |

### GitHub Flow (Simpler)

```
main  ─────●──────────────────●──────── (always deployable)
           │                  │
feature    ─────●──●──●──PR──●          (short-lived feature branch)
```

1. Branch off `main`
2. Commit to feature branch
3. Open Pull Request
4. Review + discuss
5. Merge to `main`
6. Deploy

### Trunk-Based Development

All developers commit directly to `main` (or short-lived feature branches < 2 days). Requires feature flags. Used by Google, Facebook.

---

## 8. GitHub Actions

### Basic Workflow Syntax

```yaml
# .github/workflows/selenium-tests.yml

name: Selenium Tests CI

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
  schedule:
    - cron: '0 6 * * *'        # run daily at 6 AM UTC
  workflow_dispatch:             # manual trigger

jobs:
  test:
    runs-on: ubuntu-latest      # or windows-latest, macos-latest

    strategy:
      matrix:
        browser: [chrome, firefox]   # run for each browser
        java: [11, 17]

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK ${{ matrix.java }}
        uses: actions/setup-java@v4
        with:
          java-version: ${{ matrix.java }}
          distribution: 'temurin'

      - name: Cache Maven packages
        uses: actions/cache@v3
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}

      - name: Run Tests
        run: mvn clean test -DbrowserName=${{ matrix.browser }} -DisHeadless=true
        env:
          BASE_URL: ${{ secrets.BASE_URL }}
          DB_PASSWORD: ${{ secrets.DB_PASSWORD }}

      - name: Upload Test Report
        uses: actions/upload-artifact@v3
        if: always()              # upload even if tests fail
        with:
          name: test-report-${{ matrix.browser }}
          path: Reports/

      - name: Publish TestNG Results
        uses: dorny/test-reporter@v1
        if: always()
        with:
          name: TestNG Results (${{ matrix.browser }})
          path: target/surefire-reports/*.xml
          reporter: java-junit
```

### GitHub Actions Concepts

| Concept | Description |
|---------|-------------|
| **Workflow** | YAML file in `.github/workflows/` |
| **Job** | Set of steps running on one runner |
| **Step** | Individual task (run command or use action) |
| **Action** | Reusable unit (`uses:`) from Marketplace or custom |
| **Runner** | Machine executing the job (`ubuntu-latest`) |
| **Event trigger** | `push`, `pull_request`, `schedule`, `workflow_dispatch` |
| **Secret** | Encrypted env variable (`${{ secrets.MY_SECRET }}`) |
| **Artifact** | Files persisted after job (reports, screenshots) |
| **Matrix** | Run job for each combination of variables |
| **Environment** | Named deployment target with protection rules |

### Common Actions

```yaml
uses: actions/checkout@v4               # checkout code
uses: actions/setup-java@v4             # install JDK
uses: actions/cache@v3                  # cache dependencies
uses: actions/upload-artifact@v3        # save files
uses: actions/download-artifact@v3      # retrieve saved files
uses: docker/login-action@v3            # login to Docker registry
uses: aws-actions/configure-aws-credentials@v4  # AWS auth
```

---

## 9. Jenkins

### Pipeline Types

**Declarative Pipeline** (recommended):
```groovy
pipeline {
    agent { label 'selenium-node' }

    tools {
        maven 'Maven-3.9'
        jdk   'JDK-17'
    }

    parameters {
        choice(name: 'BROWSER',      choices: ['chrome','firefox'], description: 'Browser')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Headless mode?')
        string(name: 'SUITE_FILE', defaultValue: 'testng.xml', description: 'TestNG suite')
    }

    environment {
        BASE_URL     = credentials('base-url-credential')
        DB_PASSWORD  = credentials('db-password')
    }

    triggers {
        cron('H 6 * * 1-5')   // 6 AM on weekdays
        pollSCM('H/5 * * * *') // poll every 5 minutes
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/avdhutssh/UI-Automation_Selenium.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh """
                    mvn test \
                        -DsuiteXmlFile=${params.SUITE_FILE} \
                        -DbrowserName=${params.BROWSER} \
                        -DisHeadless=${params.HEADLESS}
                """
            }
        }

        stage('Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'Reports',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Test Report'
                ])
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'ScreenShot/**', allowEmptyArchive: true
        }
        failure {
            emailext(
                to: 'team@example.com',
                subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Check: ${env.BUILD_URL}"
            )
        }
        success {
            echo 'All tests passed!'
        }
    }
}
```

### Jenkins Key Concepts

| Concept | Description |
|---------|-------------|
| **Job/Project** | Unit of work (freestyle, pipeline) |
| **Build** | One execution of a job |
| **Stage** | Logical section of a pipeline |
| **Step** | Single task within a stage |
| **Agent** | Machine running the pipeline (`agent any`) |
| **Node** | Jenkins worker machine |
| **Credentials** | Secured secrets (passwords, tokens) |
| `post` | Always/success/failure/unstable blocks |
| **Workspace** | Directory where job files live |
| **Artifact** | Files persisted after build |
| **Upstream/Downstream** | Triggering relationships between jobs |

---

## 10. Maven Build Tool

### Project Structure

```
project/
├── pom.xml
└── src/
    ├── main/java/           ← production code
    ├── main/resources/      ← production resources
    ├── test/java/           ← test code
    └── test/resources/      ← test resources (config.properties, testng.xml)
```

### Key `pom.xml` Sections

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.swag.labs</groupId>
    <artifactId>UI-Automation_Selenium</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <selenium.version>4.15.0</selenium.version>
        <testng.version>7.8.0</testng.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>${selenium.version}</version>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>${testng.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.1.2</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>${suiteXmlFile}</suiteXmlFile>
                    </suiteXmlFiles>
                    <systemPropertyVariables>
                        <browser>${browserName}</browser>
                        <isHeadless>${isHeadless}</isHeadless>
                    </systemPropertyVariables>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### Maven Lifecycle & Commands

```bash
# Default lifecycle phases (in order):
# validate → compile → test → package → verify → install → deploy

mvn validate               # check pom.xml is valid
mvn compile                # compile src/main/java
mvn test                   # run tests in src/test/java
mvn package                # compile + test + create JAR/WAR
mvn verify                 # run integration tests
mvn install                # install artifact to local ~/.m2 repo
mvn deploy                 # push artifact to remote repository
mvn clean                  # delete target/ folder

# Common combinations
mvn clean test                                    # clean then run tests
mvn clean test -DsuiteXmlFile=smoke-testng.xml    # specific test suite
mvn clean test -DbrowserName=firefox -DisHeadless=true
mvn clean install -DskipTests                     # build without tests
mvn dependency:tree                               # show dependency tree
mvn dependency:resolve                            # download all dependencies
mvn versions:display-dependency-updates           # check for newer versions
```

### Maven Scopes

| Scope | Compile? | Test? | Runtime? | Packaged? |
|-------|---------|-------|---------|---------|
| `compile` (default) | ✅ | ✅ | ✅ | ✅ |
| `test` | ❌ | ✅ | ❌ | ❌ |
| `provided` | ✅ | ✅ | ❌ | ❌ |
| `runtime` | ❌ | ✅ | ✅ | ✅ |
| `optional` | ✅ | ✅ | ✅ | ❌ |

---

## 11. Interview Q&A

**Q: What is the difference between `git merge` and `git rebase`?**
> `merge` creates a new merge commit that combines two branches. Preserves the true history of when branches diverged and merged.
> `rebase` replays your commits on top of another branch. Creates a linear history (no merge commits) but rewrites commit hashes. Rule: never rebase pushed (shared) commits — it rewrites history that others have.

**Q: What is the difference between `git fetch` and `git pull`?**
> `fetch` downloads changes from remote but does NOT merge them. Your local branches are unchanged. Safe — you can review before merging.
> `pull` = `fetch` + `merge` (or `fetch` + `rebase` with `--rebase`). Changes are immediately applied to your current branch.

**Q: What is `git stash`?**
> Temporarily saves (stashes) uncommitted changes so you can switch branches with a clean working directory. `git stash pop` restores and removes; `git stash apply` restores but keeps in stash.

**Q: What is HEAD in Git?**
> HEAD is a pointer to the current commit/branch. Usually points to the tip of the current branch. "Detached HEAD" occurs when you checkout a specific commit (not a branch) — commits made won't belong to any branch.

**Q: How do you undo a pushed commit?**
> Use `git revert` — it creates a new commit that reverses the changes. This preserves history and is safe for shared branches. Don't use `git reset --hard` + force push on shared branches — it rewrites history that others have cloned.

**Q: What is a Pull Request (PR)?**
> A request to merge your feature branch into another branch (usually `main`/`develop`). It provides a code review mechanism — team members can comment, request changes, and approve. Only after approval (and passing CI checks) is the branch merged.

**Q: What is GitFlow? When would you use GitHub Flow instead?**
> GitFlow is appropriate for projects with scheduled releases and multiple environments (staging/production). It has `develop`, `release`, and `hotfix` branches.
> GitHub Flow is simpler — only `main` and short-lived feature branches. Good for continuous deployment where every merged PR is deployed. Simpler, less overhead, works well for web apps.

**Q: What does `git clean` do?**
> Removes untracked files and directories.
> `git clean -n` — dry run (what would be deleted)
> `git clean -fd` — delete untracked files and directories

**Q: What is `cherry-pick`?**
> Apply a specific commit from one branch onto another, without merging the entire branch:
```bash
git checkout main
git cherry-pick abc1234   # apply commit abc1234 to current branch
```
> Useful for applying a hotfix to multiple branches.
