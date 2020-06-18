Install the Heroku CLI
Download and install the Heroku CLI.

If you haven't already, log in to your Heroku account and follow the prompts to create a new SSH public key.

$ heroku login
Clone the repository
Use Git to clone opolo-covid-tracker-app's source code to your local machine.

$ heroku git:clone -a opolo-covid-tracker-app
$ cd opolo-covid-tracker-app
Deploy your changes
Make some changes to the code you just cloned and deploy them to Heroku using Git.

$ git add .
$ git commit -am "make it better"
$ git push heroku master

Essential commands to deploy app to cloud:
git status,
git add . - track untracked files, stage them for commit
git commit -m "Commit message"
git push heroku master - pushes to cloud

Web address of the Heroku app: https://opolo-covid-tracker-app.herokuapp.com/
Git adress of this heroku app:  https://git.heroku.com/opolo-covid-tracker-app.git