#Contribution Guidelines

## Setting up the project

###Requirements
* Android Studio 2.2+ ( Recommended 2.2.3 or 2.3.0 beta 2 ( Canary Channel ) ) 

### Steps

1. Fork this repository.
2. Clone it to local system using `git clone https://github.com/<your_username>/ZapTap.git`.
3. Checkout development Branch using `git checkout development`
4. Select " Import Project (Eclipse, Gradle, ADT etc) " from Android Studio project selection screen.
5. After successful gradle build, open terminal from Android Studio and run
  * macOS/ Linux: `./gradlew desktop:run`
  * Windows: `gradlew.bat desktop:run`
6. If it runs successfully on Desktop , try running on Android from Studio.

### Note

1. First build of the project will take a lot of time. Keep patience. 
2. Don't try to build master branch. Your build will fail due to lack of many confidential files for Google Play Games which have not been added here.

## Creating an Issue

1. You may create issues for bugs, new features, improvements to current featured.
2. Include LogCat, Relevant Screenshots for Bugs, ANRs and Force Stops. 
3. Avoid Creating minor issues.

## Solving an Issue.

1. Before starting solving an issue, claim it to avoid multiple PRs for same issue.
2. Only single Pull Request per issue and single commit per Pull Request will be accepted. 
3. Send Pull Request to only development branch. PRs to master branch will be ignored.

